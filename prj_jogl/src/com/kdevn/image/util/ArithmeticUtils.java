package com.kdevn.image.util;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.List;

public class ArithmeticUtils
{

    private ArithmeticUtils()
    {
    }

    public static double distanceSquare(Point p1, Point p2)
    {
        // horizontal
        if (p1.y == p2.y)
        {
            return (p2.x - p1.x + 1) * (p2.x - p1.x + 1);
        }

        // vertical
        if (p1.x == p2.x)
        {
            return (p2.y - p1.y + 1) * (p2.y - p1.y + 1);
        }

        // tilt
        return (p1.x - p2.x + 1) * (p1.x - p2.x + 1) + (p1.y - p2.y + 1) * (p1.y - p2.y + 1);
    }

    public static double distance(Point p1, Point p2)
    {
        // horizontal
        if (p1.y == p2.y)
        {
            return Math.abs(p2.x - p1.x + 1);
        }

        // vertical
        if (p1.x == p2.x)
        {
            return Math.abs(p2.y - p1.y + 1);
        }

        // tilt
        return Math.sqrt((p1.x - p2.x + 1) * (p1.x - p2.x + 1) + (p1.y - p2.y + 1) * (p1.y - p2.y + 1));
    }

    public static double distance(int x1, int y1, int x2, int y2)
    {
        // horizontal
        if (y1 == y2)
        {
            return Math.abs(x2 - x1 + 1);
        }

        // vertical
        if (x1 == x2)
        {
            return Math.abs(y2 - y1 + 1);
        }

        // tilt
        return Math.sqrt((x1 - x2 + 1) * (x1 - x2 + 1) + (y1 - y2 + 1) * (y1 - y2 + 1));
    }

    public static double distance(float x1, float y1, float x2, float y2)
    {
        // horizontal
        if (new Float(y1).compareTo(new Float(y2)) == 0)
        {
            return Math.abs(x2 - x1 + 1);
        }

        // vertical
        if (new Float(x1).compareTo(new Float(x2)) == 0)
        {
            return Math.abs(y2 - y1 + 1);
        }

        // tilt
        return Math.sqrt((x1 - x2 + 1) * (x1 - x2 + 1) + (y1 - y2 + 1) * (y1 - y2 + 1));
    }

