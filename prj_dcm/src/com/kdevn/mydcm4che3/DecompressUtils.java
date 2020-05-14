package com.kdevn.mydcm4che3;

import java.awt.image.BufferedImage;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.io.IOException;
import java.io.OutputStream;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDouble;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.osgi.OpenCVNativeLoader;
import org.weasis.opencv.data.ImageCV;
import org.weasis.opencv.data.PlanarImage;
import org.weasis.opencv.op.ImageConversion;

public class DecompressUtils
{

    static
    {
        // Load the native OpenCV library
        OpenCVNativeLoader loader = new OpenCVNativeLoader();
        loader.init();
    }

    /**
     * 1_0.dcm -- compressed dicom (jpeg2000), 512x512, postion: 4086, length: 231336
     *
     * dependencies: libopencv_java.so, weasis-opencv-core-3.6.0-rc1.jar
     *
     */
    public static short[] decompress(String filename, long offset, long length, int pixelRepresentation)
            throws UnsupportedOperationException
    {
        // Imgcodecs.DICOM_FLAG_SIGNED
        // Imgcodecs.DICOM_FLAG_UNSIGNED
        // Imgcodecs.DICOM_FLAG_YBR
        int dcmFlags = pixelRepresentation == 0 ? Imgcodecs.DICOM_FLAG_UNSIGNED : Imgcodecs.DICOM_FLAG_SIGNED;

        MatOfDouble positions = null;
        MatOfDouble lengths = null;
        positions = new MatOfDouble(DecompressUtils.getDoubleArray(new long[] { offset }));
        lengths = new MatOfDouble(DecompressUtils.getDoubleArray(new long[] { length }));

        // Mat buf = new Mat();
        // int n = buf.get(row, col, inputData);
        // // PlanarImage img = ImageCV.toImageCV(Imgcodecs.dicomJpgMatRead(buf, dcmFlags, Imgcodecs.IMREAD_UNCHANGED));
        // PlanarImage img = ImageCV.toImageCV(Imgcodecs.imdecode(buf, dcmFlags));

        /*@formatter:off*/
        Mat mat = Imgcodecs.dicomJpgFileRead(filename,
                positions,
                lengths, dcmFlags, Imgcodecs.IMREAD_UNCHANGED);
        /*@formatter:on*/

        short[] r = null;
        PlanarImage img = null;
        try
        {
            img = ImageCV.toImageCV(mat);
            BufferedImage bufferedImage = ImageConversion.toBufferedImage(img);

            DataBuffer db = bufferedImage.getRaster().getDataBuffer();
            if (db.getDataType() == DataBuffer.TYPE_USHORT)
            {
                r = ((DataBufferUShort) db).getData();
            }
            else if (db.getDataType() == DataBuffer.TYPE_SHORT)
            {
                r = ((DataBufferShort) db).getData();
            }
            else
            {
                throw new UnsupportedOperationException("datetype=" + db.getDataType());
            }
        }
        finally
        {
            if (img != null)
            {
                img.release();
            }
            DecompressUtils.closeMat(mat);
            DecompressUtils.closeMat(positions);
            DecompressUtils.closeMat(lengths);
        }

        return r;
    }

    public static short[] decompress(byte[] inputData, int pixelRepresentation)
            throws UnsupportedOperationException
    {
        // Imgcodecs.DICOM_FLAG_SIGNED
        // Imgcodecs.DICOM_FLAG_UNSIGNED
        // Imgcodecs.DICOM_FLAG_YBR
        int dcmFlags = pixelRepresentation == 0 ? Imgcodecs.DICOM_FLAG_UNSIGNED : Imgcodecs.DICOM_FLAG_SIGNED;

        Mat buf = new MatOfByte(inputData); // CvType.CV_8UC1

        /*@formatter:off*/
        Mat mat = Imgcodecs.dicomJpgMatRead(buf, dcmFlags, Imgcodecs.IMREAD_UNCHANGED); // CV_16UC1
        /*@formatter:on*/

        short[] r = null;
        PlanarImage img = null;
        try
        {
            img = ImageCV.toImageCV(mat);
            BufferedImage bufferedImage = ImageConversion.toBufferedImage(img);

            DataBuffer db = bufferedImage.getRaster().getDataBuffer();
            if (db.getDataType() == DataBuffer.TYPE_USHORT)
            {
                r = ((DataBufferUShort) db).getData();
            }
            else if (db.getDataType() == DataBuffer.TYPE_SHORT)
            {
                r = ((DataBufferShort) db).getData();
            }
            else
            {
                throw new UnsupportedOperationException("datetype=" + db.getDataType());
            }
        }
        finally
        {
            if (img != null)
            {
                img.release();
            }
            DecompressUtils.closeMat(buf);
            DecompressUtils.closeMat(mat);
        }

        return r;
    }

