package com.example.mobilesafe54.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-21 下午4:30:55
 * 
 * 描 述 ：
 * 
 * 展示安全的吐司 修订历史 ：
 * 
 * ============================================================
 **/
public class ToastUtils {

	public static void showSafeTost(final Activity activity, final String msg) {

		if ("main".equals(Thread.currentThread().getName())) {
			Toast.makeText(activity, msg, 0).show();
		} else {
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(activity, msg, 0).show();

				}
			});
			
		}

	}

}
