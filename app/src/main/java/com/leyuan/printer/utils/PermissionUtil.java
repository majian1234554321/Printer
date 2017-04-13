package com.leyuan.printer.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by user on 2017/4/13.
 */

public class PermissionUtil {

    public void exeShell(String cmd) {

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                Log.i("exeShell", line);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
