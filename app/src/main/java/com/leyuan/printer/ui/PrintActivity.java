package com.leyuan.printer.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.printer.R;
import com.leyuan.printer.serialport.SerialPort;
import com.leyuan.printer.utils.BytesUtil;
import com.leyuan.printer.utils.QRCode;
import com.leyuan.printer.utils.ToastGlobal;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;

/**
 * Created by user on 2017/4/12.
 */
public class PrintActivity extends BaseActivity implements View.OnClickListener {
    private TextView txtPrintState;
    private ImageView imgPrinter;
    private ImageView imgQrCodeApp;
    private ImageView imgQrCodeWx;

    private static final int QRIMAGE_SIZE = 180;

    private SerialPort mPrintPort;
    private OutputStream mOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        txtPrintState = (TextView) findViewById(R.id.txt_print_state);
        imgPrinter = (ImageView) findViewById(R.id.img_printer);
        imgQrCodeApp = (ImageView) findViewById(R.id.img_qr_code_app);
        imgQrCodeWx = (ImageView) findViewById(R.id.img_qr_code_wx);
        findViewById(R.id.bt_back).setOnClickListener(this);


        initQRImage();

        try {
            mPrintPort = App.getInstance().getPrintPort();
            mOutputStream = mPrintPort.getOutputStream();
            byte[] text = BytesUtil.hexStringToBytes("dayintest");
            try {
                mOutputStream.write(text);
                mOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SecurityException e) {
            e.printStackTrace();
            ToastGlobal.showLong(R.string.error_security);
        } catch (IOException e) {
            e.printStackTrace();
            ToastGlobal.showLong(R.string.error_unknown);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            ToastGlobal.showLong(R.string.error_configuration);
        }

    }

    private void initQRImage() {
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo_i_dong);
        imgQrCodeApp.setImageBitmap(QRCode.createQRCodeWithLogo("sdkjklasjdflk", QRIMAGE_SIZE, logo));
        imgQrCodeWx.setImageBitmap(QRCode.createQRCodeWithLogo("http:www.baidu.com/map?a=11", QRIMAGE_SIZE, logo));
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
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().closePrintPort();
        mPrintPort = null;
        mOutputStream = null;
    }
}
