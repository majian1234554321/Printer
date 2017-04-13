package com.leyuan.printer.http.subscriber;

import android.content.Context;

import com.leyuan.printer.R;
import com.leyuan.printer.utils.ToastGlobal;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;


public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            ToastGlobal.showShortConsecutive(R.string.connect_timeout);
        } else if (e instanceof ConnectException) {
            ToastGlobal.showShortConsecutive(R.string.connect_break);
        } else {
            ToastGlobal.showShortConsecutive("" + e.getMessage());
        }
    }

    @Override
    public void onCompleted() {

    }


}
