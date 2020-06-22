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

        System.out.println(App.fmt4f("0.123456789"));
        System.out.println(App.fmt4f("1.00000001"));
        System.out.println(App.fmt4f("1.00"));
        System.out.println(App.fmt4f("1.123456"));
        System.out.println(App.fmt4f("1"));
        System.out.println(App.fmt4f("1d"));

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
