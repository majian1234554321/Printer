package com.leyuan.printer.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.printer.R;
import com.leyuan.printer.config.Constant;
import com.leyuan.printer.entry.PrintResult;
import com.leyuan.printer.mvp.presenter.PrinterPresenter;
import com.leyuan.printer.mvp.view.PrintInfoListener;
import com.leyuan.printer.utils.Logger;
import com.leyuan.printer.utils.PrintUtils;
import com.leyuan.printer.utils.ScanUtils;
import com.leyuan.printer.utils.ToastGlobal;
import com.leyuan.printer.utils.WindowDisplayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;

import static com.leyuan.printer.utils.PrintUtils.PRINT_NO_PAPER;

/**
 * Created by user on 2017/4/11.
 */
public class AppointCodeActivity extends BaseActivity implements View.OnClickListener, PrintInfoListener {

    private boolean isFirst = true;
    private byte firstIndex;
    private byte secondIndex;


    private static final int NULL_EVENT = 1;
    private static final int NULL_EVENT_INTERVAL = 30 * 1000;
    private static final int REVEIVED_SCAN_STRING = 2;
    private static final int READ_PRINTER = 3;
    private static final int PRINT_ERROR = 4;
    private static final int PRINT_IOEXCEPTION = 5;
    private static final int RECEIVED_CHAR = 6;

    private TextView txtHint;
    private ImageView imgTag;
    private EditText txtAppointCode;
    private LinearLayout linearSecond;
    private RelativeLayout layout_checking;
    private StringBuffer buffer = new StringBuffer();
    private StringBuffer scanBuffer = new StringBuffer();
    private StringBuffer printBuffer = new StringBuffer();

//    private InputStream mInputStream;
//    private ReadThread mReadThread;

    private PrinterPresenter presenter;
    private String ticketType;
    private boolean isCheckintAppointCode;
    private RelativeLayout layoutError;
    private TextView txtPrintError;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NULL_EVENT:
                    finish();
                    break;
                case REVEIVED_SCAN_STRING:
                    handler.removeMessages(NULL_EVENT);
                    startCheckAppointCode(scanBuffer.toString());
                    txtAppointCode.setText(scanBuffer.toString());
                    scanBuffer.delete(0, scanBuffer.length());

//                    txtAppointCode.setText(scanString);

//                    ToastGlobal.showShort("收到结束标志");


//                    txtAppointCode.setText(scanString);
                    break;
                case READ_PRINTER:
                    txtAppointCode.setText(printBuffer.toString());
                    break;
                case PRINT_NO_PAPER:
                    layoutError.setVisibility(View.VISIBLE);
                    txtPrintError.setText("检测到打印机无纸 请联系工作人员");

                    break;
                case PRINT_ERROR:
                    layoutError.setVisibility(View.VISIBLE);
                    txtPrintError.setText("打印机故障 请联系工作人员");
                    break;
                case RECEIVED_CHAR:
                    int size = msg.arg1;
                    byte[] bytes = (byte[]) msg.obj;

                    String temp = "";
                    try {
                        temp = new String(bytes, 0, size);
                    } catch (UnsupportedOperationException e) {
                        ToastGlobal.showLong(e.getMessage());
                    }

                    scanBuffer.append(temp);


//                    scanString = scanString.concat(new String(bytes, 0, size));
//                    ToastGlobal.showShort(scanString + " size = " + size);
                    break;
                case 10:
                    ToastGlobal.showShortConsecutive("接收到数据");
                    break;
                case 11:
                    ToastGlobal.showLong((String) msg.obj);
                    break;
                case 13:
//                    StringBuffer buffer = new StringBuffer();
//                    for (Byte b : byteArray) {
//                        buffer.append(b).append(" ");
//                    }
//                    ToastGlobal.showShort(buffer.toString());

