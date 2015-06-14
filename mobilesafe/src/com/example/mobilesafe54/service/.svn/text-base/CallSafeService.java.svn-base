package com.example.mobilesafe54.service;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.example.mobilesafe54.db.dao.BlackNumberDao;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
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
 * 创建日期 ： 2015-3-27 下午4:06:53
 * 
 * 描 述 ：
 * 
 * 黑名单的服务 修订历史 ：
 * 
 * ============================================================
 **/
public class CallSafeService extends Service {

	private BlackNumberDao dao;
	private InnerSmsReceiver receiver;
	private TelephonyManager tm;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private class MyContentObserver extends ContentObserver{
		//需要删除的电话号码
        private String incomingNumber;
		public MyContentObserver(Handler handler, String incomingNumber) {
			super(handler);
			this.incomingNumber = incomingNumber;
		}
        /**
         * 当数据库改变的时候调用的方法
         */
		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			super.onChange(selfChange);
			
			getContentResolver().unregisterContentObserver(this);
			//删除黑名单的电话号码
			deleteCallLog(incomingNumber);
		}
		
		
		
	}
	
	/**
	 * 电话状态监听
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPhoneStateListener extends PhoneStateListener {
		/**
		 * 当电话状态发生改变的时候调用的方法 state ： 表示电话的状态 incomingNumber ： 表示电话号码
		 */
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);

			switch (state) {
			// 电话闲置状态
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("CALL_STATE_IDLE");
				break;
			// 电话接通状态
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("CALL_STATE_OFFHOOK");
				break;
			// 电话响铃状态
			case TelephonyManager.CALL_STATE_RINGING:
				System.out.println("CALL_STATE_RINGING");
				// 获取到当前手机上面的拦截模式
				String mode = dao.find(incomingNumber);
				/**
				 * 黑名单的拦截模式 1 全部拦截(电话 + 短信) 2 电话拦截 3 短信拦截
				 */
				if (mode.equals("1") || mode.equals("2")) {
					System.out.println("拦截电话");

					Uri uri = Uri.parse("content://call_log/calls/");
					
					//注册内容观察者
					//true表示前缀满足
					getContentResolver().registerContentObserver(uri, true, new MyContentObserver(new Handler(),incomingNumber));
					
					
					endCall();

				}
				break;
			}

		}

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		dao = new BlackNumberDao(this);
		// 获取到电话管理者

		tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

	

		MyPhoneStateListener listener = new MyPhoneStateListener();

		// 设置电话监听器
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

		receiver = new InnerSmsReceiver();
		// 初始化短信的意图过滤器
		IntentFilter filter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		// 设置短信接收的优先级
		filter.setPriority(2147483647);

		// 注册一个广播
		registerReceiver(receiver, filter);

	}

	public void deleteCallLog(String incomingNumber) {
		Uri uri = Uri.parse("content://call_log/calls/");
		getContentResolver().delete(uri, "number = ?", new String[]{incomingNumber});
		
	}

	/**
	 * 挂断电话
	 */
	public void endCall() {

		try {
			// 通过反射拿到ServiceManager
			Class<?> clazz = getClassLoader().loadClass(
					"android.os.ServiceManager");
			// 通过反射getService方法
			Method method = clazz.getDeclaredMethod("getService", String.class);
			// 实现getService方法
			IBinder iBinder = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
			// 构建一个aidl对象
			ITelephony iTelephony = ITelephony.Stub.asInterface(iBinder);
			// 挂断电话的方法
			iTelephony.endCall();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 内部短信的广播
	 * 
	 * @author Administrator
	 * 
	 */
	private class InnerSmsReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			System.out.println("InnerSmsReceiver我接收到短信了");

			// 获取到短信
			Object[] objs = (Object[]) intent.getExtras().get("pdus");

			for (Object object : objs) {
				SmsMessage smsMessage = SmsMessage
						.createFromPdu((byte[]) object);
				// 获取到当前短信的内容
				String messageBody = smsMessage.getMessageBody();
				// 获取到短信的号码
				String originatingAddress = smsMessage.getOriginatingAddress();
				// 查询当前的电话号码是否在仇人名单里面
				String mode = dao.find(originatingAddress);
				// 如果在。那么就需要把当前的短信拦截下来
				/**
				 * 黑名单的拦截模式 1 全部拦截(电话 + 短信) 2 电话拦截 3 短信拦截
				 */
				// 判断当前用户的拦截模式是1还是3.
				if (mode.equals("1") || mode.equals("3")) {
					System.out.println("你被中断了");
					abortBroadcast();
				}
				/**
				 * 智能拦截 你的头发漂亮 分词技术
				 */
				if (messageBody.contains("发票学生妹")) {
					abortBroadcast();
				}

			}

		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);

		receiver = null;
	}

}
