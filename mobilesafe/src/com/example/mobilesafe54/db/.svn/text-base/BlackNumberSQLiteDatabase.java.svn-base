package com.example.mobilesafe54.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumberSQLiteDatabase extends SQLiteOpenHelper {
    /**
     * 
     * @param context
     * @param name 数据库的名字
     * @param factory 游标工厂
     * @param version 数据库的版本好(版本号必须大于1)
     */
	public BlackNumberSQLiteDatabase(Context context) {
		super(context, "callsafe.db", null, 1);
		// TODO Auto-generated constructor stub
	}
    /**
     * 创建数据库表的时候调用的方法 
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
	
		db.execSQL("create table blacknumber (_id integer primary key autoincrement,number varchar(20),mode varchar(2))");
	}
    /**
     * 更新数据库表的时候调用的方法
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
