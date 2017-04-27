package com.leyuan.printer.utils;

import android.app.Activity;
import android.view.View;

/**
 * Created by user on 2017/4/17.
 */
public class WindowDisplayUtils {
    private static int options = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    public static void hideNavigationBar(Activity context) {

        final View decorView = context.getWindow().getDecorView();
        decorView.setSystemUiVisibility(options);

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                decorView.setSystemUiVisibility(options);
            }
        });
    }


}
