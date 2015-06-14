package com.example.mobilesafe54.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilesafe54.R;
import com.example.mobilesafe54.db.dao.AppLockDao;
import com.example.mobilesafe54.domain.AppInfo;
import com.example.mobilesafe54.engine.AppInfoParser;

public class UnLockFragment extends Fragment {
	private TextView tv_unlock_app;
	private ListView list_view;
	private List<AppInfo> appInfos;
	private AppLockDao dao;
	private List<AppInfo> unLockAppInfos;
	private UnLockAdapter adapter;

	// 类似setContentView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_unlock, null);
		tv_unlock_app = (TextView) view.findViewById(R.id.tv_unlock_app);
		list_view = (ListView) view.findViewById(R.id.list_view);
		return view;
	}

	private class UnLockAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			tv_unlock_app.setText("未加锁软件(" + unLockAppInfos.size() + "个)");
			return unLockAppInfos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return unLockAppInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final View view;
			ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(getActivity(),
						R.layout.item_unlock_fragment, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.tv_app_name = (TextView) view
						.findViewById(R.id.tv_app_name);
				holder.iv_lock = (ImageView) view.findViewById(R.id.iv_lock);

				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}

			final AppInfo appInfo = unLockAppInfos.get(position);

			holder.iv_icon.setImageDrawable(appInfo.getIcon());

			holder.tv_app_name.setText(appInfo.getAppName());

			holder.iv_lock.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 把当前点击的app放到数据库里面
                    //初始化一个位移动画
					TranslateAnimation animation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
							1.0f, Animation.RELATIVE_TO_SELF, 0,  Animation.RELATIVE_TO_SELF, 0);
                    //设置一个动画时间
					animation.setDuration(300);
					
					view.startAnimation(animation);
					
					new Thread(){
						public void run() {
//							//睡觉睡三秒钟
							SystemClock.sleep(300);
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									dao.add(appInfo.getApkPackageName());
									unLockAppInfos.remove(appInfo);
									//只能在ui线程刷新界面
									adapter.notifyDataSetChanged();
								}
							});
						};
					}.start();
					
					
					
					
				}
			});

			return view;
		}

	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_app_name;
		ImageView iv_lock;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		dao = new AppLockDao(getActivity());

		// 拿到所有安装到手机上面的应用程序
		appInfos = AppInfoParser.getAppInfos(getActivity());

		unLockAppInfos = new ArrayList<AppInfo>();

		for (AppInfo info : appInfos) {

			if (dao.find(info.getApkPackageName())) {
				// 已经被加锁了
			} else {
				// 没有加锁
				unLockAppInfos.add(info);
			}

		}
		adapter = new UnLockAdapter();
		list_view.setAdapter(adapter);
	}
}
