package com.example.mobilesafe54;

import com.example.mobilesafe54.utils.SharedPreferencesUtils;
import com.example.mobilesafe54.utils.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Setup3Activity extends BaseSetupActivity implements
		OnClickListener {
	// 电话号码
	private EditText et_phone;
	private Button select_contacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		initUI();
	}

	private void initUI() {
		setContentView(R.layout.activity_setup3);
		et_phone = (EditText) findViewById(R.id.et_phone);
		select_contacts = (Button) findViewById(R.id.select_contacts);
		select_contacts.setOnClickListener(this);

		initTitleBar();
		// 获取安全号码
		String result = SharedPreferencesUtils.getString(this, "phone", "");
		// 判断安全号码是否为null
		if (TextUtils.isEmpty(result)) {
			et_phone.setText("");
		} else {
			et_phone.setText(result);
		}

	}

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("设置安全号码");
	}

	/**
	 * 下一步
	 * 
	 * @param view
	 */
	public void next(View view) {
		// 获取电话号码
		String str_phone = et_phone.getText().toString().trim();
		// 判断当前电话号码是否为null
		if (TextUtils.isEmpty(str_phone)) {
			ToastUtils.showSafeTost(Setup3Activity.this, "请输入电话号码");
		} else {

			SharedPreferencesUtils.saveString(this, "phone", str_phone);
			Intent intent = new Intent(this, Setup4Activity.class);
			startActivity(intent);
		}
	}

	public void pre(View view) {
		Intent intent = new Intent(this, Setup2Activity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		// 进入联系人
		Intent intent = new Intent(Setup3Activity.this,
				SelectContactsActivity.class);
		// startActivity(intent);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 判断选择联系人传过来的数据是否为null
		if (data != null) {
			// 获取到电话号码。key必须和传过来的保持一直
			String phone = data.getStringExtra("phone");

			et_phone.setText(phone);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void showPre() {
		Intent intent = new Intent(this, Setup2Activity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void showNext() {
		// 获取电话号码
		String str_phone = et_phone.getText().toString().trim();
		// 判断当前电话号码是否为null
		if (TextUtils.isEmpty(str_phone)) {
			ToastUtils.showSafeTost(Setup3Activity.this, "请输入电话号码");
		} else {

			SharedPreferencesUtils.saveString(this, "phone", str_phone);
			Intent intent = new Intent(this, Setup4Activity.class);
			startActivity(intent);
		}

	}

}
