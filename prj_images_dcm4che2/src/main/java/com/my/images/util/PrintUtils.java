// package com.my.images.util;
//
// import java.awt.image.BufferedImage;
// import java.awt.image.DataBuffer;
// import java.awt.image.DataBufferByte;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
//
// import org.dcm4che2.data.BasicDicomObject;
// import org.dcm4che2.data.DicomObject;
// import org.dcm4che2.data.Tag;
// import org.dcm4che2.data.UID;
// import org.dcm4che2.data.VR;
// import org.dcm4che2.net.DimseRSP;
// import org.dcm4che2.util.UIDUtils;
//
//
// public class PrintUtils
// {
//
// // public static DicomObject createBasicFilmSession(int numOfCopies, String filmDestination)
// public static DicomObject createBasicFilmSession(int numOfCopies)
// {
//        /*@formatter:off*/
//        DicomObject filmSessionAttrs = new BasicDicomObject();
//        // in the range from 1 to the maximum allowed by printer (from config)
//        filmSessionAttrs.putString(Tag.NumberOfCopies, filmSessionAttrs.vrOf(Tag.NumberOfCopies), String.valueOf(numOfCopies));
//        // HIGH, MED, LOW
//        // filmSessionAttrs.putString(Tag.PrintPriority, filmSessionAttrs.vrOf(Tag.PrintPriority), "MED");
//        filmSessionAttrs.putString(Tag.PrintPriority, filmSessionAttrs.vrOf(Tag.PrintPriority), ImageMgmtMgr.printPriority);
//        // PAPER, CLEAR FILM, BLUE FILM, MAMMO CLEAR FILM, MAMMO BLUE FILM
//        if(ImageMgmtMgr.enableMediumType)
//        {
//            filmSessionAttrs.putString(Tag.MediumType, filmSessionAttrs.vrOf(Tag.MediumType), ImageMgmtMgr.mediumType);
//        }
//        // MAGAZINE, PROCESSOR, BIN_i ("I" represents the bin number)
//        if(ImageMgmtMgr.enableFilmDestination)
//        {
//            filmSessionAttrs.putString(Tag.FilmDestination, filmSessionAttrs.vrOf(Tag.FilmDestination), ImageMgmtMgr.filmDestination);
//        }
//        // filmSessionAttrs.putString(Tag.FilmSessionLabel, filmSessionAttrs.vrOf(Tag.FilmSessionLabel), "CT-PatientName-StudyName");
//        return filmSessionAttrs;
//        /*@formatter:on*/
// }
//
// public static DicomObject createBasicFilmBox(String imageDisplayFormat, String filmOrientation, String filmSizeID,
// String filmSessionUID)
// {
//        /*@formatter:off*/
//        DicomObject filmBoxAttrs = new BasicDicomObject();
//        // STANDARD\C,R
//        filmBoxAttrs.putString(Tag.ImageDisplayFormat, filmBoxAttrs.vrOf(Tag.ImageDisplayFormat), imageDisplayFormat); // "STANDARD\\2,3"
//        // PORTRAIT, LANDSCAPE
//        filmBoxAttrs.putString(Tag.FilmOrientation, filmBoxAttrs.vrOf(Tag.FilmOrientation), filmOrientation);
//        // 14INX17IN, 11INX14IN, 8INX10IN, ..., 24CMX24CM, 24CMX30CM, A4, A3
//        filmBoxAttrs.putString(Tag.FilmSizeID, filmBoxAttrs.vrOf(Tag.FilmSizeID), filmSizeID);
//        // REPLICATE, BILINEAR, CUBIC, NONE
//        // filmBoxAttrs.putString(Tag.MagnificationType, filmBoxAttrs.vrOf(Tag.MagnificationType), "CUBIC");
//        filmBoxAttrs.putString(Tag.MagnificationType, filmBoxAttrs.vrOf(Tag.MagnificationType), ImageMgmtMgr.magnificationType);
//        // an integer in the range 0-15. Its value is laser specific
//        // filmBoxAttrs.putString(Tag.SmoothingType, filmBoxAttrs.vrOf(Tag.SmoothingType), "0");
//        // filmBoxAttrs.putInt(Tag.MinDensity, filmBoxAttrs.vrOf(Tag.MinDensity), 0);
//        // filmBoxAttrs.putInt(Tag.MaxDensity, filmBoxAttrs.vrOf(Tag.MaxDensity), 0);
//        // LUT=m, n, where m is a string for lookup table name, n is an integer for contrast (0-15). The value of n is laser specific.
//        // filmBoxAttrs.putString(Tag.ConfigurationInformation, filmBoxAttrs.vrOf(Tag.ConfigurationInformation), "");
//        // WHITE, BLACK
//        // filmBoxAttrs.putString(Tag.BorderDensity, filmBoxAttrs.vrOf(Tag.BorderDensity), "BLACK");
//
//        DicomObject sqDcmObj = new BasicDicomObject();
//        sqDcmObj.putString(Tag.ReferencedSOPClassUID, VR.UI, UID.BasicFilmSessionSOPClass);
//        sqDcmObj.putString(Tag.ReferencedSOPInstanceUID, VR.UI, filmSessionUID);
//        filmBoxAttrs.putNestedDicomObject(Tag.ReferencedFilmSessionSequence, sqDcmObj);
//
//        //-->2019/7/26 Added. Test for Kodak DryView 5800 Laser Imager
//        if (ImageMgmtMgr.enableReferencedImageBoxSequence)
//        {
//            DicomObject imgBoxSqDcmObj = new BasicDicomObject();
//            if (ImageMgmtMgr.enableReferencedImageBoxSequenceReferencedSOP)
//            {
//                imgBoxSqDcmObj.putString(Tag.ReferencedSOPClassUID, VR.UI, ImageMgmtMgr.referencedImageBoxSequenceReferencedSOPClassUID);
//                if(ImageMgmtMgr.enableNewReferencedImageBoxSequenceReferencedSOPInstanceUID)
//                {
//                    imgBoxSqDcmObj.putString(Tag.ReferencedSOPInstanceUID, VR.UI, UIDUtils.createUID());
//                }
//                else
//                {
//                    imgBoxSqDcmObj.putString(Tag.ReferencedSOPInstanceUID, VR.UI, filmSessionUID);
//                }
//            }
//            filmBoxAttrs.putNestedDicomObject(Tag.ReferencedImageBoxSequence, imgBoxSqDcmObj);
//        }
//        //<--
//
//        return filmBoxAttrs;
//        /*@formatter:on*/
// }
//
// // public static DicomObject createBasicGreyscaleImageBox_2(DicomObject dcmImgObj)
// // {
//    //        /*@formatter:off*/
//    //        DicomObject imageBoxAttrs = new BasicDicomObject();
//    //        imageBoxAttrs.putString(Tag.ImageBoxPosition, imageBoxAttrs.vrOf(Tag.ImageBoxPosition), "1");
//    //        // NORMAL, REVERSE
//    //        imageBoxAttrs.putString(Tag.Polarity, imageBoxAttrs.vrOf(Tag.Polarity), "NORMAL");
//    //        // attrs.putString(Tag.RequestedImageSize, imageBoxAttrs.vrOf(Tag.RequestedImageSize), "300");
//    //        DicomObject sqDcmObj = new BasicDicomObject();
//    //        sqDcmObj.putString(Tag.SamplesPerPixel, imageBoxAttrs.vrOf(Tag.SamplesPerPixel), dcmImgObj.getString(Tag.SamplesPerPixel));
//    //        // MONOCHROME1, MONOCHROME2
//    //        sqDcmObj.putString(Tag.PhotometricInterpretation, imageBoxAttrs.vrOf(Tag.PhotometricInterpretation), dcmImgObj.getString(Tag.PhotometricInterpretation));
//    //        sqDcmObj.putString(Tag.Rows, imageBoxAttrs.vrOf(Tag.Rows), dcmImgObj.getString(Tag.Rows));
//    //        sqDcmObj.putString(Tag.Columns, imageBoxAttrs.vrOf(Tag.Columns), dcmImgObj.getString(Tag.Columns));
//    //        sqDcmObj.putString(Tag.BitsAllocated, imageBoxAttrs.vrOf(Tag.BitsAllocated), dcmImgObj.getString(Tag.BitsAllocated));
//    //        sqDcmObj.putString(Tag.BitsStored, imageBoxAttrs.vrOf(Tag.BitsStored), dcmImgObj.getString(Tag.BitsStored));
//    //        sqDcmObj.putString(Tag.HighBit, imageBoxAttrs.vrOf(Tag.HighBit), dcmImgObj.getString(Tag.HighBit));
//    //        sqDcmObj.putString(Tag.PixelRepresentation, imageBoxAttrs.vrOf(Tag.PixelRepresentation), dcmImgObj.getString(Tag.PixelRepresentation));
//    //        sqDcmObj.putString(Tag.PixelAspectRatio, imageBoxAttrs.vrOf(Tag.PixelAspectRatio), dcmImgObj.getString(Tag.PixelAspectRatio));
//    //        sqDcmObj.putBytes(Tag.PixelData, imageBoxAttrs.vrOf(Tag.PixelData), dcmImgObj.getBytes(Tag.PixelData));
//    //
//    //        // imageBoxAttrs.putNestedDicomObject(Tag.ReferencedImageBoxSequence, sqDcmObj);
//    //        imageBoxAttrs.putNestedDicomObject(Tag.BasicGrayscaleImageSequence, sqDcmObj);
//    //        return imageBoxAttrs;
//    //        /*@formatter:on*/
// // }
//
// public static DicomObject createBasicGreyscaleImageBox(int imageBoxPosition, DicomObject dcmImgObj)
// {
//        /*@formatter:off*/
//        return PrintUtils.createBasicGreyscaleImageBoxInner(imageBoxPosition,
//                ImageMgmtMgr.polarity, // "NORMAL",
//                dcmImgObj.getInt(Tag.SamplesPerPixel),
//                dcmImgObj.getString(Tag.PhotometricInterpretation),
//                dcmImgObj.getInt(Tag.Rows),
//                dcmImgObj.getInt(Tag.Columns),
//                dcmImgObj.getInt(Tag.BitsAllocated),
//                dcmImgObj.getInt(Tag.BitsStored),
//                dcmImgObj.getInt(Tag.HighBit),
//                dcmImgObj.getInt(Tag.PixelRepresentation),
//                PrintUtils.getPixelAspectRatio(dcmImgObj),
//                dcmImgObj.getBytes(Tag.PixelData)
//                );
//        /*@formatter:on*/
// }
//
// private static String getPixelAspectRatio(DicomObject dcmObj)
// {
// String[] ratio = dcmObj.getStrings(Tag.PixelAspectRatio); // Required if the aspect ration is not 1\1
// if (ratio == null || ratio.length == 0)
// {
// return "1\\1";
// }
//
// if (ratio.length > 1)
// {
// return ratio[0] + "\\" + ratio[1];
// }
// else
// {
// return ratio[0] + "\\" + ratio[0];
// }
// }
//
//    /*@formatter:off*/
//    private static DicomObject createBasicGreyscaleImageBoxInner(int imageBoxPosition,
//            String polarity,
//            int samplesPerPixel,
//            String photometricInterpretation,
//            int rows,
//            int columns,
//            int bitsAllocated,
//            int bitsStored,
//            int highBit,
//            int pixelRepresentation,
//            String pixelAspectRatio,
//            byte[] pixelData
//            )
//    {
//        DicomObject imageBoxAttrs = new BasicDicomObject();
//        imageBoxAttrs.putInt(Tag.ImageBoxPosition, imageBoxAttrs.vrOf(Tag.ImageBoxPosition), imageBoxPosition);
//        // NORMAL, REVERSE
//        imageBoxAttrs.putString(Tag.Polarity, imageBoxAttrs.vrOf(Tag.Polarity), polarity);
//        // imageBoxAttrs.putString(Tag.RequestedImageSize, imageBoxAttrs.vrOf(Tag.RequestedImageSize), "300");
//        // REPLICATE, BILINEAR, CUBIC, NONE
//        // imageBoxAttrs.putString(Tag.MagnificationType, imageBoxAttrs.vrOf(Tag.MagnificationType), "CUBIC");
//        imageBoxAttrs.putString(Tag.MagnificationType, imageBoxAttrs.vrOf(Tag.MagnificationType), ImageMgmtMgr.magnificationType);
//        // an integer in the range 0-15. Its value is laser specific
//        // imageBoxAttrs.putString(Tag.SmoothingType, imageBoxAttrs.vrOf(Tag.SmoothingType), "0");
//        // LUT=m, n, where m is a string for lookup table name, n is an integer for contrast (0-15). The value of n is laser specific.
//        // imageBoxAttrs.putString(Tag.ConfigurationInformation, imageBoxAttrs.vrOf(Tag.ConfigurationInformation), "");
//
//        DicomObject sqDcmObj = new BasicDicomObject();
//        // 1
//        sqDcmObj.putInt(Tag.SamplesPerPixel, imageBoxAttrs.vrOf(Tag.SamplesPerPixel), samplesPerPixel);
//        // MONOCHROME1, MONOCHROME2
//        sqDcmObj.putString(Tag.PhotometricInterpretation, imageBoxAttrs.vrOf(Tag.PhotometricInterpretation), photometricInterpretation);
//        sqDcmObj.putInt(Tag.Rows, imageBoxAttrs.vrOf(Tag.Rows), rows);
//        sqDcmObj.putInt(Tag.Columns, imageBoxAttrs.vrOf(Tag.Columns), columns);
//        // 8 or 16
//        sqDcmObj.putInt(Tag.BitsAllocated, imageBoxAttrs.vrOf(Tag.BitsAllocated), bitsAllocated);
//        // 8 or 12
//        sqDcmObj.putInt(Tag.BitsStored, imageBoxAttrs.vrOf(Tag.BitsStored), bitsStored);
//        // 7 or 11
//        sqDcmObj.putInt(Tag.HighBit, imageBoxAttrs.vrOf(Tag.HighBit), highBit);
//        sqDcmObj.putInt(Tag.PixelRepresentation, imageBoxAttrs.vrOf(Tag.PixelRepresentation), pixelRepresentation);
//        // Usually 1\1
//        sqDcmObj.putString(Tag.PixelAspectRatio, imageBoxAttrs.vrOf(Tag.PixelAspectRatio), pixelAspectRatio);
//        sqDcmObj.putBytes(Tag.PixelData, imageBoxAttrs.vrOf(Tag.PixelData), pixelData);
//        // sqDcmObj.putBytes(Tag.PixelData, VR.OW, pixelData);
//
//        // imageBoxAttrs.putNestedDicomObject(Tag.ReferencedImageBoxSequence, sqDcmObj);
//        imageBoxAttrs.putNestedDicomObject(Tag.BasicGrayscaleImageSequence, sqDcmObj);
//        return imageBoxAttrs;
//    }
//    /*@formatter:on*/
//
// public static List<String[]> getPrinterStatusInfo(DimseRSP rsp)
// {
// List<String[]> result = new ArrayList<String[]>();
// try
// {
// if (rsp.next())
// {
// DicomObject obj1 = rsp.getCommand();
// int status = obj1.getInt(Tag.Status);
// DicomObject obj2 = rsp.getDataset();
// String printerStatus = obj2.getString(Tag.PrinterStatus); // NORMAL, WARNING, FAILURE
// String printerStatusInfo = obj2.getString(Tag.PrinterStatusInfo);
// result.add(new String[] { String.valueOf(status), printerStatus, printerStatusInfo });
// }
// }
// catch (IOException | InterruptedException e)
// {
// }
//
// return result;
// }
//
// // //////////////////////////////////////////////////////////////
//    /*@formatter:off*/
//    public static DicomObject convertToPrintableDicomObject(BufferedImage bi,
//            String implementationClassUID,
//            String implementationVersionName,
//            String sopClassUID,
//            String sopInstanceUID,
//            String studyInstanceUID,
//            String seriesInstanceUID)
//    {
//        int w = bi.getWidth();
//        int h = bi.getHeight();
//        //        byte[] newBytes = new byte[w * h];
//        //        bi.getRaster().getDataElements(0, 0, w, h, newBytes);
//        //        // System.out.println("newBytes[] size=" + newBytes.length); // 1120 * 1120 = 1254400
//        byte[] newBytes = null;
//        DataBuffer buf = bi.getRaster().getDataBuffer();
//        if(buf instanceof DataBufferByte)
//        {
//            newBytes = ((DataBufferByte)buf).getData();
//        }
//        else
//        {
//            newBytes = new byte[w * h];
//            bi.getRaster().getDataElements(0, 0, w, h, newBytes);
//        }
//
//        DicomObject dObj = PrintUtils.generatePrintableDicomObject(w, h, newBytes,
//                implementationClassUID,
//                implementationVersionName,
//                sopClassUID,
//                sopInstanceUID,
//                studyInstanceUID,
//                seriesInstanceUID);
//        return dObj;
//    }
//    /*@formatter:on*/
//
//    /*@formatter:off*/
//    public static DicomObject generatePrintableDicomObject(int w, int h, byte[] newBytes,
//            String implementationClassUID,
//            String implementationVersionName,
//            String sopClassUID,
//            String sopInstanceUID,
//            String studyInstanceUID,
//            String seriesInstanceUID)
//    {
//        DicomObject dObj = PrintUtils.generatePrintableDicomObject(w, h, newBytes);
//        dObj.putString(Tag.ImplementationClassUID, VR.UI, implementationClassUID);
//        dObj.putString(Tag.ImplementationVersionName, VR.SH, implementationVersionName);
//        dObj.putString(Tag.MediaStorageSOPClassUID, VR.UI, sopClassUID);
//        dObj.putString(Tag.MediaStorageSOPInstanceUID, VR.UI, sopInstanceUID);
//        dObj.putString(Tag.SOPClassUID, VR.UI, sopClassUID);
//        dObj.putString(Tag.SOPInstanceUID, VR.UI, sopInstanceUID);
//        dObj.putString(Tag.StudyInstanceUID, VR.UI, studyInstanceUID);
//        dObj.putString(Tag.SeriesInstanceUID, VR.UI, seriesInstanceUID);
//
//        return dObj;
//    }
//    /*@formatter:on*/
//
// private static DicomObject generatePrintableDicomObject(int w, int h, byte[] newBytes)
// {
// DicomObject dObj = new BasicDicomObject();
// dObj.initFileMetaInformation(UID.ExplicitVRLittleEndian);
// dObj.putString(Tag.ImageType, VR.CS, "DERIVED\\SECONDARY");
// Date now = new Date();
// dObj.putDate(Tag.InstanceCreationDate, VR.DA, now);
// dObj.putDate(Tag.InstanceCreationTime, VR.TM, now);
// dObj.putString(Tag.Modality, VR.CS, "HC");
// dObj.putString(Tag.DerivationDescription, VR.ST, "HardCopy");
// dObj.putString(Tag.HardcopyDeviceManufacturer, VR.LO, "Bowzin");
//
// dObj.putInt(Tag.SamplesPerPixel, VR.US, 1);
// dObj.putString(Tag.PhotometricInterpretation, VR.CS, "MONOCHROME2");
// dObj.putInt(Tag.Rows, VR.US, h);
// dObj.putInt(Tag.Columns, VR.US, w);
// dObj.putString(Tag.PixelAspectRatio, VR.IS, "1\\1");
// dObj.putInt(Tag.BitsAllocated, VR.US, 8);
// dObj.putInt(Tag.BitsStored, VR.US, 8);
// dObj.putInt(Tag.HighBit, VR.US, 7);
// dObj.putInt(Tag.PixelRepresentation, VR.US, 0);
//
// dObj.putBytes(Tag.PixelData, VR.OW, newBytes);
// return dObj;
// }
//
// // //////////////////////////////////////////////////////////////
//
// // public static List<DicomObject> generateDicomImages()
// // {
// // List<DicomObject> list = new ArrayList<DicomObject>();
// // DicomInputStream dis;
// // File dir = new File(".");
// // String path = dir.getAbsolutePath();
// // System.out.println("PATH=" + path);
// //
// // try
// // {
// // String f;
// // for (int i = 1; i <= 6; i++)
// // {
// // f = path + "/testimg/" + i + ".dcm";
// // dis = new DicomInputStream(new File(f));
// // list.add(dis.readDicomObject());
// // dis.close();
// // }
// //
// // f = path + "/testimg/test_overlay.dcm";
// // dis = new DicomInputStream(new File(f));
// // list.add(dis.readDicomObject());
// // dis.close();
// //
// // f = path + "/testimg/dicommmmmmmmmm.dcm";
// // dis = new DicomInputStream(new File(f));
// // list.add(dis.readDicomObject());
// // dis.close();
// // }
// // catch (IOException e)
// // {
// // e.printStackTrace();
// // }
// //
// // return list;
// // }
//
// private static void printRSP(DimseRSP rsp)
// {
// // if (RuntimeInfoMgr.EnableSystemPrint)
// {
// System.out.println("----->Begin printRSP");
// }
// if (rsp == null)
// {
// // if (RuntimeInfoMgr.EnableSystemPrint)
// {
// System.out.println("!!! rsp is null");
// }
// }
// else
// {
// try
// {
// while (rsp.next())
// {
// DicomObject obj1 = rsp.getCommand();
// // if (RuntimeInfoMgr.EnableSystemPrint)
// {
// System.out.println("cmd==>" + obj1);
// }
// DicomObject obj2 = rsp.getDataset();
// // if (RuntimeInfoMgr.EnableSystemPrint)
// {
// System.out.println("dataset==>" + obj2);
// }
// }
// }
// catch (IOException e)
// {
// e.printStackTrace();
// }
// catch (InterruptedException e)
// {
// e.printStackTrace();
// }
// }
// // if (RuntimeInfoMgr.EnableSystemPrint)
// {
// System.out.println("<-----End printRSP");
// }
// }
// }
