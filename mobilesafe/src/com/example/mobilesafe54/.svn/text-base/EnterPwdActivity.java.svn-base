package com.example.mobilesafe54;

import com.example.mobilesafe54.service.WatchDogService;
import com.example.mobilesafe54.utils.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EnterPwdActivity extends Activity implements OnClickListener {

	private EditText et_pwd;
	private Button bt_0;
	private Button bt_1;
	private Button bt_2;
	private Button bt_3;
	private Button bt_4;
	private Button bt_5;
	private Button bt_6;
	private Button bt_7;
	private Button bt_8;
	private Button bt_9;
	private Button bt_clean;
	private Button bt_delete;
	private String packageName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initTitleBar();
	}

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("请输入密码");
		Button bt_left = (Button) findViewById(R.id.bt_left);
		bt_left.setVisibility(View.VISIBLE);
		bt_left.setText("退出");
		bt_left.setBackgroundResource(R.drawable.header_button_bg_back);
	}

	private void initUI() {
		setContentView(R.layout.activity_enter_pwd);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		
		packageName = getIntent().getStringExtra("packageName");
		
		bt_0 = (Button) findViewById(R.id.bt_0);
		bt_1 = (Button) findViewById(R.id.bt_1);
		bt_2 = (Button) findViewById(R.id.bt_2);
		bt_3 = (Button) findViewById(R.id.bt_3);
		bt_4 = (Button) findViewById(R.id.bt_4);
		bt_5 = (Button) findViewById(R.id.bt_5);
		bt_6 = (Button) findViewById(R.id.bt_6);
		bt_7 = (Button) findViewById(R.id.bt_7);
		bt_8 = (Button) findViewById(R.id.bt_8);
		bt_9 = (Button) findViewById(R.id.bt_9);
		bt_clean = (Button) findViewById(R.id.bt_clean);
		bt_delete = (Button) findViewById(R.id.bt_delete);
		bt_0.setOnClickListener(this);
		bt_1.setOnClickListener(this);
		bt_2.setOnClickListener(this);
		bt_3.setOnClickListener(this);
		bt_4.setOnClickListener(this);
		bt_5.setOnClickListener(this);
		bt_6.setOnClickListener(this);
		bt_7.setOnClickListener(this);
		bt_8.setOnClickListener(this);
		bt_9.setOnClickListener(this);
		bt_clean.setOnClickListener(this);
		bt_delete.setOnClickListener(this);

	}

	/**
	 * 确定
	 * 
	 * @param view
	 */
	public void click(View view) {
		String str_pwd = et_pwd.getText().toString().trim();
		//判断当前用户输入的密码是否正确
		if("123".equals(str_pwd)){
			//输入密码正确。那么就不需要锁起来
			System.out.println("密码正确");
			//告诉我的狗。不要叫了
			
			//临时停止保护
			
			Intent intent = new Intent();
			intent.setAction("com.itheima.temp_stop_protect");
			intent.putExtra("packageName", packageName);
			sendBroadcast(intent);
			finish();//关闭输入密码的界面。
			
			
			finish();
		}else{
			ToastUtils.showSafeTost(EnterPwdActivity.this, "密码不正确");
		}
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_0:
			String str = et_pwd.getText().toString().trim();
			et_pwd.setText(str + bt_0.getText().toString());
			break;

		case R.id.bt_1:
			String str_1 = et_pwd.getText().toString().trim();
			et_pwd.setText(str_1 + bt_1.getText().toString());
			break;
		case R.id.bt_2:
			String str_2 = et_pwd.getText().toString().trim();
			et_pwd.setText(str_2 + bt_2.getText().toString());
			break;
		case R.id.bt_3:
			String str_3 = et_pwd.getText().toString().trim();
			et_pwd.setText(str_3 + bt_3.getText().toString());
			break;

		case R.id.bt_4:
			String str_4 = et_pwd.getText().toString().trim();
			et_pwd.setText(str_4 + bt_4.getText().toString());
			break;
		case R.id.bt_5:
			String str_5 = et_pwd.getText().toString().trim();
			et_pwd.setText(str_5 + bt_5.getText().toString());
			break;
		case R.id.bt_clean:
			
			et_pwd.setText("");
			break;
		case R.id.bt_delete:
			String str_delete = et_pwd.getText().toString().trim();
			if(str_delete.length() == 0){
				et_pwd.setText("");
			}else{
				et_pwd.setText(str_delete.substring(0, str_delete.length() - 1));
			}
		
			break;
		}
		//设置文本输入框的选中的位置
		et_pwd.setSelection(et_pwd.getText().toString().length());
	}
	
//	 <intent-filter>
//     <action android:name="android.intent.action.MAIN" />
//     <category android:name="android.intent.category.HOME" />
//     <category android:name="android.intent.category.DEFAULT" />
//     <category android:name="android.intent.category.MONKEY"/>
// </intent-filter>
	
	/**
	 * 监听后退键
	 */
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.HOME");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addCategory("android.intent.category.MONKEY");
		startActivity(intent);
		
	}

}
