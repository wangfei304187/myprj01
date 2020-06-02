/**
 * Copyright 2015 Bowing Medical Technologies Co., Ltd.
 */
package com.my.images.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;

public class ImageDisplayUtils
{

    private byte[] pixels8 = null;
    private IndexColorModel defaultColorModel;
    private SampleModel sampleModel;

    private int ymin = 0;
    private int ymax = 255;
    private int wc;
    private int ww;

    public ImageDisplayUtils()
    {
    }

    public int getWC()
    {
        return wc;
    }

    public int getWW()
    {
        return ww;
    }

    public void setWC(int wc)
    {
        this.wc = wc;
    }

    public void setWW(int ww)
    {
        this.ww = ww;
    }

    // public BufferedImage createImage(int w, int h, short[] pixels, int rescaleSlope, int rescaleIntercept,
    // short[] overlayData, boolean invert)
    private BufferedImage createImage(int w, int h, short[] pixels, double rescaleSlope, double rescaleIntercept,
            short[] overlayData, boolean invert)
    {
        byte[] pixels8 = create8BitImage(w, h, pixels, rescaleSlope, rescaleIntercept, overlayData, Constants.LINEAR);
        if (invert)
        {
            for (int i = 0; i < pixels8.length; i++)
            {
                pixels8[i] = (byte) (0xFF - (pixels8[i] & 0xFF));
            }
        }
        return createBufferedImage(w, h, pixels8);
    }

    // ref: DOCIM part03.pdf [C.11.2.1.2 Window Center and Window Width]
    // private byte[] create8BitImage(int w, int h, short[] pixels, int rescaleSlope, int rescaleIntercept,
    // short[] overlayData, String functionType)
    private byte[] create8BitImage(int w, int h, short[] pixels, double rescaleSlope, double rescaleIntercept,
            short[] overlayData, String functionType)
    {
        int size = w * h;
        if (pixels8 == null)
        {
            pixels8 = new byte[size];
        }
        if (Constants.LINEAR.equals(functionType))
        {
            int y = 0;
            for (int i = 0; i < size; i++)
            {
                // --> 2016/06/18 Modified
                // int x = pixels[i] * rescaleSlope + rescaleIntercept;
                int x = PixelUtils.toHU(pixels[i], rescaleSlope, rescaleIntercept);
                // <--
                if (x <= wc - 0.5 - (ww - 1) / 2)
                {
                    y = ymin;
                }
                else if (x > wc - 0.5 + (ww - 1) / 2)
                {
                    y = ymax;
                }
                else
                {
                    if (ww != 1)
                    {
                        y = (int) (((x - (wc - 0.5)) / (ww - 1) + 0.5) * (ymax - ymin) + ymin + 0.5);
                    }
                    else
                    {
                        y = 0;
                    }
                }
                pixels8[i] = (byte) y;
            }
        }
        else if (Constants.LINEAR_EXACT.equals(functionType))
        {
            int y = 0;
            for (int i = 0; i < size; i++)
            {
                // int x = pixels[i] * rescaleSlope + rescaleIntercept;
                int x = PixelUtils.toHU(pixels[i], rescaleSlope, rescaleIntercept);
                if (x <= wc - ww / 2)
                {
                    y = ymin;
                }
                else if (x > wc + ww / 2)
                {
                    y = ymax;
                }
                else
                {
                    if (ww != 0)
                    {
                        // y = (x - wc) / ww * (ymax - ymin) + ymin;
                        y = (int) ((x - wc) * 1.0 / ww * (ymax - ymin) + ymin + 0.5);
                    }
                }
                pixels8[i] = (byte) y;
            }
        }
        else
        {
            int y = 0;
            for (int i = 0; i < size; i++)
            {
                // int x = pixels[i] * rescaleSlope + rescaleIntercept;
                int x = PixelUtils.toHU(pixels[i], rescaleSlope, rescaleIntercept);
                y = (int) ((ymax - ymin) / (1 + Math.exp(-4 * (x - wc) * 1.0 / ww)) + 0.5);
                pixels8[i] = (byte) y;
            }
        }

        if (overlayData != null)
        {
            for (int i = 0; i < overlayData.length; i++)
            {
                short t = overlayData[i];
                for (int j = 0; j < 16; j++)
                {
                    int v = t >> j & 0x01;
                if (v == 1)
                {
                    pixels8[i * 16 + j] = (byte) 0xFF;
                }
                }
            }
        }

        return pixels8;
    }

