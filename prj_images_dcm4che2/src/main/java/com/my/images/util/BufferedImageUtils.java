package com.my.images.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class BufferedImageUtils
{
    public static BufferedImage getCopy(BufferedImage bi)
    {
        BufferedImage biCopy = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        biCopy.setData(bi.getData());
        return biCopy;
    }

    public static BufferedImage grayToRgb(BufferedImage src)
    {
        BufferedImage rgbBi = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_BGR);
        ColorConvertOp cco = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB), null);
        cco.filter(src, rgbBi);
        return rgbBi;
    }

    public static BufferedImage resizeImage(BufferedImage srcBufferedimage, int destImgWidth, int destImgHeight)
    {
        int type = srcBufferedimage.getColorModel().getTransparency();
        BufferedImage destImg = new BufferedImage(destImgWidth, destImgHeight, type);
        Graphics2D g2d = destImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(srcBufferedimage, 0, 0, destImgWidth, destImgHeight, 0, 0, srcBufferedimage.getWidth(),
                srcBufferedimage.getHeight(), null);
        g2d.dispose();
        return destImg;
    }

    public static BufferedImage flipImageHorizontally(BufferedImage srcBufferedimage)
    {
        int type = srcBufferedimage.getColorModel().getTransparency();
        int w = srcBufferedimage.getWidth();
        int h = srcBufferedimage.getHeight();
        BufferedImage destImg = new BufferedImage(w, h, type);
        Graphics2D g2d = destImg.createGraphics();
        g2d.drawImage(srcBufferedimage, 0, 0, w, h, w, 0, 0, h, null);
        g2d.dispose();
        return destImg;
    }

    public static BufferedImage flipImageVertically(BufferedImage srcBufferedimage)
    {
        int type = srcBufferedimage.getColorModel().getTransparency();
        int w = srcBufferedimage.getWidth();
        int h = srcBufferedimage.getHeight();
        BufferedImage destImg = new BufferedImage(w, h, type);
        Graphics2D g2d = destImg.createGraphics();
        g2d.drawImage(srcBufferedimage, 0, 0, w, h, 0, h, w, 0, null);
        g2d.dispose();
        return destImg;
    }

    public static BufferedImage rotateImage(BufferedImage srcBufferedimage, int degree)
    {
        int type = srcBufferedimage.getColorModel().getTransparency();
        int w = srcBufferedimage.getWidth();
        int h = srcBufferedimage.getHeight();
        BufferedImage destImg = new BufferedImage(w, h, type);
        Graphics2D g2d = destImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        g2d.drawImage(srcBufferedimage, 0, 0, null);
        g2d.dispose();
        return destImg;
    }
}
