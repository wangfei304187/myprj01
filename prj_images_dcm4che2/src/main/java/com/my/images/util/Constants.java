/**
 * Copyright 2015 Bowing Medical Technologies Co., Ltd.
 */
package com.my.images.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;

public class Constants
{
    private Constants()
    {
    }

    // used by ImageDisplayThumbnailSubPanel
    public static final int ON_STUDY_LEVEL = 1;
    public static final int ON_SERIES_LEVEL = 2;
    public static final String KEY_TOPO = "KEY_TOPO";
    public static final String KEY_SCANNO = "KEY_SCANNO";
    public static final Stroke drawLopMainStroke = new BasicStroke(2f);
    public static final Stroke drawLopSecondaryStroke = new BasicStroke(1f);

    // used by GLRectangle
    public static final int HALF_CROSS_LEN = 5;

    // //////////////////////////////////////////////////////////////////////////////////

    // DRAW PARAM BESIDE RECT
    public static final String KEY_FOV = "FoV";
    public static final String KEY_START = "SP";
    public static final String KEY_END = "EP";
    public static final String KEY_LEN = "LN";
    public static final String KEY_RECONX = "CX";
    public static final String KEY_RECONY = "CY";
    public static final String KEY_TILT = "GT";

    public static final int DEFAULT_CANVAS_WIDTH = 680;
    public static final int DEFAULT_CANVAS_HEIGHT = 680;
    public static final int DEFAULT_IMG_AREA_WIDTH = 512;
    public static final int DEFAULT_IMG_AREA_HEIGHT = 512;

    public static final int IMAGE_LAYER = 0;
    public static final int RECTANGLE_LAYER = 1;
    public static final int LINE_LAYER = 2;

    public static final String TOP_LINE = "TOP_LINE";

    public static final int RECT_NO_SELECTED = -1;
    // top edge selected
    public static final int RECT_TOP_SELECTED = 0;
    // right edge selected
    public static final int RECT_RIGHT_SELECTED = 1;
    public static final int RECT_BOTTOM_SELECTED = 2;
    public static final int RECT_LEFT_SELECTED = 3;
    // left-top corner selected
    public static final int RECT_LEFT_TOP_SELECTED = 4;
    public static final int RECT_RIGHT_TOP_SELECTED = 5;
    public static final int RECT_LEFT_BOTTOM_SELECTED = 6;
    // right-bottom corner selected
    public static final int RECT_RIGHT_BOTTOM_SELECTED = 7;
    public static final int RECT_CENTER_SELECTED = 8;

    public static final Image ROTATE_IMG =
            Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagedisplay/bmRotate.png"));

    public enum RectChangeType
    {
        MOVE,
        HEIGHT_TOP, // top edge changed
        HEIGHT_BOTTOM, // bottom edge changed
        HEIGHT,
        WIDTH_LEFT,
        WIDTH_RIGHT,
        WIDTH,
        ROTATE;

        @Override
        public String toString()
        {
            return name();
        }
    }

    public enum ZoomChangeType
    {
        ZOOM,
        DBCLICK_TO_MAGNIFY; // double click to magnify topo image

        @Override
        public String toString()
        {
            return name();
        }
    }

    public enum ImageNavigationChangeType
    {
        PREVIOUS,
        NEXT;

        @Override
        public String toString()
        {
            return name();
        }
    }

    public static final String DEFAULT_FONT_NAME = "SansSerif";
    // public static final String DEFAULT_FONT_NAME = AppMain.AppFontFamilyName;
    public static final int DEFAULT_FONT_SIZE = 14;
    // public static final float MIN_FONT_SIZE = ImageMgmtMgr.minFontSize;
    // public static final float MIN_FONT_SIZE_FOR_PRINTER = ImageMgmtMgr.minFontSizeForPrinter;
    // public static final Font DEFAULT_FONT = new Font(Constants.DEFAULT_FONT_NAME, Font.PLAIN, 12);
    public static final Font DEFAULT_FONT =
            new Font(Constants.DEFAULT_FONT_NAME, Font.PLAIN, Constants.DEFAULT_FONT_SIZE);
    public static final Color DEFAULT_DRAW_TEXT_COLOR = Color.white;

    // public static final Font DEFAULT_FONT_MINOR = new Font(Constants.DEFAULT_FONT_NAME, Font.PLAIN, 10);

    // Text annotation position
    public static final int ANNOTATION_LEFT_TOP = 0;
    public static final int ANNOTATION_RIGHT_TOP = 1;
    public static final int ANNOTATION_RIGHT_BOTTOM = 2;
    public static final int ANNOTATION_LEFT_BOTTOM = 3;
    public static final int ANNOTATION_TOP = 4;
    public static final int ANNOTATION_RIGHT = 5;
    public static final int ANNOTATION_BOTTOM = 6;
    public static final int ANNOTATION_LEFT = 7;

    // 1 pixel = 1mm
    public static final float UNIT = 1f;
    // for Vertical Ruler
    public static final int TICK_LAB_TOP = 0;
    public static final int TICK_LAB_BOTTOM = 1;
    // for Horizontal Ruler
    public static final int TICK_LAB_LEFT = 2;
    public static final int TICK_LAB_RIGHT = 3;
    // for Vertical Ruler
    public static final int TICK_ALIGN_LEFT = 0;
    public static final int TICK_ALIGN_RIGHT = 1;
    // for Horizontal Rulers
    public static final int TICK_ALIGN_BOTTOM = 2;
    public static final int TICK_ALIGN_TOP = 3;

    // VOILUTFunction
    public static final String LINEAR = "LINEAR";
    public static final String SIGMOID = "SIGMOID";
    public static final String LINEAR_EXACT = "LINEAR_EXACT";

    // Click to Add Text Node
    public static BasicStroke TEXT_OUTLINE_STROKE =
            new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[] { 4f, 4f }, 0f);

    public static final Color NAVIGATOR_SQUARE_OUTLINE_COLOR = Color.white;
    public static final Color NAVIGATOR_SQUARE_FILL_COLOR = Color.gray;
    public static final int ANGLE_DEGREE = 45;

    // operation mode on canvas
    public static int MODE_DEFAULT = 1;
    public static int MODE_DRAW = 2;
    public static int MODE_DRAG_IMAGE = 3; // drag image after zooming in/out

    public static int MODE_SELECTONLY_RECT = 4;
    public static int MODE_RESIZE_RECT = 5;
    public static int MODE_MOVE_RECT = 6;
    public static int MODE_ROTATE_RECT = 7;

    // draw mode
    // public static int DEFAULT_DRAW = 20;
    // public static int DRAW_TEXT = 21;
    // public static int DRAW_LINE = 22;
    // public static int DRAW_LINE_ARROW = 23;
    // public static int DRAW_RECT = 24;
    // public static int DRAW_ANGLE = 25;

    public static final int offsetXLeft = 5;
    public static final int offsetXRight = 5;
    public static final int offsetYTop = 3;
    public static final int offsetYBottom = 3;
}