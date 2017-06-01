package com.leyuan.printer.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyuan.printer.R;
import com.leyuan.printer.adapter.AdapterViewpager;
import com.leyuan.printer.config.Constant;
import com.leyuan.printer.utils.QRCode;
import com.leyuan.printer.utils.WindowDisplayUtils;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int PAGE_CAROUSEL = 0;
    private static final int DELAYED_MILLIS = 5 * 1000;
    private static final int QRIMAGE_SIZE = 108;
    private static final int QRLOGO_SIZE = 26;

    private ViewPager viewpager;
    private ImageView imgBg;
    private LinearLayout layoutAppTicket;
    private ImageView imgApp;
    //    private LinearLayout layoutWxTicket;
//    private ImageView imgWx;
    private TextView txtQrCodeApp;
    private ImageView imgQrCodeApp;
    private TextView txtQrCodeWx;
    private ImageView imgQrCodeWx;

    private int[] imgs = new int[]{R.drawable.page_six_month_one, R.drawable.page_six_month_second};
    private ArrayList<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (Constant.BANNERS != null && !Constant.BANNERS.isEmpty()) {
            initViewPager(Constant.BANNERS);
        } else {
            initViewPager();
        }
    }

    private void initView() {

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        imgBg = (ImageView) findViewById(R.id.img_bg);
        layoutAppTicket = (LinearLayout) findViewById(R.id.layout_app_ticket);
        imgApp = (ImageView) findViewById(R.id.img_app);
//        layoutWxTicket = (LinearLayout) findViewById(R.id.layout_wx_ticket);
//        imgWx = (ImageView) findViewById(R.id.img_wx);
        txtQrCodeApp = (TextView) findViewById(R.id.txt_qr_code_app);
        imgQrCodeApp = (ImageView) findViewById(R.id.img_qr_code_app);
        txtQrCodeWx = (TextView) findViewById(R.id.txt_qr_code_wx);
        imgQrCodeWx = (ImageView) findViewById(R.id.img_qr_code_wx);

        layoutAppTicket.setOnClickListener(this);
//        layoutWxTicket.setOnClickListener(this);
    }

    private void initViewPager() {
        views.clear();
        for (int i = 0; i < imgs.length; i++) {
            ImageView img = new ImageView(this);
            img.setLayoutParams(new ViewPager.LayoutParams());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(imgs[i]).into(img);
            views.add(img);
        }

        AdapterViewpager adapterViewpager = new AdapterViewpager(views);
        viewpager.setAdapter(adapterViewpager);
        if (views.size() > 1) {
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessageDelayed(PAGE_CAROUSEL, DELAYED_MILLIS);
        }

    }

    private void initViewPager(ArrayList<String> banners) {
        views.clear();

        for (String banner : banners) {
            ImageView img = new ImageView(this);
            img.setLayoutParams(new ViewPager.LayoutParams());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(banner).into(img);
            views.add(img);
        }

        AdapterViewpager adapterViewpager = new AdapterViewpager(views);
        viewpager.setAdapter(adapterViewpager);
        if (views.size() > 1) {
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessageDelayed(PAGE_CAROUSEL, DELAYED_MILLIS);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == PAGE_CAROUSEL) {
                int current = viewpager.getCurrentItem();
                if (current == viewpager.getChildCount() - 1) {
                    current = 0;
                } else {
                    current++;
                }
                viewpager.setCurrentItem(current);
                handler.sendEmptyMessageDelayed(PAGE_CAROUSEL, DELAYED_MILLIS);
            }
        }
    };

    private void initQRImage() {
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo_i_dong);
        imgQrCodeApp.setImageBitmap(QRCode.createQRCodeWithLogo("sdkjklasjdflk", QRIMAGE_SIZE, logo));
        imgQrCodeWx.setImageBitmap(QRCode.createQRCodeWithLogo("http:www.baidu.com/map?a=11", QRIMAGE_SIZE, logo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_app_ticket:
                AppointCodeActivity.start(this, null);
                break;
//            case R.id.layout_wx_ticket:
//                AppointCodeActivity.start(this, Constant.WX_TICKET);
//                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WindowDisplayUtils.hideNavigationBar(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        App.getInstance().exitApp();
    }
}
