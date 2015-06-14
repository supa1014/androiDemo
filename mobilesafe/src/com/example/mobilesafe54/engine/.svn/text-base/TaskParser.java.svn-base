package com.example.mobilesafe54.engine;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;
import android.os.SystemClock;

import com.example.mobilesafe54.R;
import com.example.mobilesafe54.domain.TaskInfo;

public class TaskParser {

	public static List<TaskInfo> getTaskInfos(Context context) {

		ArrayList<TaskInfo> taskInfos = new ArrayList<TaskInfo>();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取到当前正在运行的进程

		PackageManager packageManager = context.getPackageManager();

		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();

		for (RunningAppProcessInfo runningAppProcessInfo : appProcesses) {

			TaskInfo taskInfo = new TaskInfo();

			//获取到进程内存的基本信息
			
			MemoryInfo[] processMemoryInfos = activityManager.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid});
			//Dirty弄脏了的意思:
			//为了方便格式化* 1024 因为系统返回的是km
			long totalPrivateDirty = processMemoryInfos[0].getTotalPrivateDirty() * 1024;
			
			taskInfo.setMemorySize(totalPrivateDirty);
			// 获取到当前进程的名字
			// 进程名字实际上就是包名
			String processName = runningAppProcessInfo.processName;

			taskInfo.setPackageName(processName);
			// 获取到当前手机上面安装包的名字
			// String installerPackageName =
			// packageManager.getInstallerPackageName(processName);

			try {
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(processName, 0);
				// 获取到图标
				Drawable icon = applicationInfo.loadIcon(packageManager);
				taskInfo.setIcon(icon);
				// 获取到应用的名字
				String appName = applicationInfo.loadLabel(packageManager)
						.toString();
				taskInfo.setAppName(appName);
				
				if((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
					//系统进程
					taskInfo.setUserTask(false);
				} else{
					//用户进程
					taskInfo.setUserTask(true);
				}
				SystemClock.sleep(50);

				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				taskInfo.setAppName(processName);
				
				taskInfo.setIcon(context.getResources().getDrawable(R.drawable.ic_launcher));
//				taskInfo.setIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
			}
			taskInfos.add(taskInfo);
		}

		return taskInfos;
	}
}
