package com.my.images;

public class Rle
{
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

    private Mode curMode;

    private enum Mode
    {
        INIT,
        LITERAL,
        REPLICATE2,
        REPLICATE3
    }

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

    private void reset()
    {
        oCntIdx = oPos;
        oPos = oCntIdx + 1;
        curMode = Mode.INIT;
        resetCount();
    }

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
            if (iPos == 126)
            {
                System.out.println();
            }
            byte a0 = in[iPos];
            byte a1;
            byte a2;
            if (iPos + 2 < iEnd)
            {
                a1 = in[iPos + 1];
                a2 = in[iPos + 2];
            }
            else
            {
                if (curMode == Mode.REPLICATE3)
                {
                    iPos = iPos + 2;
                    changeMode(Mode.INIT);
                    break;
                }

                if (curMode == Mode.LITERAL)
                {
                    if (iPos + 1 < iEnd)
                    {
                        a1 = in[iPos + 1];

                        // if (a0 == a1)
                        // {
                        // out[oCntIdx] = (byte) -1;
                        // out[oPos] = in[iPos++];
                        //
                        // oPos++;
                        // reset();
                        // }
                        // else
                        // {
                        out[oCntIdx] += 2;
                        out[oPos++] = in[iPos++];
                        out[oPos++] = in[iPos++];

                        reset();
                        // }
                    }
                    else
                    {
                        out[oCntIdx] += 1;
                        out[oPos++] = in[iPos++];

                        reset();
                    }

                    break;
                }

                if (curMode == Mode.INIT)
                {
                    if (iPos + 1 < iEnd)
                    {
                        a1 = in[iPos + 1];

                        if (a0 == a1)
                        {
                            out[oCntIdx] = (byte) -1;
                            out[oPos] = in[iPos++];

                            oPos++;
                            reset();
                        }
                        else
                        {
                            out[oCntIdx] = 1;
                            out[oPos++] = in[iPos++];

                            reset();
                        }
                    }
                    else
                    {
                        out[oCntIdx] = 0;
                        out[oPos++] = in[iPos++];

                        reset();
                    }
                }

                break;
            }

            if (curMode == Mode.INIT)
            {
                Mode mode = whichMode(a0, a1, a2);
                changeMode(mode);
                continue;
            }

            if (curMode == Mode.LITERAL)
            {
                Mode mode = whichMode(a0, a1, a2);
                if (mode == Mode.LITERAL || mode == Mode.REPLICATE2)
                {
                    if (count + 1 <= LIMIT)
                    {
                        count++;
                        out[oCntIdx] = (byte) (count - 1);
                        out[oPos++] = in[iPos++];
                    }
                    else
                    {
                        // oCntIdx = oPos;
                        // oPos = oCntIdx + 1;
                        // curMode = mode;
                        changeMode(Mode.INIT);
                        continue;
                    }
                }
                else
                {
                    if (mode != Mode.REPLICATE3)
                    {
                        throw new IllegalArgumentException("doRun error. curMode=" + curMode + ", mode=" + mode);
                    }

                    // --> if count is 1, keep mode LITERAL
                    if (count == 1)
                    {
                        if (count + 1 <= LIMIT)
                        {
                            count++;
                            out[oCntIdx] = (byte) (count - 1);
                            out[oPos++] = in[iPos++];
                        }
                        else
                        {
                            changeMode(Mode.INIT);
                            continue;
                        }

                        // continue
                    }
                    // <--
                    else
                    {
                        // if count > 1, permit to set mode REPLICATE3
                        changeMode(mode);
                        continue;
                    }
                }
            }

            if (curMode == Mode.REPLICATE3)
            {
                Mode mode = whichMode(a0, a1, a2);
                if (mode == Mode.REPLICATE3)
                {
                    if (count3 + 1 <= LIMIT)
                    {
                        out[oCntIdx] = (byte) (-(count3 + 1) + 1);
                        out[oPos] = in[iPos++];

                        count3++;
                    }
                    else
                    {
                        iPos = iPos + 2;

                        changeMode(Mode.INIT);
                        continue;
                    }
                }
                else
                {
                    if (mode != Mode.REPLICATE2)
                    {
                        throw new IllegalArgumentException("doRun error. curMode=" + curMode + ", mode=" + mode);
                    }

                    iPos = iPos + 2;

                    changeMode(Mode.INIT);
                    continue;
                }
            }

            if (curMode == Mode.REPLICATE2)
            {
                Mode mode = whichMode(a0, a1, a2);
                if (mode == Mode.REPLICATE2)
                {
                    if (count2 + 1 <= LIMIT)
                    {
                        out[oCntIdx] = (byte) (-(count2 + 1) + 1);
                        out[oPos] = in[iPos++];

                        count2++;
                    }
                    else
                    {
                        throw new IllegalArgumentException("doRun error. count2=" + count2 + ", LIMIT=" + LIMIT);
                    }
                }
                else
                {
                    if (mode != Mode.LITERAL)
                    {
                        throw new IllegalArgumentException("doRun error. curMode=" + curMode + ", mode=" + mode);
                    }

                    iPos = iPos + 1;

                    changeMode(Mode.INIT);
                    continue;
                }
            }

        } // end while

        return oCntIdx;
    }

    private Mode whichMode(byte a0, byte a1, byte a2)
    {
        if (a0 != a1)
        {
            return Mode.LITERAL;
        }
        else
        {
            if (a0 == a1 && a1 != a2)
            {
                return Mode.REPLICATE2;
            }

            if (a0 == a1 && a0 == a2)
            {
                return Mode.REPLICATE3;
            }

            throw new IllegalArgumentException("whichMode error. a0: " + a0 + ", a1: " + a1 + ", a2: " + a2);
        }
    }

    private void changeMode(Mode mode)
    {
        resetCount();

        if (curMode == Mode.INIT)
        {
            if (mode == Mode.LITERAL || mode == Mode.REPLICATE2 || mode == Mode.REPLICATE3)
            {
                curMode = mode;
            }
            else
            {
                // throw new IllegalArgumentException("changeMode error. curMode: " + curMode + ", mode: " + mode);
            }
        }
        else if (curMode == Mode.LITERAL)
        {
            if (mode == Mode.REPLICATE3)
            {
                oCntIdx = oPos;
                oPos = oCntIdx + 1;
                curMode = mode;
            }
            else if (mode == Mode.INIT)
            {
                oCntIdx = oPos;
                oPos = oCntIdx + 1;
                curMode = mode;
            }
            else
            {
                throw new IllegalArgumentException("changeMode error. curMode: " + curMode + ", mode: " + mode);
            }
        }
        else if (curMode == Mode.REPLICATE3)
        {
            if (mode == Mode.REPLICATE2)
            {
                oPos++;
                oCntIdx = oPos;
                oPos = oCntIdx + 1;
                curMode = mode;
            }
            else if (mode == Mode.INIT)
            {
                oPos++;
                oCntIdx = oPos;
                oPos = oCntIdx + 1;
                curMode = mode;
            }
            else
            {
                throw new IllegalArgumentException("changeMode error. curMode: " + curMode + ", mode: " + mode);
            }
        }
        else if (curMode == Mode.REPLICATE2)
        {
            if (mode == Mode.INIT)
            {
                oPos++;
                oCntIdx = oPos;
                oPos = oCntIdx + 1;
                curMode = mode;
            }
            else
            {
                throw new IllegalArgumentException("changeMode error. curMode: " + curMode + ", mode: " + mode);
            }
        }
    }

}
