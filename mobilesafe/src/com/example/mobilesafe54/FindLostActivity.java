package com.example.mobilesafe54;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesafe54.utils.SharedPreferencesUtils;

public class FindLostActivity extends Activity implements OnClickListener {

	private TextView tv_set_guide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initTitleBar();
	}

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("手机防盗");
	}

	private void initUI() {
		setContentView(R.layout.activity_find_lost);
		TextView tv_safe_phone = (TextView) findViewById(R.id.tv_safe_phone);
		ImageView iv_lock = (ImageView) findViewById(R.id.iv_lock);

		tv_set_guide = (TextView) findViewById(R.id.tv_set_guide);

		System.out.println("----------------" + tv_set_guide.isClickable());
		/**
		 * 注意：textview天生没有点击事件tv_set_guide.isClickable() 返回false.
		 * 但是系统会帮助我们设置为ture
		 */
		tv_set_guide.setOnClickListener(this);

		// 获取到安全号码
		String result = SharedPreferencesUtils.getString(this, "phone", "");
		if (!TextUtils.isEmpty(result)) {
			tv_safe_phone.setText(result);
		}
		boolean protecting = SharedPreferencesUtils.getBoolean(this,
				"protecting", false);
		if (protecting) {
			iv_lock.setImageResource(R.drawable.lock);
		} else {
			iv_lock.setImageResource(R.drawable.unlock);
		}
	}

	/**
	 * 创建菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	
	
	/**
	 * 菜单的点击事件
	 */

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		
		if(R.id.change_name == item.getItemId()){
			
			
			AlertDialog.Builder builder = new Builder(FindLostActivity.this);
			
			builder.setTitle("请修改应用程序的名称");
			
			final EditText editText = new EditText(FindLostActivity.this);
			
			
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String newName = editText.getText().toString().trim();
					
					
					SharedPreferencesUtils.saveString(FindLostActivity.this, "newName", newName);
				}
			});
			
			
			builder.setView(editText);
			
			builder.show();
			
			System.out.println("我被点击了");
			
		} 
		
		
		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onClick(View v) {
		// System.out.println("+++++++++++++"+tv_set_guide.isClickable());
		// ToastUtils.showSafeTost(this, "我被点击了");
		Intent intent = new Intent(this, Setup1Activity.class);
		startActivity(intent);
	}

}
