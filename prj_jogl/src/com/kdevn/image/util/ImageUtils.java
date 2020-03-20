package com.kdevn.image.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageUtils
{
    public IndexColorModel getColorModel()
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
        return new IndexColorModel(8, 256, r, g, b);
    }

    protected SampleModel getIndexSampleModel(IndexColorModel icm, int width, int height)
    {
        WritableRaster wr = icm.createCompatibleWritableRaster(1, 1);
        SampleModel sm = wr.getSampleModel();
        SampleModel sampleModel = sm.createCompatibleSampleModel(width, height);
        return sampleModel;
    }

    public BufferedImage createBufferedImage(int width, int height, byte[] pixels)
    {
        ColorModel cm = getColorModel();
        SampleModel sm = getIndexSampleModel((IndexColorModel) cm, width, height);
        DataBuffer db = new DataBufferByte(pixels, width * height, 0);
        WritableRaster raster = Raster.createWritableRaster(sm, db, null);
        BufferedImage image = new BufferedImage(cm, raster, false, null);
        return image;
    }

    public BufferedImage createBufferedImage(BufferedImage src, int destWidth, int destHeight)
    {
        ColorModel cm = src.getColorModel();
        BufferedImage image =
                new BufferedImage(cm, cm.createCompatibleWritableRaster(destWidth, destHeight),
                        cm.isAlphaPremultiplied(), null);
        return image;
    }

    public int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels)
    {
        int type = image.getType();
        System.out.println("type: " + type);
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
        {
            return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
        }
        else
        {
            return image.getRGB(x, y, width, height, pixels, 0, width);
        }
    }

    public void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels)
    {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
        {
            image.getRaster().setDataElements(x, y, width, height, pixels);
        }
        else
        {
            image.setRGB(x, y, width, height, pixels, 0, width);
        }
    }

    public BufferedImage readImageFile(File file)
    {
        try
        {
            BufferedImage image = ImageIO.read(file);
            return image;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage readImageFile(URL url)
    {
        try
        {
            BufferedImage image = ImageIO.read(url);
            return image;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void writeImageFile(BufferedImage bi, File outFile, String ext) throws IOException
    {
        ImageIO.write(bi, ext, outFile);
    }

    public int[] getOnePixel(int rIdx, int cIdx, int width, int height, int[] inPixels)
    {
        if (rIdx >= height)
        {
            rIdx = height - 1;
        }
        if (rIdx < 0)
        {
            rIdx = 0;
        }
        if (cIdx < 0)
        {
            cIdx = 0;
        }
        if (cIdx >= width)
        {
            cIdx = width - 1;
        }

        int index = rIdx * width + cIdx;
        int[] rgb = new int[3];
        rgb[0] = inPixels[index] >> 16 & 0xff;
        rgb[1] = inPixels[index] >> 8 & 0xff;
        rgb[2] = inPixels[index] & 0xff;
        return rgb;
    }

    public int getOnePixelInt(int rIdx, int cIdx, int width, int height, int[] inPixels)
    {
        if (rIdx >= height)
        {
            rIdx = height - 1;
        }
        if (rIdx < 0)
        {
            rIdx = 0;
        }
        if (cIdx < 0)
        {
            cIdx = 0;
        }
        if (cIdx >= width)
        {
            cIdx = width - 1;
        }

        int index = rIdx * width + cIdx;
        // int[] rgb = new int[3];
        // rgb[0] = inPixels[index] >> 16 & 0xff;
        // rgb[1] = inPixels[index] >> 8 & 0xff;
        // rgb[2] = inPixels[index] & 0xff;
        // return rgb;
        return inPixels[index];
    }
}