    // <--2018/6/1 Added.
    public BufferedImage createImage(int w, int h, byte[] inputPixels8, double rescaleSlope, double rescaleIntercept,
            short[] overlayData, boolean invert, int pixelRepresentation)
    {
        byte[] pixels8 = create8BitImage(w, h, inputPixels8, rescaleSlope, rescaleIntercept, overlayData,
                Constants.LINEAR, pixelRepresentation);
        if (invert)
        {
            for (int i = 0; i < pixels8.length; i++)
            {
                pixels8[i] = (byte) (0xFF - (pixels8[i] & 0xFF));
            }
        }
        return createBufferedImage(w, h, pixels8);
    }

    // ref: DOCIM part03.pdf [C.11.2.1.2 Window Center and Window Width]
    private byte[] create8BitImage(int w, int h, byte[] inputPixels8, double rescaleSlope, double rescaleIntercept,
            short[] overlayData, String functionType, int pixelRepresentation)
    {
        int size = w * h;
        if (pixels8 == null)
        {
            pixels8 = new byte[size];
        }
        if (Constants.LINEAR.equals(functionType))
        {
            int y = 0;
            for (int i = 0; i < size; i++)
            {
                int s;
                if (pixelRepresentation == 0)
                {
                    s = PixelUtils.bytesLE2ushort(inputPixels8, i << 1);
                }
                else
                {
                    s = PixelUtils.bytesLE2sshort(inputPixels8, i << 1);
                }
                int x = PixelUtils.toHU(s, rescaleSlope, rescaleIntercept);

                if (x <= wc - 0.5 - (ww - 1) / 2)
                {
                    y = ymin;
                }
                else if (x > wc - 0.5 + (ww - 1) / 2)
                {
                    y = ymax;
                }
                else
                {
                    if (ww != 1)
                    {
                        y = (int) (((x - (wc - 0.5)) / (ww - 1) + 0.5) * (ymax - ymin) + ymin + 0.5);
                    }
                    else
                    {
                        y = 0;
                    }
                }
                pixels8[i] = (byte) y;
            }
        }
        else if (Constants.LINEAR_EXACT.equals(functionType))
        {
            int y = 0;
            for (int i = 0; i < size; i++)
            {
                int s;
                if (pixelRepresentation == 0)
                {
                    s = PixelUtils.bytesLE2ushort(inputPixels8, i << 1);
                }
                else
                {
                    s = PixelUtils.bytesLE2sshort(inputPixels8, i << 1);
                }
                int x = PixelUtils.toHU(s, rescaleSlope, rescaleIntercept);
                if (x <= wc - ww / 2)
                {
                    y = ymin;
                }
                else if (x > wc + ww / 2)
                {
                    y = ymax;
                }
                else
                {
                    if (ww != 0)
                    {
                        // y = (x - wc) / ww * (ymax - ymin) + ymin;
                        y = (int) ((x - wc) * 1.0 / ww * (ymax - ymin) + ymin + 0.5);
                    }
                }
                pixels8[i] = (byte) y;
            }
        }
        else
        {
            int y = 0;
            for (int i = 0; i < size; i++)
            {
                int s;
                if (pixelRepresentation == 0)
                {
                    s = PixelUtils.bytesLE2ushort(inputPixels8, i << 1);
                }
                else
                {
                    s = PixelUtils.bytesLE2sshort(inputPixels8, i << 1);
                }
                int x = PixelUtils.toHU(s, rescaleSlope, rescaleIntercept);
                y = (int) ((ymax - ymin) / (1 + Math.exp(-4 * (x - wc) * 1.0 / ww)) + 0.5);
                pixels8[i] = (byte) y;
            }
        }

        if (overlayData != null)
        {
            for (int i = 0; i < overlayData.length; i++)
            {
                short t = overlayData[i];
                for (int j = 0; j < 16; j++)
                {
                    int v = t >> j & 0x01;
                if (v == 1)
                {
                    pixels8[i * 16 + j] = (byte) 0xFF;
                }
                }
            }
        }

        return pixels8;
    }

    // <--

    public BufferedImage createBufferedImage(int w, int h, byte[] pixels8)
    {
        SampleModel sm = getIndexSampleModel(w, h);
        DataBuffer db = new DataBufferByte(pixels8, w * h, 0);
        WritableRaster raster = Raster.createWritableRaster(sm, db, null);
        ColorModel cm = getDefaultColorModel();
        BufferedImage image = new BufferedImage(cm, raster, false, null);
        return image;
    }