    public static double distance2(float x1, float y1, float x2, float y2)
    {
        // horizontal
        if (new Float(y1).compareTo(new Float(y2)) == 0)
        {
            return Math.abs(x2 - x1);
        }

        // vertical
        if (new Float(x1).compareTo(new Float(x2)) == 0)
        {
            return Math.abs(y2 - y1);
        }

        // tilt
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    // 角p1p2p3
    public static double angle(Point p1, Point p2, Point p3)
    {
        double a = ArithmeticUtils.distance(p1, p3);
        double b = ArithmeticUtils.distance(p2, p1);
        double c = ArithmeticUtils.distance(p2, p3);
        if (b * c == 0)
        {
            return 0;
        }
        else
        {
            double r = (b * b + c * c - a * a) / (2 * b * c);
            if (r < -1)
            {
                r = -1;
            }
            else if (r > 1)
            {
                r = 1;
            }
            double arcAngle = Math.acos(r);
            if (String.valueOf(arcAngle).equals("NaN"))
            {
                return 0;
            }

            return arcAngle * 180 / Math.PI;
        }
    }

    // 角p1p2p3, try to fix bug 317 : 角度接近180度时，测量得到的角度值不准确
    public static double angleEx(Point p1, Point p2, Point p3)
    {
        double value = 0;

        double a = ArithmeticUtils.distance(p1, p3);
        double b = ArithmeticUtils.distance(p2, p1);
        double c = ArithmeticUtils.distance(p2, p3);
        if (b * c == 0)
        {
            value = 0;
        }
        else
        {
            double r = (b * b + c * c - a * a) / (2 * b * c);
            if (r < -1)
            {
                r = -1;
            }
            else if (r > 1)
            {
                r = 1;
            }
            double arcAngle = Math.acos(r);
            if (String.valueOf(arcAngle).equals("NaN"))
            {
                value = 0;
            }

            value = arcAngle * 180 / Math.PI;
        }

        if (value > 90)
        {
            // (symmetricP1X, symmetricP1Y) 为P1相对于P2的对称点
            int symmetricP1X = 2 * p2.x - p1.x;
            int symmetricP1Y = 2 * p2.y - p1.y;

            double acuteAngle = ArithmeticUtils.angle(new Point(symmetricP1X, symmetricP1Y), p2, p3);
            value = 180 - acuteAngle;
        }

        return value;
    }

    public static double angle(int x1, int y1, int x2, int y2, int x3, int y3)
    {
        // angle
        double angle12 = Math.atan2(y1 - y2, x1 - x2);
        double angle32 = Math.atan2(y3 - y2, x3 - x2);

        return (angle32 - angle12) * 180.0 / Math.PI;
    }

    public static double angle(float x1, float y1, float x2, float y2, float x3, float y3)
    {
        // angle
        double angle12 = Math.atan2(y1 - y2, x1 - x2);
        double angle32 = Math.atan2(y3 - y2, x3 - x2);

        return (angle32 - angle12) * 180.0 / Math.PI;
    }

    // p1到p2p3的距离
    public static double height(Point p1, Point p2, Point p3)
    {
        double alpha = ArithmeticUtils.angle(p1, p2, p3);
        double d = ArithmeticUtils.distance(p1, p2);
        return d * Math.sin(alpha * Math.PI / 180);
    }

    // Tests if the triangle p1p2p3 contains a point (x, y)
    public static boolean contain(Point p1, Point p2, Point p3, int x, int y)
    {
        int cnt = 0;

        // 多边形内一点引出的向右的射线
        Line2D.Float ray = new Line2D.Float();

        Line2D.Float line = new Line2D.Float();

        int maxX = p1.x > p2.x ? p1.x : p2.x;
        maxX = maxX > p3.x ? maxX : p3.x;

        ray.setLine(x, y, maxX, y);

        line.setLine(p1.x, p1.y, p2.x, p2.y);
        if (ArithmeticUtils.intersetValid(ray, line))
        {
            cnt++;
        }

        line.setLine(p1.x, p1.y, p3.x, p3.y);
        if (ArithmeticUtils.intersetValid(ray, line))
        {
            cnt++;
        }

        line.setLine(p2.x, p2.y, p3.x, p3.y);
        if (ArithmeticUtils.intersetValid(ray, line))
        {
            cnt++;
        }

        if (cnt % 2 != 0)
        {
            return true;
        }

        return false;
    }

    private static boolean intersetValid(Line2D ray, Line2D line)
    {
        if (ray.intersectsLine(line))
        {
            int y1 = (int) (line.getY1() + 0.5);
            int y2 = (int) (line.getY2() + 0.5);

            int y = (int) (ray.getY1() + 0.5);
            if (y > y1 && y > y2)
            {
                return false;
            }

            if (y < y1 && y < y2)
            {
                return false;
            }

            if (y > y1 && y < y2 || y < y1 && y > y2)
            {
                return true;
            }

            if (y == y1 && y2 > y)
            {
                // line is below
                return true;
            }

            if (y == y1 && y2 < y)
            {
                // line is above
                return false;
            }

            if (y == y2 && y1 > y)
            {
                // line is below
                return true;
            }

            if (y == y2 && y1 < y)
            {
                // line is above
                return false;
            }

        }

        return false;
    }

    public static int sum(int[] arr)
    {
        if (arr == null || arr.length == 0)
        {
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++)
        {
            sum += arr[i];
        }

        return sum;
    }

    // for debug
    public static long sum(short[] arr, boolean toUnshort)
    {
        if (arr == null || arr.length == 0)
        {
            return -1;
        }

        long sum = 0;
        for (int i = 0; i < arr.length; i++)
        {
            if (toUnshort)
            {
                sum += arr[i] & 0xFFFF;
            }
            else
            {
                sum += arr[i];
            }

        }

        return sum;
    }

    public static int[] minMax(List<Integer> li)
    {
        if (li == null || li.size() == 0)
        {
            return null;
        }

        int min = li.get(0);
        int max = li.get(0);
        if (li.size() > 1)
        {
            for (int i = 1; i < li.size(); i++)
            {
                int v = li.get(i);
                if (v < min)
                {
                    min = v;
                }

                if (v > max)
                {
                    max = v;
                }
            }
        }

        return new int[] { min, max };
    }

    public static Double mean(List<Integer> li)
    {
        if (li == null || li.size() == 0)
        {
            return null;
        }

        long t = 0;
        int size = li.size();
        for (int i = 0; i < size; i++)
        {
            t += li.get(i);
        }

        return t / (double) size;
    }

    // standard deviation
    public static Double sd(List<Integer> li)
    {
        if (li == null || li.size() == 0)
        {
            return null;
        }

        double t = 0;
        double meanValue = ArithmeticUtils.mean(li);
        for (int i = 0; i < li.size(); i++)
        {
            int v = li.get(i);
            t += (v - meanValue) * (v - meanValue);
        }

        return Math.sqrt(t / li.size());
    }

    public static Double sd(List<Integer> li, double meanValue)
    {
        if (li == null || li.size() == 0)
        {
            return null;
        }

        double t = 0;
        for (int i = 0; i < li.size(); i++)
        {
            int v = li.get(i);
            t += (v - meanValue) * (v - meanValue);
        }

        // return Math.sqrt(t);
        return Math.sqrt(t / li.size());
    }

    // theta < 0: 顺时针; theta > 0: 逆时针; (srcX, srcY)绕(rotateCX, rotateCY)旋转theta角度
    public static Point rotatePoint(int theta, int srcX, int srcY, int rotateCX, int rotateCY)
    {
        float degArc = (float) Math.toRadians(theta);
        float destX = (float) ((srcX - rotateCX) * Math.cos(degArc) + (srcY - rotateCY) * Math.sin(degArc) + rotateCX);
        float destY = (float) (-(srcX - rotateCX) * Math.sin(degArc) + (srcY - rotateCY) * Math.cos(degArc) + rotateCY);
        return new Point(Math.round(destX), Math.round(destY));
    }

    /*
     * 任意点(x,y)，绕一个坐标点(rx0,ry0)逆时针旋转RotaryAngle角度后的新的坐标设为(x', y')，公式：
     * x'= (x - rx0)*cos(RotaryAngle) + (y - ry0)*sin(RotaryAngle) + rx0 ;
     * y'=-(x - rx0)*sin(RotaryAngle) + (y - ry0)*cos(RotaryAngle) + ry0 ;
     */

    // theta < 0: 顺时针; theta > 0: 逆时针; (srcX, srcY)绕(rotateCX, rotateCY)旋转theta角度
    public static double[] rotatePoint(double theta, double srcX, double srcY, double rotateCX, double rotateCY)
    {
        double degArc = Math.toRadians(theta);
        double destX = (srcX - rotateCX) * Math.cos(degArc) + (srcY - rotateCY) * Math.sin(degArc) + rotateCX;
        double destY = -(srcX - rotateCX) * Math.sin(degArc) + (srcY - rotateCY) * Math.cos(degArc) + rotateCY;
        return new double[] { destX, destY };
    }

    // theta < 0: 顺时针; theta > 0: 逆时针; 点(srcX, srcY)不动，旋转坐标系theta角度，求点在新坐标系中的坐标
    public static double[] rotateCoordinateSystem(double theta, double srcX, double srcY)
    {
        double degArc = Math.toRadians(theta);
        double destX = srcX * Math.cos(degArc) - srcY * Math.sin(degArc);
        double destY = srcX * Math.sin(degArc) + srcY * Math.cos(degArc);
        return new double[] { destX, destY };
    }

    public static int getNearest(int[] sortedArr, int n)
    {
        int idx = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < sortedArr.length; i++)
        {
            int v = Math.abs(sortedArr[i] - n);
            if (v < min)
            {
                min = v;
                idx = i;
            }
        }

        return sortedArr[idx];
    }

