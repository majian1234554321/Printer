package com.leyuan.printer.mvp.presenter;

import android.content.Context;

import com.leyuan.printer.config.Constant;
import com.leyuan.printer.entry.PrintResult;
import com.leyuan.printer.http.subscriber.BaseSubscriber;
import com.leyuan.printer.mvp.model.PrinterModel;
import com.leyuan.printer.mvp.view.BannerViewListener;
import com.leyuan.printer.mvp.view.PrintInfoListener;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by user on 2017/4/17.
 */

public class PrinterPresenter {

    private Context context;
    private PrinterModel model;
    private PrintInfoListener printInfoListenerlistener;
    private BannerViewListener bannerViewListener;

    public PrinterPresenter(Context context) {
        this.context = context;
        model = new PrinterModel();
    }

    public void setPrintInfoListener(PrintInfoListener printInfoListenerlistener) {
        this.printInfoListenerlistener = printInfoListenerlistener;
    }

    public void setBannerViewListener(BannerViewListener bannerViewListener) {
        this.bannerViewListener = bannerViewListener;
    }

    public void getPrintInfo(String code, String lessonType) {
        model.getPrintInfo(code, lessonType, new BaseSubscriber<PrintResult>(context) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
//                ToastGlobal.showShort(e.getMessage());
                if (printInfoListenerlistener != null)
                    printInfoListenerlistener.onGetPrintInfo(null);
            }

            @Override
            public void onNext(PrintResult printResult) {
                if (printInfoListenerlistener != null)
                    printInfoListenerlistener.onGetPrintInfo(printResult);
            }
        });
    }

    public void printSuccess(String code, String lessonType) {
        model.printSuccess(code, lessonType, new Subscriber<PrintResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (printInfoListenerlistener != null) {
                    printInfoListenerlistener.onPostPrintState(false);
                }
            }

            @Override
            public void onNext(PrintResult printResult) {
                if (printInfoListenerlistener != null) {
                    printInfoListenerlistener.onPostPrintState(true);
                }
            }
        });
    }

    public void getBanners() {
        model.getBanners(new BaseSubscriber<ArrayList<String>>(context) {
            @Override
            public void onNext(ArrayList<String> strings) {
                if (strings != null)
                    Constant.BANNERS = strings;
                if (bannerViewListener != null) {
                    bannerViewListener.onGetBannerArray(strings);
                }
            }
        });
    }
}
