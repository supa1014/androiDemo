package com.example.mobilesafe54;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {

	protected TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initTitleBar();
		initData();
	}

	protected abstract void initData() ;
	protected void initTitleBar() {
		tv = (TextView) findViewById(R.id.tv);
	
		
	}

	protected abstract void initUI();
}
