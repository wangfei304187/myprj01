package com.my.images;

public class Rle
{
    private int INVALI_VALUE = Integer.MIN_VALUE;
    private int LIMIT = 128;

    private byte[] out;
    private int oStartOffset;
    private int oCntIdx;
    private int oPos;

    private byte[] in;
    private int iStartOffset;
    private int iEnd;
    private int iPos;

    private int count;
    private int count2;
    private int count3;

    private int sizePerConversion;

    private Mode prevMode;
    private Mode curMode;

    private enum Mode
    {
        INIT,
        LITERAL,
        REPLICATE1,
        REPLICATE2,
        REPLICATE3
    }

    // private InitDetail initDetail = InitDetail.InitNoraml;
    //
    // private enum InitDetail
    // {
    // InitNoraml,
    // InitReset
    // }

    public Rle(byte[] out, int oStartOffset, byte[] in, int iStartOffset, int sizePerConversion)
    {
        this.out = out;
        this.oStartOffset = oStartOffset;
        this.in = in;
        this.iStartOffset = iStartOffset;
        this.sizePerConversion = sizePerConversion;

        oCntIdx = 0 + oStartOffset;
        oPos = oCntIdx + 1;

        iPos = 0 + iStartOffset;
        iEnd = iStartOffset + sizePerConversion;

        curMode = Mode.INIT;

        resetCount();
    }

    // private void reset()
    // {
    // oCntIdx = oPos;
    // oPos = oCntIdx + 1;
    // curMode = Mode.INIT;
    // resetCount();
    // }

    private void resetCount()
    {
        count = 0;
        count2 = 1;
        count3 = 2;
    }

    public int doRun()
    {
        while (iPos < iEnd)
        {
            int prev2_a0 = INVALI_VALUE;
            if (iPos - 2 >= iStartOffset)
            {
                prev2_a0 = in[iPos - 2];
            }
            int prev_a0 = INVALI_VALUE;
            if (iPos - 1 >= iStartOffset)
            {
                prev_a0 = in[iPos - 1];
            }

            byte a0 = in[iPos];

            int a1 = INVALI_VALUE;
            if (iPos + 1 < iEnd)
            {
                a1 = in[iPos + 1];
            }
            int a2 = INVALI_VALUE;
            if (iPos + 2 < iEnd)
            {
                a2 = in[iPos + 2];
            }

            Mode mode;
            if (curMode == Mode.INIT)
            {
                if (prev2_a0 == a0 && prev_a0 == a0 && a0 != a2)
                {
                    if (a0 == a1)
                    {
                        curMode = Mode.REPLICATE2;
                    }
                    else
                    {
                        curMode = Mode.REPLICATE1;
                    }
                }
                else
                {
                    mode = whichMode(prev2_a0, prev_a0, a0, a1, a2);
                    updateMode(mode); // update current mode
                }
            }
            else
            {
                mode = whichMode(prev2_a0, prev_a0, a0, a1, a2);
                if (curMode != mode)
                {
                    updateMode(mode); // update current mode, update index pointer
                    resetCount();
                    continue;
                }
            }

            if (curMode == Mode.LITERAL)
            {
                if (count + 1 <= LIMIT)
                {
                    count++;
                    out[oCntIdx] = (byte) (count - 1);
                    out[oPos++] = in[iPos++];

                    if (iPos >= iEnd)
                    {
                        oCntIdx = oPos;
                        oPos = oCntIdx + 1;
                    }
                }
                else
                {
                    oCntIdx = oPos;
                    oPos = oCntIdx + 1;

                    prevMode = curMode;
                    curMode = Mode.INIT;
                    resetCount();

                    continue;
                }
            }
            else if (curMode == Mode.REPLICATE3)
            {
                if (count3 + 1 <= LIMIT)
                {
                    out[oCntIdx] = (byte) (-(count3 + 1) + 1);
                    out[oPos] = in[iPos++];

                    count3++;

                    if (iPos >= iEnd)
                    {
                        oPos++;
                        oCntIdx = oPos;
                        oPos = oCntIdx + 1;
                    }
                }
                else
                {
                    oPos++;
                    oCntIdx = oPos;
                    oPos = oCntIdx + 1;

                    iPos = iPos + 2;

                    prevMode = curMode;
                    curMode = Mode.INIT;
                    resetCount();

                    continue;
                }
            }
            else if (curMode == Mode.REPLICATE2)
            {
                if (count2 + 1 <= LIMIT)
                {
                    out[oCntIdx] = (byte) (-(count2 + 1) + 1);
                    out[oPos] = in[iPos++];

                    count2++;

                    oPos++;
                    oCntIdx = oPos;
                    oPos = oCntIdx + 1;

                    iPos = iPos + 1;

                    prevMode = curMode;
                    curMode = Mode.INIT;
                    resetCount();
                }
                else
                {
                    throw new IllegalArgumentException("doRun error. curMode=" + curMode
                            + ", count2=" + count2 + ", LIMIT=" + LIMIT);
                }
            }
            else if (curMode == Mode.REPLICATE1)
            {
                out[oCntIdx] = 0;
                out[oPos] = in[iPos++];

                // updateMode(Mode.INIT);
                // resetCount();
                // continue;

                oPos++;
                oCntIdx = oPos;
                oPos = oCntIdx + 1;

                prevMode = curMode;
                curMode = Mode.INIT;
                resetCount();
            }
            else
            {
                throw new IllegalArgumentException("doRun error. curMode=" + curMode);
            }
        } // end while

        return oCntIdx;
    }

