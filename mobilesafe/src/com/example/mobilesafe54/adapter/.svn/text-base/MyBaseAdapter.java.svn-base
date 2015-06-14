package com.example.mobilesafe54.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-22 下午2:57:42
 * 
 * 描 述 ：
 * 
 *        适配器的父类
 * 修订历史 ：
 * 
 * ============================================================
 **/
public abstract class MyBaseAdapter<T> extends BaseAdapter {
	
	
	public List<T> lists;
	
	public Context context;
	
	
	
	

	public MyBaseAdapter(List<T> lists, Context context) {
		super();
		this.lists = lists;
		this.context = context;
	}

	public MyBaseAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
}
