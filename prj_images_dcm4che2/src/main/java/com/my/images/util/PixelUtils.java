package com.my.images.util;

import org.dcm4che2.util.ByteUtils;

public class PixelUtils
{

    private PixelUtils()
    {
    }

    // pixels => mm
    public static float toMillimeterH(float p, float zoomFactor, float pixelSpacingH)
    {
        return p / zoomFactor * pixelSpacingH;
    }

    public static float toMillimeterV(float p, float zoomFactor, float pizelSpacingV)
    {
        return p / zoomFactor * pizelSpacingV;
    }

    // mm => pixels, pixelSpacingH unit: mm
    public static float toPixelH(float mm, float zoomFactor, float pixelSpacingH)
    {
        return mm / pixelSpacingH * zoomFactor;
    }

    // mm => pixels, pixelSpacingV unit: mm
    public static float toPixelV(float mm, float zoomFactor, float pixelSpacingV)
    {
        return mm / pixelSpacingV * zoomFactor;
    }

    // return unsigned short
    public static int getPixel(int rowIdx, int colIdx, short[] pixel16, int rowNum, int colNum)
    {
        // return pixel16[rowIdx * colNum + colIdx] & 0xFFFF;
        return pixel16[rowIdx * colNum + colIdx];
    }

    public static int toHU(int pixel, double rescaleSlope, double rescaleIntercept)
    {
        // return pixel * rescaleSlope + rescaleIntercept;
        // --> 2017/01/24 Modified
        // if (RuntimeInfoMgr.IsShift)
        // {
        // return (int) Math.round(pixel * rescaleSlope + rescaleIntercept);
        // }
        // else
        // {
        // return pixel;
        // }
        return (int) Math.round(pixel * rescaleSlope + rescaleIntercept);
        // <--
    }

    // public static int toPixel(int HU, double rescaleSlope, double rescaleIntercept)
    // {
    // return (int) Math.round((HU - rescaleIntercept) / rescaleSlope);
    // }

    // public static short[] toShorts(byte[] val, boolean bigEndian)
    // {
    // return bigEndian ? ByteUtils.bytesBE2shorts(val) : ByteUtils.bytesLE2shorts(val);
    // }

    public static byte[] toBytes(short[] val, boolean bigEndian)
    {
        return bigEndian ? ByteUtils.shorts2bytesBE(val) : ByteUtils.shorts2bytesLE(val);
    }

    // -->2018/6/1 Added.
    public static int getPixel(int rowIdx, int colIdx, byte[] pixel8, int rowNum, int colNum, int pixelRepresentation)
    {
        int off = rowIdx * colNum * 2 + colIdx * 2;
        if (pixelRepresentation == 0)
        {
            return PixelUtils.bytesLE2ushort(pixel8, off);
        }
        else
        {
            return PixelUtils.bytesLE2sshort(pixel8, off);
        }
    }

    public static int bytesLE2sshort(byte[] b, int off)
    {
        return b[off + 1] << 8 | b[off] & 0xff;
    }

    public static int bytesLE2ushort(byte[] b, int off)
    {
        return PixelUtils.bytesLE2sshort(b, off) & 0xffff;
    }

    // public static int bytesBE2sshort(byte[] b, int off)
    // {
    // return b[off] << 8 | b[off + 1] & 0xff;
    // }
    //
    // public static int bytesBE2ushort(byte[] b, int off)
    // {
    // return PixelUtils.bytesBE2sshort(b, off) & 0xffff;
    // }
    // <--

    public static int bytesLE2int(byte[] b, int off)
    {
        // return (b[off + 3] & 0xff) << 24 | (b[off + 2] & 0xff) << 16 | (b[off + 1] & 0xff) << 8 | b[off] & 0xff;
        return (b[off + 2] & 0xff) << 16 | (b[off + 1] & 0xff) << 8 | b[off] & 0xff;
    }

    public static int[] bytesLE2ints(byte[] b)
    {
        if (b == null)
        {
            return null;
        }
        if ((b.length & 0x3) != 0)
        {
            throw new IllegalArgumentException("byte[" + b.length + "]");
        }
        int[] val = new int[b.length >> 2];
        for (int i = 0; i < val.length; i++)
        {
            val[i] = PixelUtils.bytesLE2int(b, i << 2);
        }
        return val;
    }

    public static byte[] int2bytesLE(int val, byte[] b, int off)
    {
        b[off] = (byte) val;
        b[off + 1] = (byte) (val >>> 8);
        b[off + 2] = (byte) (val >>> 16);
        b[off + 3] = (byte) (val >>> 24);
        return b;
    }

    public static byte[] ints2bytesLE(int[] val)
    {
        if (val == null)
        {
            return null;
        }
        byte[] b = new byte[val.length << 2];
        for (int i = 0; i < val.length; i++)
        {
            PixelUtils.int2bytesLE(val[i], b, i << 2);
        }
        return b;
    }
}
