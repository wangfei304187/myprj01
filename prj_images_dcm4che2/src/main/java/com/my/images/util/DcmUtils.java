/**
 * Copyright 2015 Bowing Medical Technologies Co., Ltd.
 */
package com.my.images.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.SequenceDicomElement;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.DicomOutputStream;

public class DcmUtils
{
    private static String displayEncoding = "GBK";

    public DcmUtils()
    {
    }

    public DicomObject readDicomObject(InputStream is) throws IOException
    {
        DicomInputStream dcmInputStream = null;
        try
        {
            dcmInputStream = new DicomInputStream(is);
            DicomObject dcmObj = dcmInputStream.readDicomObject();
            return dcmObj;
        }
        finally
        {
            if (dcmInputStream != null)
            {
                dcmInputStream.close();
            }
        }
    }

    // public Map<String, Object> getParams(InputStream is) throws IOException
    // {
    // DicomObject dcmObj = readDicomObject(is);
    //
    // Map<String, Object> m = new HashMap<String, Object>();
    // // Patient Info
    // String patientName = dcmObj.getString(Tag.PatientName);
    // String patientId = dcmObj.getString(Tag.PatientID);
    // Date patientBirthDate = dcmObj.getDate(Tag.PatientBirthDate);
    // String patientAge = dcmObj.getString(Tag.PatientAge);
    // String patientSex = dcmObj.getString(Tag.PatientSex);
    // String accessionNumber = dcmObj.getString(Tag.AccessionNumber);
    // m.put(TagConstants.patientName, patientName);
    // m.put(TagConstants.patientId, patientId);
    // String patientBirthDateStr = "";
    // if (patientBirthDate != null)
    // {
    // patientBirthDateStr = DateFormatUtils.DF.format(patientBirthDate);
    // }
    // m.put(TagConstants.patientBirthDate, patientBirthDateStr);
    // m.put(TagConstants.patientAge, patientAge);
    // m.put(TagConstants.patientSex, patientSex);
    // m.put(TagConstants.accessionNumber, accessionNumber);
    //
    // String operatorName = dcmObj.getString(Tag.OperatorsName);
    // String requestingInstitutionalDepartmentName = dcmObj.getString(Tag.RequestingService);
    // String requestingPhysician = dcmObj.getString(Tag.RequestingPhysician);
    // m.put(TagConstants.operatorName, operatorName);
    // m.put(TagConstants.requestingInstitutionalDepartmentName, requestingInstitutionalDepartmentName);
    // m.put(TagConstants.requestingPhysician, requestingPhysician);
    //
    // // Exam Info
    // Date acquisitionDate = dcmObj.getDate(Tag.AcquisitionDate);
    // Date acquisitionTime = dcmObj.getDate(Tag.AcquisitionTime);
    // Date acquisitionDateTime = dcmObj.getDate(Tag.AcquisitionDateTime);
    // String studyId = dcmObj.getString(Tag.StudyID);
    // String seriesNumber = dcmObj.getString(Tag.SeriesNumber);
    // String imageId = dcmObj.getString(Tag.ImageID);
    // String imageNumber = dcmObj.getString(Tag.InstanceNumber);
    // String patientPosition = dcmObj.getString(Tag.PatientPosition);
    // String viewPosition = dcmObj.getString(Tag.ViewPosition);
    //
    // String acquisitionDateStr = "";
    // if (acquisitionDate != null)
    // {
    // acquisitionDateStr = DateFormatUtils.DF.format(acquisitionDate);
    // }
    // m.put(TagConstants.acquisitionDate, acquisitionDateStr);
    // String acquisitionTimeStr = "";
    // if (acquisitionTime != null)
    // {
    // acquisitionTimeStr = DateFormatUtils.TF.format(acquisitionTime);
    // }
    // m.put(TagConstants.acquisitionTime, acquisitionTimeStr);
    // String acquisitionDateTimeStr = "";
    // if (acquisitionDateTime != null)
    // {
    // acquisitionDateTimeStr = DateFormatUtils.DTF.format(acquisitionDateTime);
    // }
    // m.put(TagConstants.acquisitionDateTime, acquisitionDateTimeStr);
    // m.put(TagConstants.studyId, studyId);
    // m.put(TagConstants.seriesNumber, seriesNumber);
    // m.put(TagConstants.imageId, imageId);
    // m.put(TagConstants.imageNumber, imageNumber);
    // m.put(TagConstants.patientPosition, patientPosition);
    // m.put(TagConstants.viewPosition, viewPosition);
    //
    // // Scan Parameters
    // String scanType = dcmObj.getString(Tag.ScanType);
    // // TODO: study name / series name
    // String protocolName = dcmObj.getString(Tag.ProtocolName);
    // String seriesDesc = dcmObj.getString(Tag.SeriesDescription);
    //
    // String kvp = dcmObj.getString(Tag.KVP);
    // int ma = dcmObj.getInt(Tag.XRayTubeCurrent);
    // int ms = dcmObj.getInt(Tag.ExposureTime);
    // String gantryDetectorTilt = dcmObj.getString(Tag.GantryDetectorTilt);
    // String tablePosition = dcmObj.getString(Tag.TablePosition);
    // String tableHeight = dcmObj.getString(Tag.TableHeight);
    // String tableSpeed = dcmObj.getString(Tag.TableSpeed);
    // // TODO scan start
    // String scanLength = dcmObj.getString(Tag.ScanLength);
    // String sliceThickness = dcmObj.getString(Tag.SliceThickness);
    // // TODO
    //
    // m.put(TagConstants.scanType, scanType);
    // m.put(TagConstants.protocolName, protocolName);
    // m.put(TagConstants.seriesDesc, seriesDesc);
    // m.put(TagConstants.kvp, kvp);
    // m.put(TagConstants.mAs, String.valueOf(ms * 1.0 / 1000 * ma));
    // m.put(TagConstants.gantryDetectorTilt, gantryDetectorTilt);
    // m.put(TagConstants.tablePosition, tablePosition);
    // m.put(TagConstants.tableHeight, tableHeight);
    // m.put(TagConstants.tableSpeed, tableSpeed);
    // m.put(TagConstants.scanLength, scanLength);
    // m.put(TagConstants.sliceThickness, sliceThickness);
    //
    // // System Info
    // String institutionName = dcmObj.getString(Tag.InstitutionName);
    // String manufacturerModelName = dcmObj.getString(Tag.ManufacturerModelName);
    // String softwareVersions = dcmObj.getString(Tag.SoftwareVersions);
    // m.put(TagConstants.institutionName, institutionName);
    // m.put(TagConstants.manufacturerModelName, manufacturerModelName);
    // m.put(TagConstants.softwareVersions, softwareVersions);
    //
    // // Recon Parameters
    // String reconstructionFieldOfView = dcmObj.getString(Tag.ReconstructionFieldOfView);
    // String reconstructionTargetCenterPatient = dcmObj.getString(Tag.ReconstructionTargetCenterPatient);
    // String convolutionKernel = dcmObj.getString(Tag.ConvolutionKernel);
    // m.put(TagConstants.reconstructionFieldOfView, reconstructionFieldOfView);
    // m.put(TagConstants.reconstructionTargetCenterPatient, reconstructionTargetCenterPatient);
    // m.put(TagConstants.convolutionKernel, convolutionKernel);
    // String[] wcStrs = dcmObj.getStrings(Tag.WindowCenter);
    // int[] wc = new int[wcStrs.length];
    // for (int i = 0; i < wc.length; i++)
    // {
    // // wc[i] = Integer.parseInt(wcStrs[i]);
    // wc[i] = Math.round((float) Double.parseDouble(wcStrs[i]));
    // }
    // String[] wwStrs = dcmObj.getStrings(Tag.WindowWidth);
    // int[] ww = new int[wwStrs.length];
    // for (int i = 0; i < ww.length; i++)
    // {
    // // ww[i] = Integer.parseInt(wwStrs[i]);
    // ww[i] = Math.round((float) Double.parseDouble(wwStrs[i]));
    // }
    // m.put(TagConstants.windowCenter, wc);
    // m.put(TagConstants.windowWidth, ww);
    //
    // // Comments
    // String patientComments = dcmObj.getString(Tag.PatientComments);
    // String imageComments = dcmObj.getString(Tag.ImageComments);
    // m.put(TagConstants.patientComments, patientComments);
    // m.put(TagConstants.imageComments, imageComments);
    //
    // // Orientation Labels
    // String[] patientOrientation = dcmObj.getStrings(Tag.PatientOrientation);
    // m.put(TagConstants.patientOrientation, patientOrientation);
    //
    // // --------------------------------------------------------------
    //
    // int cols = dcmObj.getInt(Tag.Columns);
    // int rows = dcmObj.getInt(Tag.Rows);
    //
    // m.put(TagConstants.cols, cols);
    // m.put(TagConstants.rows, rows);
    //
    // String[] pixelspacing = dcmObj.getStrings(Tag.PixelSpacing);
    // float pixelspacingV = Float.parseFloat(pixelspacing[0]);
    // float pixelspacingH = Float.parseFloat(pixelspacing[1]);
    //
    // m.put(TagConstants.pixelspacingV, pixelspacingV);
    // m.put(TagConstants.pixelspacingH, pixelspacingH);
    //
    // int rescaleIntercept = Integer.parseInt(dcmObj.getString(Tag.RescaleIntercept));
    // int rescaleSlope = Integer.parseInt(dcmObj.getString(Tag.RescaleSlope));
    // m.put(TagConstants.rescaleIntercept, rescaleIntercept);
    // m.put(TagConstants.rescaleSlope, rescaleSlope);
    //
    // // GET PIXEL DATA
    // byte[] ps = dcmObj.getBytes(Tag.PixelData);
    // // short[] pixels16 = toShorts(ps, false);
    // short[] pixels16 = PixelUtils.toShorts(ps, false);
    // m.put(TagConstants.pixelData, pixels16);
    //
    // // GET OVERLAY DATA
    // // return 0 if 'OverlayBitPosition' is not set
    // int overlayBitPosition = dcmObj.getInt(Tag.OverlayBitPosition);
    // // return 0 if 'OverlayBitsAllocated' is not set
    // int overlayBitsAllocated = dcmObj.getInt(Tag.OverlayBitsAllocated);
    // if (overlayBitPosition == 0 && overlayBitsAllocated == 1)
    // {
    // short[] overlayData = dcmObj.getShorts(Tag.OverlayData);
    // m.put(TagConstants.overlayData, overlayData);
    // }
    //
    // return m;
    // }

