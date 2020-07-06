package org.dcm4che2.tool.dcmqr;

import java.util.List;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;

public class DcmQR_QueryStudy
{

    // 13:14:35,598 INFO - Query Response #1:
    // (0008,0005) CS #10 [ISO_IR 192] Specific Character Set
    // (0008,0020) DA #8 [20200609] Study Date
    // (0008,0030) TM #14 [113800.000000] Study Time
    // (0008,0050) SH #0 [] Accession Number
    // *******(0008,0052) CS #6 [STUDY] Query/Retrieve Level
    // (0008,0054) AE #6 [QRSCP] Retrieve AE Title
    // (0008,0061) CS #2 [MR] Modalities in Study
    // (0008,1030) LO #6 [******] Study Description
    // (0010,0010) PN #4 [****] Patient’s Name
    // (0010,0020) LO #6 [******] Patient ID
    // (0010,0030) DA #8 [19540812] Patient’s Birth Date
    // (0010,0040) CS #2 [M] Patient’s Sex
    // (0020,000D) UI #38 [1.2.826.0.1.3680043.8.852.123456789.26] Study Instance UID
    // (0020,0010) SH #2 [26] Study ID
    // (0020,1206) IS #2 [2] Number of Study Related Series
    // (0020,1208) IS #2 [26] Number of Study Related Instances

    // 13:11:42,784 INFO - Query Response #1:
    // (0008,0005) CS #10 [ISO_IR 192] Specific Character Set
    // *******(0008,0052) CS #6 [SERIES] Query/Retrieve Level
    // (0008,0054) AE #6 [QRSCP] Retrieve AE Title
    // (0008,0060) CS #2 [MR] Modality
    // (0008,103E) LO #10 [Plan Scan] Series Description
    // (0020,000D) UI #38 [1.2.826.0.1.3680043.8.852.123456789.26] Study Instance UID
    // (0020,000E) UI #40 [1.2.826.0.1.3680043.8.852.123456789.26.1] Series Instance UID
    // (0020,0011) IS #2 [1] Series Number
    // (0020,1209) IS #2 [2] Number of Series Related Instances
    //
    // 13:11:42,785 INFO - Query Response #2:
    // (0008,0005) CS #10 [ISO_IR 192] Specific Character Set
    // *******(0008,0052) CS #6 [SERIES] Query/Retrieve Level
    // (0008,0054) AE #6 [QRSCP] Retrieve AE Title
    // (0008,0060) CS #2 [MR] Modality
    // (0008,103E) LO #14 [T2 2D FSE Sag] Series Description
    // (0020,000D) UI #38 [1.2.826.0.1.3680043.8.852.123456789.26] Study Instance UID
    // (0020,000E) UI #40 [1.2.826.0.1.3680043.8.852.123456789.26.2] Series Instance UID
    // (0020,0011) IS #2 [2] Series Number
    // (0020,1209) IS #2 [24] Number of Series Related Instances

    public static void main(String[] args)
    {
        // Query on study level

        args = new String[] {
                "-L"
                , "BMQRSCU:11113"
                , "QRSCP@www.dicomserver.co.uk:104"
                // , "-P"
                // , "-S"
                // , "-I"
                , "-qStudyDate=20200609-20200609"
                // , "-qModalitiesInStudy=MR"
                // , "-qPatientName=Trest^Zzest^^^"
                // , "-qPatientID=Test"
                , "-qStudyInstanceUID=1.2.826.0.1.3680043.8.852.123456789.26"
                , "-r", "SpecificCharacterSet"
                , "-r", "RetrieveAETitle"
                , "-r", "ModalitiesInStudy"
                , "-r", "PatientID"
                , "-r", "PatientName"
                , "-r", "PatientAge"
                , "-r", "PatientSex"
                , "-r", "PatientBirthDate"
                , "-r", "StudyDate"
                , "-r", "StudyTime"
                , "-r", "StudyDescription"
                , "-r", "StudyInstanceUID"
                , "-r", "StudyID"
                , "-r", "AccessionNumber"
                , "-r", "NumberOfStudyRelatedSeries"
                , "-r", "NumberOfStudyRelatedInstances"
        };

        StringBuilder sb = new StringBuilder(100);
        for (String s : args)
        {
            sb.append(s).append(" ");
        }
        System.out.println(sb);

        List<DicomObject> list = DcmQR.doMain(args, new ICallbackObject()
        {
            public void doCallback(Object obj)
            {
            }
        });

        DcmQR_QueryStudy.output(list);
    }

    public static void output(List<DicomObject> list)
    {
        System.out.println("\n\n******************************SIZE: " + list.size());
        for (int i = 0; i < list.size(); i++)
        {
            DicomObject dcmObj = list.get(i);
            String pid = dcmObj.getString(Tag.PatientID);
            if (pid == null || pid.isEmpty())
            {
                continue;
            }

            System.out.println(list.get(i));
        }
    }

}
