package com.example.mobilesafe54;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilesafe54.adapter.MyBaseAdapter;
import com.example.mobilesafe54.utils.ToastUtils;

public class CleanCacheActivity extends BaseActivity {

	private LinearLayout ll_loading;
	private PackageManager packageManager;
	private List<CacheInfo> cacheInfos;
	private ListView list_view;

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_clean_cache);
		initTitleBar();
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		ll_loading.setVisibility(View.VISIBLE);
		tv.setText("垃圾清理");
		list_view = (ListView) findViewById(R.id.list_view);

	}
	
	private class MyIPackageDataObserver extends IPackageDataObserver.Stub{

		@Override
		public void onRemoveCompleted(String packageName, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub
			
		}
		
	}
	/**
	 * 全部清理
	 * @param view
	 */
	public void cleanAll(View view){
		Method[] methods = PackageManager.class.getDeclaredMethods();
		
		for (Method method : methods) {
			if(method.getName().equals("freeStorageAndNotify")){
				
				try {
					System.out.println("xxxxxxxxxxxxxxxxxxxxx");
					method.invoke(packageManager,Integer.MAX_VALUE, new MyIPackageDataObserver());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		ToastUtils.showSafeTost(CleanCacheActivity.this, "清理完毕");
	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_app_name;
		TextView tv_cache_size;
		ImageView iv_clean;
	}

	private class CleanCacheAdapter extends MyBaseAdapter<CacheInfo> {

		public CleanCacheAdapter(CleanCacheActivity cleanCacheActivity,
				List<CacheInfo> cacheInfos) {
			super(cacheInfos, cleanCacheActivity);
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(CleanCacheActivity.this,
						R.layout.item_clean_cache, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.tv_app_name = (TextView) view
						.findViewById(R.id.tv_app_name);
				holder.tv_cache_size = (TextView) view
						.findViewById(R.id.tv_cache_size);
				holder.iv_clean = (ImageView) view.findViewById(R.id.iv_clean);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.iv_icon.setImageDrawable(lists.get(position).icon);
			holder.tv_app_name.setText(lists.get(position).appName);
			holder.tv_cache_size.setText(Formatter.formatFileSize(
					CleanCacheActivity.this, lists.get(position).cacheSize));

			holder.iv_clean.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// <intent-filter>
					// <action
					// android:name="android.settings.APPLICATION_DETAILS_SETTINGS"
					// />
					// <category android:name="android.intent.category.DEFAULT"
					// />
					// <data android:scheme="package" />
					// </intent-filter>

					Intent intent = new Intent();

					intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");

					intent.addCategory("android.intent.category.DEFAULT");

					intent.setData(Uri.parse("package:"
							+ lists.get(position).apkPackageName));

					startActivity(intent);
				}
			});
			return view;
		}

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ll_loading.setVisibility(View.INVISIBLE);
			CleanCacheAdapter adapter = new CleanCacheAdapter(
					CleanCacheActivity.this, cacheInfos);
			list_view.setAdapter(adapter);

		};
	};
	private LinearLayout ll_container;

	@Override
	protected void initData() {

		cacheInfos = new ArrayList<CacheInfo>();
		packageManager = getPackageManager();

		new Thread() {
			public void run() {
				List<PackageInfo> installedPackages = packageManager
						.getInstalledPackages(0);

				for (PackageInfo packageInfo : installedPackages) {
					getCacheSize(packageInfo);
					SystemClock.sleep(50);
				}
				handler.sendEmptyMessage(0);
			};
		}.start();

	}

	private void getCacheSize(final PackageInfo packageInfo) {

		try {
			Method method = PackageManager.class.getDeclaredMethod(
					"getPackageSizeInfo", String.class,
					IPackageStatsObserver.class);
			// method.invoke(packageManager,
			// packageInfo.applicationInfo.packageName,new
			// MyIPackageStatsObserver(packageInfo));
			method.invoke(packageManager,
					packageInfo.applicationInfo.packageName,
					new IPackageStatsObserver.Stub() {

						@Override
						public void onGetStatsCompleted(PackageStats pStats,
								boolean succeeded) throws RemoteException {
							long cacheSize = pStats.cacheSize;
							if (cacheSize > 0) {

								CacheInfo cacheInfo = new CacheInfo();

								cacheInfo.icon = packageInfo.applicationInfo
										.loadIcon(packageManager);

								cacheInfo.appName = packageInfo.applicationInfo
										.loadLabel(packageManager).toString();

								cacheInfo.cacheSize = cacheSize;

								cacheInfo.apkPackageName = packageInfo.applicationInfo.packageName;

								System.out.println("应用的名字----"
										+ packageInfo.applicationInfo
												.loadLabel(packageManager));
								System.out.println("cacheSize----" + cacheSize);

								cacheInfos.add(cacheInfo);
                                
								
							}

						}

					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class MyIPackageStatsObserver extends IPackageStatsObserver.Stub {

		private PackageInfo packageInfo;

		public MyIPackageStatsObserver(PackageInfo packageInfo) {
			this.packageInfo = packageInfo;
		}

		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			long cacheSize = pStats.cacheSize;
			if (cacheSize > 0) {

				// CacheInfo cacheInfo = new CacheInfo();
				//
				// cacheInfo.icon =
				// packageInfo.applicationInfo.loadIcon(packageManager);
				//
				// cacheInfo.appName =
				// packageInfo.applicationInfo.loadLabel(packageManager).toString();
				//
				// cacheInfo.cacheSize = cacheSize;
				//
				// cacheInfo.apkPackageName =
				// packageInfo.applicationInfo.packageName;
				//
				// cacheInfos.add(cacheInfo);
				//

				CacheInfo cacheInfo = new CacheInfo();
				Drawable icon = packageInfo.applicationInfo
						.loadIcon(packageManager);
				cacheInfo.icon = icon;
				String appName = packageInfo.applicationInfo.loadLabel(
						packageManager).toString();
				cacheInfo.appName = appName;
				cacheInfo.cacheSize = cacheSize;
				cacheInfos.add(cacheInfo);
				System.out
						.println("应用的名字----"
								+ packageInfo.applicationInfo
										.loadLabel(packageManager));
				System.out.println("cacheSize----" + cacheSize);
			}
		}

	}

	class CacheInfo {
		Drawable icon;
		String appName;
		long cacheSize;
		String apkPackageName;
	}

}