    public static double getNearest(double[] sortedArr, double f)
    {
        int idx = 0;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < sortedArr.length; i++)
        {
            double v = Math.abs(sortedArr[i] - f);
            if (v < min)
            {
                min = v;
                idx = i;
            }
        }

        return sortedArr[idx];
    }

    public static double getNearest(double[] sortedArr, double f, float step)
    {
        int idx = 0;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < sortedArr.length; i++)
        {
            double v = Math.abs(sortedArr[i] - f);
            if (v < min)
            {
                min = v;
                idx = i;
            }
        }

        if (idx == 0 || idx == sortedArr.length - 1)
        {
            return ArithmeticUtils.findNearest(sortedArr[idx], f, step);
        }
        else
        {
            return sortedArr[idx];
        }
    }

    private static double findNearest(double refValue, double f, float step)
    {
        if (f > refValue)
        {
            int n = (int) Math.round((f - refValue) / step);
            return refValue + n * step;
        }
        else if (f < refValue)
        {
            int n = (int) Math.round((refValue - f) / step);
            return refValue - n * step;
        }
        else
        {
            return refValue;
        }
    }

    public static boolean exist(int[] arr, int v)
    {
        for (int i = 0; i < arr.length; i++)
        {
            int f = arr[i];
            if (f == v)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean exist(double[] arr, double v)
    {
        // -->2017/03/03 Modified. 0.0f == -0.0f ===> true
        // fix error "The range box of this recon. NeckA_4.5_B31.is not valid. Please adjust the them before continue."
        // return ArithmeticUtils.exist(arr, new Float(v));
        for (int i = 0; i < arr.length; i++)
        {
            double f = arr[i];
            if (f == v)
            {
                return true;
            }
        }
        return false;
        // <--
    }

    // -->2017/03/03 Deleted
    // public static boolean exist(float[] arr, Float v)
    // {
    // for (int i = 0; i < arr.length; i++)
    // {
    // Float f = arr[i];
    // if (f.equals(v))
    // {
    // return true;
    // }
    // }
    // return false;
    // }
    // <--

    public static int correction(int v, int min, int max)
    {
        if (v < min)
        {
            v = min;
        }

        if (v > max)
        {
            v = max;
        }

        return v;
    }

    public static String toString(int[] arr)
    {
        StringBuilder sb = new StringBuilder(100);
        for (int i = 0; i < arr.length; i++)
        {
            if (i != arr.length - 1)
            {
                sb.append(arr[i]).append(", ");
            }
            else
            {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public static String toString(double[] arr)
    {
        StringBuilder sb = new StringBuilder(100);
        for (int i = 0; i < arr.length; i++)
        {
            if (i != arr.length - 1)
            {
                sb.append(arr[i]).append(", ");
            }
            else
            {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

}
