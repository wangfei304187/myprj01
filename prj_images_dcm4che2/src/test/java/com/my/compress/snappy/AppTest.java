package com.my.compress.snappy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.xerial.snappy.BitShuffle;
import org.xerial.snappy.Snappy;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testApp() {
		assertTrue(true);
	}

	public void test01() throws IOException {
		String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of "
				+ "Snappy, a fast compresser/decompresser.";
		byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
		byte[] uncompressed = Snappy.uncompress(compressed);
		String result = new String(uncompressed, "UTF-8");
		System.out.println(result);
	}

	public void test02() throws IOException {
		int[] data = new int[] { 1, 3, 34, 43, 34 };
		byte[] shuffledByteArray = BitShuffle.shuffle(data);
		byte[] compressed = Snappy.compress(shuffledByteArray);
		byte[] uncompressed = Snappy.uncompress(compressed);
		int[] result = BitShuffle.unshuffleIntArray(uncompressed);
		System.out.println(Arrays.toString(result));
	}
	
	public void test03()
	{
		String basePath = "/home/wf/xx";
		long lo1 = System.currentTimeMillis();
		doCompress(new File(basePath + "/java_pid1634.hprof"), new File(basePath + "/test.snappy"));
		long lo2 = System.currentTimeMillis();
		doUncompress(new File(basePath + "/test.snappy"), new File(basePath + "/a.out"));
		long lo3 = System.currentTimeMillis();
		System.out.println("doCompress, elapsed time: " + (lo2-lo1)/1000f + "s");
		System.out.println("doUncompress, elapsed time: " + (lo3-lo2)/1000f + "s");
	}
	
	public void doCompress(File src, File dest)
	{
		byte[] buffer = new byte[1024 * 1024 * 8];
		FileInputStream  fi = null;
		FileOutputStream fo = null;
		SnappyOutputStream sout = null;
		try
		{
		    fi = new FileInputStream(src); 
		    fo = new FileOutputStream(dest);
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
	
	public void doUncompress(File compressedFile, File outFile)
	{
		byte[] buffer = new byte[1024 * 1024 * 8];
		FileInputStream  fi = null;
		FileOutputStream fo = null;
		SnappyInputStream sin = null;
		try
		{
		    fo = new FileOutputStream(outFile);
		    fi = new FileInputStream(compressedFile);
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

}
