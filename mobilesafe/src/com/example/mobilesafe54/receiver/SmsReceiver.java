package com.example.mobilesafe54.receiver;

import com.example.mobilesafe54.R;
import com.example.mobilesafe54.service.LocationService;

import android.R.raw;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	DevicePolicyManager mDPM;

	@Override
	public void onReceive(Context context, Intent intent) {

		System.out.println("SmsReceiver");
        //获取到设备管理者
		mDPM = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);

		// 获取到短信
		Object[] objs = (Object[]) intent.getExtras().get("pdus");

		for (Object object : objs) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) object);
			// 获取到当前短信的内容
			String messageBody = smsMessage.getMessageBody();
			// 获取到短信的号码
			String originatingAddress = smsMessage.getOriginatingAddress();

			if (messageBody.equals("dingwei#")) {
				
				//不能在广播里面做耗时的操作。因为他的存活时间只有10秒钟
				
				Intent locationService = new Intent(context,LocationService.class);
				
				context.startService(locationService);
				
				abortBroadcast();
				// 定位
			} else if (messageBody.equals("xiaohui#")) {
				
				mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
				abortBroadcast();
				
				// 销毁数据
			} else if (messageBody.equals("suoding#")) {
				//设置密码
				mDPM.resetPassword("123", 0);
				//锁屏
				mDPM.lockNow();
				abortBroadcast();
				System.out.println("锁定手机");
				// 锁定手机
			} else if (messageBody.equals("baojing#")) {
				// 设置报警音乐
				System.out.println("设置报警音乐");
				MediaPlayer player = MediaPlayer.create(context, R.raw.alarm);
				player.setVolume(1.0f, 1.0f);

				player.start();
				abortBroadcast();
			}

		}
	}

}
