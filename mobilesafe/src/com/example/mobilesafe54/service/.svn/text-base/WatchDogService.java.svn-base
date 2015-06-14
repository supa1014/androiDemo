package com.example.mobilesafe54.service;

import java.util.List;

import com.example.mobilesafe54.EnterPwdActivity;
import com.example.mobilesafe54.db.dao.AppLockDao;
import com.example.mobilesafe54.domain.AppInfo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

public class WatchDogService extends Service {

	private ActivityManager activityManager;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	private String tempStopProtectPackageName;
	private class WatchDogReceiver extends BroadcastReceiver {
		

		@Override
		public void onReceive(Context context, Intent intent) {
           if("com.itheima.temp_stop_protect".equals(intent.getAction())){
        	   //需要临时停止保护
        	   //当前这个包就是我需要停止保护的对象
        	   
        	   tempStopProtectPackageName = intent.getStringExtra("packageName");
        	   
        	   System.out.println("----" + tempStopProtectPackageName);
        	   //优化的三个地方
           }else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
        	   //锁屏
        	   System.out.println("锁屏");
        	   
        	   tempStopProtectPackageName = null;
        	   
        	   flag = true;
        	   
           }else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
        	   System.out.println("解锁");
        	   
        	   if(flag){
        		   
        		   flag = false;
        		   
        		   startWatchDog();
        	   }
           }
		}
	}

	private class MyContentObserver extends ContentObserver{

		public MyContentObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}
        //当数据库改变的时候调用的方法
		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			super.onChange(selfChange);
			System.out.println("数据库发生改变了...");
			appLocks = dao.findAll();
		}
		
		
		
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		
		Uri uri = Uri.parse("content://xxxx");
		
		//注册一个内容观察者
		getContentResolver().registerContentObserver(uri, true, new MyContentObserver(new Handler()));
		
		dao = new AppLockDao(this);
		
		appLocks = dao.findAll();
		
		receiver = new WatchDogReceiver();
		
		//需要设置一个临时停止保护的标记
		IntentFilter filter = new IntentFilter("com.itheima.temp_stop_protect");
		//如果注册的是锁屏的广播的话。必须使用代码注册。不能静态注册。
		//注册一个锁屏的广播 当用户把屏幕已经给锁住了。就没有必要进行保护了。
		//进行锁屏
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		//如果用户已经 进行解锁了。那么还需要进行保护
		//解锁
		filter.addAction(Intent.ACTION_SCREEN_ON);
		
		registerReceiver(receiver, filter);

		activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		
		startWatchDog();
	}

	private boolean flag = false;
	private AppLockDao dao;
	private List<String> appLocks;
	private WatchDogReceiver receiver;

	private void startWatchDog() {
		new Thread() {
			public void run() {

				while (!flag) {

					// 获取到当前正在运行的任务
                    // 优化的第一个地方
					List<RunningTaskInfo> tasks = activityManager
							.getRunningTasks(1);
					// 获取到最顶层的任务
					RunningTaskInfo runningTaskInfo = tasks.get(0);
					// 获取到最顶层任务的activity。然后在获取应用程序的包明
					String packageName = runningTaskInfo.topActivity
							.getPackageName();

					System.out.println("包名:" + packageName);

					// 判断当前正在点击的包名是否在程序锁的数据库里面。如果在。那么就需要把当前的应用程序给锁起来
                    //比较浪费性能,可以优化的第二个地方
					//从内存当中查找,一次性把所有的数据里面的数据全部取出来
					//lists是否包含了当前的这个包名 从内存当中去进行查找
//					if (dao.find(packageName)) {
					if(appLocks.contains(packageName)){
						// 查询到了

//						System.out.println("把当前的应用程序锁起来");
						
						
						
						if(packageName.equals(tempStopProtectPackageName)){
							//临时停止保护
							System.out.println("临时停止保护");
							
							
							
						}else{
							Intent intent = new Intent(WatchDogService.this,
									EnterPwdActivity.class);
							
							intent.putExtra("packageName", packageName);

							// 需要注意。如果是服务跳activity的话。必须设置flag
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

							startActivity(intent);
						}

						

					} else {

					}

					SystemClock.sleep(50);

				}

			};
		}.start();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		flag = true;
		unregisterReceiver(receiver);
		receiver = null;
	}

}