    public static short[] decompress2(byte[] inputData, int pixelRepresentation)
            throws UnsupportedOperationException
    {
        // Imgcodecs.DICOM_FLAG_SIGNED
        // Imgcodecs.DICOM_FLAG_UNSIGNED
        // Imgcodecs.DICOM_FLAG_YBR
        int dcmFlags = pixelRepresentation == 0 ? Imgcodecs.DICOM_FLAG_UNSIGNED : Imgcodecs.DICOM_FLAG_SIGNED;

        Mat buf = new MatOfByte(inputData); // CvType.CV_8UC1

        /*@formatter:off*/
        // BELOW CODE, BOTH IS OK
        // Mat mat = Imgcodecs.dicomJpgMatRead(buf, dcmFlags, Imgcodecs.IMREAD_UNCHANGED);  // CV_16UC1
        Mat mat = Imgcodecs.imdecode(buf, Imgcodecs.IMREAD_UNCHANGED);  // CV_16UC1
        /*@formatter:on*/

        short[] r = null;
        PlanarImage img = null;
        try
        {
            img = ImageCV.toImageCV(mat);
            BufferedImage bufferedImage = ImageConversion.toBufferedImage(img);

            DataBuffer db = bufferedImage.getRaster().getDataBuffer();
            if (db.getDataType() == DataBuffer.TYPE_USHORT)
            {
                r = ((DataBufferUShort) db).getData();
            }
            else if (db.getDataType() == DataBuffer.TYPE_SHORT)
            {
                r = ((DataBufferShort) db).getData();
            }
            else
            {
                throw new UnsupportedOperationException("datetype=" + db.getDataType());
            }
        }
        finally
        {
            if (img != null)
            {
                img.release();
            }
            DecompressUtils.closeMat(buf);
            DecompressUtils.closeMat(mat);
        }

        return r;
    }

    public static void closeMat(Mat mat)
    {
        if (mat != null)
        {
            mat.release();
        }
    }

    private static void writeTo(Raster raster, OutputStream out) throws IOException
    {
        SampleModel sm = raster.getSampleModel();
        DataBuffer db = raster.getDataBuffer();
        switch (db.getDataType())
        {
            case DataBuffer.TYPE_BYTE:
                DecompressUtils.writeTo(sm, ((DataBufferByte) db).getBankData(), out);
                break;
            case DataBuffer.TYPE_USHORT:
                DecompressUtils.writeTo(sm, ((DataBufferUShort) db).getData(), out);
                break;
            case DataBuffer.TYPE_SHORT:
                DecompressUtils.writeTo(sm, ((DataBufferShort) db).getData(), out);
                break;
            case DataBuffer.TYPE_INT:
                DecompressUtils.writeTo(sm, ((DataBufferInt) db).getData(), out);
                break;
            default:
                throw new UnsupportedOperationException(
                        "Unsupported Datatype: " + db.getDataType());
        }
    }

    private static void writeTo(SampleModel sm, short[] data, OutputStream out)
            throws IOException
    {
        int h = sm.getHeight();
        int w = sm.getWidth();
        int stride = ((ComponentSampleModel) sm).getScanlineStride();
        byte[] b = new byte[w * 2];
        for (int y = 0; y < h; ++y)
        {
            for (int i = 0, j = y * stride; i < b.length;)
            {
                short s = data[j++];
                b[i++] = (byte) s;
                b[i++] = (byte) (s >> 8);
            }
            out.write(b);
        }
    }

    private static void writeTo(SampleModel sm, int[] data, OutputStream out)
            throws IOException
    {
        int h = sm.getHeight();
        int w = sm.getWidth();
        int stride = ((SinglePixelPackedSampleModel) sm).getScanlineStride();
        byte[] b = new byte[w * 3];
        for (int y = 0; y < h; ++y)
        {
            for (int i = 0, j = y * stride; i < b.length;)
            {
                int s = data[j++];
                b[i++] = (byte) (s >> 16);
                b[i++] = (byte) (s >> 8);
                b[i++] = (byte) s;
            }
            out.write(b);
        }
    }

    private static void writeTo(SampleModel sm, byte[][] bankData, OutputStream out)
            throws IOException
    {
        int h = sm.getHeight();
        int w = sm.getWidth();
        ComponentSampleModel csm = (ComponentSampleModel) sm;
        int len = w * csm.getPixelStride();
        int stride = csm.getScanlineStride();
        if (csm.getBandOffsets()[0] != 0)
        {
            DecompressUtils.bgr2rgb(bankData[0]);
        }
        for (byte[] b : bankData)
        {
            for (int y = 0, off = 0; y < h; ++y, off += stride)
            {
                out.write(b, off, len);
            }
        }
    }

    private static void bgr2rgb(byte[] bs)
    {
        for (int i = 0, j = 2; j < bs.length; i += 3, j += 3)
        {
            byte b = bs[i];
            bs[i] = bs[j];
            bs[j] = b;
        }
    }

    private static double[] getDoubleArray(long[] array)
    {
        double[] a = new double[array.length];
        for (int i = 0; i < a.length; i++)
        {
            a[i] = array[i];
        }
        return a;
    }
}
