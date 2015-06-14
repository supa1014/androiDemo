package com.example.mobilesafe54.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.mobilesafe54.domain.AppInfo;

public class AppInfoParser {

	public static List<AppInfo> getAppInfos(Context context) {

		List<AppInfo> lists = new ArrayList<AppInfo>();

		// 获取到包的管理器
		PackageManager packageManager = context.getPackageManager();
		// 获取到当前安装到手机上面的所有的包
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);

		for (PackageInfo packageInfo : packages) {

			AppInfo appInfo = new AppInfo();

			String packageName = packageInfo.packageName;

			appInfo.setApkPackageName(packageName);

			Drawable icon = packageInfo.applicationInfo
					.loadIcon(packageManager);

			appInfo.setIcon(icon);

			String appName = packageInfo.applicationInfo.loadLabel(
					packageManager).toString();

			appInfo.setAppName(appName);

			String sourceDir = packageInfo.applicationInfo.sourceDir;

			File file = new File(sourceDir);

			appInfo.setApkSize(file.length());
			// 获取到当前应用程序的标记

			int flags = packageInfo.applicationInfo.flags;

			System.out.println("程序的标记----" + flags);
			System.out.println("系统标记---" + ApplicationInfo.FLAG_SYSTEM);
			// ApplicationInfo.FLAG_SYSTEM表示系统的标记
			if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				// 系统程序
				appInfo.setUserApp(false);
			} else {
				// 用户程序

				appInfo.setUserApp(true);
			}
			//判断当前的用户到底是安装到sd卡还是安装到机身内存
			//FLAG_EXTERNAL_STORAGE表示外置存储 。表示就是sd卡
			if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0){
				//sd卡
				appInfo.setRom(false);
			}else{
				//机身内存
				appInfo.setRom(true);
			}
			
			// System.out.println("---------------------");
			// if (sourceDir.startsWith("/data")) {
			// // 用户程序
			// System.out.println("用户程序" + appName);
			// appInfo.setUserApp(true);
			// } else {
			// System.out.println("系统程序" + appName);
			// appInfo.setUserApp(false);
			// }

			lists.add(appInfo);

		}

		return lists;
	}
}
