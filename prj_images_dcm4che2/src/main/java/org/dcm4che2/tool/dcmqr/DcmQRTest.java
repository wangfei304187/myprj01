package org.dcm4che2.tool.dcmqr;

import java.util.List;

import org.dcm4che2.data.DicomObject;

public class DcmQRTest
{

    public static void main(String[] args)
    {
        args = new String[] {
                "QRSCP@www.dicomserver.co.uk:104"
                , "-P"
                , "-qStudyDate=20200702-20200703"
                // , "-qPatientName=Trest^Zzest^^^"
                // , "-qPatientID=Test"
                , "-r", "PatientID"
                , "-r", "PatientName"
                , "-r", "PatientAge"
                , "-r", "PatientSex"
                , "-r", "PatientBirthDate"
                , "-r", "StudyDate"
                , "-r", "StudyTime"
                , "-r", "StudyDescription"
        };

        List<DicomObject> list = DcmQR.doMain(args, new ICallbackObject()
        {
            public void doCallback(Object obj)
            {
            }
        });

        DcmQRTest.output(list);
    }

    public static void output(List<DicomObject> list)
    {
        System.out.println("SIZE: " + list.size());
        for (int i = 0; i < list.size(); i++)
        {
            if (i > 3)
            {
                break;
            }

            System.out.println(list.get(i));
        }
    }

}
