package com.example.mobilesafe54.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilesafe54.R;
import com.example.mobilesafe54.db.dao.AppLockDao;
import com.example.mobilesafe54.domain.AppInfo;
import com.example.mobilesafe54.engine.AppInfoParser;

public class LockFragment extends Fragment {
  
	private ListView list_view;
	private TextView tv_lock_app;
	private AppLockDao dao;
	private List<AppInfo> lockInfos;
	private LockAdapter adapter;


	//类似setContentView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_lock, null);
		list_view = (ListView) view.findViewById(R.id.list_view);
		tv_lock_app = (TextView) view.findViewById(R.id.tv_lock_app);
		return view;
	}
	
	
	private class LockAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			tv_lock_app.setText("已加锁软件("+lockInfos.size()+"个)");
			return lockInfos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return lockInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final View view ;
			ViewHolder holder = null;
			if(convertView == null){
				view = View.inflate(getActivity(), R.layout.item_lock_fragment, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
				holder.iv_lock = (ImageView) view.findViewById(R.id.iv_lock);
				view.setTag(holder);
			}else{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			
			final AppInfo appInfo = lockInfos.get(position);
			
			holder.iv_icon.setImageDrawable(appInfo.getIcon());
			
			holder.tv_app_name.setText(appInfo.getAppName());
			
			holder.iv_lock.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TranslateAnimation animation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
							-1.0f, Animation.RELATIVE_TO_SELF, 0,  Animation.RELATIVE_TO_SELF, 0);
					
					animation.setDuration(300);
					
					view.startAnimation(animation);
					
					
					new Thread(){
						public void run() {
							SystemClock.sleep(300);
							getActivity().runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									dao.delete(appInfo.getApkPackageName());
									
									
									lockInfos.remove(appInfo);
									
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
	
	static class ViewHolder{
		ImageView iv_icon;
		TextView tv_app_name;
		ImageView iv_lock;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		dao = new AppLockDao(getActivity());
		//获取到所有的app
		List<AppInfo> appInfos = AppInfoParser.getAppInfos(getActivity());
		//初始化一个加锁的集合
		
		lockInfos = new ArrayList<AppInfo>();
		
		//迭代所有的应用程序。
		for(AppInfo info : appInfos){
			//判断当前有多少app在应用锁里面
			if(dao.find(info.getApkPackageName())){
				//如果能找到说明在加锁的数据库里面
				lockInfos.add(info);
			}
		}
		adapter = new LockAdapter();
		list_view.setAdapter(adapter);
	}
}
