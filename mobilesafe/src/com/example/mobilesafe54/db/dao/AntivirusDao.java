package com.example.mobilesafe54.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AntivirusDao {

	/**
	 * 获取到描述信息
	 * @param md5 病毒样例
	 * @return
	 */
	public static String getDesc(String md5) {

		String result = null;
		
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				"/data/data/com.example.mobilesafe54/files/antivirus.db", null,
				SQLiteDatabase.OPEN_READONLY);
		
		Cursor cursor = db.query("datable", new String[]{"desc"}, "md5 = ?", new String[]{md5}, null, null, null);
		
		if(cursor.moveToNext()){
			result = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return result;
	}
    /**
     * 添加到病毒数据库里面
     *  注意： 一般在公司开发都必须要先查询。然后在添加。不然的话。每次都会把重复的md5特征码存到数据库里面
     * @param md5
     * @param desc
     */
	public static void add(String md5,String desc){
		//SQLiteDatabase.OPEN_READWRITE表示读写权限
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				"/data/data/com.example.mobilesafe54/files/antivirus.db", null,
				SQLiteDatabase.OPEN_READWRITE);
		ContentValues values = new ContentValues();
		values.put("md5", md5);
		values.put("desc", desc);
		values.put("type", 6);
		values.put("name", "Android.Troj.Kituri.a");
		db.insert("datable", null, values);
	}
	
	
}
