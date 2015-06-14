package com.example.mobilesafe54;

import com.example.mobilesafe54.utils.SharedPreferencesUtils;
import com.example.mobilesafe54.utils.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Setup2Activity extends BaseSetupActivity implements OnClickListener {

	private ImageView iv_lock_unlock;
	private TelephonyManager tm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();

	}

	private void initUI() {
		setContentView(R.layout.activity_setup2);
		Button bind_unbind_sim = (Button) findViewById(R.id.bind_unbind_sim);
		bind_unbind_sim.setOnClickListener(this);
		iv_lock_unlock = (ImageView) findViewById(R.id.iv_lock_unlock);
		//获取到电话系统的服务
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
	   TextView tv = (TextView) findViewById(R.id.tv);
	   tv.setText("手机防盗");
	   //获取sim卡是否缓存
	   String result = SharedPreferencesUtils.getString(this, "sim", "");
	   //如果有sim卡设置成锁的图片。没有sim卡设置解锁的图片
		if (TextUtils.isEmpty(result)){
			iv_lock_unlock.setImageResource(R.drawable.unlock);
		}else{
			iv_lock_unlock.setImageResource(R.drawable.lock);
			
		}
	
	}
	/**
	 * 上一步
	 * @param view
	 */
	public void pre(View view){
		Intent intent = new Intent(this,Setup1Activity.class);
		startActivity(intent);
	}
	

	/**
	 * 下一步
	 * @param view
	 */
	public void next(View view){
		String result = SharedPreferencesUtils.getString(this, "sim", "");
		//如果当前的sim卡为null给用户提示。必须绑定
		if(TextUtils.isEmpty(result)){
			ToastUtils.showSafeTost(Setup2Activity.this, "请绑定sim卡");
		
		}else{
			Intent intent = new Intent(Setup2Activity.this,Setup3Activity.class);
			startActivity(intent);
		}
		
		
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bind_unbind_sim:
			//判断当前的手机是否绑定了sim卡
			String result = SharedPreferencesUtils.getString(Setup2Activity.this, "sim", "");
			//如果当前的结果里面是为null的话。说明没有绑定
			if(TextUtils.isEmpty(result)){
				//没有绑定
				//获取到当前的sim卡
				String serialNumber = tm.getSimSerialNumber();
				SharedPreferencesUtils.saveString(Setup2Activity.this, "sim", serialNumber);
				iv_lock_unlock.setImageResource(R.drawable.lock);
				ToastUtils.showSafeTost(Setup2Activity.this, "已经绑定");
			}else{
				//解绑就是 在sp里面存放一个空值
				SharedPreferencesUtils.saveString(Setup2Activity.this, "sim", "");
				//当前已经绑定
				iv_lock_unlock.setImageResource(R.drawable.unlock);
				ToastUtils.showSafeTost(Setup2Activity.this, "已经解绑");
			}
			
		

			break;

		}

	}
    /**
     * 上一步
     */
	@Override
	protected void showPre() {
		Intent intent = new Intent(this,Setup1Activity.class);
		startActivity(intent);
		
	}
    /**
     * 下一步
     */
	@Override
	protected void showNext() {
		String result = SharedPreferencesUtils.getString(this, "sim", "");
		//如果当前的sim卡为null给用户提示。必须绑定
		if(TextUtils.isEmpty(result)){
			ToastUtils.showSafeTost(Setup2Activity.this, "请绑定sim卡");
		
		}else{
			Intent intent = new Intent(Setup2Activity.this,Setup3Activity.class);
			startActivity(intent);
		}
		
	}

}
