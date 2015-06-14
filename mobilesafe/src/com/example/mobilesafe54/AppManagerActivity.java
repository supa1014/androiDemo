package com.example.mobilesafe54;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mobilesafe54.domain.AppInfo;
import com.example.mobilesafe54.engine.AppInfoParser;
import com.example.mobilesafe54.utils.ToastUtils;

public class AppManagerActivity extends Activity implements OnClickListener {

	private ListView list_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initTitleBar();
		initData();
	}

	private List<AppInfo> appInfos;

	private class AppManagerAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return userAppInfos.size() + 1 + systemAppInfos.size() + 1;
		}

		@Override
		public Object getItem(int position) {

			if (position == 0) {
				return null;
			} else if (position == 1 + userAppInfos.size()) {
				return null;
			}

			AppInfo appInfo;

			if (position < userAppInfos.size() + 1) {
				// 表示用户程序

				appInfo = userAppInfos.get(position - 1);

			} else {
				// 系统程序
				int location = position - 1 - userAppInfos.size() - 1;
				appInfo = systemAppInfos.get(location);
			}

			return appInfo;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 判断当前的位置是0用来存放textview的
			if (position == 0) {

				TextView textView = new TextView(AppManagerActivity.this);
				// 设置背景
				textView.setBackgroundColor(Color.GRAY);

				// 设置字体颜色
				textView.setTextColor(Color.WHITE);

				textView.setText("用户程序(" + userAppInfos.size() + ")");

				return textView;
				// 表示系统程序的特殊条目
			} else if (position == 1 + userAppInfos.size()) {

				TextView textView = new TextView(AppManagerActivity.this);
				// 设置背景
				textView.setBackgroundColor(Color.GRAY);

				// 设置字体颜色
				textView.setTextColor(Color.WHITE);

				textView.setText("系统程序(" + systemAppInfos.size() + ")");

				return textView;

			}

			View view;
			ViewHolder holder;
			if (convertView != null && convertView instanceof LinearLayout) {
				view = convertView;

				holder = (ViewHolder) view.getTag();
			} else {

				view = View.inflate(AppManagerActivity.this,
						R.layout.item_app_manager, null);

				holder = new ViewHolder();

				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

				holder.tv_app_name = (TextView) view
						.findViewById(R.id.tv_app_name);

				holder.tv_is_rom = (TextView) view.findViewById(R.id.tv_is_rom);

				holder.tv_app_size = (TextView) view
						.findViewById(R.id.tv_app_size);

				view.setTag(holder);

			}

			AppInfo appInfo;

			if (position < userAppInfos.size() + 1) {
				// 表示用户程序

				appInfo = userAppInfos.get(position - 1);

			} else {
				// 系统程序
				int location = position - 1 - userAppInfos.size() - 1;
				appInfo = systemAppInfos.get(location);
			}

			holder.iv_icon.setImageDrawable(appInfo.getIcon());

			holder.tv_app_name.setText(appInfo.getAppName());

			holder.tv_app_size.setText(Formatter.formatFileSize(
					AppManagerActivity.this, appInfo.getApkSize()));
			//如果当前的值为true
			if(appInfo.isRom()){
				//表示机身内存
				holder.tv_is_rom.setText("手机内存");
			}else {
				//表示sd卡
				holder.tv_is_rom.setText("SD卡");
			}

			return view;
		}

	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_app_name;
		TextView tv_app_size;
		TextView tv_is_rom;
	}
	private AppManagerAdapter adapter;
	private List<AppInfo> userAppInfos;
	private List<AppInfo> systemAppInfos;

	private void initData() {

		new Thread() {

			public void run() {
				// 这个代码比较耗时
				// 获取到所有的应用程序
				appInfos = AppInfoParser.getAppInfos(AppManagerActivity.this);
				// 初始化用户程序
				userAppInfos = new ArrayList<AppInfo>();
				// 初始化系统程序
				systemAppInfos = new ArrayList<AppInfo>();

				// 把所有的应用一分为二
				// 用户程序

				// 系统程序
				for (AppInfo appInfo : appInfos) {
					if (appInfo.isUserApp()) {
						userAppInfos.add(appInfo);
					} else {
						systemAppInfos.add(appInfo);
					}
				}

				runOnUiThread(new Runnable() {
					

					public void run() {
						adapter = new AppManagerAdapter();
						list_view.setAdapter(adapter);
					}
				});
			};
		}.start();

	}

	// private Handler handler = new Handler(){
	// public void handleMessage(android.os.Message msg) {
	//
	// };
	// };

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("应用程序管理");
	}

	private AppInfo appClickInfo;
	private PopupWindow popupWindow;
	private TextView tv_app_size;

	private void initUI() {
		setContentView(R.layout.activity_app_manger);
		TextView tv_rom = (TextView) findViewById(R.id.tv_rom);
		TextView tv_sd = (TextView) findViewById(R.id.tv_sd);
		
		
		tv_app_size = (TextView) findViewById(R.id.tv_app_size);
		
		// 获取到数据的目录获取到rom内存的剩余的大小
		// getFreeSpace 当前的这个方法是android10之后才推出的api
		long rom_FreeSpace = Environment.getDataDirectory().getFreeSpace();
		// 获取到当前手机sd卡的存储目录.然后获取到sd卡的剩余的大小
		long sd_FreeSpace = Environment.getExternalStorageDirectory()
				.getFreeSpace();

		System.out.println("rom_FreeSpace==" + rom_FreeSpace);
		System.out.println("sd_FreeSpace==" + sd_FreeSpace);
		// 格式化文件的大小
		tv_rom.setText("内存可用:" + Formatter.formatFileSize(this, rom_FreeSpace));

		tv_sd.setText("sd可用:" + Formatter.formatFileSize(this, sd_FreeSpace));

		list_view = (ListView) findViewById(R.id.list_view);
		
		list_view.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				popupWindowDismiss();
				System.out.println("--------------------------------");
				System.out.println("firstVisibleItem" + firstVisibleItem);
				System.out.println("visibleItemCount" + visibleItemCount);
				System.out.println("totalItemCount" + totalItemCount);
				
				if(userAppInfos != null && systemAppInfos != null ){
					
					
					if(firstVisibleItem > userAppInfos.size() + 1){
						//系统程序
						tv_app_size.setText("系统程序(" + systemAppInfos.size() +")");
						
					}else{
						//用户程序
						tv_app_size.setText("用户程序(" + userAppInfos.size() +")");
					}
					
				}
				
			}
		});
		

		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object object = list_view.getItemAtPosition(position);

				if (object != null) {

					appClickInfo = (AppInfo) object;

					View contentView = View.inflate(AppManagerActivity.this,
							R.layout.popup_view, null);

					LinearLayout ll_unintall = (LinearLayout) contentView
							.findViewById(R.id.ll_unintall);
					LinearLayout ll_run = (LinearLayout) contentView
							.findViewById(R.id.ll_run);
					LinearLayout ll_share = (LinearLayout) contentView
							.findViewById(R.id.ll_share);

					ll_unintall.setOnClickListener(AppManagerActivity.this);
					ll_run.setOnClickListener(AppManagerActivity.this);
					ll_share.setOnClickListener(AppManagerActivity.this);

					popupWindowDismiss();

					popupWindow = new PopupWindow(contentView, -2, -2);
					// 初始化位置 x ，y
					int[] location = new int[2];

					// 当前的view获取到在当前窗体的位置
					view.getLocationInWindow(location);
					// 展示的位置
					popupWindow.showAtLocation(parent, Gravity.LEFT
							+ Gravity.TOP, 60, location[1]);
				}

			}

		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		popupWindowDismiss();

	}

	private void popupWindowDismiss() {
		// 判断当前的窗体是否已经展示。如果已经展示出来了。就必须把他销毁
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_run:
			System.out.println("运行");
			Intent localIntent = AppManagerActivity.this
					.getPackageManager()
					.getLaunchIntentForPackage(appClickInfo.getApkPackageName());
			if(localIntent == null){
				ToastUtils.showSafeTost(AppManagerActivity.this, "当前应用程序不能开启");
			}else{
				AppManagerActivity.this.startActivity(localIntent);
			}
		
			break;

		case R.id.ll_share:
			System.out.println("分享");
			Intent sharelIntent = new Intent("android.intent.action.SEND");
			sharelIntent.setType("text/plain");
			sharelIntent.putExtra("android.intent.extra.SUBJECT", "f分享");
			sharelIntent.putExtra(
					"android.intent.extra.TEXT",
					"Hi！推荐您使用软件："+ appClickInfo.getAppName()+"下载地址:" +
					"https://play.google.com/store/apps/details?id="+appClickInfo.getApkPackageName());
			AppManagerActivity.this.startActivity(Intent.createChooser(
					sharelIntent, "分享"));
			break;
		case R.id.ll_unintall:
			System.out.println("卸载");
			Intent unIntallIntent = new Intent("android.intent.action.DELETE", Uri.parse("package:" + appClickInfo.getApkPackageName()));
			startActivity(unIntallIntent);
			adapter.notifyDataSetChanged();
			break;

		}
		popupWindowDismiss();
	}
}
