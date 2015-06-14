package com.example.mobilesafe54.service;

import java.util.Timer;
import java.util.TimerTask;

import com.example.mobilesafe54.R;
import com.example.mobilesafe54.receiver.KillProcessReceiver;
import com.example.mobilesafe54.receiver.MyAppWidgetProvider;
import com.example.mobilesafe54.utils.SystemInfo;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Formatter;
import android.widget.RemoteViews;

public class AppWidgetProviderServices extends Service {

	private Timer timer;
	private TimerTask timerTask;
	private AppWidgetManager appWidgetManager;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//获取到桌面小控件的管理者
		
		appWidgetManager = AppWidgetManager.getInstance(this);
		
		timer = new Timer();
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("桌面小控件被调用了");
				//第一个参数表示包名
				//第二个参数表示类名
				ComponentName provider = new ComponentName(getPackageName(),"com.example.mobilesafe54.receiver.MyAppWidgetProvider");
				//这里需要把我们当前的程序放置到桌面上。所以必须要有一个远程的view.
				//这个远程的view是一个代理对象
				RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.process_widget);
				//设置控件的文本
				remoteViews.setTextViewText(R.id.process_count, "正在运行的软件:" + SystemInfo.getProcessCount(getApplicationContext()));
				
				remoteViews.setTextViewText(R.id.process_memory, "可用内存:" + Formatter.formatFileSize(getApplicationContext(), SystemInfo.getAvailMem(getApplicationContext())));
				
				Intent intent = new Intent(getApplicationContext(),KillProcessReceiver.class);
				
				//需要使用隐式意图
				
				//registerReceiver(receiver, filter)
			
				
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
				//设置按钮的点击事件
				remoteViews.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);
				
				//更新桌面
				//ComponentName 组件的名字 当前的桌面小控件由谁去执行
				//RemoteViews 远程的view
				appWidgetManager.updateAppWidget(provider, remoteViews);
				
			}
		};
		timer.schedule(timerTask, 0, 5000);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(timer != null && timerTask != null){
			timerTask.cancel();
			timer.cancel();
			timer = null;
			timerTask = null;
		}
		
	}
	

}
