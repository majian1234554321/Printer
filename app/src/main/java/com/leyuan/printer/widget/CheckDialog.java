package com.leyuan.printer.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.leyuan.printer.R;
import com.leyuan.printer.utils.WindowDisplayUtils;

/**
 * Created by user on 2017/4/15.
 */

public class CheckDialog extends Dialog {
    private Context context;

    public CheckDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_check, null);
        this.setContentView(view);
        setCancelable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        WindowDisplayUtils.hideNavigationBar((Activity) context);
    }
//    public CheckDialog(Context context, int themeResId) {
//        super(context, themeResId);
//    }
//
//    protected CheckDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }
}
