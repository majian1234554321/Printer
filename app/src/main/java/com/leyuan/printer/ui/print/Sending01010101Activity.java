/*
 * Copyright 2011 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.leyuan.printer.ui.print;

import android.os.Bundle;

import com.leyuan.printer.R;
import com.leyuan.printer.utils.PrintUtils;

import java.io.IOException;

public class Sending01010101Activity extends SerialPortActivity {
	SendingThread mSendingThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sending01010101);

		if (mSerialPort != null) {
			mSendingThread = new SendingThread();
			mSendingThread.start();
		}
	}

	@Override
	protected void onDataReceived(byte[] buffer, int size) {
		// ignore incoming data

	}

	private class SendingThread extends Thread {
		@Override
		public void run() {
			try {
				if (mOutputStream != null) {

					setCommand(PrintUtils.RESET);

					setCommand(PrintUtils.ALIGN_LEFT);
					setCommand(PrintUtils.OPEN_CHINA);
					lineFeed(3);
					printText("店名:测试店铺\n");
					printText("销售员:狗总\n");
					printText("订单编号:16050817235343\n");
					printText("订单时间:2017-4-13 19：44\n");
					setCommand(PrintUtils.CLOSE_CHINA);

					printText("------------------------");
					lineFeed(3);

				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	/**
	 * 设置打印格式
	 *
	 * @param command 格式指令
	 */
	public  void setCommand(byte[] command) {
		try {
			mOutputStream.write(command);
			mOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印文字
	 *
	 * @param text 要打印的文字
	 */
	public  void printText(String text) throws IOException{
		byte[] data = text.getBytes("gbk");
		mOutputStream.write(data, 0, data.length);
		mOutputStream.flush();
	}

	public void lineFeed(int num)throws IOException{
		for(int i =0; i < num;i++){
			mOutputStream.write(PrintUtils.LINE_FEED);
		}
		mOutputStream.flush();
	}


}