    public SampleModel getIndexSampleModel(int w, int h)
    {
        if (sampleModel == null)
        {
            IndexColorModel icm = getDefaultColorModel();
            WritableRaster wr = icm.createCompatibleWritableRaster(1, 1);
            sampleModel = wr.getSampleModel();
            sampleModel = sampleModel.createCompatibleSampleModel(w, h);
        }
        return sampleModel;
    }

    public IndexColorModel getDefaultColorModel()
    {
        if (defaultColorModel == null)
        {
            byte[] r = new byte[256];
            byte[] g = new byte[256];
            byte[] b = new byte[256];
            for (int i = 0; i < 256; i++)
            {
                r[i] = (byte) i;
                g[i] = (byte) i;
                b[i] = (byte) i;
            }
            defaultColorModel = new IndexColorModel(8, 256, r, g, b);
        }
        return defaultColorModel;
    }

    public static short[] read16bitImage(InputStream is, int w, int h) throws IOException
    {
        boolean intelByteOrder = true;

        int bytesPerPixel = 2;
        int byteCount = w * h * bytesPerPixel;
        int nPixels = w * h;
        int bufferSize = 8192;

        int pixelsRead;
        byte[] buffer = new byte[bufferSize];
        short[] pixels = new short[nPixels];
        int totalRead = 0;
        int base = 0;
        int count;
        int bufferCount;

        while (totalRead < byteCount)
        {
            if (totalRead + bufferSize > byteCount)
            {
                bufferSize = byteCount - totalRead;
            }
            bufferCount = 0;
            while (bufferCount < bufferSize)
            { // fill the buffer
                count = is.read(buffer, bufferCount, bufferSize - bufferCount);
                if (count == -1)
                {
                    if (bufferCount > 0)
                    {
                        for (int i = bufferCount; i < bufferSize; i++)
                        {
                            buffer[i] = 0;
                        }
                    }
                    totalRead = byteCount;
                    break;
                }
                bufferCount += count;
            }
            totalRead += bufferSize;
            pixelsRead = bufferSize / bytesPerPixel;
            if (intelByteOrder)
            {
                for (int i = base, j = 0; i < base + pixelsRead; i++, j += 2)
                {
                    pixels[i] = (short) ((buffer[j + 1] & 0xff) << 8 | buffer[j] & 0xff);
                }
            }
            else
            {
                for (int i = base, j = 0; i < base + pixelsRead; i++, j += 2)
                {
                    pixels[i] = (short) ((buffer[j] & 0xff) << 8 | buffer[j + 1] & 0xff);
                }
            }
            base += pixelsRead;
        }
        return pixels;
    }

    // ========================================================================================

    // 1 -- BYTE GRAY
    // 2 -- TYPE_INT_BGR, color overlay
    // 3 -- TYPE_USHORT_GRAY
    private int SCHEMA = 2;

    // inputPixels8 size: w * h * 3
    public BufferedImage createImageRGB(int w, int h, byte[] inputPixels8, double rescaleSlope,
            double rescaleIntercept, short[] overlayData, int overlayWidth, int overlayHeight,
            boolean invert, int pixelRepresentation, int samplesPerPixel,
            int bitsAllocated, boolean isEncapsulated)
    {
        byte[] pixels8 = create8BitImageRGB(w, h, inputPixels8, rescaleSlope, rescaleIntercept,
                Constants.LINEAR, pixelRepresentation, samplesPerPixel, bitsAllocated);

        return createBufferedImageRGB(w, h, pixels8, overlayData, overlayWidth, overlayHeight,
                invert, samplesPerPixel, bitsAllocated, isEncapsulated);
    }

