package com.example.mobilesafe54;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilesafe54.db.dao.AddressNumberDao;
import com.example.mobilesafe54.utils.ToastUtils;

public class QueryNumberAddressActivity extends Activity implements
		OnClickListener {

	private EditText et_phone;
	private TextView tv_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		initUI();
		initTitleBar();
	}

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("号码归属地查询");
	}

	private void initUI() {
		setContentView(R.layout.activity_query_address);
		Button bt_query = (Button) findViewById(R.id.bt_query);
		bt_query.setOnClickListener(this);
		et_phone = (EditText) findViewById(R.id.et_phone);
		tv_address = (TextView) findViewById(R.id.tv_address);
		//文本编辑框设置添加改变的监听
		et_phone.addTextChangedListener(new TextWatcher(){
            //改变之前调用的方法
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				System.out.println("beforeTextChanged");
				
			}
            //改变中
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				System.out.println("onTextChanged");
				
			}
            //改变之后调用的方法
			@Override
			public void afterTextChanged(Editable s) {
				String str_phone = et_phone.getText().toString().trim();
				if(!TextUtils.isEmpty(str_phone)){
					String location = AddressNumberDao.getLocation(str_phone);
					//当归属地的位置不为null的时候设置位置
					if(!TextUtils.isEmpty(location)){
						tv_address.setText("归属地:" + location);
					}
				}
				
				
			}
			
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_query:
			String str_phone = et_phone.getText().toString().trim();
			// 判断当前的用户输入的电话号码是否为null
			if (TextUtils.isEmpty(str_phone)) {

				Animation shake = AnimationUtils.loadAnimation(this,
						R.anim.shake);
				et_phone.startAnimation(shake);
				ToastUtils.showSafeTost(this, "请输入电话号码");
			} else {
                //获取到归属地的位置
				String location = AddressNumberDao.getLocation(str_phone);
				//当归属地的位置不为null的时候设置位置
				if(!TextUtils.isEmpty(location)){
					tv_address.setText("归属地:" + location);
				}
				
			}

			break;

		}

	}

}
