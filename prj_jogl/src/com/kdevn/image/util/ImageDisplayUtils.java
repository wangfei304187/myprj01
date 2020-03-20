/**
 * Copyright 2015 Bowing Medical Technologies Co., Ltd.
 */
package com.kdevn.image.util;

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

    public BufferedImage createImage(int w, int h, byte[] inputPixels8, double rescaleSlope, double rescaleIntercept,
            short[] overlayData, boolean invert)
    {
        byte[] pixels8 = create8BitImage(w, h, inputPixels8, rescaleSlope, rescaleIntercept, overlayData, "LINEAR");
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
    public byte[] create8BitImage(int w, int h, byte[] inputPixels8, double rescaleSlope, double rescaleIntercept,
            short[] overlayData, String functionType)
    {
        int size = w * h;
        if (pixels8 == null)
        {
            pixels8 = new byte[size];
        }
        if ("LINEAR".equals(functionType))
        {
            int y = 0;
            for (int i = 0; i < size; i++)
            {
                // --> 2016/06/18 Modified
                int s = PixelUtils.bytesLE2ushort(inputPixels8, i << 1);
                int x = PixelUtils.toHU(s, rescaleSlope, rescaleIntercept);
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
        else if ("LINEAR_EXACT".equals(functionType))
        {
            int y = 0;
            for (int i = 0; i < size; i++)
            {
                int s = PixelUtils.bytesLE2ushort(inputPixels8, i << 1);
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
                int s = PixelUtils.bytesLE2ushort(inputPixels8, i << 1);
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

}
