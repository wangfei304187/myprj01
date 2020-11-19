package com.my.snappy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.xerial.snappy.BitShuffle;
import org.xerial.snappy.Snappy;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import junit.framework.TestCase;

public class Test01 extends TestCase {

	public void test01() throws UnsupportedEncodingException, IOException {
		String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of "
		        + "Snappy, a fast compresser/decompresser.";
		byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
		byte[] uncompressed = Snappy.uncompress(compressed);

		String result = new String(uncompressed, "UTF-8");
		System.out.println(result);
	}

	public void test02() throws IOException
	{
		int[] data = new int[] {1, 3, 34, 43, 34};
		byte[] shuffledByteArray = BitShuffle.shuffle(data);
		byte[] compressed = Snappy.compress(shuffledByteArray);
		byte[] uncompressed = Snappy.uncompress(compressed);
		int[] result = BitShuffle.unshuffleIntArray(uncompressed);

		System.out.println(Arrays.toString(result));
	}
	
	public void test03() throws IOException {
		
		long lo1 = System.currentTimeMillis();
		compress();
		long lo2 = System.currentTimeMillis();
		// doZipFile();
		long lo3 = System.currentTimeMillis();
		
//		0.32s
//		7.372s
		System.out.println((lo2 - lo1 ) / 1000f + "s");
		System.out.println((lo3 - lo2 ) / 1000f + "s");
	}
	
	public void test04()
	{
		
	}
	
	private void compress()
	{
		File file = new File("D:\\PPDownload\\国家底线\\国家底线 - 第1集.MP4"); //待压缩文件
		File out = new File("D:\\PPDownload\\", "test.snappy"); //压缩结果文件
//		File file = new File("D:\\testdcm\\1\\1.dcm");
//		File out = new File("D:\\testdcm\\1\\1.snappy");

		byte[] buffer = new byte[1024 * 1024 * 8];
		FileInputStream  fi = null;
		FileOutputStream fo = null;
		SnappyOutputStream sout = null;
		try
		{
		    fi = new FileInputStream(file); 
		    fo = new FileOutputStream(out);
		    sout = new SnappyOutputStream(fo);
		    while(true)
		    {
		        int count = fi.read(buffer, 0, buffer.length);
		        if(count == -1) { break; }
		        sout.write(buffer, 0, count);
		    }
		    sout.flush();
		}
		catch(Throwable ex)
		{
		    ex.printStackTrace();
		}
		finally 
		{
		    if(sout != null) {try { sout.close();} catch (Exception e) {}}
		    if(fi != null) { try { fi.close(); } catch(Exception x) {} }
		    if(fo != null) { try { fo.close(); } catch(Exception x) {} }
		}
	}
	
	public void uncompress()
	{
		File file = new File("xxx"); //待解压文件
		File out = new File("xxx");  //解压后文件

		byte[] buffer = new byte[1024 * 1024 * 8];
		FileInputStream  fi = null;
		FileOutputStream fo = null;
		SnappyInputStream sin = null;
		try
		{
		    fo = new FileOutputStream(out);
		    fi = new FileInputStream(file.getPath());
		    sin = new SnappyInputStream(fi);

		    while(true)
		    {
		        int count = sin.read(buffer, 0, buffer.length);
		        if(count == -1) { break; }
		        fo.write(buffer, 0, count);
		    }
		    fo.flush();
		}
		catch(Throwable ex)
		{
		    ex.printStackTrace();
		}
		finally 
		{
		    if(sin != null) { try { sin.close(); } catch(Exception x) {} }
		    if(fi != null) { try { fi.close(); } catch(Exception x) {} }
		    if(fo != null) { try { fo.close(); } catch(Exception x) {} }
		}
	}
	
	public void doZipFile() throws IOException
	{
		File file = new File("D:\\PPDownload\\国家底线\\国家底线 - 第1集.MP4"); //待压缩文件
        File out = new File("D:\\PPDownload\\", "test.zip") ;   // 定义压缩文件名称  
        
        byte[] buffer = new byte[1024 * 1024 * 8];
		FileInputStream  fi = null;
		FileOutputStream fo = null;
		ZipOutputStream zipOut = null;
		try
		{
		    fi = new FileInputStream(file); 
		    fo = new FileOutputStream(out);
		    zipOut = new ZipOutputStream(fo);
		    zipOut.putNextEntry(new ZipEntry(file.getName())) ; // 设置ZipEntry对象  
	        zipOut.setComment("comments") ;  // 设置注释  
		    while(true)
		    {
		        int count = fi.read(buffer, 0, buffer.length);
		        if(count == -1) { break; }
		        zipOut.write(buffer, 0, count);
		    }
		    zipOut.flush();
		}
		catch(Throwable ex)
		{
		    ex.printStackTrace();
		}
		finally 
		{
		    if(zipOut != null) {try { zipOut.close();} catch (Exception e) {}}
		    if(fi != null) { try { fi.close(); } catch(Exception x) {} }
		    if(fo != null) { try { fo.close(); } catch(Exception x) {} }
		}
	}
}