    // //////////////////////////////////////////////////////////////////////////////////////////

    // public void setReconXY(DicomObject dcmObj, float reconX, float reconY)
    // {
    // // dcmObj.putFloats(Tag.ReconstructionTargetCenterPatient, VR.FD, new float[] { reconX, reconY, 0 });
    // dcmObj.putStrings(Tag.ReconstructionTargetCenterPatient, VR.FD, new String[] { "" + reconX, "" + reconY, "0" });
    // }
    //
    // public void setPixelSpacing(DicomObject dcmObj, float pixelSpacingV, float pixelSpacingH)
    // {
    // dcmObj.putFloats(Tag.PixelSpacing, VR.DS, new float[] { pixelSpacingV, pixelSpacingH });
    // }

    // //////////////////////////////////////////////////////////////////////////////////////////

    public String getManufacturer(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.Manufacturer);
    }

    public String getSoftwareVersions(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.SoftwareVersions);
    }

    public String[] getImageType(DicomObject dcmObj)
    {
        return dcmObj.getStrings(Tag.ImageType);
    }

    public String getIssuerOfPatientID(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.IssuerOfPatientID);
    }

    public Date getDOB(DicomObject dcmObj)
    {
        Date date = dcmObj.getDate(Tag.PatientBirthDate);
        return date != null ? date : new Date();
    }

    public String getSex(DicomObject dcmObj)
    {
        String sex = dcmObj.getString(Tag.PatientSex);
        return sex != null ? sex : "O";
    }

    public String getAge(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.PatientAge);
    }

    public String getModality(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.Modality);
    }

    public String getSOPInstanceUID(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.SOPInstanceUID);
    }

    public String getStudyDescription(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.StudyDescription);
    }

    public Date getDicomStudyDateTime(DicomObject dcmObj)
    {
        Date d = dcmObj.getDate(Tag.StudyDate);
        d = d != null ? d : new Date();
        Date d2 = dcmObj.getDate(Tag.StudyTime);
        Calendar cal = Calendar.getInstance();
        if (d2 != null)
        {
            cal.set(d.getYear() + 1900, d.getMonth(), d.getDate(), d2.getHours(), d2.getMinutes(), d2.getSeconds());
        }
        else
        {
            cal.set(d.getYear() + 1900, d.getMonth(), d.getDate(), 0, 0, 0);
        }
        return cal.getTime();
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
        try
        {
            return Integer.parseInt(dcmObj.getString(Tag.RescaleIntercept));
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public int getRescaleSlope(DicomObject dcmObj)
    {
        try
        {
            return Integer.parseInt(dcmObj.getString(Tag.RescaleSlope));
        }
        catch (Exception e)
        {
            return 1;
        }
    }

    public int getPixelRepresentation(DicomObject dcmObj)
    {
        return dcmObj.getInt(Tag.PixelRepresentation);
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

    // public short[] getPixels16(DicomObject dcmObj)
    // {
    // byte[] pd = dcmObj.getBytes(Tag.PixelData);
    // short[] pixels16 = PixelUtils.toShorts(pd, false);
    // return pixels16;
    // }

    public byte[] getPixels8(DicomObject dcmObj)
    {
        // return dcmObj.getBytes(Tag.PixelData);
        return getPixelData(dcmObj);
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

    // public int getFOV(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(Tag.ReconstructionFieldOfView);
    // }

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

    public String getDerivedImageType(DicomObject dcmObj)
    {
        String[] imageTypeStrs = getImageType(dcmObj);
        if (imageTypeStrs != null && imageTypeStrs.length > 2)
        {
            return "DERIVED\\SECONDARY\\" + imageTypeStrs[2];
        }
        else
        {
            return "DERIVED\\SECONDARY\\OTHER";
        }
    }

    public boolean isOriginalLocalizer(DicomObject dcmObj)
    {
        String[] imageTypeStrs = getImageType(dcmObj);
        if (imageTypeStrs != null && imageTypeStrs.length > 2)
        {
            return imageTypeStrs[0].contains("ORIGINAL") && imageTypeStrs[2].contains("LOCALIZER");
        }

        return false;
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

    private String getDisplayEncodingString(DicomObject dcmObj, int tag)
    {
        byte[] bytes = dcmObj.getBytes(tag);
        if (bytes != null)
        {
            return DcmUtils.decode(bytes, dcmObj);
        }
        else
        {
            return "unknown";
        }
    }

    public static String decode(byte[] bytes, DicomObject obj)
    {
        try
        {
            // if (RuntimeInfoMgr.EnableAutoRecognizeCharacterSetOnImageDisplay)
            // {
            String characterSet = obj.getString(Tag.SpecificCharacterSet);
            if (characterSet != null)
            {
                try
                {
                    return obj.getSpecificCharacterSet().decode(bytes);
                }
                catch (Exception e)
                {
                    return new String(bytes, DcmUtils.displayEncoding);
                }

            }
            else
            {
                return new String(bytes, DcmUtils.displayEncoding);
            }
            // }
            // else
            // {
            // return new String(bytes, ImageMgmtMgr.displayEncoding);
            // }
        }
        catch (UnsupportedEncodingException e)
        {
            return "Unknown: charset is not supported (" + DcmUtils.displayEncoding + ")";
        }
    }

    public String getPatientID(DicomObject dcmObj)
    {
        // return dcmObj.getString(Tag.PatientID);
        return getDisplayEncodingString(dcmObj, Tag.PatientID);
    }

    public String getPatientName(DicomObject dcmObj)
    {
        // return dcmObj.getString(Tag.PatientName);
        return getDisplayEncodingString(dcmObj, Tag.PatientName);
    }

    public String getBodyPart(DicomObject dcmObj)
    {
        String str = dcmObj.getString(Tag.BodyPartExamined);
        return str != null ? str : "";
    }

    public String getSeriesDescription(DicomObject dcmObj)
    {
        return dcmObj.getString(Tag.SeriesDescription);
    }

    public String getAccessionNumber(DicomObject dcmObj)
    {
        return getDisplayEncodingString(dcmObj, Tag.AccessionNumber);
    }

    public String getOperatorName(DicomObject dcmObj)
    {
        return getDisplayEncodingString(dcmObj, Tag.OperatorsName);
    }

    public String getReferringPhysicianName(DicomObject dcmObj)
    {
        return getDisplayEncodingString(dcmObj, Tag.ReferringPhysicianName);
    }

    public String getInstitutionName(DicomObject dcmObj)
    {
        return getDisplayEncodingString(dcmObj, Tag.InstitutionName);
    }

    // /////////////////////// Custom Tag Begin /////////////////////

    // /** (0029,0011) VR=LO, AutoTransferNodes */
    // public String getAutoTransferNodes(DicomObject dcmObj)
    // {
    // // return dcmObj.getString(BMTag.AutoTransferNodes);
    // return getDisplayEncodingString(dcmObj, BMTag.AutoTransferNodes);
    // }
    //
    // /** (0029,0001) VR=IS, VM=1 Auto Reference Line */
    // public int getAutoReferenceLine(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(BMTag.AutoReferenceLine);
    // }
    //
    // // /** (0029,0002) VR=FL, Topo Start Angle */
    // /** (0029,0002) VR=IS, Topo Start Angle */
    // // public float getTopoStartAngle(DicomObject dcmObj)
    // // {
    // // return dcmObj.getFloat(BMTag.TopoStartAngle);
    // // }
    // public int getTopoStartAngle(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(BMTag.TopoStartAngle);
    // }
    //
    // /** (0029,0003) VR=IS, Table In or Out, 1--In; 0--Out */
    // public int getTableInOrOut(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(BMTag.TableInOut);
    // }
    //
    // /** (0029,0004) VR=IS, Scan Sequence Number in a Exam */
    // public int getScanSeqNumInExam(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(BMTag.ScanSeqNumInExam);
    // }
    //
    // /** (0029,0005) VR=IS, Recon Sequence Number in a Scan */
    // public int getReconSeqNumInScan(DicomObject dcmObj)
    // {
    // try
    // {
    // return dcmObj.getInt(BMTag.ReconSeqNumInScan);
    // }
    // catch (UnsupportedOperationException e)
    // {
    // return 0;
    // }
    // }
    //
    // /** (0029,0015) VR=IS, Scan Sequence Number in a UIStudy */
    // public int getScanSeqNumInUIStudy(DicomObject dcmObj)
    // {
    // try
    // {
    // return dcmObj.getInt(BMTag.ScanSeqNumInUIStudy);
    // }
    // catch (UnsupportedOperationException e)
    // {
    // return 0;
    // }
    // }
    //
    // /** (0029,0006) VR=IS, Scan Group Number in a Exam */
    // public int getScanGroupNum(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(BMTag.ScanGroupNum);
    // }
    //
    // /** (0029,0007) VR=IS, Series Sequence Number in a Exam */
    // public int getSeriesSeqNum(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(BMTag.SeriesSeqNum);
    // }
    //
    // /** (0029,0008) VR=LO, BmSeriesId */
    // public String getBmSeriesIdFromImageHeader(DicomObject dcmObj)
    // {
    // return dcmObj.getString(BMTag.BmSeriesId);
    // }
    //
    // /** (0029,0009) VR=IS, BmStudyId */
    // public String getBmStudyIdFromImageHeader(DicomObject dcmObj)
    // {
    // return dcmObj.getString(BMTag.BmStudyId);
    // }
    //
    // /** (0029,0010) VR=IS, BmPatientId */
    // public String getBmPatientIdFromImageHeader(DicomObject dcmObj)
    // {
    // return dcmObj.getString(BMTag.BmPatientId);
    // }
    //
    // /** (0029,0012) VR=IS, IsDoseReport */
    // public int getIsDoseReport(DicomObject dcmObj)
    // {
    // try
    // {
    // return dcmObj.getInt(BMTag.IsDoseReport);
    // }
    // catch (UnsupportedOperationException e)
    // {
    // return 0;
    // }
    // }
    //
    // /** (0029,0013) VR=IS, IsARLDrawed */
    // public int getIsARLDrawed(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(BMTag.IsARLDrawed);
    // }
    //
    // /** (0029,0014) VR=IS, IsARLTopo */
    // public int getIsARLTopo(DicomObject dcmObj)
    // {
    // try
    // {
    // return dcmObj.getInt(BMTag.IsARLTopo);
    // }
    // catch (UnsupportedOperationException e)
    // {
    // return 0;
    // }
    // }
    //
    // public int getIsResampleFrom3D(DicomObject dcmObj)
    // {
    // try
    // {
    // return dcmObj.getInt(BMTag.ResampleSliceTag);
    // }
    // catch (UnsupportedOperationException e)
    // {
    // return 0;
    // }
    // }

    // /** (0029,0016) VR=IS, Dynamic Mode, 0--normal scan; 1--premonitoring; 2--monitoring; 3--bolus */
    // public int getDynamicMode(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(BMTag.DynamicMode);
    // }
    //
    // /** (0029,0017) VR=IS, IsARLDrawed */
    // public int getIsROIDrawed(DicomObject dcmObj)
    // {
    // return dcmObj.getInt(BMTag.IsROIDrawed);
    // }
    //
    // /** (0029,0018) VR=DS, TrackingCX */
    // public float getTrackingCX(DicomObject dcmObj)
    // {
    // return dcmObj.getFloat(BMTag.TrackingCX);
    // }
    //
    // /** (0029,0019) VR=DS, TrackingCY */
    // public float getTrackingCY(DicomObject dcmObj)
    // {
    // return dcmObj.getFloat(BMTag.TrackingCY);
    // }
    //
    // /** (0029,0020) VR=DS, TrackingRadius */
    // public float getTrackingRadius(DicomObject dcmObj)
    // {
    // return dcmObj.getFloat(BMTag.TrackingRadius);
    // }
    //
    // /** (0029,0024) VR=DS, TrackingROIDeltaMean */
    // public float getTrackingROIDeltaMean(DicomObject dcmObj)
    // {
    // return dcmObj.getFloat(BMTag.TrackingROIDeltaMean);
    // }
    //
    // /** (0029,0021) VR=LO, ExamStudyId */
    // public String getExamStudyId(DicomObject dcmObj)
    // {
    // return dcmObj.getString(BMTag.ExamStudyId);
    // }
    //
    // /** (0029,0022) VR=LO, ExamScanId */
    // public String getExamScanId(DicomObject dcmObj)
    // {
    // return dcmObj.getString(BMTag.ExamScanId);
    // }
    //
    // /** (0029,0023) VR=LO, ExamReconId */
    // public String getExamReconId(DicomObject dcmObj)
    // {
    // return dcmObj.getString(BMTag.ExamReconId);
    // }

    // /////////////////////// Custom Tag End ///////////////////////

    public int getNumberOfFrames(DicomObject dcmObj)
    {
        if (dcmObj.contains(Tag.NumberOfFrames))
        {
            return dcmObj.getInt(Tag.NumberOfFrames);
        }
        else
        {
            return 1;
        }
    }

    public byte[] getPixelData(DicomObject dcmObj)
    {
        if (dcmObj.get(Tag.PixelData) instanceof SequenceDicomElement)
        {
            SequenceDicomElement d = (SequenceDicomElement) dcmObj.get(Tag.PixelData);
            int size = d.countItems();
            for (int i = 0; i < size; i++)
            {
                // 0 : offset item
                // 1~n : fragment
                byte[] bytes = d.getFragment(i);
                if (i > 0 && bytes.length != 0)
                {
                    int rows = getRows(dcmObj);
                    int cols = getColumns(dcmObj);
                    int expectLen = rows * cols * Short.BYTES;
                    if (bytes.length == expectLen)
                    {
                        return bytes;
                    }
                    else if (bytes.length < expectLen)
                    {
                        byte[] dest = new byte[expectLen];
                        System.arraycopy(bytes, 0, dest, 0, bytes.length);
                        return dest;
                    }
                    else
                    {
                        byte[] dest = new byte[expectLen];
                        System.arraycopy(bytes, 0, dest, 0, dest.length);
                        return dest;
                    }
                }
            }
        }

        return dcmObj.getBytes(Tag.PixelData);
    }

    public void setPixelData(DicomObject dcmObj, byte[] bytes)
    {
        // dcmObj.putBytes(Tag.PixelData, dcmObj.vrOf(Tag.PixelData), bytes);
        dcmObj.putBytes(Tag.PixelData, VR.OW, bytes);
    }

    // public BufferedImage dcmToBufferedImage(File dcmFile) throws Exception
    // {
    // DicomObject dcmObj = readDicomObject(new FileInputStream(dcmFile));
    // return dcmToBufferedImage(dcmObj);
    // }

    // public BufferedImage dcmToBufferedImage(DicomObject dcmObj)
    // {
    // int cols = getColumns(dcmObj);
    // int rows = getRows(dcmObj);
    //
    // int rescaleSlope = getRescaleSlope(dcmObj);
    // int rescaleIntercept = getRescaleIntercept(dcmObj);
    //
    // int wc = getWC(dcmObj);
    // int ww = getWW(dcmObj);
    //
    // short[] pixels16 = getPixels16(dcmObj);
    // short[] overlay = getOverlayData(dcmObj);
    //
    // ImageDisplayUtils u = new ImageDisplayUtils();
    // u.setWC(wc);
    // u.setWW(ww);
    // BufferedImage img = u.createImage(cols, rows, pixels16, rescaleSlope, rescaleIntercept, overlay, false);
    //
    // return img;
    // }
    //
    // public BufferedImage dcmToBufferedImage(DicomObject dcmObj, short[] pixels16)
    // {
    // int cols = getColumns(dcmObj);
    // int rows = getRows(dcmObj);
    //
    // int rescaleSlope = getRescaleSlope(dcmObj);
    // int rescaleIntercept = getRescaleIntercept(dcmObj);
    //
    // int wc = getWC(dcmObj);
    // int ww = getWW(dcmObj);
    //
    // if (pixels16 == null)
    // {
    // pixels16 = getPixels16(dcmObj);
    // }
    // short[] overlay = getOverlayData(dcmObj);
    //
    // ImageDisplayUtils u = new ImageDisplayUtils();
    // u.setWC(wc);
    // u.setWW(ww);
    // BufferedImage img = u.createImage(cols, rows, pixels16, rescaleSlope, rescaleIntercept, overlay, false);
    //
    // return img;
    // }

    public BufferedImage dcmToBufferedImage(DicomObject dcmObj)
    {
        int cols = getColumns(dcmObj);
        int rows = getRows(dcmObj);

        int rescaleSlope = getRescaleSlope(dcmObj);
        int rescaleIntercept = getRescaleIntercept(dcmObj);
        int pixelRepresentation = getPixelRepresentation(dcmObj);

        int wc = getWC(dcmObj);
        int ww = getWW(dcmObj);

        // short[] pixels16 = getPixels16(dcmObj);
        byte[] pixels8 = getPixels8(dcmObj);
        short[] overlay = getOverlayData(dcmObj);

        ImageDisplayUtils u = new ImageDisplayUtils();
        u.setWC(wc);
        u.setWW(ww);
        // BufferedImage img = u.createImage(cols, rows, pixels16, rescaleSlope, rescaleIntercept, overlay, false);
        BufferedImage img =
                u.createImage(cols, rows, pixels8, rescaleSlope, rescaleIntercept, overlay, false, pixelRepresentation);

        return img;
    }

    public BufferedImage dcmToBufferedImage(DicomObject dcmObj, byte[] pixels8)
    {
        int cols = getColumns(dcmObj);
        int rows = getRows(dcmObj);

        int rescaleSlope = getRescaleSlope(dcmObj);
        int rescaleIntercept = getRescaleIntercept(dcmObj);
        int pixelRepresentation = getPixelRepresentation(dcmObj);

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
        BufferedImage img = u.createImage(cols, rows, pixels8, rescaleSlope, rescaleIntercept, overlay, false,
                pixelRepresentation);

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
        // try (DicomOutputStream dos = new DicomOutputStream(new BufferedOutputStream(new FileOutputStream(outputDcmFile))))
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
        DicomInputStream dcmInputStream = null;
        try
        {
            dcmInputStream = new DicomInputStream(new FileInputStream(inputDcmFile));
            DicomObject dcmObj = dcmInputStream.readDicomObject();

            saveOverlay(overlay, dcmObj);

            saveDcm(dcmObj, outDcmFile);
        }
        finally
        {
            if (dcmInputStream != null)
            {
                dcmInputStream.close();
            }
        }
    }

    public DicomObject makeCopy(DicomObject dcmObjSrc)
    {
        DicomObject dcmObjCopy = new BasicDicomObject();

        boolean copyWithoutPrivateTag = false;
        dcmObjSrc.copyTo(dcmObjCopy, copyWithoutPrivateTag);

        // int autoReferenceLine = getAutoReferenceLine(dcmObjSrc);
        // float topoStartAngle = getTopoStartAngle(dcmObjSrc);
        // int tableInOrOut = getTableInOrOut(dcmObjSrc);
        // int scanNumber = getScanSeqNumInExam(dcmObjSrc);
        // int reconSeqNumInScan = getReconSeqNumInScan(dcmObjSrc);
        //
        // // (0029,0001) VR=IS, VM=1 Auto Reference Line
        // dcmObjCopy.putInt(BMTag.AutoReferenceLine, VR.IS, autoReferenceLine);
        // // (0029,0002) VR=FL, Topo Start Angle
        // dcmObjCopy.putFloat(BMTag.TopoStartAngle, VR.FL, topoStartAngle);
        // // (0029,0003) VR=IS, Table In or Out, 1--In; 0--Out
        // dcmObjCopy.putInt(BMTag.TableInOut, VR.IS, tableInOrOut);
        // // (0029,0004) VR=IS, Scan Sequence Number in a Exam
        // dcmObjCopy.putInt(BMTag.ScanSeqNumInExam, VR.IS, scanNumber);
        // // (0029,0005) VR=IS, Recon Sequence Number in a Scan
        // dcmObjCopy.putInt(BMTag.ReconSeqNumInScan, VR.IS, reconSeqNumInScan);

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
            double rescaleSlope, double rescaleIntercept, byte[] pixels8, int pixelRepresentation)
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

        //        BMLogging.getInstance().logInfo("extractHU", "DcmUtils::extractHU",
        //                "cx=" + cx + ", cy=" + cy + ", dd=" + dd +
        //                ",\nleftX=" + leftX + ",\nrightX=" + rightX + ",\ntopY=" + topY + ",\nbottomY=" + bottomY
        //                + "\n\n" + BMUtils.getCallerStackTraceString(), BMLogging.timestamp());

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
                    // <--2016/02/15
                    // int rowIdx = rowNum / 2 + x;
                    // int colIdx = colNum / 2 + y;
                    int colIdx = colNum / 2 + x; // colIdx, rowIdx: relative to image left-top corner.
                    int rowIdx = rowNum / 2 + y;
                    // <--
                    if (rowIdx >= 0 && rowIdx < rowNum && colIdx >= 0 && colIdx < colNum)
                    {
                        int us = PixelUtils.getPixel(rowIdx, colIdx, pixels8, rowNum, colNum, pixelRepresentation);
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

    // private static long[] fetchFrameOffsetAndLength(File dcmFile)
    // {
    // long[] offsetLength = new long[2];
    // int frameIndex = 0;
    // try
    // {
    // ImageInputStream imageInputStream = ImageIO.createImageInputStream(dcmFile);
    // Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("DICOM");
    // DicomImageReader reader = (DicomImageReader) iter.next();
    // reader.setInput(imageInputStream, false);
    // offsetLength = reader.getImageInputStreamOffsetLength(frameIndex);
    // imageInputStream.close();
    // }
    // catch (IOException e)
    // {
    // e.printStackTrace();
    // }
    // return offsetLength;
    // }

    /*@formatter:off*/
    /*
    public byte[] getPixelDataCompressed(DicomObject dcmObj)
    {
        if (dcmObj.get(Tag.PixelData) instanceof SequenceDicomElement)
        {
            int pixelRepresentation = dcmObj.getInt(Tag.PixelRepresentation);

            // long offset = DcmUtils.fetchFrameStatrPos(dcmFile); // TODO enhance

            SequenceDicomElement d = (SequenceDicomElement) dcmObj.get(Tag.PixelData);
            int size = d.countItems();
            for (int i = 0; i < size; i++)
            {
                // 0 : offset item
                // 1~n : fragment
                byte[] bytes = d.getFragment(i);
                if (i > 0 && bytes.length != 0)
                {
                    // short[] s = DecompressUtils.decompress(dcmFile.getAbsolutePath(),
                    //     offset, bytes.length, pixelRepresentation);
                    short[] s = DecompressUtils.decompress(bytes, pixelRepresentation);
                    // byte[] decodeBytes = PixelUtils.toBytes(s, false);
                    // dcmObj.putBytes(Tag.PixelData, VR.OW, decodeBytes);
                    dcmObj.putShorts(Tag.PixelData, VR.OW, s);

                    return PixelUtils.toBytes(s, false);
                }
            }
        }

        return null;
    }

    public byte[] getPixelDataCompressed(DicomObject dcmObj, File dcmFile)
    {
        if (dcmObj.get(Tag.PixelData) instanceof SequenceDicomElement)
        {
            int pixelRepresentation = dcmObj.getInt(Tag.PixelRepresentation);

            long offset = DcmUtils.fetchFrameStatrPos(dcmFile); // TODO enhance

            SequenceDicomElement d = (SequenceDicomElement) dcmObj.get(Tag.PixelData);
            int size = d.countItems();
            for (int i = 0; i < size; i++)
            {
                // 0 : offset item
                // 1~n : fragment
                byte[] bytes = d.getFragment(i);
                if (i > 0 && bytes.length != 0)
                {
                    short[] s = DecompressUtils.decompress(dcmFile.getAbsolutePath(),
                            offset,
                            bytes.length,
                            pixelRepresentation);
                    // byte[] decodeBytes = PixelUtils.toBytes(s, false);
                    // dcmObj.putBytes(Tag.PixelData, VR.OW, decodeBytes);
                    dcmObj.putShorts(Tag.PixelData, VR.OW, s);

                    return PixelUtils.toBytes(s, false);
                }
            }
        }

        return null;
    }

    private static long fetchFrameStatrPos(File dcmFile)
    {
        DicomInputStream dis = null;
        try
        {
            dis = new DicomInputStream(dcmFile);
            DicomInputHandler ih = new StopTagInputHandler(Tag.PixelData);
            // ih = new SizeSkipInputHandler(ih);
            dis.setHandler(ih);
            dis.readDicomObject();

            long pixelDataPos = dis.getStreamPosition();
            // int pixelDataLen = dis.valueLength();
            // boolean compressed = pixelDataLen == -1;
            return pixelDataPos + 16; // 16, 24
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (dis != null)
            {
                try
                {
                    dis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return -1;
    }
     */
    /*@formatter:on*/
}
