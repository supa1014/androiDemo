package com.example.mobilesafe54;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilesafe54.adapter.MyBaseAdapter;
import com.example.mobilesafe54.db.dao.BlackNumberDao;
import com.example.mobilesafe54.domain.BlackNumber;
import com.example.mobilesafe54.utils.ToastUtils;

public class CallSafeActivity2 extends Activity {

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
		public View getView(int position, View convertView, ViewGroup parent) {
			System.out.println("getView" + position);
			ViewHolder holder ;
			if(convertView == null){
				convertView = View.inflate(CallSafeActivity2.this,
						R.layout.item_call_safe, null);
				
				holder = new ViewHolder();
				
				holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
				holder.tv_mode = (TextView) convertView.findViewById(R.id.tv_mode);
				
				//设置标记
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
	

			

			String mode = lists.get(position).getMode();

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

			return convertView;
		}

	}

	
	static class ViewHolder{
		TextView tv_number;
		TextView tv_mode;
	}
	
	/**
	 * 黑名单的集合
	 */
	private List<BlackNumber> lists;
	
	/**
	 * 每页的大小
	 */
	private int pageSize = 20;
	/**
	 * 当前页面
	 */
	private int mCurrentPage = 0;

	private void initData() {

		dao = new BlackNumberDao(this);
		//获取到总的页面
		
		totalPage = (dao.getTotalCount() + pageSize - 1)/ pageSize;
		//设置当前页面和总共都是页面 0/1
		tv_total_page.setText(mCurrentPage+ "/" + totalPage);

		new Thread() {

			public void run() {
                //查询出来当前所有的黑名单的数据
				lists = dao.findPart(pageSize, mCurrentPage );

				handler.sendEmptyMessage(0);
			};
		}.start();

	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			CallSafeAdapter adapter = new CallSafeAdapter(
					CallSafeActivity2.this, lists);
			list_view.setAdapter(adapter);

		};
	};
	//总的页面数
	private int totalPage;
	private EditText et_page;
	private TextView tv_total_page;

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
        
	}
	/**
	 * 下页
	 * @param view
	 */
	public void next(View view){
		//判断当前的页面不能超过总的页面数
		if(mCurrentPage >= totalPage - 1 ){
			
			ToastUtils.showSafeTost(this, "已经是最后一页了");
			
			return;
			
		}
		
		mCurrentPage++;
		initData();
		
	}
	/**
	 * 上页
	 * @param view
	 */
	public void pre(View view){
		//当前页面不能小于0
		if(mCurrentPage <= 0){
			ToastUtils.showSafeTost(this, "已经是第一页了");
			return;
		}
		
		mCurrentPage--;
		initData();
		
	}
	/**
	 * 跳转到哪页
	 * @param view
	 */
	public void jump(View view){
		//拿到当前用户输入的页面数字
		String str_page = et_page.getText().toString().trim();
		//判断当前用户是否输入了
		if(TextUtils.isEmpty(str_page)){
			ToastUtils.showSafeTost(this, "请输入当前页码");
		}else{
			//转换成page页面
			int page = Integer.parseInt(str_page);
			//判断当前的页码不能小于0页和大于总的页码
			if(page >= 0 && page < totalPage ){
				
				
				mCurrentPage =  page;
				initData();
			}else{
				ToastUtils.showSafeTost(this, "请输入正确页码");
			}
			
		}
	}

}