    private byte[] create8BitImageRGB(int w, int h, byte[] inputPixels8, double rescaleSlope, double rescaleIntercept,
            String functionType, int pixelRepresentation, int samplesPerPixel, int bitsAllocated)
    {
        int bytesPerSample = bitsAllocated / 8; // 8 / 8 => 1; 16 / 8 => 2
        int bytesPerPixel = samplesPerPixel * bytesPerSample;

        int size;
        if (samplesPerPixel == 3)
        {
            size = w * h * samplesPerPixel; // samplesPerPixel: 3, bitsAllocated: 8
        }
        else if (samplesPerPixel == 1)
        {
            if (SCHEMA == 1) // samplesPerPixel: 1, bitsAllocated: 16
            {
                size = w * h;
            }
            else if (SCHEMA == 2) // samplesPerPixel: 1, bitsAllocated: 16
            {
                size = w * h * samplesPerPixel;
            }
            else
            { // SCHEMA == 3
                size = w * h * bytesPerPixel; // samplesPerPixel: 1, bitsAllocated: 16
            }
        }
        else
        {
            throw new IllegalArgumentException("samplesPerPixel=" + samplesPerPixel);
        }

        if (pixels8 == null)
        {
            pixels8 = new byte[size];
        }

        int k = 0;
        int y = 0;
        for (int i = 0; i < inputPixels8.length; i = i + bytesPerPixel)
        {
            if (k > size - 1)
            {
                break;
            }

            int s;
            if (samplesPerPixel == 3)
            {
                // samplesPerPixel: 3, bitsAllocated: 8, bytesPerSample: 1
                if (i + 2 > inputPixels8.length - 1)
                {
                    // ignore redundant bytes, inputPixels8.length != inputPixels8.length / 3 * 3
                    break;
                }

                // s = PixelUtils.bytesLE2int(inputPixels8, i); // 3 bytes => int
                // int x = PixelUtils.toHU(s, rescaleSlope, rescaleIntercept); // rescaleIntercept should be: 0
                // pixels8[k++] = (byte) applyWindow(x & 0xFF, functionType); // R
                // pixels8[k++] = (byte) applyWindow(x >> 8 & 0xFF, functionType); // G
                // pixels8[k++] = (byte) applyWindow(x >> 16 & 0xFF, functionType); // B

                // ignore rescaleSlope, rescaleIntercept
                if (pixelRepresentation == 0)
                {
                    pixels8[k++] = (byte) applyWindow(inputPixels8[i] & 0xFF, functionType); // R
                    pixels8[k++] = (byte) applyWindow(inputPixels8[i + 1] & 0xFF, functionType); // G
                    pixels8[k++] = (byte) applyWindow(inputPixels8[i + 2] & 0xFF, functionType); // B
                }
                else
                {
                    pixels8[k++] = (byte) applyWindow(inputPixels8[i], functionType); // R
                    pixels8[k++] = (byte) applyWindow(inputPixels8[i + 1], functionType); // G
                    pixels8[k++] = (byte) applyWindow(inputPixels8[i + 2], functionType); // B
                }
            }
            else if (samplesPerPixel == 1)
            {
                if (bytesPerSample == 2) // samplesPerPixel: 1, bitsAllocated: 16
                {
                    if (pixelRepresentation == 0)
                    {
                        s = PixelUtils.bytesLE2ushort(inputPixels8, i);
                    }
                    else
                    {
                        s = PixelUtils.bytesLE2sshort(inputPixels8, i);
                    }
                    int x = PixelUtils.toHU(s, rescaleSlope, rescaleIntercept);

                    if (SCHEMA == 1)
                    {
                        y = applyWindow(x, functionType);
                        pixels8[k++] = (byte) y; // size = w * h
                    }
                    else if (SCHEMA == 2)
                    {
                        y = applyWindow(x, functionType);
                        pixels8[k++] = (byte) y; // size = w * h * samplesPerPixel
                    }
                    else
                    { // SCHEMA == 3
                        y = applyWindow(x, functionType);
                        pixels8[k++] = (byte) (y & 0xFF);
                        pixels8[k++] = (byte) (y >> 8 & 0xFF); // size = w * h * bytesPerPixel
                    }
                }
                else if (bytesPerSample == 1) // samplesPerPixel: 1, bitsAllocated: 8
                {
                    if (pixelRepresentation == 0)
                    {
                        s = inputPixels8[i] & 0xFF;
                    }
                    else
                    {
                        s = inputPixels8[i];
                    }
                    int x = PixelUtils.toHU(s, rescaleSlope, rescaleIntercept);

                    y = applyWindow(x, functionType);
                    pixels8[k++] = (byte) y;
                }
                else
                {
                    throw new IllegalArgumentException("bitsAllocated=" + bitsAllocated + ", *bytesPerSample="
                            + bytesPerSample);
                }
            }
            else
            {
                throw new IllegalArgumentException("samplesPerPixel=" + samplesPerPixel);
            }
        }

        // move to createBufferedImageRGB(..)
        //        /*@formatter:off*/
        //        if (overlayData != null)
        //        {
        //            for (int i = 0; i < overlayData.length; i++)
        //            {
        //                short t = overlayData[i];
        //                for (int j = 0; j < 16; j++)
        //                {
        //                    int v = t >> j & 0x01;
        //                if (v == 1)
        //                {
        //                    if (samplesPerPixel == 3)
        //                    {
        //                        // bytesPerSample: 1
        //                        pixels8[i * 16 * bytesPerPixel + j * bytesPerPixel] = (byte) 0xFF;
        //                        pixels8[i * 16 * bytesPerPixel + j * bytesPerPixel + 1] = (byte) 0xFF;
        //                        pixels8[i * 16 * bytesPerPixel + j * bytesPerPixel + 2] = (byte) 0x00;
        //                    }
        //                    else if (samplesPerPixel == 1)
        //                    {
        //                        // pixels8[i * 16 + j] = (byte) 0xFF;
        //                        if (bytesPerSample == 2)
        //                        {
        //                            // pixels8[i * 16 * bytesPerPixel + j * bytesPerPixel] = (byte) 0x00;
        //                            // pixels8[i * 16 * bytesPerPixel + j * bytesPerPixel + 1] = (byte) 0x00;
        //                            pixels8[i * 16 + j] = (byte) 0xFF;
        //                        }
        //                        else if (bytesPerSample == 1)
        //                        {
        //                            pixels8[i * 16 + j] = (byte) 0xFF;
        //                        }
        //                    }
        //                }
        //                }// end for
        //            } // end for
        //        }
        //        /*@formatter:on*/

        return pixels8;
    }

