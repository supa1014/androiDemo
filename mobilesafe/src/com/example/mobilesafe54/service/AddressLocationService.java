package com.example.mobilesafe54.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mobilesafe54.R;
import com.example.mobilesafe54.db.dao.AddressNumberDao;
import com.example.mobilesafe54.utils.SharedPreferencesUtils;

public class AddressLocationService extends Service {

	private TelephonyManager tm;
	private MyPhoneStateListener listener;
	private WindowManager mWM;
	private TextView textView;
	private View view;
	private WindowManager.LayoutParams params;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 注意当前的PhoneStateListener状态。只能处理。别人给你打电话
     * @author Administrator
     *
     */
	private class MyPhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);

			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:

				String location = AddressNumberDao.getLocation(incomingNumber);

				// Toast.makeText(AddressLocationService.this, "归属地:"+location,
				// 0).show();
				// 初始化自定义的土司
				showMyToast(location);
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				if (view != null) {
					mWM.removeView(view);
				}

				break;

			}
		}

	}

	/**
	 * 外拨电话
	 * @author Administrator
	 *
	 */
	private class OutCallReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
		    //获取到当前正在拨打的电话号码
			String resultData = getResultData();
			String location = AddressNumberDao.getLocation(resultData);
			showMyToast(location);
		}
		
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		// 获取到窗体的服务
		mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

		listener = new MyPhoneStateListener();

		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		//初始化外拨电话的广播
		OutCallReceiver receiver = new OutCallReceiver();
		//Intent.ACTION_NEW_OUTGOING_CALL外拨电话的过滤器
		registerReceiver(receiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));

	}

	private int[] style = { R.drawable.call_locate_white,
			R.drawable.call_locate_orange,R.drawable.call_locate_blue,
			R.drawable.call_locate_gray,R.drawable.call_locate_green};

	public void showMyToast(String location) {
		view = View.inflate(this, R.layout.toast, null);
		
		TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
		
		tv_phone.setText("归属地:" + location);
		
		//获取到背景
		int bgStyle = SharedPreferencesUtils.getInt(this, "which", 0);
		
		//设置电话的背景
		view.setBackgroundResource(style[bgStyle]);
		
		

		WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

		params = mParams;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		//设置到屏幕的左上 角 注意：吐司天生在屏幕的中心。需要移动到屏幕 的原点
		params.gravity = Gravity.LEFT + Gravity.TOP;
		//设置移动之后控件 的x轴和y轴的值
		params.x = SharedPreferencesUtils.getInt(AddressLocationService.this, "lastX", 0);
		params.y = SharedPreferencesUtils.getInt(AddressLocationService.this, "lastY", 0);

		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		// | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE 这个标记表示不能触摸
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		// params.type = WindowManager.LayoutParams.TYPE_TOAST;吐司天生是没有焦点
		//注意需要添加权限<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;// 设置电话的类型
		mWM.addView(view, mParams);
		// 设置view的触摸事件
		view.setOnTouchListener(new OnTouchListener() {

			private int startX;
			private int startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				// 当手指按下去的时候调用的方法
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();

					startY = (int) event.getRawY();

					break;

				case MotionEvent.ACTION_MOVE:

					int currentX = (int) event.getRawX();

					int currentY = (int) event.getRawY();
					// 获取到手指移动之后的间距
					int dx = currentX - startX;

					int dy = currentY - startY;
					
					
					params.x += dx;
					
					params.y += dy;
					
					//判断当前的x轴的值如果已经移动出了屏幕。那么就让当前的x轴等于0
					if(params.x < 0){
						params.x = 0;
					}
					
					if(params.y < 0){
						params.y = 0;
					}
					//获取到屏幕的宽和高
//					System.out.println("屏幕的宽---" +mWM.getDefaultDisplay().getWidth() );
					
//					System.out.println("屏幕的高---" + mWM.getDefaultDisplay().getHeight());
					if(params.x > mWM.getDefaultDisplay().getWidth() - view.getWidth()){
						params.x = mWM.getDefaultDisplay().getWidth() - view.getWidth();
					}
					
					if(params.y > mWM.getDefaultDisplay().getHeight() - view.getHeight()){
						params.y = mWM.getDefaultDisplay().getHeight() - view.getHeight();
					}
					
					
					
					
					//更新当前的界面
					mWM.updateViewLayout(view, params);
					
					startX = (int) event.getRawX();

					startY = (int) event.getRawY();

					break;
				case MotionEvent.ACTION_UP:
					
					
					//保存x轴和y轴的值
					
					SharedPreferencesUtils.saveInt(AddressLocationService.this, "lastX", params.x);
					SharedPreferencesUtils.saveInt(AddressLocationService.this, "lastY", params.y);
					

					break;
				}
				return true;
			}
		});

	}

	@Override
	public void onDestroy() {
		// 当前的监听器设置为null
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		super.onDestroy();
	}

}
