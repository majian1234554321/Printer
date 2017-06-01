package com.leyuan.printer.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.printer.R;
import com.leyuan.printer.entry.PrintItem;
import com.leyuan.printer.mvp.presenter.PrinterPresenter;
import com.leyuan.printer.utils.PrintUtils;
import com.leyuan.printer.utils.QRCode;
import com.leyuan.printer.utils.StringLength;
import com.leyuan.printer.utils.ToastGlobal;
import com.leyuan.printer.utils.WindowDisplayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import android_serialport_api.SerialPort;

/**
 * Created by user on 2017/4/12.
 */
public class PrintActivity extends BaseActivity implements View.OnClickListener {
    private static final int MESSAGE_FINISH = 1;
    private static final int READ_PRINT_STATE = 2;
    private static final int MAX_LENGTH = 48;
    private static final int MAX_LENGTH_TRIM = 38;
    private TextView txtPrintState;
    private ImageView imgPrinter;
    private ImageView imgQrCodeApp;
    private ImageView imgQrCodeWx;

    private static final int QRIMAGE_SIZE = 180;

    private OutputStream mOutputStream;

    private String title;
    private ArrayList<PrintItem> items;
    private StringBuffer printBuffer = new StringBuffer();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_FINISH:
                    finish();
                    break;
                case READ_PRINT_STATE:
                    txtPrintState.setText(printBuffer.toString());
                    break;
                case 10:
                    txtPrintState.setText((String) msg.obj);
                    break;
            }
        }
    };

    private InputStream printInputStream;
    private PrintReadThread printReadThread;
    private int isPrinted;
    private String lessonType;
    private String code;


    public static void start(Context context, String title, ArrayList<PrintItem> items, int isPrint, String lessonType, String code) {
        Intent intent = new Intent(context, PrintActivity.class);
        intent.putExtra("title", title);
        intent.putParcelableArrayListExtra("items", items);
        intent.putExtra("isPrinted", isPrint);
        intent.putExtra("lessonType", lessonType);
        intent.putExtra("code", code);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        title = getIntent().getStringExtra("title");
        items = getIntent().getParcelableArrayListExtra("items");
        isPrinted = getIntent().getIntExtra("isPrinted", 0);
        lessonType = getIntent().getStringExtra("lessonType");
        code = getIntent().getStringExtra("code");

        initView();
        initPrintPort(isPrinted);
//        initQRImage();
    }

    private void initView() {

        txtPrintState = (TextView) findViewById(R.id.txt_print_state);
        imgPrinter = (ImageView) findViewById(R.id.img_printer);
        imgQrCodeApp = (ImageView) findViewById(R.id.img_qr_code_app);
        imgQrCodeWx = (ImageView) findViewById(R.id.img_qr_code_wx);
        findViewById(R.id.bt_back).setOnClickListener(this);
    }

    private void initQRImage() {
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo_i_dong);
        imgQrCodeApp.setImageBitmap(QRCode.createQRCodeWithLogo("sdkjklasjdflk", QRIMAGE_SIZE, logo));
        imgQrCodeWx.setImageBitmap(QRCode.createQRCodeWithLogo("http:www.baidu.com/map?a=11", QRIMAGE_SIZE, logo));
    }

    private void initPrintPort(int isPrinted) {
        try {
            SerialPort mPrintPort = App.getInstance().getPrintPort();
            mOutputStream = mPrintPort.getOutputStream();
            printInputStream = mPrintPort.getInputStream();

//            printReadThread = new PrintReadThread();
//            printReadThread.start();

            setCommand(PrintUtils.RESET);
            setCommand(PrintUtils.LINE_SPACING_ZERO);
            setCommand(PrintUtils.OPEN_CHINA);

            if (isPrinted != 0) {
                setCommand(PrintUtils.BOLD);
                setCommand(PrintUtils.ALIGN_RIGHT);
//            setCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);
//            printText("口\n");
//            setCommand(PrintUtils.NORMAL);
//            setCommand(PrintUtils.BACK_ONE_LINE);
                printText("———\n");
                printText("|副票|\n");
                printText("———\n");
                setCommand(PrintUtils.BOLD_CANCEL);
            }

            setCommand(PrintUtils.LINE_SPACING_SIXTY);
            if (title != null) {
                setCommand(PrintUtils.ALIGN_CENTER);
//                setCommand(PrintUtils.BOLD);
                setCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);
                printText(title + "\n");
                lineFeed(1);
            }

            setCommand(PrintUtils.RESET);
            setCommand(PrintUtils.LINE_SPACING_SIXTY);
            setCommand(PrintUtils.OPEN_CHINA);
            if (items != null && !items.isEmpty()) {
                setCommand(PrintUtils.ALIGN_LEFT);
                for (PrintItem item : items) {
                    if (!TextUtils.isEmpty(item.getName())) {
                        int nameLength = StringLength.length(item.getName());
                        int valueLength = StringLength.length(item.getValue());
//                        ToastGlobal.showLong("nameLength = " + nameLength + " , valueLength = " + valueLength);

                        if (nameLength + valueLength > MAX_LENGTH_TRIM) {
                            int divider = StringLength.dividerLength(item.getValue(), valueLength - (nameLength + valueLength - MAX_LENGTH_TRIM));

                            String firstValue = item.getValue().substring(0, divider);
                            String secondValue = item.getValue().substring(divider);
                            printText("     " + item.getName() + ": " + firstValue + "\n");
                            char[] c = new char[nameLength + 7];
                            for (int i = 0; i < c.length; i++) {
                                c[i] = ' ';
                            }
                            printText(new String(c) + secondValue + "\n");
                        } else {
                            printText("     " + item.getName() + ": " + item.getValue() + "\n");
                        }
                    }
                }
            }

            lineFeed(1);
            printText("--------------------------------------------\n");
            lineFeed(5);
            setCommand(PrintUtils.PAPER_CUT);

            mOutputStream.write(PrintUtils.PRINT_STATE);
            byte[] buffer = new byte[64];
            int size = printInputStream.read(buffer);
            if (size > 0) {
                if (buffer[0] == PrintUtils.PRINT_NORMAL) {
                    txtPrintState.setText("打印成功\n请在机器下方取票");
                    new PrinterPresenter(this).printSuccess(code, lessonType);
                } else {
                    txtPrintState.setText("打印失败\n请联系工作人员");
                }
            }

            handler.sendEmptyMessageDelayed(MESSAGE_FINISH, 10 * 1000);
            mOutputStream.close();
            mOutputStream = null;
            printInputStream.close();
            printInputStream = null;

        } catch (SecurityException e) {
            e.printStackTrace();
            ToastGlobal.showLong(R.string.error_security);
            txtPrintState.setText("打印失败\n请联系工作人员");
            handler.sendEmptyMessageDelayed(MESSAGE_FINISH, 10 * 1000);
        } catch (IOException e) {
            e.printStackTrace();
            ToastGlobal.showLong(R.string.error_unknown);
            txtPrintState.setText("打印失败\n请联系工作人员");
            handler.sendEmptyMessageDelayed(MESSAGE_FINISH, 10 * 1000);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            ToastGlobal.showLong(R.string.error_configuration);
            handler.sendEmptyMessageDelayed(MESSAGE_FINISH, 10 * 1000);
            txtPrintState.setText("打印失败\n请联系工作人员");
        } catch (Exception e) {
            ToastGlobal.showShort(e.getMessage());
            handler.sendEmptyMessageDelayed(MESSAGE_FINISH, 10 * 1000);
            txtPrintState.setText("打印失败\n请联系工作人员");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        WindowDisplayUtils.hideNavigationBar(this);
    }


    public void releasePrint() {
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mOutputStream = null;
            }
            mOutputStream = null;
        }

        if (printReadThread != null) {
            printReadThread.interrupt();
            printReadThread = null;
        }

        App.getInstance().closePrintPort();
        if (printInputStream != null) {
            try {
                printInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                printInputStream = null;
            }
            printInputStream = null;
        }

    }

    @Override
    protected void onDestroy() {
        releasePrint();
        super.onDestroy();
    }

    /**
     * 设置打印格式
     *
     * @param command 格式指令
     */
    public void setCommand(byte[] command) {
        try {
            mOutputStream.write(command);
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印文字
     *
     * @param text 要打印的文字
     */
    public void printText(String text) throws IOException {
        byte[] data = text.getBytes("gbk");
        mOutputStream.write(data, 0, data.length);
        mOutputStream.flush();
    }

    public void lineFeed(int num) throws IOException {
        for (int i = 0; i < num; i++) {
            mOutputStream.write(PrintUtils.LINE_FEED);
        }
        mOutputStream.flush();
    }

    private class PrintReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (printInputStream == null) return;
                    size = printInputStream.read(buffer);
                    if (size > 0) {
                        byte[] b = new byte[]{buffer[0]};
                        printBuffer.append(byte2hex(b));
                        handler.sendEmptyMessage(READ_PRINT_STATE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.obj = e.getMessage();
                    msg.what = 10;
                    handler.sendMessage(msg);
                    return;
                }
            }
        }
    }

    private static String byte2hex(byte[] buffer) {
        String h = "";

        for (int i = 0; i < buffer.length; i++) {
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            h = h + " " + temp;
        }

        return h + " ";

    }

}
