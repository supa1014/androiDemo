package com.example.mobilesafe54.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.ListView;

import com.example.mobilesafe54.db.AppLockSQLiteOpenHelper;
import com.example.mobilesafe54.domain.AppInfo;

public class AppLockDao {

	private AppLockSQLiteOpenHelper helper;

	private Context mContext;
	
	public AppLockDao(Context context) {
		this.mContext = context;
		helper = new AppLockSQLiteOpenHelper(context);
	}
    /**
     * 根据包名进行查询
     * @param packageName
     * @return
     */
	public boolean find(String packageName){
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("info", null, "packname=?", new String[]{packageName}, null, null, null);
	    if(cursor.moveToNext()){
	    	result = true;
	    }
	   cursor.close();
	   db.close();
	   return result;
	}
	/**
	 * 查询当前所有的包名
	 * @return
	 */
	public List<String> findAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("info", new String[]{"packname"}, null, null, null, null, null);
		ArrayList<String> lists = new ArrayList<String>();
		while(cursor.moveToNext()){
		
			lists.add(cursor.getString(0));
		}
		cursor.close();
		db.close();
		return lists;
	}
	
	public void add(String packageName){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("packname", packageName);
		db.insert("info", null, values);
		db.close();
		//当数据库发生改变的时候调用的
		mContext.getContentResolver().notifyChange(Uri.parse("content://xxxx"), null);
	}
	
	public void delete(String packageName){
		SQLiteDatabase db = helper.getWritableDatabase();
		
		db.delete("info", "packname=?", new String[]{packageName});
		db.close();
		mContext.getContentResolver().notifyChange(Uri.parse("content://xxxx"), null);
	}
	

}
