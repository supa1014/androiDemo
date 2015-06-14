package com.example.mobilesafe54.receiver;

import com.example.mobilesafe54.utils.SharedPreferencesUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-24 上午9:25:19
 * 
 * 描 述 ：
 * 
 *        手机开机接收到的广播
 * 修订历史 ：
 * 
 * ============================================================
 **/
public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("我接收到广播了");
        /**
         * 判断当前的小偷会不会换掉我们的sim卡
         * 1  获取到我们缓存的sim卡
         * 2  获取到当前的sim卡
         * 3  判断缓存的sim卡和当前的sim卡是否一致
         *       如果不一致。那么小偷就换掉了我们的sim卡
         * 
         */
		//获取到我们缓存的sim卡
		String sim = SharedPreferencesUtils.getString(context, "sim", "");
		//获取到电话相关的系统服务
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		//获取到当前手机里面的sim卡串号
		String simSerialNumber = tm.getSimSerialNumber() + "aaaa";
		//判断sim卡是否一致
		if(simSerialNumber.equals(sim)){
			//如果相同。说明手机没丢
		}else{
			//如果不同就可能丢了
			
			//获取到安全号码
			
			String safePhone = SharedPreferencesUtils.getString(context, "phone", "");
			
			SmsManager.getDefault().sendTextMessage(safePhone, null, "sim change", null, null);
			
			
		}
	}

}












