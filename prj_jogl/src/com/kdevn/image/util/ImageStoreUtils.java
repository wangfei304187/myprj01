/**
 * Copyright 2015 Bowing Medical Technologies Co., Ltd.
 */
package com.kdevn.image.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class ImageStoreUtils
{

    private ImageStoreUtils()
    {
    }

    public static void toPng(BufferedImage bi, File f) throws IOException
    {
        ImageIO.write(bi, "png", f);
    }

    public static void toBmp(BufferedImage bi, File f) throws IOException
    {
        ImageIO.write(bi, "bmp", f);
    }

    public static void toTif(BufferedImage bi, File f) throws IOException
    {
        ImageIO.write(bi, "tif", f);
    }

    public static void toJpg(BufferedImage bi, File f) throws IOException
    {
        ImageStoreUtils.toJpg(bi, f, 1f);
    }

    public static void toJpg(BufferedImage bi, File f, float quality) throws IOException
    {
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
        if (iter.hasNext())
        {
            ImageWriter writer = iter.next();
            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            FileImageOutputStream out = null;
            try
            {
                out = new FileImageOutputStream(f);
                writer.setOutput(out);
                writer.write(null, new IIOImage(bi, null, null), param);
            }
            finally
            {
                if (out != null)
                {
                    out.close();
                }
                if (writer != null)
                {
                    writer.dispose();
                }
            }
        }
    }
}