    /*@formatter:off*/
    public BufferedImage createBufferedImageRGB(int w, int h, byte[] pixels8, short[] overlayData, int overlayWidth, int overlayHeight,
            boolean invert, int samplesPerPixel, int bitsAllocated, boolean isEncapsulated)
    {
        int bytesPerSample = bitsAllocated / 8; // 8 / 8 => 1; 16 / 8 => 2
        // int bytesPerPixel = samplesPerPixel * bytesPerSample;

        BufferedImage image;
        int imageType;
        if (samplesPerPixel == 3)  // bitsAllocated: 8, bytesPerSample: 1
        {
            //            if (isEncapsulated)
            //            {
            //                imageType = BufferedImage.TYPE_3BYTE_BGR; // pixels8.length is : w * h * 3
            //            }
            //            else
            //            {
            //                imageType = BufferedImage.TYPE_INT_RGB;
            //            }
            imageType = BufferedImage.TYPE_INT_RGB;
            image = new BufferedImage(w, h, imageType);
            int k = 0;
            for (int j = 0; j < h; j++)
            {
                for (int i = 0; i < w; i++)
                {
                    byte v1 = applyInvert(pixels8[k++], invert);
                    byte v2 = applyInvert(pixels8[k++], invert);
                    byte v3 = applyInvert(pixels8[k++], invert);
                    int rgb;
                    if (isEncapsulated)
                    {
                        rgb = (v1 & 0xFF) + (v2 << 8 & 0xFFFF) + (v3 << 16 & 0xFFFFFF);
                    }
                    else
                    {
                        rgb = (v1 << 16 & 0xFFFFFF) + (v2 << 8 & 0xFFFF) + (v3 & 0xFF);
                    }
                    image.setRGB(i, j, rgb);
                }
            }
        }
        else if (samplesPerPixel == 1)
        {
            if (bytesPerSample == 2)  // bitsAllocated: 16
            {
                if (SCHEMA == 1)
                {
                    // Schema 1 -- OK.  should set 'size = w * h * samplesPerPixel' in create8BitImageRGB(..)
                    if (invert)
                    {
                        for (int i = 0; i < pixels8.length; i++)
                        {
                            pixels8[i] = (byte) (0xFF - (pixels8[i] & 0xFF));
                        }
                    }
                    image = createBufferedImage(w, h, pixels8);
                }
                else if (SCHEMA == 2)
                {
                    // Schema 2 -- OK.  should set 'size = w * h * samplesPerPixel' in create8BitImageRGB(..)
                    // imageType = BufferedImage.TYPE_BYTE_GRAY;
                    imageType = BufferedImage.TYPE_INT_RGB; // BufferedImage.TYPE_INT_BGR
                    image = new BufferedImage(w, h, imageType);
                    int k = 0;
                    for (int j = 0; j < h; j++)
                    {
                        for (int i = 0; i < w; i++)
                        {
                            byte v = applyInvert(pixels8[k], invert);
                            int rgb = (v & 0xFF) + (v << 8 & 0xFFFF) + (v << 16 & 0xFFFFFF);
                            k++;
                            image.setRGB(i, j, rgb);
                        }
                    }
                }
                else
                { // SCHEMA == 3
                    // Schema 3 -- OK.  should set 'size = w * h * bytesPerPixel' in create8BitImageRGB(..)
                    // imageType = BufferedImage.TYPE_BYTE_GRAY;
                    imageType = BufferedImage.TYPE_USHORT_GRAY;
                    image = new BufferedImage(w, h, imageType);
                    int k = 0;
                    for (int j = 0; j < h; j++)
                    {
                        for (int i = 0; i < w; i++)
                        {
                            short s = (short)((pixels8[k++] & 0xFF) + (pixels8[k++] << 8 & 0xFFFF));
                            s = applyInvert(s, invert); // ??
                            int rgb = (s & 0xFF) + (s << 8 & 0xFFFF) + (s << 16 & 0xFFFFFF);
                            image.setRGB(i, j, rgb);
                        }
                    }
                }
            }
            else if (bytesPerSample == 1)  // bitsAllocated: 8
            {
                if (invert)
                {
                    for (int i = 0; i < pixels8.length; i++)
                    {
                        pixels8[i] = (byte) (0xFF - (pixels8[i] & 0xFF));
                    }
                }
                image = createBufferedImage(w, h, pixels8);
            }
            else
            {
                throw new IllegalArgumentException("samplesPerPixel=" + samplesPerPixel + ", bitsAllocated="
                        + bitsAllocated + ", *bytesPerSample=" + bytesPerSample);
            }
        }
        else
        {
            throw new IllegalArgumentException("samplesPerPixel=" + samplesPerPixel);
        }

        if (overlayData != null)
        {
            for (int i = 0; i < overlayData.length; i++)
            {
                short t = overlayData[i];
                for (int j = 0; j < 16; j++)
                {
                    int v = t >> j & 0x01;
                if (v == 1)
                {
                    // int rowIdx = (i * 16 + j) / w;
                    // int colIdx = (i * 16 + j) % w;
                    int rowIdx = (i * 16 + j) / overlayWidth;
                    int colIdx = (i * 16 + j) % overlayWidth;
                    if (rowIdx <= h-1 && colIdx <= w-1)
                    {
                        image.setRGB(colIdx, rowIdx, applyInvert(Color.yellow.getRGB(), invert));
                    }
                }
                }// end for
            } // end for
        }

        return image;
    }
    /* @formatter:on */