    private void updateMode(Mode mode)
    {
        if (curMode == Mode.INIT)
        {
            if (mode == Mode.LITERAL
                    || mode == Mode.REPLICATE1
                    || mode == Mode.REPLICATE2
                    || mode == Mode.REPLICATE3)
            {
                // oPos++;
                // oCntIdx = oPos;
                // oPos = oCntIdx + 1;

                // prevMode = curMode;
                curMode = mode;
            }
            else
            {
                // throw new IllegalArgumentException("changeMode error. curMode: " + curMode + ", mode: " + mode);
            }
        }
        else if (curMode == Mode.LITERAL)
        {
            if (mode != Mode.LITERAL)
            {
                oCntIdx = oPos;
                oPos = oCntIdx + 1;

                prevMode = curMode;
                // curMode = mode;
                curMode = Mode.INIT;
            }
            // else if (mode == Mode.REPLICATE3)
            // {
            // oCntIdx = oPos;
            // oPos = oCntIdx + 1;
            // curMode = mode;
            // }
            else
            {
                throw new IllegalArgumentException("changeMode error. curMode: " + curMode + ", mode: " + mode);
            }
        }
        else if (curMode == Mode.REPLICATE3)
        {
            if (mode != Mode.REPLICATE3)
            {
                oPos++;
                oCntIdx = oPos;
                oPos = oCntIdx + 1;

                prevMode = curMode;
                // curMode = mode;
                curMode = Mode.INIT;

                iPos = iPos + 2;
            }
            // else if (mode == Mode.REPLICATE2)
            // {
            // oPos++;
            // oCntIdx = oPos;
            // oPos = oCntIdx + 1;
            // curMode = mode;
            // }
            else
            {
                throw new IllegalArgumentException("changeMode error. curMode: " + curMode + ", mode: " + mode);
            }
        }
        else if (curMode == Mode.REPLICATE2)
        {
            if (mode != Mode.REPLICATE2)
            {
                oPos++;
                oCntIdx = oPos;
                oPos = oCntIdx + 1;

                prevMode = curMode;
                // curMode = mode;
                curMode = Mode.INIT;

                iPos = iPos + 1;
            }
            else
            {
                throw new IllegalArgumentException("changeMode error. curMode: " + curMode + ", mode: " + mode);
            }
        }
        else if (curMode == Mode.REPLICATE1)
        {
            if (mode != Mode.REPLICATE1)
            {
                oPos++;
                oCntIdx = oPos;
                oPos = oCntIdx + 1;

                prevMode = curMode;
                // curMode = mode;
                curMode = Mode.INIT;
            }
            else
            {
                throw new IllegalArgumentException("changeMode error. curMode: " + curMode + ", mode: " + mode);
            }
        }
    }

