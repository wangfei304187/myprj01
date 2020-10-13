package com.my.images;

import org.mindrot.jbcrypt.BCrypt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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

        System.out.println("out[" + total + "]=" + dp[total]);
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
}
