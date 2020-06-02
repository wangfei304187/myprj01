package com.kdevn.mydcm4che3;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.dcm4che3.imageio.plugins.dcm.DicomImageReader;

public class TestDcmCompress4
{

    // static
    // {
    // // Load the native OpenCV library
    // OpenCVNativeLoader loader = new OpenCVNativeLoader();
    // loader.init();
    // }

    /**
     * 1_0.dcm -- compressed dicom (jpeg2000), 512x512, postion: 4086, length: 231336
     */
    public static void main(String[] args) throws Exception
    {

    }

    private static long[] fetchFrameOffsetAndLength(File dcmFile)
    {
        long[] offsetLength = new long[2];
        int frameIndex = 0;
        try
        {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(dcmFile);
            Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("DICOM");
            DicomImageReader reader = (DicomImageReader) iter.next();
            reader.setInput(imageInputStream, false);
            // offsetLength = reader.getImageInputStreamOffsetLength(frameIndex);
            imageInputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return offsetLength;
    }
}
