package org.dcm4che2.tool.dcmqr;

import java.util.List;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;

public class DcmQR_CGetStudy
{
    public static void main(String[] args)
    {
        // CGet on study level

        args = new String[] {
                "-L"
                , "BMQRSCU:11113"
                , "QRSCP@www.dicomserver.co.uk:104"
                , "-cget"
                , "-qStudyDate=20200609-20200609"
                // , "-qModalitiesInStudy=MR"
                // , "-qPatientName=Trest^Zzest^^^"
                // , "-qPatientID=Test"
                , "-qStudyInstanceUID=1.2.826.0.1.3680043.8.852.123456789.26"
                , "-cstore", "MR"
                , "-cstoredest", "/tmp/dcm01"
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

        DcmQR_CGetStudy.output(list);
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
