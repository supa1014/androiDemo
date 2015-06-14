package com.example.mobilesafe54;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilesafe54.adapter.MyBaseAdapter;
import com.example.mobilesafe54.db.dao.BlackNumberDao;
import com.example.mobilesafe54.domain.BlackNumber;
import com.example.mobilesafe54.utils.SystemInfo;
import com.example.mobilesafe54.utils.ToastUtils;

public class CallSafeActivity extends Activity implements OnClickListener {

	private ListView list_view;
	private BlackNumberDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initTitleBar();
		initData();
	}

	private class CallSafeAdapter extends MyBaseAdapter<BlackNumber> {

		public CallSafeAdapter(Context context, List<BlackNumber> lists) {
			super(lists, context);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(CallSafeActivity.this,
						R.layout.item_call_safe, null);

				holder = new ViewHolder();

				holder.tv_number = (TextView) convertView
						.findViewById(R.id.tv_number);
				holder.tv_mode = (TextView) convertView
						.findViewById(R.id.tv_mode);
				
				holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);

				// 设置标记
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			String mode = lists.get(position).getMode();
			
			final String number = lists.get(position).getNumber();

			holder.tv_number.setText(lists.get(position).getNumber());

			/**
			 * 黑名单的拦截模式 1 全部拦截(电话 + 短信) 2 电话拦截 3 短信拦截
			 */
			if (mode.equals("1")) {
				// 全部拦截
				holder.tv_mode.setText("全部拦截");
			} else if (mode.equals("2")) {
				holder.tv_mode.setText("电话拦截");
			} else if (mode.equals("3")) {
				holder.tv_mode.setText("短信拦截");
			}

			
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    ToastUtils.showSafeTost(CallSafeActivity.this, "我被点击了");	
				    
				    boolean result = dao.delelte(number);
				    //判断当前是否删除成功。如果等于true表示删除成功
				    if(result){
				    	
				    	infos.remove(position);
				    	//当界面改变的时候一定要调用当前这个方法
				    	adapter.notifyDataSetChanged();
				    	
				    }
				    
				}
			});
			
			return convertView;
		}

	}

	static class ViewHolder {
		TextView tv_number;
		TextView tv_mode;
		ImageView iv_delete;
	}

	/**
	 * 黑名单的集合
	 */
	private List<BlackNumber> infos;

	/**
	 * 开始加载的位置
	 */
	private int startIndex = 0;

	/**
	 * 每页最多加载20条数据
	 */
	private int maxCount = 20;

	private void initData() {

		dao = new BlackNumberDao(this);
		// 总共有多少个仇人

		totalCount = dao.getTotalCount();

		new Thread() {

			public void run() {
				// 需要注意。如果第二次获取到数据之后。会把第一次的数据给覆盖掉

				if (infos == null) {
					// 第一次拿数据
					infos = dao.findPart2(startIndex, maxCount);
				} else {
					// 第二次加载数据的时候。需要addall
					infos.addAll(dao.findPart2(startIndex, maxCount));
				}

				handler.sendEmptyMessage(0);
			};
		}.start();

	}

	CallSafeAdapter adapter = null;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// 判断当前的适配器是否为null
			if (adapter == null) {
				// 适配器
				adapter = new CallSafeAdapter(CallSafeActivity.this, infos);
				list_view.setAdapter(adapter);
			} else {
				// 刷新界面
				//一旦listview的界面数据发生改变的时候调用当前的这个方法刷新
				adapter.notifyDataSetChanged();
			}

		};
	};
	// 总的页面数
	private int totalPage;
	private EditText et_page;
	private TextView tv_total_page;
	private int totalCount;
	private AlertDialog dialog;

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("黑名单管理");
		Button bt_right = (Button) findViewById(R.id.bt_right);
		bt_right.setVisibility(View.VISIBLE);
	}

	private void initUI() {
		setContentView(R.layout.activity_call_safe);
		list_view = (ListView) findViewById(R.id.list_view);
		et_page = (EditText) findViewById(R.id.et_page);
		tv_total_page = (TextView) findViewById(R.id.tv_total_page);
		
		Button bt_right = (Button) findViewById(R.id.bt_right);
		
		bt_right.setOnClickListener(this);
		
		// 设置listview的滚动监听
		list_view.setOnScrollListener(new OnScrollListener() {
			// 当listview的滚动状态发生变化的时候调用的方法
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				System.out.println("onScrollStateChanged");
				// 当前listview展示界面的最后一条可见的数据
				int lastVisiblePosition = list_view.getLastVisiblePosition();

				System.out.println("lastVisiblePosition=="
						+ lastVisiblePosition);
				// 判断当前的最后一个条目到了20条 ，加载下页的数据
				if (lastVisiblePosition == infos.size() - 1) {
					// 加载下页数据
					System.out.println("我已经到了底部");

					startIndex += maxCount;

					if (startIndex >= totalCount - 1) {
						ToastUtils.showSafeTost(CallSafeActivity.this,
								"已经没有更多的数据了");
						return;
					}

					initData();

				}

			}

			// listview滚动实时调用的方法
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				// System.out.println("onScroll");
			}
		});
	}
    /**
     * 添加黑名单
     */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_right:
			//添加黑名单
			
			AlertDialog.Builder builder = new Builder(this);
			
			View dialog_View = View.inflate(CallSafeActivity.this, R.layout.dialog_add_blacknumber, null);
			
			final CheckBox cb_phone = (CheckBox) dialog_View.findViewById(R.id.cb_phone);
			
			final CheckBox cb_sms = (CheckBox) dialog_View.findViewById(R.id.cb_sms);
			
			final EditText et_phone = (EditText) dialog_View.findViewById(R.id.et_phone);
			
			//取消
			dialog_View.findViewById(R.id.bt_cancel).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();	
				}
			});
			//确定
			dialog_View.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					//获取到黑名单的电话号码
					
					String phone = et_phone.getText().toString().trim();
					
					//如果当前的这个电话号码为null。那么就给用户提示
					if(TextUtils.isEmpty(phone)){
						ToastUtils.showSafeTost(CallSafeActivity.this, "请输入电话号码");
						return;
					}
					String mode = "";
					
					/**
					 * 黑名单的拦截模式
					 *  1 全部拦截(电话 + 短信) 
					 *  2 电话拦截 
					 *  3 短信拦截
					 */
					
					
					//判断当前用户是否勾选上拦截模式
					if(cb_phone.isChecked() && cb_sms.isChecked()){
						//全部拦截
						mode = "1";
						
					}else if(cb_phone.isChecked()){
						//电话拦截
						mode = "2";
						
					}else if(cb_sms.isChecked()){
						//短信拦截
						mode = "3";
						
					}else{
						ToastUtils.showSafeTost(CallSafeActivity.this, "请选择拦截模式");
						return;
					}
					//判断当前添加黑名单是否正确
					boolean result = dao.add(phone, mode);
					
					BlackNumber blackNumber = new BlackNumber();
					
					blackNumber.setMode(mode);
					
					blackNumber.setNumber(phone);
					
					if(result){
						//添加正确
						infos.add(0, blackNumber);
						
						adapter.notifyDataSetChanged();
					}
					
					dialog.dismiss();	
				}
			});
			
			
			builder.setView(dialog_View);
			
			dialog = builder.show();
			break;

		
		}
		
	}

}
