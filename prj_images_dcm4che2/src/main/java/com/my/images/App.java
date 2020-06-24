package com.my.images;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args)
    {
        System.out.println("Hello World!");

        // DecimalFormat df4 = new DecimalFormat("0.0000");
        //
        // System.out.println(df4.format(1.234567890));
        // System.out.println(Double.parseDouble(df4.format(1.22300000000001)));

        // System.out.println(App.fmt4f("0.123456789"));
        // System.out.println(App.fmt4f("1.00000001"));
        // System.out.println(App.fmt4f("1.00"));
        // System.out.println(App.fmt4f("1.123456"));
        // System.out.println(App.fmt4f("1"));
        // System.out.println(App.fmt4f("1d"));

        /*@formatter:off*/
        byte[] lenByte = new byte[] {
                0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-4,-2,3,13,18,13,15,17,18,17,17,19,18,23,22,15,20,21,22,23,17,20,21,25,23,20,19,21,22,22,22,16,20,19,19,22,21,23,23,21,22,22,16,18,22,27,22,20,23,23,22,21,24,21,21,19,19,21,18,21,18,20,18,18,17,18,17,20,11,20,19,20,19,22,20,24,22,23,20,19,22,24,21,22,23,22,23,19,17,21,17,20,21,20,22,18,22,25,21,21,24,27,19,15,19,23,20,24,18,21,20,21,24,22,19,16,23,21,23,22,19,16,15,18,17,16,22,18,11,15,21,19,18,16,16,16,15,20,21,20,19,21,18,20,19,22,24,21,21,21,21,25,22,24,19,20,21,23,23,25,18,22,26,18,20,28,26,20,20,22,20,20,17,23,18,18,14,17,19,13,13,17,19,19,17,21,27,26,20,20,22,26,27,25,26,24
        };
        System.out.println("lenByte[] len: " + lenByte.length);

        byte[] bs = new byte[] {
                -127,0,-6,0,27,-4,-2,3,13,18,13,15,17,18,17,17,19,18,23,22,15,20,21,22,23,17,20,21,25,23,20,19,21,-2,22,101,16,20,19,19,22,21,23,23,21,22,22,16,18,22,27,22,20,23,23,22,21,24,21,21,19,19,21,18,21,18,20,18,18,17,18,17,20,11,20,19,20,19,22,20,24,22,23,20,19,22,24,21,22,23,22,23,19,17,21,17,20,21,20,22,18,22,25,21,21,24,27,19,15,19,23,20,24,18,21,20,21,24,22,19,16,23,21,23,22,19,16,15,18,17,16,22,18,11,15,21,19,18,-2,16,10,15,20,21,20,19,21,18,20,19,22,24,-3,21,50,25,22,24,19,20,21,23,23,25,18,22,26,18,20,28,26,20,20,22,20,20,17,23,18,18,14,17,19,13,13,17,19,19,17,21,27,26,20,20,22,26,27,25,26,24,22,22,23,27,27,24
        };
        /*@formatter:on*/
        System.out.println("byte[] len: " + bs.length);
        int total = 0;
        for (int i = 0; i < bs.length; i++)
        {
            byte b = bs[i];
            if (b < 0)
            {
                total += -b + 1;
                i = i + 1;
            }

            if (b >= 0)
            {
                total += b + 1;
                i = i + b + 1;
            }
        }
        System.out.println("total: " + total);

    }

    private static String fmt4f(String floatStr)
    {
        try
        {
            // return String.format("%.4f", Float.valueOf(floatStr));
            return App.fmtFloatNoMoreThan4f(Float.valueOf(floatStr));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return floatStr;
        }
    }

    private static String fmtFloatNoMoreThan4f(float f)
    {
        String s = String.format("%.4f", f);
        // int endIdx = s.length();
        // int idx = s.indexOf(".");
        // for (int i = s.length() - 1; i >= idx + 2; i--)
        // {
        // char ch = s.charAt(i);
        // if (ch == '0')
        // {
        // endIdx--;
        // }
        // }
        //
        // return s.substring(0, endIdx);
        return String.valueOf(Float.parseFloat(s));
    }
}