    private int applyInvert(int v, boolean invert) // samplesPerPixel: 3
    {
        // return invert ? 0xFFFFFF - v : v;
        if (invert)
        {
            byte a1 = (byte) (0xFF - (v >> 16 & 0xFF));
            byte a2 = (byte) (0xFF - (v >> 8 & 0xFF));
            byte a3 = (byte) (0xFF - (v & 0xFF));
            return (a1 << 16 & 0xFFFFFF) + (a2 << 8 & 0xFFFF) + (a3 & 0xFF);
        }
        else
        {
            return v;
        }
    }

    private short applyInvert(short s, boolean invert) // samplesPerPixel: 1
    {
        return (short) (invert ? 0xFFFF - (s & 0xFFFF) : s);
    }

    private byte applyInvert(byte b, boolean invert)
    {
        return (byte) (invert ? 0xFF - (b & 0xFF) : b);
    }

    private int applyWindow(int x, String functionType)
    {
        int y = 0;
        if (Constants.LINEAR.equals(functionType))
        {
            if (x <= wc - 0.5 - (ww - 1) / 2)
            {
                y = ymin;
            }
            else if (x > wc - 0.5 + (ww - 1) / 2)
            {
                y = ymax;
            }
            else
            {
                if (ww != 1)
                {
                    y = (int) (((x - (wc - 0.5)) / (ww - 1) + 0.5) * (ymax - ymin) + ymin + 0.5);
                }
                else
                {
                    y = 0;
                }
            }
        }
        else if (Constants.LINEAR_EXACT.equals(functionType))
        {
            if (x <= wc - ww / 2)
            {
                y = ymin;
            }
            else if (x > wc + ww / 2)
            {
                y = ymax;
            }
            else
            {
                if (ww != 0)
                {
                    // y = (x - wc) / ww * (ymax - ymin) + ymin;
                    y = (int) ((x - wc) * 1.0 / ww * (ymax - ymin) + ymin + 0.5);
                }
            }
        }
        else
        {
            y = (int) ((ymax - ymin) / (1 + Math.exp(-4 * (x - wc) * 1.0 / ww)) + 0.5);
        }

        return y;
    }
}
