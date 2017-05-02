package com.leyuan.printer.services;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user on 2017/4/28.
 */

public class ScanReadThread extends Thread {

    private InputStream mInputStream;
    private OnDatReceiverListener listener;

    public ScanReadThread(InputStream inputStream, OnDatReceiverListener listener) {
        this.mInputStream = inputStream;
        this.listener = listener;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            int size;
            try {
                byte[] buffer = new byte[64];
                if (mInputStream == null) return;
                size = mInputStream.read(buffer);
                if (size > 0) {
                    listener.onDataReceived(buffer, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }


}
