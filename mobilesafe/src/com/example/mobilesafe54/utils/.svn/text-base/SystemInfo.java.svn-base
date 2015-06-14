package com.example.mobilesafe54.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;

import android.R.integer;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-27 下午4:45:19
 * 
 * 描 述 ：
 * 
 * 用来判断当前的手机系统相关的一些方法 修订历史 ：
 * 
 * ============================================================
 **/
public class SystemInfo {

	// 用来判断当前的服务是否开启

	public static boolean isServiceRunning(Context context, String serviceName) {
		// 获取到当前的系统服务
		// ActivityManager表示进程管理器
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取当前正在运行的服务
		List<RunningServiceInfo> runningServices = am.getRunningServices(100);
		// 迭代当前所有的服务
		for (RunningServiceInfo info : runningServices) {
			System.out.println("-----------------------");
			// 获取当前服务的名字
			String className = info.service.getClassName();
			if (serviceName.equals(className)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 返回进程的个数
	 * 
	 * @param context
	 * @return
	 */
	public static int getProcessCount(Context context) {
		// 进程管理器(活动管理)
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取到当前手机上面正在运行的进程
		List<RunningAppProcessInfo> runningAppProcesses = activityManager
				.getRunningAppProcesses();
		int size = runningAppProcesses.size();
		return size;
	}

	/**
	 * 获取到有用的内存信息
	 * 
	 * @param context
	 * @return
	 */
	public static long getAvailMem(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		// 获取到内存的基本信息
		activityManager.getMemoryInfo(memoryInfo);
		// 获取到一个可用内存

		return memoryInfo.availMem;

	}

	/**
	 * 返回总共进程的大小
	 * 
	 * @param context
	 * @return
	 */
	public static long getMemCount(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// MemTotal: 513492 kB
		// 获取到一个输入流

		try {
			FileInputStream fis = new FileInputStream(new File("/proc/meminfo"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			// 一次性可以读取一行数据
			String readLine = reader.readLine();

			StringBuffer sb = new StringBuffer();

			for (char c : readLine.toCharArray()) {
				if (c >= '0' && c <= '9') {
					sb.append(c);
				}
			}
			// 为了格式化方便* 1024
			return Long.parseLong(sb.toString()) * 1024;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

}