    private Mode whichMode(int prev2_a0, int prev_a0, byte a0, int a1, int a2)
    {
        if (prev2_a0 == INVALI_VALUE && prev_a0 == INVALI_VALUE)
        {
            if (a1 == INVALI_VALUE && a2 == INVALI_VALUE)
            {
                return Mode.LITERAL;
            }
            else if (a1 != INVALI_VALUE && a2 == INVALI_VALUE)
            {
                if (a0 != a1)
                {
                    return Mode.LITERAL;
                }
                else
                { // a0 == a1

                    return Mode.REPLICATE2;
                }
            }
            else if (a1 != INVALI_VALUE && a2 != INVALI_VALUE)
            {
                if (a0 != a1)
                {
                    return Mode.LITERAL;
                }
                else
                { // a0 == a1

                    if (a0 != a2)
                    {
                        // return Mode.REPLICATE2;
                        return Mode.LITERAL; // start 'AABC..'
                    }
                    else
                    {
                        // a0 == a1 == a2
                        return Mode.REPLICATE3;
                    }
                }
            }
            else
            {
                throw new IllegalArgumentException("whichMode error. prev2_a0=" + prev2_a0 + ", prev_a0=" + prev_a0
                        + ", a0=" + a0 + ", a1=" + a1 + ", a2=" + a2);
            }
        }
        else if (prev2_a0 == INVALI_VALUE && prev_a0 != INVALI_VALUE)
        {
            if (a1 == INVALI_VALUE && a2 == INVALI_VALUE)
            {
                return Mode.LITERAL;
            }
            else if (a1 != INVALI_VALUE && a2 == INVALI_VALUE)
            {
                if (a0 != a1)
                {
                    return Mode.LITERAL;
                }
                else
                { // a0 == a1

                    return Mode.REPLICATE2;
                }
            }
            else if (a1 != INVALI_VALUE && a2 != INVALI_VALUE)
            {
                if (a0 != a1)
                {
                    return Mode.LITERAL;
                }
                else
                { // a0 == a1

                    if (a0 != a2)
                    {
                        return Mode.LITERAL;
                    }
                    else
                    {
                        // a0 == a1 == a2
                        return Mode.REPLICATE3;
                    }
                }
            }
            else
            {
                throw new IllegalArgumentException("whichMode error. prev2_a0=" + prev2_a0 + ", prev_a0=" + prev_a0
                        + ", a0=" + a0 + ", a1=" + a1 + ", a2=" + a2);
            }
        }
        else if (prev2_a0 != INVALI_VALUE && prev_a0 != INVALI_VALUE)
        {
            if (a1 == INVALI_VALUE && a2 == INVALI_VALUE)
            {
                return Mode.LITERAL; // end of [..., a0]
            }
            else if (a1 != INVALI_VALUE && a2 == INVALI_VALUE)
            {
                if (a0 != a1)
                {
                    return Mode.LITERAL; // end of [..., a0, a1]
                }
                else
                { // a0 == a1

                    return Mode.REPLICATE2; // end of [..., a0, a1]
                }
            }
            else if (a1 != INVALI_VALUE && a2 != INVALI_VALUE)
            {
                if (a0 != a1)
                {
                    return Mode.LITERAL; // [..., a0, a1, a2] or [..., a0, a1, a2, ...]
                }
                else
                { // a0 == a1

                    if (a0 != a2)
                    {
                        // a0 == a1 != a2
                        return Mode.LITERAL;
                    }
                    else
                    {
                        // a0 == a1 == a2
                        return Mode.REPLICATE3;
                    }
                }
            }
            else
            {
                throw new IllegalArgumentException("whichMode error. prev2_a0=" + prev2_a0 + ", prev_a0=" + prev_a0
                        + ", a0=" + a0 + ", a1=" + a1 + ", a2=" + a2);
            }
        }
        else
        {
            throw new IllegalArgumentException("whichMode error. prev2_a0=" + prev2_a0 + ", prev_a0=" + prev_a0
                    + ", a0=" + a0 + ", a1=" + a1 + ", a2=" + a2);
        }

    }

}
