package cn.my.app.grpc.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils
{

    // public static void main(String[] args) throws Exception
    // {
    // ZipUtils.compress(new String[] { "/data/dicomImage/100166155827", "/data/dicomImage/100165745707/" },
    // "dicomImage.zip");
    //
    // ZipUtils.decompress("dicomImage.zip", "~/");
    // }

    public static void compress(String[] srcDir, String outDir) throws IOException
    {
        ZipOutputStream zos = null;
        OutputStream out = null;
        try
        {
            out = new FileOutputStream(new File(outDir));
            zos = new ZipOutputStream(out);
            for (String dir : srcDir)
            {
                File sourceFile = new File(dir);
                String name = sourceFile.getName();
                ZipUtils.compress(sourceFile, zos, name, true);
            }
        }
        finally
        {
            ZipUtils.safeClose(out);
            ZipUtils.safeClose(zos);
        }
    }

    // private static void compress(List<File> sourceFileList,
    // ZipOutputStream zos, boolean KeepDirStructure) throws IOException
    // {
    // for (File sourceFile : sourceFileList)
    // {
    // String name = sourceFile.getName();
    // ZipUtils.compress(sourceFile, zos, name, KeepDirStructure);
    // }
    // }

    private static void compress(File sourceFile, ZipOutputStream zos,
            String name, boolean KeepDirStructure) throws IOException
    {
        byte[] buf = new byte[2048];
        if (sourceFile.isFile())
        {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1)
            {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        }
        else
        {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0)
            {
                if (KeepDirStructure)
                {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }
            }
            else
            {
                for (File file : listFiles)
                {
                    if (KeepDirStructure)
                    {
                        ZipUtils.compress(file, zos, name + "/" + file.getName(),
                                KeepDirStructure);
                    }
                    else
                    {
                        ZipUtils.compress(file, zos, file.getName(), KeepDirStructure);
                    }

                }
            }
        }
    }

    public static void decompress(File zipFilePath, String outputPath) throws IOException
    {
        ZipInputStream zin = null;
        BufferedInputStream inputStream = null;
        try
        {
            zin = new ZipInputStream(new FileInputStream(zipFilePath), Charset.defaultCharset());
            inputStream = new BufferedInputStream(zin);
            String parent = outputPath;
            File outputFile = null;
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null && !entry.isDirectory())
            {
                outputFile = new File(parent, entry.getName());
                if (!outputFile.exists())
                {
                    new File(outputFile.getParent()).mkdirs();
                }

                FileOutputStream fout = null;
                BufferedOutputStream bout = null;
                try
                {
                    fout = new FileOutputStream(outputFile);
                    bout = new BufferedOutputStream(fout);
                    int b;
                    while ((b = inputStream.read()) != -1)
                    {
                        bout.write(b);
                    }
                }
                finally
                {
                    ZipUtils.safeClose(bout);
                    ZipUtils.safeClose(fout);
                }
            }

        }
        finally
        {

            ZipUtils.safeClose(inputStream);
            ZipUtils.safeClose(zin);
        }
    }

    private static void safeClose(InputStream in)
    {
        if (in != null)
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    private static void safeClose(OutputStream out)
    {
        if (out != null)
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
            }
        }
    }
}
