package com.leyuan.printer.utils;

/**
 * Created by user on 2017/4/13.
 */

public class PrintUtils {
    /**
     * 复位打印机
     */
    public static final byte[] RESET = {0x1b, 0x40};

    /**
     * 左对齐
     */
    public static final byte[] ALIGN_LEFT = {0x1b, 0x61, 0x00};

    /**
     * 中间对齐
     */
    public static final byte[] ALIGN_CENTER = {0x1b, 0x61, 0x01};

    /**
     * 右对齐
     */
    public static final byte[] ALIGN_RIGHT = {0x1b, 0x61, 0x02};

    /**
     * 选择加粗模式
     */
    public static final byte[] BOLD = {0x1b, 0x45, 0x01};

    /**
     * 取消加粗模式
     */
    public static final byte[] BOLD_CANCEL = {0x1b, 0x45, 0x00};

    /**
     * 宽高加倍
     */
    public static final byte[] DOUBLE_HEIGHT_WIDTH = {0x1d, 0x21, 0x11};

    /**
     * 宽加倍
     */
    public static final byte[] DOUBLE_WIDTH = {0x1d, 0x21, 0x10};

    /**
     * 高加倍
     */
    public static final byte[] DOUBLE_HEIGHT = {0x1d, 0x21, 0x01};

    /**
     * 字体不放大
     */
    public static final byte[] NORMAL = {0x1d, 0x21, 0x00};

    /**
     * 设置默认行间距
     */
    public static final byte[] LINE_SPACING_DEFAULT = {0x1b, 0x32};


    public static final byte[] BACK_ONE_LINE = {0x1B, 0x6A, 0x01};

    public static final byte[] LINE_SPACING_SIXTY = {0x1B, 0x33, 0x30};
    public static final byte[] LINE_SPACING_ONE = {0x1B, 0x33, 0x01};
    public static final byte[] LINE_SPACING_ZERO = {0x1B, 0x33, 0x0};
    public static final byte[] MARGN_LEFT_THIRTY = {0x1D, 0x4C, 0x30};
    public static final byte[] MARGN_LEFT_TEN = {0x1D, 0x4C, 0x10};

    public static final byte[] MARGN_LEFT_RIGHT_CZ = {0x1C, 0x53, 0x64,0x64};

    public static final byte[] OPEN_CHINA = {0x1C, 0x26};
    public static final byte[] CLOSE_CHINA = {0x1C, 0x2E};
    public static final byte[] LINE_FEED = {0x0A};

    public static final byte[] OPEN_WIDEN = {0x1B, 0x0E};
    public static final byte[] CLOSE_WIDEN = {0x1B, 0x14};

    public static final byte[] SIZE_two = {0x29, 0x33, 0x02};

    public static final byte[] SIZE_one = {0x29, 0x33, 0x01};

    public static final byte[] PRINT_STATE = {0x10, 0x04, 0x01};

    public static final byte[] PRINT_STATE_MACHINE = {0x10, 0x04, 0x02};

    public static final byte[] PRINT_STATE_HEADER = {0x10, 0x04, 0x03};

    public static final byte[] PRINT_STATE_PAPER = {0x10, 0x04, 0x04};
    public static final byte PRINT_NORMAL = 0x16;

    public static final byte PRINT_NO_PAPER = 0x7e;
    public static final byte PRINT_NO_P = 0x1e;
    public static final byte[] PAPER_CUT = {0x1B, 0x69};
    public static final byte MACHINE_NORMAL = 0x12;

    //QRCODE
    public static final byte[] QRCODE_SIZE = {0x1D, 0x01,0X03,0X06};

    public static final byte[] QRCODE_ERROR_LEVEL = {0x1D, 0x01,0X04,0X32};
    public static final byte[] QRCODE_NUMBER = {0x1D, 0x01,0X01,0X17,0X00};
    public static final byte[] QRCODE_CONTENT = {0x68, 0x74,0x74,0x70,0x3A,0x5C,0x5C,0x77,0x5C,0x5C
            ,0x5C,0x5C,0x5C,0x5C,0x5C,0x5C,0x5C,0x5C,0x5C,0x5C,0x5C,0x5C,0x5C};
    public static final byte[] QRCODE_START_PRINT = {0x1D, 0x01,0X02};

    //BAR_CODE
    public static final byte[] BARCODE_HEIGHT = {0x1D, 0x68,0x7E};

    public static final byte[] BARCODE_WIDTH = {0x1D, 0x77,0x06};
    public static final byte[] BARCODE_CONTENT = {0X1D,0X6B,0X48,0X0C
            ,0X30,0X31,0X32,0X7A,0X34,0X35,0X5A,0X37,0X38,0X39,0X6B,0X50};


}
