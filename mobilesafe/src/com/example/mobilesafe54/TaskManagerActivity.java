package com.example.mobilesafe54;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.mobilesafe54.domain.TaskInfo;
import com.example.mobilesafe54.engine.TaskParser;
import com.example.mobilesafe54.utils.SharedPreferencesUtils;
import com.example.mobilesafe54.utils.SystemInfo;
import com.example.mobilesafe54.utils.ToastUtils;

import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TaskManagerActivity extends Activity {

	private TextView tv_ram;
	private TextView tv_process_count;
	// 进程集合
	private List<TaskInfo> taskInfos;
	private ListView list_view;
	private LinearLayout ll_loading;
	private List<TaskInfo> userTaskInfos;
	private TaskInfo taskInfo;
	private List<TaskInfo> systemTaskInfos;
	private TaskManagerAdapter adapter;
	private int processCount;
	private long totalMem;
	private long availMem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initTitleBar();
		initData();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(adapter != null){
			adapter.notifyDataSetChanged();
		}
	
	}

	private class TaskManagerAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			boolean result = SharedPreferencesUtils.getBoolean(TaskManagerActivity.this, "show_system_process", false);
			if(result){
				return userTaskInfos.size() + 1 + systemTaskInfos.size() + 1;
			}else{
				return userTaskInfos.size() + 1;
			}
			
		}

		@Override
		public Object getItem(int position) {

			if (position == 0) {
				return null;
			} else if (position == 1 + userTaskInfos.size()) {
				return null;
			}
			TaskInfo taskInfo;

			if (position < userTaskInfos.size() + 1) {
				taskInfo = userTaskInfos.get(position - 1);
			} else {
				int location = position - 1 - userTaskInfos.size() - 1;

				taskInfo = systemTaskInfos.get(location);
			}
			return taskInfo;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder;

			//
			if (position == 0) {

				TextView textView = new TextView(TaskManagerActivity.this);
				textView.setText("用户进程(" + userTaskInfos.size() + ")");
				textView.setTextColor(Color.WHITE);
				textView.setBackgroundColor(Color.GRAY);

				return textView;

			} else if (position == 1 + userTaskInfos.size()) {
				TextView textView = new TextView(TaskManagerActivity.this);
				textView.setText("系统进程(" + systemTaskInfos.size() + ")");
				textView.setTextColor(Color.WHITE);
				textView.setBackgroundColor(Color.GRAY);
				return textView;
			}

			if (convertView != null && convertView instanceof LinearLayout) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {

				view = View.inflate(TaskManagerActivity.this,
						R.layout.item_task_manager, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.tv_app_name = (TextView) view
						.findViewById(R.id.tv_app_name);
				holder.tv_process_mem_size = (TextView) view
						.findViewById(R.id.tv_process_mem_size);
				holder.cb_status = (CheckBox) view.findViewById(R.id.cb_status);
				view.setTag(holder);
			}

			TaskInfo taskInfo;

			if (position < userTaskInfos.size() + 1) {
				taskInfo = userTaskInfos.get(position - 1);
			} else {
				int location = position - 1 - userTaskInfos.size() - 1;

				taskInfo = systemTaskInfos.get(location);
			}

			holder.iv_icon.setImageDrawable(taskInfo.getIcon());

			holder.tv_app_name.setText(taskInfo.getAppName());
			holder.tv_process_mem_size.setText(Formatter.formatFileSize(
					TaskManagerActivity.this, taskInfo.getMemorySize()));
			// 如果是自己的进程。那么就需要隐藏单选按钮
			if (taskInfo.getPackageName().equals(getPackageName())) {
				holder.cb_status.setVisibility(View.INVISIBLE);
			} else {
				holder.cb_status.setVisibility(View.VISIBLE);
			}

			holder.cb_status.setChecked(taskInfo.isChecked());

			return view;
		}

	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_app_name;
		TextView tv_process_mem_size;
		CheckBox cb_status;
	}

	private void initData() {
		new Thread() {

			public void run() {
				// 获取到所有的进程

				taskInfos = TaskParser.getTaskInfos(TaskManagerActivity.this);

				userTaskInfos = new ArrayList<TaskInfo>();

				systemTaskInfos = new ArrayList<TaskInfo>();

				for (TaskInfo info : taskInfos) {
					if (info.isUserTask()) {
						userTaskInfos.add(info);
					} else {
						systemTaskInfos.add(info);
					}
				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ll_loading.setVisibility(View.INVISIBLE);

						adapter = new TaskManagerAdapter();
						list_view.setAdapter(adapter);
					}
				});
			};
		}.start();

	}

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("进程管理");
	}

	/**
	 * 清理进程
	 * 
	 * @param view
	 */
	public void killProcess(View view) {

		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		// 进程的个数
		int process_count = 0;
		// 释放内存的大小
		int mem_size = 0;

		List<TaskInfo> lists = new ArrayList<TaskInfo>();

		for (TaskInfo info : userTaskInfos) {
			if (info.isChecked()) {
				process_count++;
				mem_size += info.getMemorySize();
				lists.add(info);
			}
		}

		for (TaskInfo info : systemTaskInfos) {
			if (info.isChecked()) {
				process_count++;
				mem_size += info.getMemorySize();
				lists.add(info);
			}
		}
		
		

		for (TaskInfo taskInfo : lists) {
			if (taskInfo.isUserTask()) {
				userTaskInfos.remove(taskInfo);
			} else {
				systemTaskInfos.remove(taskInfo);
			}
			activityManager.killBackgroundProcesses(taskInfo.getPackageName());
		}

		adapter.notifyDataSetChanged();

		ToastUtils.showSafeTost(
				TaskManagerActivity.this,
				"共清理了"
						+ process_count
						+ "个进程,释放了"
						+ Formatter.formatFileSize(TaskManagerActivity.this,
								mem_size) + "内存");

		tv_process_count.setText("进程:" + (processCount - process_count));
		tv_ram.setText("剩余/总共:" + Formatter.formatFileSize(this, availMem + mem_size)
				+ "/" + Formatter.formatFileSize(this, totalMem));
	}

	/**
	 * 全部选择
	 * 
	 * @param view
	 */
	public void AllSelect(View view) {

		for (TaskInfo info : taskInfos) {
			// 判断当前的进程是否是自己的包名
			if (info.getPackageName().equals(getPackageName())) {
				continue;
			}

			info.setChecked(true);
		}
		adapter.notifyDataSetChanged();
	}

	/**
	 * 反选
	 * 
	 * @param view
	 */
	public void oppositeSelect(View view) {
		for (TaskInfo info : taskInfos) {
			// 判断当前的进程是否是自己的包名
			if (info.getPackageName().equals(getPackageName())) {
				continue;
			}

			info.setChecked(!info.isChecked());

		}
		adapter.notifyDataSetChanged();
	}

	/**
	 * 打开设置界面
	 * 
	 * @param view
	 */
	public void openSetting(View view) {
         Intent intent = new Intent(this,TaskSettingActivity.class);
         startActivity(intent);
	}

	private void initUI() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_task_manager);
		tv_ram = (TextView) findViewById(R.id.tv_ram);
		tv_process_count = (TextView) findViewById(R.id.tv_process_count);

		list_view = (ListView) findViewById(R.id.list_view);

		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		// 让加载进度条展示出来
		ll_loading.setVisibility(View.VISIBLE);

		processCount = SystemInfo.getProcessCount(this);
		tv_process_count.setText("进程:" + processCount);
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		// 获取到内存的基本信息
		activityManager.getMemoryInfo(memoryInfo);
		// 获取到一个可用内存
		
		availMem = memoryInfo.availMem;

		totalMem = SystemInfo.getMemCount(this);

		tv_ram.setText("剩余/总共:" + Formatter.formatFileSize(this, availMem)
				+ "/" + Formatter.formatFileSize(this, totalMem));

		// System.out.println("可用内存:" + availMem);
		// System.out.println("总共内存:" + totalMem);
		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object object = list_view.getItemAtPosition(position);
				ViewHolder holder;
				if (object != null) {

					taskInfo = (TaskInfo) object;

					holder = (ViewHolder) view.getTag();
					
					if(taskInfo.getPackageName().equals(getPackageName())){
						
						return ;
					}
					
					
					// 判断当前是否选中
					if (taskInfo.isChecked()) {
						// 没有选中
						taskInfo.setChecked(false);
						holder.cb_status.setChecked(false);
					} else {
						// 已经选中
						taskInfo.setChecked(true);
						holder.cb_status.setChecked(true);
					}

				}

			}

		});
	}
}
