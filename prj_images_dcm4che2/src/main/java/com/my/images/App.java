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
        byte[] bs = new byte[] {
                // -127,0,-127,0,-127,0,-127,0,-95,0,-2,0,-1,-68,-3,0,-1,-68,-1,0,-6,-68,-3,0,-4,-68,-3,0,-7,-68,-1,0,1,-68,0,-4,0,1,-68,0,-2,0,-2,-68,-4,0,-1,-68,-2,0,-1,-68,-2,0,-1,-68,-2,0,-1,-68,-2,0,-7,-68,-12,0,-4,-68,-5,0,-2,-68,-4,0,-1,-68,-2,0,-1,-68,-4,0,-2,-68,-4,0,-4,-68,-4,0,-6,-68,-72,0,-5,-68,-3,0,-6,-68,-2,0,-1,-68,-2,0,-1,-68,-4,0,-2,-68,-127,0,-16,0,-1,-68,-3,0,-1,-68,-125,0,-2,-88,-5,0,-1,-88,-11,0,-3,-88,-4,0,-1,-88,-4,0,-1,-88,-5,0,-2,-88,-10,0,-2,0,-1,-68,-3,0,-1,-68,-1,0,-6,-68,-2,0,-6,-68,-2,0,-7,-68,-1,0,-1,-68,-3,0,-1,-68,-2,0,-4,-68,-3,0,-1,-68,-2,0,-1,-68,-2,0,-1,-68,-2,0,-1,-68,-2,0,-7,-68,-11,0,-6,-68,-3,0,-4,-68,-3,0,-1,-68,-2,0,-1,-68,-3,0,-4,-68,-3,0,-5,-68,-3,0,-6,-68,-72,0,-6,-68,-2,0,-6,-68,-2,0,-1,-68,-2,0,-1,-68,-3,0,-3,-68,-127,0,-16,0,-1,-68,-3,0,-1,-68,-125,0,-2,-88,-5,0,-1,-88,-9,0,-7,-88,-2,0,-1,-88,-3,0,-1,-88,-6,0,-2,-88,-10,0,-2,0,-1,-68,-3,0,-1,-68,-1,0,-1,-68,-7,0,-1,-68,-3,0,1,-68,0,-4,0,-1,-68,-4,0,-2,-68,-1,0,-2,-68,-1,0,-1,-68,-2,0,-1,-68,-2,0,-1,-68,-2,0,-1,-68,-2,0,-2,-68,-1,0,-1,-68,-5,0,-1,-68,-14,0,-1,-68,-3

                -2,0,-1,-68,-3,0,-1,-68,-1,0,-1,-68,-7,0,-1,-68,-3,0,1,-68,0,-4,0,-1,-68,-4,0,-2,-68,-1,0,-2,-68,-1,0,-1,-68,-2,0,-1,-68,-2,0,-1,-68,-2,0,-1,-68,-2,0,-2,-68,-1,0,-1,-68,-5,0,-1,-68,-14,0,-1,-68,-3

        };
        /*@formatter:on*/
        System.out.println(bs.length);
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
                i = i + b;
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
