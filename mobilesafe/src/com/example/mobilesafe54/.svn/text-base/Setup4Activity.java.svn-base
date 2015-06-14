package com.example.mobilesafe54;

import com.example.mobilesafe54.utils.SharedPreferencesUtils;
import com.example.mobilesafe54.utils.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class Setup4Activity extends BaseSetupActivity {
	
	private TextView tv_status;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initUI();
		initTitleBar();
	}

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("请开启防盗保护");
	}

	private void initUI() {
		setContentView(R.layout.activity_setup4);
		CheckBox cb_status = (CheckBox) findViewById(R.id.cb_status);
		
	    tv_status = (TextView) findViewById(R.id.tv_status);
	    
	    boolean result = SharedPreferencesUtils.getBoolean(this, "protecting", false);
	    
	    if(result){
	    	cb_status.setChecked(true);
	    }else{
	    	cb_status.setChecked(false);
	    }
	    
		//设置checkbox状态改变的监听
		cb_status.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					tv_status.setText("防盗保护已经开启");
					//缓存防盗保护是否开启
					SharedPreferencesUtils.saveBoolean(Setup4Activity.this, "protecting", true);
				}else{
					tv_status.setText("请开启防盗保护");
				}
				
			}
		});
	}
	/**
	 * 设置完成
	 * @param view
	 */
	public void next(View view){
		boolean result = SharedPreferencesUtils.getBoolean(this, "protecting", false);
		if(result){
			ToastUtils.showSafeTost(this, "设置完成");
			Intent intent = new Intent(this,FindLostActivity.class);
			startActivity(intent);
			//设置完成防盗保护的界面
			SharedPreferencesUtils.saveBoolean(this, "setfinish", true);
		}else{
			ToastUtils.showSafeTost(this, "请开启防盗保护");
		}
	}
	/**
	 * 上一步
	 * @param view
	 */
	public void pre(View view){
		Intent intent = new Intent(this,Setup3Activity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this,Setup3Activity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void showNext() {
		boolean result = SharedPreferencesUtils.getBoolean(this, "protecting", false);
		if(result){
			ToastUtils.showSafeTost(this, "设置完成");
			Intent intent = new Intent(this,FindLostActivity.class);
			startActivity(intent);
			//设置完成防盗保护的界面
			SharedPreferencesUtils.saveBoolean(this, "setfinish", true);
		}else{
			ToastUtils.showSafeTost(this, "请开启防盗保护");
		}
		
	}

}
