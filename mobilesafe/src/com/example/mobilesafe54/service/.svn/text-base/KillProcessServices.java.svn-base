package com.example.mobilesafe54.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class KillProcessServices extends Service {

	private ActivityManager activityManager;


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class ScreenLockReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
			for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
				activityManager.killBackgroundProcesses(runningAppProcessInfo.processName);
			}
			
		}
		
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		System.out.println("自动清理的服务");
		
		activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		
		//锁屏的过滤器
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		
		ScreenLockReceiver receiver = new ScreenLockReceiver();
		//注册一个锁屏的广播
		registerReceiver(receiver, filter);
		
		//定时器
//		Timer timer = new Timer();
//		TimerTask timerTask = new TimerTask() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				System.out.println("我是定时器");
//			
//			}
//		};
//		//定时调度
//		//每隔2二个小时清理一次
//		timer.schedule(timerTask, 0, 1000 * 60 * 60 *2);
	}

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
