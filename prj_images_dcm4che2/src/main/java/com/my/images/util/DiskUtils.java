package com.my.images.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiskUtils {
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
}
