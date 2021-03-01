package com.my.test;

import java.util.Random;

/**
 * Hello world!
 *
 */
public class App
{
    // i=0, N=2, R=1, r=1
    // i=1, N=4, R=0, r=1
    // i=2, N=8, R=0, r=1
    // i=3, N=16, R=7, r=7
    // i=4, N=32, R=31, r=31
    // i=5, N=64, R=28, r=28
    // i=6, N=128, R=49, r=49
    // i=7, N=256, R=242, r=242
    // i=8, N=512, R=152, r=152
    // i=9, N=1024, R=191, r=191
    public static void main(String[] args)
    {
        Random random = new Random();
        int retryCount = 0;
        for (int i = 0; i < 10; i++)
        {
            retryCount = i;
            int N = 1 << retryCount + 1; // 2^retryCount + 1
            System.out.print("i=" + i + ", N=" + N + ", ");
            int R = random.nextInt(N);
            int r = Math.max(1, R);
            System.out.println("R=" + R + ", r=" + r);
        }

    }
}
