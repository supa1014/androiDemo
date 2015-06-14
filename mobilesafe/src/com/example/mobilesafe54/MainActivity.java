package com.example.mobilesafe54;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesafe54.utils.Md5Utils;
import com.example.mobilesafe54.utils.SharedPreferencesUtils;
import com.example.mobilesafe54.utils.ToastUtils;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-21 上午10:13:46
 * 
 * 描 述 ：
 * 
 * 
 * 修订历史 ：
 * 
 * ============================================================
 **/

public class MainActivity extends Activity implements OnItemClickListener {

	private GridView grid_view;

	private int icons[] = { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app_selector, R.drawable.taskmanager,
			R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize,
			R.drawable.atools, R.drawable.settings };

	private String names[] = { "手机防盗", "通讯卫视", "软件管家", "进程管理", "流量统计", "手机杀毒",
			"缓存清理", "高级工具", "设置中心" };

	private AlertDialog dialog;

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		initUI();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initData();

	}

	private class MainAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return icons.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(MainActivity.this,
					R.layout.item_main_giridview, null);
			// 需要注意。一定要通过view.findViewById
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

			// 设置图片的大小。也就是src
			iv_icon.setImageResource(icons[position]);
			// 设置图片控件的大小。也就是background
			iv_icon.setBackgroundResource(icons[position]);

			tv_name.setText(names[position]);

			/**
			 * 1 判断当前sp里面是否有新的名字。 2如果有新的名字。那么就使用新的名字。 3 如果没有就使用现在的
			 */

			if (position == 0) {
				String newName = SharedPreferencesUtils.getString(
						MainActivity.this, "newName", "");
				// 如果不为null。那么就设置值
				if (!TextUtils.isEmpty(newName)) {
					tv_name.setText(newName);
				}
			}

			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	private void initData() {
		MainAdapter adapter = new MainAdapter();
		grid_view.setAdapter(adapter);
		grid_view.setOnItemClickListener(this);
	}

	private void initUI() {
		setContentView(R.layout.activity_main);
		grid_view = (GridView) findViewById(R.id.grid_view);
		System.out.println("---------------------------");
		System.out.println(Thread.currentThread().getName());
		// 初始化sp并且设置成私有的模式

		// sp = getSharedPreferences("config", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		//手机防盗
		case 0:
			// 判断当前用户是否设置过密码
			if (isEnterPwd()) {
				// 已经设置过密码
				showPwd();
			} else {
				// 没有设置密码
				showEnterPwd();
			}

			break;
		case 1:
            //高级工具
			Intent callSafeIntent = new Intent(this, CallSafeActivity.class);
			startActivity(callSafeIntent);
			break;
			
		case 2:
            //应用管理
			Intent appManagerIntent = new Intent(this, AppManagerActivity.class);
			startActivity(appManagerIntent);
			break;
		case 3:
            //进程管理
			Intent taskManagerIntent = new Intent(this, TaskManagerActivity.class);
			startActivity(taskManagerIntent);
			break;
			
		case 5:
            //病毒查杀
			Intent antivirusIntent = new Intent(this, AntivirusActivity.class);
			startActivity(antivirusIntent);
			break;
		case 6:
            //缓存清理
			Intent cleancacheIntent = new Intent(this, CleanCacheActivity.class);
			startActivity(cleancacheIntent);
			break;
			
		case 7:
            //高级工具
			Intent aToolIntent = new Intent(this, AtoolsActivity.class);
			startActivity(aToolIntent);
			break;

		case 8:
            //设置
			Intent settingIntent = new Intent(this, SettingCenterActivity.class);
			startActivity(settingIntent);
			break;

		}

	}

	private void showPwd() {
		AlertDialog.Builder builder = new Builder(this);

		View dialog_view = View.inflate(MainActivity.this, R.layout.dialog_pwd,
				null);

		final EditText et_enter_pwd = (EditText) dialog_view
				.findViewById(R.id.et_enter_pwd);

		// 点击确定
		dialog_view.findViewById(R.id.bt_ok).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						String str_enter_pwd = et_enter_pwd.getText()
								.toString().trim();

						// 判断当前用户是否输入密码
						if (TextUtils.isEmpty(str_enter_pwd)) {
							ToastUtils.showSafeTost(MainActivity.this, "请输入密码");
							return;
						}
						// 获取缓存的密码数据
						String result = SharedPreferencesUtils.getString(
								MainActivity.this, "pwd", "");

						// 判断当前用户输入的密码是否和缓存的密码一致
						if (result.equals(Md5Utils.encode(str_enter_pwd))) {

							// 判断密码正确
							// Md5Utils.encode(str_enter_pwd);
							// ToastUtils.showSafeTost(MainActivity.this,
							// "密码正确");
							// 判断当前用户是否设置了防盗保护
							// 如果已经设置了防盗保护。进入到防盗界面。如果没有设置进入到引导界面
							boolean setfinish = SharedPreferencesUtils
									.getBoolean(MainActivity.this, "setfinish",
											false);

							if (setfinish) {
								Intent intent = new Intent(MainActivity.this,
										FindLostActivity.class);
								startActivity(intent);
							} else {
								Intent intent = new Intent(MainActivity.this,
										Setup1Activity.class);
								startActivity(intent);
							}

						} else {
							ToastUtils.showSafeTost(MainActivity.this, "密码错误");
						}

						dialog.dismiss();

					}
				});
		// 点击取消
		dialog_view.findViewById(R.id.bt_cancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();

					}
				});

		builder.setView(dialog_view);

		dialog = builder.show();
	}

	/**
	 * 判断当前是否输入过密码
	 * 
	 * @return ture 表示设置过了 false 表示没有设置密码
	 */
	private boolean isEnterPwd() {
		String reuslt = SharedPreferencesUtils.getString(MainActivity.this,
				"pwd", "");
		// 判断当前的pwd是否有值。如果有值说明已经存入了密码.
		if (TextUtils.isEmpty(reuslt)) {
			return false;
		} else {
			// 说明已经缓存过数据了
			return true;
		}

	}

	/**
	 * 设置密码
	 */
	private void showEnterPwd() {
		// 设置输入密码
		// 初始化对话框
		AlertDialog.Builder builder = new Builder(MainActivity.this);

		// 自定义对话框

		View dialong_view = View.inflate(MainActivity.this,
				R.layout.dialog_enter_pwd, null);
		// 输入密码
		final EditText et_enter_pwd = (EditText) dialong_view
				.findViewById(R.id.et_enter_pwd);
		// 确认密码
		final EditText et_confirm_pwd = (EditText) dialong_view
				.findViewById(R.id.et_confirm_pwd);

		// 确认
		dialong_view.findViewById(R.id.bt_ok).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 获取到当前的密码。并且去掉空格
						String str_enter_pwd = et_enter_pwd.getText()
								.toString().trim();

						String str_confirm_pwd = et_confirm_pwd.getText()
								.toString().trim();
						// 判断当前的密码是否为null, 如果为null那么就跳出程序
						if (TextUtils.isEmpty(str_enter_pwd)
								|| TextUtils.isEmpty(str_confirm_pwd)) {
							ToastUtils.showSafeTost(MainActivity.this,
									"请正确输入密码");
							return;
						}
						// 判断当前的密码和确认密码是否一致。如果相同就存起来
						if (str_enter_pwd.equals(str_confirm_pwd)) {

							// 存入密码 使用sp缓存数据
							SharedPreferencesUtils.saveString(
									MainActivity.this, "pwd",
									Md5Utils.encode(str_confirm_pwd));

						} else {
							ToastUtils.showSafeTost(MainActivity.this,
									"密码输入不一致");
						}

						dialog.dismiss();
					}
				});
		// 取消
		dialong_view.findViewById(R.id.bt_cancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

		// 注意:需要把当前 的xml布局设置进来
		builder.setView(dialong_view);
		// 展示对话框
		dialog = builder.show();

	}

}
