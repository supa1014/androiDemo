package com.example.mobilesafe54.domain;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class AppInfo {
	/**
	 * 图片
	 */
	private Drawable icon;
	/**
	 * 应用程序的名字
	 */
	private String appName;
	/**
	 * 是否是在机身内存
	 */
	private boolean isRom;

	/**
	 * 应用程序的大小
	 */
	private long apkSize;
	/**
	 * 是否是用户程序
	 */
	private boolean isUserApp;
	/**
	 * 应用程序的包名
	 */
	private String apkPackageName;

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public boolean isRom() {
		return isRom;
	}

	public void setRom(boolean isRom) {
		this.isRom = isRom;
	}

	public long getApkSize() {
		return apkSize;
	}

	public void setApkSize(long apkSize) {
		this.apkSize = apkSize;
	}

	public boolean isUserApp() {
		return isUserApp;
	}

	public void setUserApp(boolean isUserApp) {
		this.isUserApp = isUserApp;
	}

	public String getApkPackageName() {
		return apkPackageName;
	}

	public void setApkPackageName(String apkPackageName) {
		this.apkPackageName = apkPackageName;
	}

	@Override
	public String toString() {
		return "AppInfo [appName=" + appName + ", isRom=" + isRom
				+ ", apkSize=" + apkSize + ", isUserApp=" + isUserApp
				+ ", apkPackageName=" + apkPackageName + "]";
	}

	
}
