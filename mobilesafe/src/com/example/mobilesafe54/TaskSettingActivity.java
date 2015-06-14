package com.example.mobilesafe54;

import com.example.mobilesafe54.service.KillProcessServices;
import com.example.mobilesafe54.utils.SharedPreferencesUtils;
import com.example.mobilesafe54.utils.SystemInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class TaskSettingActivity extends Activity {

	private CheckBox cb_kill_process;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initTitleBar();
	}

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("进程管理设置");
	}

	private void initUI() {
		setContentView(R.layout.activity_task_setting);
		CheckBox cb_show_system_process = (CheckBox) findViewById(R.id.cb_show_system_process);
		cb_kill_process = (CheckBox) findViewById(R.id.cb_kill_process);
		cb_show_system_process.setChecked(SharedPreferencesUtils.getBoolean(
				this, "show_system_process", false));
		cb_show_system_process
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						SharedPreferencesUtils.saveBoolean(
								TaskSettingActivity.this,
								"show_system_process", isChecked);
					}
				});
		final Intent intent = new Intent(this,KillProcessServices.class);
		
		cb_kill_process.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					startService(intent);
				}
			}
		});
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		if(SystemInfo.isServiceRunning(this, "com.example.mobilesafe54.service.KillProcessServices")){
			cb_kill_process.setChecked(true);
		}else{
			cb_kill_process.setChecked(false);
		}
	}
}
