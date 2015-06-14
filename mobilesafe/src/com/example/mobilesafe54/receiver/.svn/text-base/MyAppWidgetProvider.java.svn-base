package com.example.mobilesafe54.receiver;

import com.example.mobilesafe54.service.AppWidgetProviderServices;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class MyAppWidgetProvider extends AppWidgetProvider {

	/**
	 * 桌面小控件初始化的方法 只会被调用一次
	 */
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		Intent intent = new Intent(context,AppWidgetProviderServices.class);
		context.startService(intent);
	}

	/**
	 * 当桌面上面所有的小控件全部删除的时候才会被调用
	 * 
	 */
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		Intent intent = new Intent(context,AppWidgetProviderServices.class);
		context.stopService(intent);
	}

}
