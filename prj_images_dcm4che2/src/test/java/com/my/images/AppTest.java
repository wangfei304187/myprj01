package com.my.images;

import org.mindrot.jbcrypt.BCrypt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppTest 
    extends TestCase
{
    public AppTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        assertTrue( true );
    }
    
    public void testBCrypt()
    {
    	String salt = BCrypt.gensalt();
    	System.out.println("salt=" + salt);
    	String pw_hash = BCrypt.hashpw("abcd", salt);
    	System.out.println("pw_hash=" + pw_hash);
    	
    	boolean r = BCrypt.checkpw("abcd", pw_hash);
    	System.out.println("r=" + r);
    }

    public void testC()
    {
        int[] cc = new int[] {1, 3, 5, 7};
        int total = 14;

        int[] dp = produceDp(cc, total);

        System.out.println("dp[" + total + "]=" + dp[total]);

        System.out.println("----------------------------------");

        detail(cc, total);

        System.out.println("==================================");
        detail(new int[] {1,2,5,11,20,50}, 23);

    }

    private int[] produceDp(int[] cc, int total) {
        int[] dp = new int[total + 1];

        for(int i=0; i<dp.length; i++) {
            dp[i] = -1;
        }

        for(int i=0; i<dp.length; i++) {

            if (i == 0) {
                dp[i] = 0;
                continue;
            }

            int min = Integer.MAX_VALUE;
            for(int j=0; j<cc.length; j++) {
                int c = cc[j];
                if (c <= i) {
                    int tmp = dp[i-c] + 1;
                    if (tmp < min)
                    {
                        min = tmp;
                    }
                }
            }
            dp[i] = min;
        }
        return dp;
    }

    private int[][] produceDp2(int[] cc, int total) {

        int[] dpArr = new int[total + 1];
        int[] ciArr = new int[total + 1];
        int[] cvArr = new int[total + 1];

        for(int i=0; i<dpArr.length; i++) {
            dpArr[i] = -1;
        }

        for(int i=0; i<dpArr.length; i++) {

            if (i == 0) {
                dpArr[i] = 0;
                continue;
            }

            int min = Integer.MAX_VALUE;
            int ccIdx = 0;
            for(int j=0; j<cc.length; j++) {
                int cv = cc[j];
                if (cv <= i) {
                    int tmp = dpArr[i-cv] + 1;
                    if (tmp < min)
                    {
                        min = tmp;
                        ccIdx = j;
                    }
                }
            }
            dpArr[i] = min;
            ciArr[i] = ccIdx;
            cvArr[i] = cc[ccIdx];
        }

        int[][] result = new int[3][];
        result[0] = dpArr;
        result[1] = ciArr;
        result[2] = cvArr;
        return result;
    }

    private void detail(int[] cc, int total) {
        int[][] result = produceDp2(cc, total);
        int[] dp2 = result[0];
        int[] ciArr = result[1];
        int[] cvArr = result[2];

        System.out.println("dpArr: " + Arrays.toString(dp2));
        System.out.println("ciArr: " + Arrays.toString(ciArr));
        System.out.println("cvArr: " + Arrays.toString(cvArr));
        System.out.println("dp2[" + total + "]=" + dp2[total]);

        // total == ccIdxes.length-1
        for(int k=total; k>0; )
        {
            // both is ok
//            int ccIdx = ciArr[k];
//            int cv = cc[ccIdx];
            int cv = cvArr[k];

            System.out.print(cv + " ");
            k = k - cv;
        }
        System.out.println();
    }
}
