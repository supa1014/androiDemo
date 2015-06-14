package com.example.mobilesafe54;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.mobilesafe54.service.AddressLocationService;
import com.example.mobilesafe54.service.CallSafeService;
import com.example.mobilesafe54.service.WatchDogService;
import com.example.mobilesafe54.utils.SharedPreferencesUtils;
import com.example.mobilesafe54.utils.SystemInfo;
import com.example.mobilesafe54.view.SettingView;

public class SettingCenterActivity extends Activity {

	private TextView tv;
	private TextView tv_title;
	private TextView tv_desc;
	private SettingView sv_view;
	private SettingView sv_blacknumber;
	private Intent callSafeIntent;
	private SettingView sv_address_location;
	private Intent addressLocationIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initTitleBar();

	}

	private void initTitleBar() {
		tv.setText("设置中心");

	}

	private String[] items = { "半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿" };
	private TextView tv_style_name;
	private SettingView sv_watchdog;
	private Intent watchDogServiceIntent;

	/**
	 * 设置背景的样式
	 * 
	 * @param view
	 */
	public void changeBgStyle(View view) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(R.drawable.block_icon);
		builder.setTitle("归属地的提示风格");
		//设置单选
		builder.setSingleChoiceItems(items, SharedPreferencesUtils.getInt(
				SettingCenterActivity.this, "which", 0),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						tv_style_name.setText(items[which]);
						
						SharedPreferencesUtils.saveInt(
								SettingCenterActivity.this, "which", which);
						dialog.dismiss();
					}
				});

		

		builder.show();
	}

	private void initUI() {
		setContentView(R.layout.activity_setting_center);

		tv = (TextView) findViewById(R.id.tv);
		
		tv_style_name = (TextView) findViewById(R.id.tv_style_name);
		//设置默认归属地风格默认的值
		int style_name = SharedPreferencesUtils.getInt(this, "which", 0);
		
		tv_style_name.setText(items[style_name]);
		
		// 更新
		initUpdate();

		// 黑名单
		initBlackNumber();
		// 位置查询
		initAddressLocation();
		
		initWatchDog();

	}

	private void initWatchDog() {
		sv_watchdog = (SettingView) findViewById(R.id.sv_watchdog);

		watchDogServiceIntent = new Intent(this, WatchDogService.class);

		sv_watchdog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sv_watchdog.isChecked()) {
					sv_watchdog.setChecked(false);
					stopService(watchDogServiceIntent);
				} else {
					sv_watchdog.setChecked(true);
					startService(watchDogServiceIntent);
				}
			}
		});
		
	}

	private void initAddressLocation() {
		sv_address_location = (SettingView) findViewById(R.id.sv_address_location);

		addressLocationIntent = new Intent(this, AddressLocationService.class);

		sv_address_location.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sv_address_location.isChecked()) {
					sv_address_location.setChecked(false);
					stopService(addressLocationIntent);
				} else {
					sv_address_location.setChecked(true);
					startService(addressLocationIntent);
				}
			}
		});

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 判断当前的服务是否开启
		if (SystemInfo.isServiceRunning(this,
				"com.example.mobilesafe54.service.CallSafeService")) {
			sv_blacknumber.setChecked(true);
		} else {
			sv_blacknumber.setChecked(false);
		}

		if (SystemInfo.isServiceRunning(this,
				"com.example.mobilesafe54.service.AddressLocationService")) {
			sv_address_location.setChecked(true);
		} else {
			sv_address_location.setChecked(false);
		}
		
		
		if (SystemInfo.isServiceRunning(this,
				"com.example.mobilesafe54.service.WatchDogService")) {
			sv_watchdog.setChecked(true);
		} else {
			sv_watchdog.setChecked(false);
		}

	}

	/**
	 * 是否开启黑名单服务
	 */
	private void initBlackNumber() {
		sv_blacknumber = (SettingView) findViewById(R.id.sv_blacknumber);

		callSafeIntent = new Intent(this, CallSafeService.class);

		sv_blacknumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sv_blacknumber.isChecked()) {
					sv_blacknumber.setChecked(false);
					stopService(callSafeIntent);
				} else {
					sv_blacknumber.setChecked(true);
					startService(callSafeIntent);
				}
			}
		});

	}

	/**
	 * 是否开启更新
	 */
	private void initUpdate() {
		sv_view = (SettingView) findViewById(R.id.sv_view);

		boolean result = SharedPreferencesUtils.getBoolean(this, "isupdate",
				false);

		if (result) {
			sv_view.setChecked(true);
		} else {
			sv_view.setChecked(false);
		}

		sv_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 判断当前的settingview是否被勾选上
				if (sv_view.isChecked()) {
					sv_view.setChecked(false);
					SharedPreferencesUtils.saveBoolean(
							SettingCenterActivity.this, "isupdate", false);
				} else {
					sv_view.setChecked(true);
					SharedPreferencesUtils.saveBoolean(
							SettingCenterActivity.this, "isupdate", true);
				}

			}
		});

	}
}