                    break;

            }
        }
    };
    private ScanReceiver scanReceiver;

    public static void start(Context context, String ticketType) {
        Intent intent = new Intent(context, AppointCodeActivity.class);
        intent.putExtra("ticketType", ticketType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ticketType = getIntent().getStringExtra("ticketType");

        setContentView(R.layout.activity_appoint_code);

        presenter = new PrinterPresenter(this);
        presenter.setPrintInfoListener(this);
        initView();
        initPrintPort();
        sendFinishMessage();

        initScanPort();
    }

    private void initView() {
        txtHint = (TextView) findViewById(R.id.txt_hint);
        imgTag = (ImageView) findViewById(R.id.img_tag);
        txtAppointCode = (EditText) findViewById(R.id.txt_appoint_code);
        linearSecond = (LinearLayout) findViewById(R.id.linear_second);
        layout_checking = (RelativeLayout) findViewById(R.id.layout_checking);
        layoutError = (RelativeLayout) findViewById(R.id.layout_error);
        txtPrintError = (TextView) findViewById(R.id.txt_print_error);
        txtAppointCode.setInputType(InputType.TYPE_NULL);

        findViewById(R.id.bt_one).setOnClickListener(this);
        findViewById(R.id.bt_two).setOnClickListener(this);
        findViewById(R.id.bt_three).setOnClickListener(this);
        findViewById(R.id.bt_delete).setOnClickListener(this);
        findViewById(R.id.bt_four).setOnClickListener(this);
        findViewById(R.id.bt_five).setOnClickListener(this);
        findViewById(R.id.bt_six).setOnClickListener(this);
        findViewById(R.id.bt_seven).setOnClickListener(this);
        findViewById(R.id.bt_eight).setOnClickListener(this);
        findViewById(R.id.bt_nine).setOnClickListener(this);
        findViewById(R.id.bt_clear).setOnClickListener(this);
        findViewById(R.id.bt_zero).setOnClickListener(this);
        findViewById(R.id.bt_ok).setOnClickListener(this);
        findViewById(R.id.bt_back).setOnClickListener(this);
    }


    private void initPrintPort() {
        try {
            SerialPort mPrintPort = App.getInstance().getPrintPort();
            OutputStream printOutputStream = mPrintPort.getOutputStream();
            InputStream printInputStream = mPrintPort.getInputStream();


            printOutputStream.write(PrintUtils.PRINT_STATE);
            byte[] buffer = new byte[64];
            int size = printInputStream.read(buffer);
            if (size > 0) {
                if (buffer[0] == PrintUtils.PRINT_NORMAL) {
                    initScanPort();

                } else {
                    layoutError.setVisibility(View.VISIBLE);
                    printOutputStream.write(PrintUtils.PRINT_STATE_HEADER);
                    byte[] b = new byte[64];
                    int s = printInputStream.read(b);
                    if (s > 0) {
                        if (b[0] == PrintUtils.MACHINE_NORMAL) {
                            printOutputStream.write(PrintUtils.PRINT_STATE_PAPER);
                            byte[] b2 = new byte[64];
                            int s2 = printInputStream.read(b2);
                            if (s2 > 0) {
                                if (b2[0] == PrintUtils.PRINT_NO_PAPER) {
                                    txtPrintError.setText("检测到打印机无纸 请联系工作人员");
                                }
                            }


                        }
                    }
                }
            } else {
                layoutError.setVisibility(View.VISIBLE);
            }

            printOutputStream.flush();

            printOutputStream.close();
            printInputStream.close();
            App.getInstance().closePrintPort();
        } catch (SecurityException e) {
            e.printStackTrace();
            layoutError.setVisibility(View.VISIBLE);
            ToastGlobal.showLong(R.string.error_security);
        } catch (IOException e) {
            e.printStackTrace();
            layoutError.setVisibility(View.VISIBLE);
            ToastGlobal.showLong(R.string.error_unknown);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            layoutError.setVisibility(View.VISIBLE);
            ToastGlobal.showLong(R.string.error_configuration);
        } catch (Exception e) {
            layoutError.setVisibility(View.VISIBLE);
            ToastGlobal.showShort(e.getMessage());
        }
    }


    private void initScanPort() {
        scanReceiver = new ScanReceiver();
        registerReceiver(scanReceiver, new IntentFilter(Constant.RECEIVER_SCAN));

//        try {
//            SerialPort scanPort =  App.getInstance().getScanPort();
//
//            mInputStream = scanPort.getInputStream();
//            mReadThread = new ReadThread();
//            mReadThread.start();
//        } catch (SecurityException e) {
//            ToastGlobal.showShort(R.string.error_security);
//        } catch (IOException e) {
//            ToastGlobal.showShort(R.string.error_unknown);
//        } catch (InvalidParameterException e) {
//            ToastGlobal.showShort(R.string.error_configuration);
//        } catch (Exception e) {
//            ToastGlobal.showShort(e.getMessage());
//        }
    }


    private void initEditText() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                    boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(txtAppointCode, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_one:
                buffer.append("1");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;
            case R.id.bt_two:
                buffer.append("2");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;
            case R.id.bt_three:
                buffer.append("3");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;
            case R.id.bt_four:
                buffer.append("4");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;
            case R.id.bt_five:
                buffer.append("5");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;
            case R.id.bt_six:
                buffer.append("6");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;
            case R.id.bt_seven:
                buffer.append("7");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;
            case R.id.bt_eight:
                buffer.append("8");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;
            case R.id.bt_nine:
                buffer.append("9");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;
            case R.id.bt_zero:
                buffer.append("0");
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;

            case R.id.bt_delete:
                if (buffer.length() > 0) {
                    buffer.deleteCharAt(buffer.length() - 1);
                    txtAppointCode.setText(buffer.toString());
                }
                sendFinishMessage();
                break;

            case R.id.bt_clear:
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                txtAppointCode.setText(buffer.toString());
                sendFinishMessage();
                break;

            case R.id.bt_ok:
                sendFinishMessage();
                if (buffer.length() == 0) {
                    ToastGlobal.showShort("预约号不能为空");
                } else {
                    handler.removeMessages(NULL_EVENT);
                    startCheckAppointCode(buffer.toString());
                }
                break;
            case R.id.bt_back:
                finish();
                break;
        }

    }

    private void startCheckAppointCode(String code) {
        isCheckintAppointCode = true;
        layout_checking.setVisibility(View.VISIBLE);
//        code = "101434133440";
        presenter.getPrintInfo(code, ticketType);
//        ToastGlobal.showShortConsecutive("startCheck  isCheck ：" + isCheckintAppointCode);
        Logger.i("startCheck  isCheckintAppointCode = " + isCheckintAppointCode);
    }

    @Override
    public void onGetPrintInfo(PrintResult printResult) {
        layout_checking.setVisibility(View.GONE);
        isCheckintAppointCode = false;
//        ToastGlobal.showShortConsecutive("onGet  isCheck ：" + isCheckintAppointCode);
        Logger.i("onGetPrintI  isCheckintAppointCode = " + isCheckintAppointCode);

//        ArrayList<PrintItem> items = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            PrintItem item = new PrintItem("title" + i, "value");
//            items.add(item);
//        }
//
//        PrintActivity.start(this, "爱动取票", items, 1, 1, "8938493242075");
//        finish();

//release

        if (printResult == null || printResult.getLessonInfo() == null) {

            sendFinishMessage();
            ToastGlobal.showLongCenter("无效的核销码");
        } else {
            PrintActivity.start(this, printResult.getLessonInfo().getTitle(), printResult.getLessonInfo().getItem()
                    , printResult.getIsPrint(), ticketType, printResult.getCode());//printResult.getLessonType()
            finish();
        }

    }

    @Override
    public void onPostPrintState(boolean success) {

    }

    private void sendFinishMessage() {
        handler.removeMessages(NULL_EVENT);
        handler.sendEmptyMessageDelayed(NULL_EVENT, NULL_EVENT_INTERVAL);
    }


    private class ReadThread extends Thread {

        @Override
        public void run() {
//            super.run();
//            while (!isInterrupted()) {
//                Logger.i("ReadThread isCheckintAppointCode = " + isCheckintAppointCode);
//
//                int size;
//                try {
//                    byte[] buffer = new byte[64];
////                    if (mInputStream == null) return;
////                    if(mInputStream.available() >4){
////                    }
////                    size = mInputStream.read(buffer);
////                    if (size > 0) {
////                        onDataReceived(buffer, size);
////                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return;
//                }
//            }
        }
    }

