package com.leyuan.printer.mvp.view;

import com.leyuan.printer.entry.PrintResult;

/**
 * Created by user on 2017/4/17.
 */
public interface PrintInfoListener {
    void onGetPrintInfo(PrintResult printResult, String code);

    void onPostPrintState(boolean success);
}
