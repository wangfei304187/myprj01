package com.my.images;

import org.mindrot.jbcrypt.BCrypt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
}
