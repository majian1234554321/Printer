package com.leyuan.printer.mvp.model;

import com.leyuan.printer.entry.PrintResult;
import com.leyuan.printer.http.RetrofitHelper;
import com.leyuan.printer.http.RxHelper;
import com.leyuan.printer.http.api.PrintService;
import com.leyuan.printer.ui.App;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by user on 2017/4/17.
 */

public class PrinterModel {

    private PrintService service;

    public PrinterModel() {
        service = RetrofitHelper.createApi(PrintService.class);

    }

    public void getPrintInfo(String code, String lessonType, Subscriber<PrintResult> subscribe) {
        service.getPrintInfo(code, App.getInstance().getChannel(), lessonType)
                .compose(RxHelper.<PrintResult>transform())
                .subscribe(subscribe);
    }

    public void getPrintInfo(String code, Subscriber<PrintResult> subscribe) {
        service.getPrintInfo(code, App.getInstance().getChannel())
                .compose(RxHelper.<PrintResult>transform())
                .subscribe(subscribe);
    }

    public void printSuccess(String code, String lessonType, Subscriber<PrintResult> subscribe) {
        service.printSuccess(code, App.getInstance().getChannel(), lessonType)
                .compose(RxHelper.<PrintResult>transform())
                .subscribe(subscribe);
    }

    public void getBanners(Subscriber<ArrayList<String>> subscribe) {
        service.getBanners(App.getInstance().getChannel())
                .compose(RxHelper.<ArrayList<String>>transform())
                .subscribe(subscribe);
    }
}