//    private List<Byte> byteArray = new Vector<>();

    private void onDataReceived(final byte[] buf, final int size) {
        if (isCheckintAppointCode) return;


        byte b = buf[0];
        if (b > 32 && b <= 126) {

//            for (int i = 0; i < size; i++) {
//                byteArray.add(buf[i]);
//            }


//        if (b != ScanUtils.END_TAG && b != ScanUtils.END_TAG_SECOND) {

            Message receiverCharMessage = Message.obtain();
            receiverCharMessage.what = RECEIVED_CHAR;
            receiverCharMessage.obj = buf;
            receiverCharMessage.arg1 = size;
            handler.sendMessage(receiverCharMessage);

//            scanString.concat(new String(buf, 0, size, UTF8_CHARSET));


//            scanBuffer.append(new String(buf, 0, size));


//            handler.sendEmptyMessage(13);

        } else if (b == ScanUtils.END_TAG) {
            handler.sendEmptyMessage(REVEIVED_SCAN_STRING);
        }
        //        Message message = Message.obtain();
//        message.obj = b + "";
//        message.what = 11;
//
//        handler.sendMessage(message);


    }


    public void releaseScanPort() {
//        if (mReadThread != null)
//            mReadThread.interrupt();
//        App.getInstance().closeScanPort();
//        if (mInputStream != null) {
//            try {
////                mInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                mInputStream = null;
//            }
//            mInputStream = null;
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        WindowDisplayUtils.hideNavigationBar(this);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
//        releasePrint();
        releaseScanPort();
        if (scanReceiver != null) {
            unregisterReceiver(scanReceiver);
        }
        super.onDestroy();

    }


    class ScanReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String buffer = intent.getStringExtra("scanBuffer");
//            ToastGlobal.showShort("receiver broadcast");

            if (buffer != null && !isCheckintAppointCode) {
                handler.removeMessages(NULL_EVENT);
                startCheckAppointCode(buffer);
//                txtAppointCode.setText(buffer);
//                ToastGlobal.showShort("received : " + buffer);

//                scanBuffer.delete(0, scanBuffer.length());
            }

//            if (end) {
//                handler.removeMessages(NULL_EVENT);
//                startCheckAppointCode(scanBuffer.toString());
//                txtAppointCode.setText(scanBuffer.toString());
//                scanBuffer.delete(0, scanBuffer.length());
//
//            } else if (buffer != null) {
//                scanBuffer.append(buffer);
//                ToastGlobal.showShortConsecutive(scanBuffer.toString());
//            }
        }
    }
}
