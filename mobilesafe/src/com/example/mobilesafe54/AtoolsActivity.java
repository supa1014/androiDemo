package com.example.mobilesafe54;

import net.youmi.android.listener.Interface_ActivityListener;
import net.youmi.android.offers.OffersManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobilesafe54.utils.SmsUtils;
import com.example.mobilesafe54.utils.SmsUtils.SmsBackUP;
import com.example.mobilesafe54.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-25 上午9:44:33
 * 
 * 描 述 ：
 * 
 * 高级工具 修订历史 ：
 * 
 * ============================================================
 **/
public class AtoolsActivity extends Activity {

	private ProgressDialog progressDialog;
	@ViewInject(R.id.progressBar1)
	private ProgressBar progressBar1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tool);

		// 注入
		ViewUtils.inject(this);

	}
	
	
	
    /**
     * 展示积分墙
     * @param view
     */
	public void btn_show_offerswall(View view) {
		OffersManager.getInstance(this).showOffersWall(
				new Interface_ActivityListener() {

					/**
					 * 但积分墙销毁的时候，即积分墙的Activity调用了onDestory的时候回调
					 */
					@Override
					public void onActivityDestroy(Context context) {
						Toast.makeText(context, "全屏积分墙退出了", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	/**
	 * 程序锁
	 * 
	 * @param view
	 */
	public void appLock(View view) {
		Intent intent = new Intent(this, AppLockActivity.class);
		startActivity(intent);
	}

	public void queryAddress(View view) {
		Intent intent = new Intent(this, QueryNumberAddressActivity.class);
		startActivity(intent);
	}

	/**
	 * 短信备份
	 * 
	 * @param view
	 */
	public void smsBackup(View view) {

		// 初始化一个对话框的进度条

		progressDialog = new ProgressDialog(this);
		// 设置横着滚动的样式
		progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);

		// 设置标题
		progressDialog.setTitle("提示");
		// 设置消息
		progressDialog.setMessage("稍安勿躁。正在备份...");

		progressDialog.show();

		new Thread() {
			public void run() {
				boolean result = SmsUtils.getSmsBackup(AtoolsActivity.this,
						new SmsBackUP() {

							@Override
							public void beforeSmsUp(int size) {
								// TODO Auto-generated method stub
								progressDialog.setMax(size);
								progressBar1.setMax(size);
							}

							@Override
							public void onSmsUp(int process) {
								// TODO Auto-generated method stub
								progressDialog.setProgress(process);
								progressBar1.setProgress(process);
							}

						});
				if (result) {
					ToastUtils.showSafeTost(AtoolsActivity.this, "备份成功");

				} else {
					ToastUtils.showSafeTost(AtoolsActivity.this, "备份失败");
				}
				progressDialog.dismiss();
			};
		}.start();

	}

}
