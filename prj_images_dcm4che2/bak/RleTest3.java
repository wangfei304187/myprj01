package com.my.images;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RleTest3
{
    public static void main(String[] args)
    {
        // RleTest3.test1();
        RleTest3.test2();
    }

    public static void test1()
    {
        /*@formatter:off*/
        byte[][] in = new byte[][]{
                new byte[] { 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'C', 'C' },

                new byte[] { 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'C'},
                new byte[] { 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C'},
                new byte[] { 'A', 'A', 'A', 'B', 'B', 'C'},
                new byte[] { 'A', 'A', 'A', 'B', 'C'},
                new byte[] { 'A', 'A', 'B', 'C'},

                new byte[] { 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'C', 'C' },
                new byte[] { 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'C', 'C', 'C' },
                new byte[] { 'A', 'B', 'B', 'C', 'C', 'C' },
                new byte[] { 'A', 'B', 'C', 'C', 'C' },
                new byte[] { 'A', 'B', 'C', 'C' },

                new byte[] { 'A', 'B', 'C'},
                new byte[] { 'A', 'B' }
                //, new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
                //, new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -68, -68, 0, 0, 0, 0, -68, -68, 0, 0, -68, -68, -68, -68, -68, -68, -68, 0, 0, 0, 0}
                ,
                new byte[] {
                        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
                        ,
                        0,0,0,-68,-68,0,0,0,0,-68,-68,0,0,-68,-68,-68,-68,-68,-68,-68,0,0,0,0,-68,-68,-68,-68,-68,0,0,0,0,-68,-68,-68,-68,-68,-68,-68,-68,0,0,-68,0,0,0,0,0,0,-68,0,0,0,0,-68,-68,-68,0,0,0,0,0,-68,-68,0,0,0,-68,-68,0,0,0,-68,-68,0,0,0,-68,-68,0,0,0,-68,-68,-68,-68,-68,-68,-68,-68,0,0,0,0,0,0,0,0,0,0,0,0,0,-68,-68,-68,-68,-68,0,0,0,0,0,0,-68,-68,-68,0,0,0,0,0,-68,-68,0,0,0,-68,-68,0,0,0,0,0,-68,-68,-68,0,0,0,0,0,-68,-68,-68,-68,-68,0,0,0,0,0,-68,-68,-68,-68,-68,-68,-68,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-68,-68,-68,-68,-68,-68,0,0,0,0,-68,-68,-68,-68,-68,-68,-68,0,0,0,-68,-68,0,0,0,-68,-68,0,0,0,0,0,-68,-68,-68,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-68,-68,0,0,0,0,-68,-68,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-88,-88,-88,0,0,0,0,0,0,-88,-88,0,0,0,0,0,0,0,0,0,0,0,0,-88,-88,-88,-88,0,0,0,0,0,-88,-88,0,0,0,0,0,-88,-88,0,0,0,0,0,0,-88,-88,-88,0,0,0,0,0,0,0,0,0,0,0
                }
                // , new byte[] { 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B', 'B' }

        };
        /*@formatter:on*/

        for (int i = 0; i < in.length; i++)
        {
            System.out.println("Case " + i + ", input len=" + in[i].length);
            RleTest3.doTest(in[i], 608);

            if (i % 5 == 0)
            {
                System.out.println();
            }
        }
    }

    public static void test2()
    {
        byte[] bs = RleTest3.readBytesFromFile();
        RleTest3.doTest(bs, 512);
    }

    public static void doTest(byte[] in, int lineWidth)
    {
        System.out.println("in doTest, in.len=" + in.length + ", lineWidth=" + lineWidth);
        byte[] out = new byte[280000];
        RleTest3.rle(out, in, lineWidth);
        for (int i = 0; i < out.length; i++)
        {
            System.out.print(out[i] + " ");
        }
        System.out.println();
    }

    public static void rle(byte[] out, byte[] inputBytes, int sizePerConversion)
    {
        int outStartOffset = 0;
        int inStartOffset = 0;
        while (true)
        {
            if (inStartOffset + sizePerConversion < inputBytes.length)
            {
                // System.out.println("*** outStartOffset=" + outStartOffset + ", inStartOffset=" + inStartOffset
                // + ", sizePerConversion=" + sizePerConversion + ", inputBytes.length=" + inputBytes.length);

                outStartOffset = RleTest3.rle(out, outStartOffset, inputBytes, inStartOffset, sizePerConversion);
                inStartOffset += sizePerConversion;
            }
            else if (inStartOffset + sizePerConversion == inputBytes.length)
            {
                System.out.println("*** outStartOffset=" + outStartOffset + ", inStartOffset=" + inStartOffset
                        + ", sizePerConversion=" + sizePerConversion + ", inputBytes.length=" + inputBytes.length);

                outStartOffset = RleTest3.rle(out, outStartOffset, inputBytes, inStartOffset, sizePerConversion);
                inStartOffset += sizePerConversion;

                break;
            }
            else
            {
                sizePerConversion = inputBytes.length - inStartOffset;

                System.out.println("*** outStartOffset=" + outStartOffset + ", inStartOffset=" + inStartOffset
                        + ", sizePerConversion=" + sizePerConversion + ", inputBytes.length=" + inputBytes.length);

                outStartOffset = RleTest3.rle(out, outStartOffset, inputBytes, inStartOffset, sizePerConversion);
                inStartOffset += sizePerConversion;
                break;
            }
        }
    }

    public static int rle(byte[] out, int outStartOffset, byte[] inputBytes, int inStartOffset, int sizePerConversion)
    {
        int LIMIT = 128;
        // 0 -- INIT, 1 -- LITERAL RUN, 2 -- REPLICATE RUN
        int MODE_INIT = 0;
        int MODE_LITERAL = 1;
        int MODE_REPLICATE = 2;

        int curMode = MODE_INIT;

        int outCntIdx = 0 + outStartOffset;
        int outPos = outCntIdx + 1;

        int inPos = 0 + inStartOffset;
        int inPosNext = inPos + 1;

        int count = 0;
        int count1 = 1;

        // int inputEnd = inputBytes.length;
        int inputEnd = inStartOffset + sizePerConversion;

        while (inPos < inputEnd)
        {
            byte curr = inputBytes[inPos];
            byte next;
            if (inPosNext < inputEnd)
            {
                next = inputBytes[inPosNext];
            }
            else
            {
                if (curMode == MODE_INIT)
                {
                    out[outCntIdx] = 0;
                }
                else if (curMode == MODE_LITERAL)
                {
                    out[outCntIdx]++;
                }
                else
                {
                    // do nothing
                }

                out[outPos] = curr;

                outPos++;
                outCntIdx = outPos;
                outPos = outCntIdx + 1;
                count = 0;
                count1 = 1;
                break;
            }

            if (next != curr) // MODE_LITERAL
            {
                if (curMode == MODE_REPLICATE) // 2 -- REPLICATE RUN
                {
                    inPos++;
                    inPosNext = inPos + 1;

                    outCntIdx += 2;
                    outPos = outCntIdx + 1;
                    count = 0;
                    count1 = 1;

                    curMode = MODE_INIT;
                    continue;
                }

                curMode = MODE_LITERAL;

                if (count + 1 <= LIMIT)
                {
                    count++;

                    out[outCntIdx] = (byte) (count - 1);
                    out[outPos] = inputBytes[inPos];
                    outPos++;

                    inPos++;
                    inPosNext++;
                }
                else
                {
                    outCntIdx += count + 1;
                    outPos = outCntIdx + 1;
                    count = 0;
                    count1 = 1;

                    curMode = MODE_INIT;
                    continue;
                }
            }
            else
            { // Case2: next == curr, MODE_REPLICATE

                if (curMode == MODE_LITERAL) // 1 -- LITERAL RUN
                {
                    // inPos++;

                    outCntIdx = outPos;
                    outPos = outCntIdx + 1;
                    count = 0;
                    count1 = 1;

                    curMode = MODE_INIT;
                    continue;
                }

                curMode = MODE_REPLICATE;

                if (count1 + 1 <= LIMIT)
                {
                    // count++;

                    out[outCntIdx] = (byte) (-(count1 + 1) + 1);
                    out[outPos] = inputBytes[inPos];

                    count1++;

                    inPos++;
                    inPosNext++;
                }
                else
                {
                    outCntIdx += 2;
                    outPos = outCntIdx + 1;
                    count = 0;
                    count1 = 1;

                    inPos++;
                    inPosNext++;

                    curMode = MODE_INIT;
                    continue;
                }
            }

        } // end while

        return outCntIdx;

    } // end rle

    public static byte[] readBytesFromFile()
    {
        BufferedReader br = null;
        try
        {
            List<Byte> list = new ArrayList<Byte>();
            int lineNum = 0;

            br = new BufferedReader(new FileReader("bytes.txt"));
            String line = br.readLine();
            while (line != null)
            {
                lineNum++;
                RleTest3.parseLine(line, list);
                line = br.readLine();
            }

            byte[] bs = new byte[list.size()];
            for (int i = 0; i < bs.length; i++)
            {
                bs[i] = list.get(i);
            }

            System.out.println("read line cnt: " + lineNum);
            System.out.println("total bytes: " + bs.length);
            return bs;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }

    private static void parseLine(String line, List<Byte> list)
    {
        int cnt = 0;
        String[] strs = line.split(",");
        for (String string : strs)
        {
            if (!string.isEmpty())
            {
                byte b = Byte.parseByte(string);
                list.add(b);
                cnt++;
            }
        }
        // System.out.println("line byte cnt: " + cnt);
    }

    public static int rle2(byte[] out, int outStartOffset, byte[] inputBytes, int inStartOffset, int sizePerConversion)
    {
        int LIMIT = 128;
        // 0 -- INIT, 1 -- LITERAL RUN, 2 -- REPLICATE RUN
        int MODE_INIT = 0;
        int MODE_LITERAL = 1;
        int MODE_REPLICATE = 2;

        int curMode = MODE_INIT;

        int outCntIdx = 0 + outStartOffset;
        int outPos = outCntIdx + 1;

        int inPos = 0 + inStartOffset;
        int inPosNext = inPos + 1;

        int count = 0;
        int count1 = 1;

        // int inputEnd = inputBytes.length;
        int inputEnd = inStartOffset + sizePerConversion;

        while (inPos < inputEnd)
        {
            byte curr = inputBytes[inPos];
            byte next;
            if (inPosNext < inputEnd)
            {
                next = inputBytes[inPosNext];
            }
            else
            {
                if (curMode == MODE_INIT)
                {
                    out[outCntIdx] = 0;
                }
                else if (curMode == MODE_LITERAL)
                {
                    out[outCntIdx]++;
                }
                else
                {
                    // do nothing
                }

                out[outPos] = curr;

                outPos++;
                outCntIdx = outPos;
                outPos = outCntIdx + 1;
                count = 0;
                count1 = 1;
                break;
            }

            if (next != curr) // MODE_LITERAL
            {
                if (curMode == MODE_REPLICATE) // 2 -- REPLICATE RUN
                {
                    inPos++;
                    inPosNext = inPos + 1;

                    outCntIdx += 2;
                    outPos = outCntIdx + 1;
                    count = 0;
                    count1 = 1;

                    curMode = MODE_INIT;
                    continue;
                }

                curMode = MODE_LITERAL;

                if (count + 1 <= LIMIT)
                {
                    count++;

                    out[outCntIdx] = (byte) (count - 1);
                    out[outPos] = inputBytes[inPos];
                    outPos++;

                    inPos++;
                    inPosNext++;
                }
                else
                {
                    outCntIdx += count + 1;
                    outPos = outCntIdx + 1;
                    count = 0;
                    count1 = 1;

                    curMode = MODE_INIT;
                    continue;
                }
            }
            else
            { // Case2: next == curr, MODE_REPLICATE

                if (curMode == MODE_LITERAL) // 1 -- LITERAL RUN
                {
                    // inPos++;

                    outCntIdx = outPos;
                    outPos = outCntIdx + 1;
                    count = 0;
                    count1 = 1;

                    curMode = MODE_INIT;
                    continue;
                }

                curMode = MODE_REPLICATE;

                if (count1 + 1 <= LIMIT)
                {
                    // count++;

                    out[outCntIdx] = (byte) (-(count1 + 1) + 1);
                    out[outPos] = inputBytes[inPos];

                    count1++;

                    inPos++;
                    inPosNext++;
                }
                else
                {
                    outCntIdx += 2;
                    outPos = outCntIdx + 1;
                    count = 0;
                    count1 = 1;

                    inPos++;
                    inPosNext++;

                    curMode = MODE_INIT;
                    continue;
                }
            }

        } // end while

        return outCntIdx;

    }

}
