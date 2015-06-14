package com.example.mobilesafe54;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilesafe54.adapter.MyBaseAdapter;
import com.example.mobilesafe54.domain.ContactInfo;
import com.example.mobilesafe54.engine.ContactInfoParser;

public class SelectContactsActivity extends Activity {

	private ListView list_view;
	private List<ContactInfo> mContactInfos;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initData();
	}
	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("选择联系人");
	}
	private void initData() {
		//获取当前手机里面的所有的联系人
		mContactInfos = ContactInfoParser.findAll(this);
		
		SelectContactsAdapter adapter = new SelectContactsAdapter(mContactInfos,this);
		list_view.setAdapter(adapter);
	}

	class SelectContactsAdapter extends MyBaseAdapter<ContactInfo>{

		
		
		public SelectContactsAdapter(List<ContactInfo> mContactInfos,
				Context context) {
			super(mContactInfos,context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view = View.inflate(SelectContactsActivity.this, R.layout.item_select_contacts, null);
			
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			
			TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
			
			tv_name.setText(lists.get(position).getName());
			
			tv_phone.setText(lists.get(position).getPhone());
			
			return view;
		}
		
		

		

		
		
	}
	
	private void initUI() {
		setContentView(R.layout.activity_select_contacts);
		list_view = (ListView) findViewById(R.id.list_view);
		initTitleBar();
		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//获取到当前点击item条目的对象
				
				Object obj = list_view.getItemAtPosition(position);
				
				if(obj != null){
					//强转成联系人的类型
					ContactInfo mContactInfo = (ContactInfo) obj;
					//获取到联系人的电话号码
					String phone = mContactInfo.getPhone();
					
					Intent intent = new Intent();
					//把电话号码带到下一个页面
					intent.putExtra("phone", phone);
					
					setResult(0,intent);
					
					finish();
				}
			}
			
		});
	}
}
