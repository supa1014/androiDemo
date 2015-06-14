package com.example.mobilesafe54.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe54.R;

public class SettingView extends RelativeLayout {

	private TextView tv_desc;
	private TextView tv_title;
	private CheckBox cb_status;
	private String[] descs;

	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		System.out.println("3个参数");
		initUI(context);
	}

	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		System.out.println("2个参数");
//		System.out.println(attrs.getAttributeValue(0));
//		System.out.println(attrs.getAttributeValue(1));
//		System.out.println(attrs.getAttributeValue(2));
//		System.out.println(attrs.getAttributeValue(3));
//		System.out.println(attrs.getAttributeValue(4));
		//获取到标题
		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe54", "title");
		//获取到描述信息
		String desc = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.example.mobilesafe54", "desc");
		//切割描述信息
		//自动更新已经开启#自动更新已经关闭
		descs = desc.split("#");
		
		initUI(context);
		setTitle(title);
		setDesc(descs,false);
		
	}



	public void setDesc(String[] desc, boolean ischeck) {
		if(ischeck){
			tv_desc.setText(desc[0]);
		}else{
			tv_desc.setText(desc[1]);
		}
		
	}

	private void setTitle(String title) {
		tv_title.setText(title);
		
	}

	private void initUI(Context context) {
		View view = View.inflate(context, R.layout.ui_settingview, this);
		
	    
		tv_desc = (TextView) view.findViewById(R.id.tv_desc);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		
		cb_status = (CheckBox) view.findViewById(R.id.cb_status);
		
	}
	
	public boolean isChecked(){
		return cb_status.isChecked();
	}
	
	/**
	 * 判断当前的CheckBox是否被勾选上
	 * @param isChecked 
	 */
	public void setChecked(boolean isChecked){
		if(isChecked){
			cb_status.setChecked(true);
			tv_desc.setTextColor(Color.GREEN);
			if(descs != null){
				tv_desc.setText(descs[0]);
			}
		
		}else{
			cb_status.setChecked(false);
			tv_desc.setTextColor(Color.RED);
			if(descs != null){
				tv_desc.setText(descs[1]);
			}
			
		}
	}
	

	public SettingView(Context context) {
		super(context);
		System.out.println("1个参数");
		initUI(context);
	}

}
