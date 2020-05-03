/**
 * Copyright 2015 Bowing Medical Technologies Co., Ltd.
 */
package com.kdevn.image.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.DicomOutputStream;

public class DcmUtils
{
    public DcmUtils()
    {
    }

    public DicomObject readDicomObject(InputStream is) throws IOException
    {
        try (DicomInputStream dcmInputStream = new DicomInputStream(is))
        {
            DicomObject dcmObj = dcmInputStream.readDicomObject();

            return dcmObj;
        }
    }

    public String[] getImageType(DicomObject dcmObj)
    {
        return dcmObj.getStrings(Tag.ImageType);
    }

    public String getPatientID(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.PatientID);
    }

    public String getStudyID(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.StudyID);
    }

    public String getStudyInstanceUID(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.StudyInstanceUID);
    }

    public String getSeriesInstanceUID(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.SeriesInstanceUID);
    }

    public int getSeriesNumber(DicomObject dcmObj)
    {
        return dcmObj.getInt(Tag.SeriesNumber);
    }

    public int getInstanceNumber(DicomObject dcmObj)
    {
        return dcmObj.getInt(Tag.InstanceNumber);
    }

    public String getPatientPosition(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.PatientPosition);
    }

    public int getColumns(DicomObject dcmObj)
    {
        return dcmObj.getInt(Tag.Columns);
    }

    public int getRows(DicomObject dcmObj)
    {
        return dcmObj.getInt(Tag.Rows);
    }

    public float getPixelSpacingV(DicomObject dcmObj)
    {
        String[] pixelspacing = dcmObj.getStrings(Tag.PixelSpacing);
        float pixelspacingV = Float.parseFloat(pixelspacing[0]);
        return pixelspacingV;
    }

    public float getPixelSpacingH(DicomObject dcmObj)
    {
        String[] pixelspacing = dcmObj.getStrings(Tag.PixelSpacing);
        float pixelspacingH = 0;
        if (pixelspacing.length > 1)
        {
            pixelspacingH = Float.parseFloat(pixelspacing[1]);
        }
        else
        {
            pixelspacingH = Float.parseFloat(pixelspacing[0]);
        }
        return pixelspacingH;
    }

    public int getRescaleIntercept(DicomObject dcmObj)
    {
        return Integer.parseInt(dcmObj.getString(Tag.RescaleIntercept));
    }

    public int getRescaleSlope(DicomObject dcmObj)
    {
        return Integer.parseInt(dcmObj.getString(Tag.RescaleSlope));
    }

    public String[] getWindowCenter(DicomObject dcmObj)
    {
        return dcmObj.getStrings(Tag.WindowCenter);
    }

    public String[] getWindowWidth(DicomObject dcmObj)
    {
        return dcmObj.getStrings(Tag.WindowWidth);
    }

    public int getWC(DicomObject dcmObj)
    {
        String[] wcStrs = dcmObj.getStrings(Tag.WindowCenter);
        int[] wc = new int[wcStrs.length];
        for (int i = 0; i < wc.length; i++)
        {
            wc[i] = Math.round((float) Double.parseDouble(wcStrs[i]));
        }

        return wc[0];
    }

    public int getWW(DicomObject dcmObj)
    {
        String[] wwStrs = dcmObj.getStrings(Tag.WindowWidth);
        int[] ww = new int[wwStrs.length];
        for (int i = 0; i < ww.length; i++)
        {
            ww[i] = Math.round((float) Double.parseDouble(wwStrs[i]));
        }

        return ww[0];
    }

    // public byte[] getPixelData(DicomObject dcmObj)
    // {
    // byte[] pd = dcmObj.getBytes(Tag.PixelData);
    // return pd;
    // }

	public short[] getPixels16(DicomObject dcmObj) {
		byte[] pd = dcmObj.getBytes(Tag.PixelData);
		short[] pixels16 = PixelUtils.toShorts(pd, false);
		return pixels16;
	}

    public byte[] getPixels8(DicomObject dcmObj)
    {
        return dcmObj.getBytes(Tag.PixelData);
    }

    public short[] getOverlayData(DicomObject dcmObj)
    {
        // // return 0 if 'OverlayBitPosition' is not set
        // int overlayBitPosition = dcmObj.getInt(Tag.OverlayBitPosition);
        // // return 0 if 'OverlayBitsAllocated' is not set
        // int overlayBitsAllocated = dcmObj.getInt(Tag.OverlayBitsAllocated);
        // if (overlayBitPosition == 0 && overlayBitsAllocated == 1)
        // {
        return dcmObj.getShorts(Tag.OverlayData);
        // }
        //
        // return new short[0];
    }

    public float getReconX(DicomObject dcmObj)
    {
        String[] strs = dcmObj.getStrings(Tag.ReconstructionTargetCenterPatient);
        if (strs != null)
        {
            float[] r = new float[strs.length];
            for (int i = 0; i < r.length; i++)
            {
                r[i] = (float) Double.parseDouble(strs[i]);
            }

            return r[0];
        }

        return 0;
    }

    public float getReconY(DicomObject dcmObj)
    {
        String[] strs = dcmObj.getStrings(Tag.ReconstructionTargetCenterPatient);
        if (strs != null)
        {
            float[] r = new float[strs.length];
            for (int i = 0; i < r.length; i++)
            {
                r[i] = (float) Double.parseDouble(strs[i]);
            }

            return r[1];
        }

        return 0;
    }

    public double getFOV(DicomObject dcmObj)
    {
        return dcmObj.getDouble(Tag.ReconstructionFieldOfView);
    }

    public float getTilt(DicomObject dcmObj)
    {
        return dcmObj.getFloat(Tag.GantryDetectorTilt);
    }

    public float getSliceThickness(DicomObject dcmObj)
    {
        return dcmObj.getFloat(Tag.SliceThickness);
    }

    public double getTablePosition(DicomObject dcmObj)
    {
        return dcmObj.getDouble(Tag.TablePosition);
    }

    public double getSliceLocation(DicomObject dcmObj)
    {
        return dcmObj.getDouble(Tag.SliceLocation);
    }
    
    public boolean isLocalizer(DicomObject dcmObj)
    {
        String[] imageTypeStrs = getImageType(dcmObj);
        if (imageTypeStrs != null && imageTypeStrs.length > 2)
        {
            return imageTypeStrs[2].contains("LOCALIZER");
        }

        return false;
    }
    
    public boolean isHelical(DicomObject dcmObj)
    {
        String[] imageTypeStrs = getImageType(dcmObj);
        if (imageTypeStrs != null && imageTypeStrs.length > 2)
        {
            return imageTypeStrs[2].contains("HELICAL");
        }

        return false;
    }
    
    public float getSpacingBetweenSlice(DicomObject dcmObj)
    {
    	return dcmObj.getFloat(Tag.SpacingBetweenSlices);
    }

    public void setPixelData(DicomObject dcmObj, byte[] bytes)
    {
        dcmObj.putBytes(Tag.PixelData, dcmObj.vrOf(Tag.PixelData), bytes);
    }

    public BufferedImage dcmToBufferedImage(File dcmFile) throws Exception
    {
        DicomObject dcmObj = readDicomObject(new FileInputStream(dcmFile));
        return dcmToBufferedImage(dcmObj);
    }

    public BufferedImage dcmToBufferedImage(DicomObject dcmObj)
    {
        int cols = getColumns(dcmObj);
        int rows = getRows(dcmObj);

        int rescaleSlope = getRescaleSlope(dcmObj);
        int rescaleIntercept = getRescaleIntercept(dcmObj);

        int wc = getWC(dcmObj);
        int ww = getWW(dcmObj);

        // short[] pixels16 = getPixels16(dcmObj);
        byte[] pixels8 = getPixels8(dcmObj);
        short[] overlay = getOverlayData(dcmObj);

        ImageDisplayUtils u = new ImageDisplayUtils();
        u.setWC(wc);
        u.setWW(ww);
        // BufferedImage img = u.createImage(cols, rows, pixels16, rescaleSlope, rescaleIntercept, overlay, false);
        BufferedImage img = u.createImage(cols, rows, pixels8, rescaleSlope, rescaleIntercept, overlay, false);

        return img;
    }

    public BufferedImage dcmToBufferedImage(DicomObject dcmObj, byte[] pixels8)
    {
        int cols = getColumns(dcmObj);
        int rows = getRows(dcmObj);

        int rescaleSlope = getRescaleSlope(dcmObj);
        int rescaleIntercept = getRescaleIntercept(dcmObj);

        int wc = getWC(dcmObj);
        int ww = getWW(dcmObj);

        // if (pixels16 == null)
        // {
        // pixels16 = getPixels16(dcmObj);
        // }
        if (pixels8 == null)
        {
            pixels8 = getPixels8(dcmObj);
        }
        short[] overlay = getOverlayData(dcmObj);

        ImageDisplayUtils u = new ImageDisplayUtils();
        u.setWC(wc);
        u.setWW(ww);
        // BufferedImage img = u.createImage(cols, rows, pixels16, rescaleSlope, rescaleIntercept, overlay, false);
        BufferedImage img = u.createImage(cols, rows, pixels8, rescaleSlope, rescaleIntercept, overlay, false);

        return img;
    }

    public void saveOverlay(short[] overlay, DicomObject dcmObj)
    {
        dcmObj.putShorts(Tag.OverlayData, VR.OW, overlay);

        int rowNum = getRows(dcmObj);
        int colNum = getColumns(dcmObj);

        dcmObj.putInt(Tag.OverlayRows, VR.US, rowNum);
        // dcmObj.putInt(Tag.OverlayColumns, VR.US, colNum / 16);
        dcmObj.putInt(Tag.OverlayColumns, VR.US, colNum);
        dcmObj.putString(Tag.NumberOfFramesInOverlay, VR.IS, "1");
        dcmObj.putString(Tag.OverlayDescription, VR.LO, "Bowing Graphics");
        dcmObj.putString(Tag.OverlayType, VR.CS, "G");
        dcmObj.putShorts(Tag.OverlayOrigin, VR.SS, new short[] { 1, 1 });
        dcmObj.putInt(Tag.ImageFrameOrigin, VR.US, 1);
        dcmObj.putInt(Tag.OverlayBitsAllocated, VR.US, 1);
        dcmObj.putInt(Tag.OverlayBitPosition, VR.US, 0);
    }

    public void saveDcm(DicomObject dcmObj, File outputDcmFile) throws IOException
    {
        /*@formatter:off*/
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        DicomOutputStream dos = null;
        try
        {
            fos = new FileOutputStream(outputDcmFile);
            bos = new BufferedOutputStream(fos);
            dos = new DicomOutputStream(bos);

            dos.writeDicomFile(dcmObj);

            dos.flush();
            bos.flush();
            fos.flush();
            fos.getFD().sync();
        }
        finally
        {
            if(dos != null)
            {
                dos.close();
            }
            if(bos != null)
            {
                bos.close();
            }
            if(fos != null)
            {
                fos.close();
            }
        }
        /*@formatter:on*/
    }

    public void saveDcm(File inputDcmFile, short[] overlay, File outDcmFile) throws IOException
    {
        try (DicomInputStream dcmInputStream = new DicomInputStream(new FileInputStream(inputDcmFile)))
        {
            DicomObject dcmObj = dcmInputStream.readDicomObject();

            saveOverlay(overlay, dcmObj);

            saveDcm(dcmObj, outDcmFile);
        }
    }

    public DicomObject makeCopy(DicomObject dcmObjSrc)
    {
        DicomObject dcmObjCopy = new BasicDicomObject();

        boolean copyWithoutPrivateTag = false;
        dcmObjSrc.copyTo(dcmObjCopy, copyWithoutPrivateTag);

        return dcmObjCopy;
    }

    public short[] getOverlayByMinusBufferedImage(BufferedImage biWithDot, BufferedImage biWithoutDot)
    {
        return getOverlayByMinusBufferedImage(biWithDot, biWithoutDot, true);
    }

    private short[] getOverlayByMinusBufferedImage(BufferedImage b1, BufferedImage b2, boolean inverseBits)
    {
        int w1 = b1.getWidth();
        int h1 = b1.getHeight();

        int w2 = b2.getWidth();
        int h2 = b2.getHeight();

        if (w1 != w2 || h1 != h2)
        {
            return new short[0];
        }

        // int[] outData1 = new int[w1 * h1];
        byte[] outData1 = new byte[w1 * h1];
        b1.getRaster().getDataElements(0, 0, w1, h1, outData1);

        // int[] outData2 = new int[w2 * h2];
        byte[] outData2 = new byte[w2 * h2];
        b2.getRaster().getDataElements(0, 0, w2, h2, outData2);

        int j = 0;
        short[] overlay = new short[w1 / 16 * h1]; // w1 == 16*N

        short s = 0;
        for (int i = 0; i < outData1.length; i++)
        {
            int n = outData1[i] - outData2[i];
            if (n != 0)
            {
                s = (short) ((s << 1) + 1);
            }
            else
            {
                s = (short) ((s << 1) + 0);
            }

            if ((i + 1) % 16 == 0)
            {
                if (inverseBits)
                {
                    overlay[j++] = inverseBits(s);
                }
                else
                {
                    overlay[j++] = s;
                }

                s = 0;
            }
        }

        return overlay;
    }

    private short inverseBits(short s)
    {
        int r = 0;
        for (int i = 0; i < 16; i++)
        {
            int n = s >> i & 0x01;
        r = (n << 15 - i) + r;
        }

        return (short) (r & 0xFFFF);
    }

    // -->2019/4 Added.
    /*@formatter:off*/
    // theta: 旋转坐标系theta角度
    public static List<Integer> extractHU(float cx, float cy, double dd,
            double theta, boolean isFlipH, boolean isFlipV, int imageHeight, int imageWidth,
            double rescaleSlope, double rescaleIntercept, byte[] pixels8)
            {
        double[] dest = ArithmeticUtils.rotateCoordinateSystem(theta, cx, cy);
        cx = (float) dest[0];
        cy = (float) dest[1];

        if (isFlipH)
        {
            cx = -cx;
        }
        if (isFlipV)
        {
            cy = -cy;
        }

        int leftX = (int) Math.round(cx - dd / 2);
        int rightX = (int) Math.round(cx + dd / 2);

        int topY = (int) Math.round(cy - dd / 2);
        int bottomY = (int) Math.round(cy + dd / 2);

        List<Integer> li = new ArrayList<Integer>();
        int rowNum = imageHeight;
        int colNum = imageWidth;

        // -->2019/4/1 Added. fix bug 2455.
        leftX = Math.max(leftX, -colNum / 2);
        rightX = Math.min(rightX, colNum / 2);
        topY = Math.max(topY, -rowNum / 2);
        bottomY = Math.min(bottomY, rowNum / 2);
        // <--

        for (int i = leftX; i <= rightX; i++)
        {
            for (int j = topY; j <= bottomY; j++)
            {
                // x, y : offset relative to image center under zoomFactor = 1.0f
                int x = i;
                int y = j;
                Float f1 = new Float(ArithmeticUtils.distance(x, y, cx, cy));
                // Float f1 = new Float(ArithmeticUtils.distance2(x, y, cx, cy));
                Float f2 = new Float(dd / 2);
                int result = f1.compareTo(f2);
                if (result < 0 || result == 0)
                {
                    int colIdx = colNum / 2 + x; // colIdx, rowIdx: relative to image left-top corner.
                    int rowIdx = rowNum / 2 + y;
                    if (rowIdx >= 0 && rowIdx < rowNum && colIdx >= 0 && colIdx < colNum)
                    {
                        int us = PixelUtils.getPixel(rowIdx, colIdx, pixels8, rowNum, colNum); // short value
                        int hu = PixelUtils.toHU(us, rescaleSlope, rescaleIntercept);
                        li.add(hu);
                    }
                }
            }
        }
        return li;
            }
    /*@formatter:on*/
    // <--
}
