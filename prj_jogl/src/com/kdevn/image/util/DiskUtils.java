package com.kdevn.image.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;

public class DiskUtils
{

    private DiskUtils()
    {
    }

    public static List<DicomObject> readDcmFromDisk(File dir) throws IOException
    {
        List<File> fileLi = new ArrayList<File>();
        DiskUtils.listFiles(dir, fileLi);

        List<DicomObject> li = new ArrayList<DicomObject>();
        DcmUtils du = new DcmUtils();
        for (File file : fileLi)
        {
            DicomObject dcmObj = du.readDicomObject(new FileInputStream(file));
            li.add(dcmObj);
        }

        DiskUtils.doSort(li);

        return li;
    }

    public static void doSort(List<? extends DicomObject> li)
    {
        /*@formatter:off*/
        Collections.sort(li, new Comparator<DicomObject>() {
            @Override
            public int compare(DicomObject o1, DicomObject o2)
            {
                return DiskUtils.doCompare(o1, o2);
            }
        });
        /*@formatter:on*/
    }

    public static int doCompare(DicomObject o1, DicomObject o2)
    {
        long r = 0;
        String prefix = "P";
        String p1 = o1.getString(Tag.PatientID);
        String p2 = o2.getString(Tag.PatientID);
        r = DiskUtils.compareDcmImgIds(p1, p2, prefix);

        if (r == 0)
        {
            prefix = "DicomStudyInstanceUID";
            String study1 = o1.getString(Tag.StudyInstanceUID);
            String study2 = o2.getString(Tag.StudyInstanceUID);
            // prefix = "S";
            // String study1 = o1.getString(Tag.StudyID);
            // String study2 = o2.getString(Tag.StudyID);
            r = DiskUtils.compareDcmImgIds(study1, study2, prefix);
        }

        if (r == 0)
        {
            prefix = "DicomSeriesInstanceUID";
            String study1 = o1.getString(Tag.SeriesInstanceUID);
            String study2 = o2.getString(Tag.SeriesInstanceUID);
            // prefix = "R";
            // String study1 = o1.getString(Tag.SeriesNumber);
            // String study2 = o2.getString(Tag.SeriesNumber);
            r = DiskUtils.compareDcmImgIds(study1, study2, prefix);
        }

        if (r == 0)
        {
            int imageNumber1 = o1.getInt(Tag.InstanceNumber);
            int imageNumber2 = o2.getInt(Tag.InstanceNumber);
            r = imageNumber1 - imageNumber2;
        }

        return (int) r;
    }

    public static void listFiles(File f, List<File> li)
    {
        if (f.isDirectory())
        {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                File tmpFile = files[i];
                DiskUtils.listFiles(tmpFile, li);
            }
        }
        else if (f.isFile())
        {
            li.add(f);
        }
    }

    public static List<File> listFiles(File dir)
    {
        List<File> result = new ArrayList<File>();
        if (dir.isDirectory())
        {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                File tmpFile = files[i];
                DiskUtils.listFiles(tmpFile, result);
            }
        }

        // do sort, 将文件名解析为数字，按数字大小排序
        /*@formatter:off*/
        Collections.sort(result, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2)
            {
                // 案1
                if(o1.getName().endsWith(".dcm"))
                {
                    try
                    {
                        int n1 = Integer.parseInt(o1.getName().substring(0, o1.getName().length()-4));
                        int n2 = Integer.parseInt(o2.getName().substring(0, o2.getName().length()-4));
                        return n1 - n2;
                    }
                    catch(NumberFormatException e)
                    {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
                else
                {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
        /*@formatter:on*/

        return result;
    }

    private static long compareDcmImgIds(String id1, String id2, String prefix)
    {
        // -->2016/06/18 Deleted
        // if (id1.startsWith(prefix) && id2.startsWith(prefix))
        // {
        // long l1 = Long.parseLong(id1.substring(prefix.length()));
        // long l2 = Long.parseLong(id2.substring(prefix.length()));
        //
        // return l1 - l2;
        // }
        // <--

        return id1.compareTo(id2);
    }

}
