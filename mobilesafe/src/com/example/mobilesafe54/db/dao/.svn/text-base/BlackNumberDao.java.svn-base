package com.example.mobilesafe54.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.example.mobilesafe54.db.BlackNumberSQLiteDatabase;
import com.example.mobilesafe54.domain.BlackNumber;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-27 上午9:07:51
 * 
 * 描 述 ：
 * 
 * 实现数据库的增删改查 修订历史 ：
 * 
 * ============================================================
 **/
public class BlackNumberDao {

	private BlackNumberSQLiteDatabase helper;

	public BlackNumberDao(Context context) {
		helper = new BlackNumberSQLiteDatabase(context);
	}

	/**
	 * 获取到当前一共有多少个仇人
	 */
	public int getTotalCount() {

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select count(*) from blacknumber", null);

		cursor.moveToNext();

		int count = cursor.getInt(0);

		return count;
	}

	/**
	 * 查询出所有的数据
	 * 
	 * @return
	 */
	public List<BlackNumber> findAll() {

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db
				.query("blacknumber", new String[] { "number", "mode" }, null,
						null, null, null, null);

		List<BlackNumber> lists = new ArrayList<BlackNumber>();

		while (cursor.moveToNext()) {

			BlackNumber blackNumber = new BlackNumber();

			blackNumber.setNumber(cursor.getString(0));

			blackNumber.setMode(cursor.getString(1));

			lists.add(blackNumber);

		}
		cursor.close();
		db.close();
		return lists;

	}

	/**
	 * 分页查询
	 * 
	 * @param pageSize
	 *            每一个页面的大小
	 * @param pageNumber
	 *            从什么地方开始(步长)
	 * @return
	 */
	public List<BlackNumber> findPart(int pageSize, int pageNumber) {

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery(
				"select number,mode from blacknumber limit ? offset ?",
				new String[] { String.valueOf(pageSize),
						String.valueOf(pageSize * pageNumber) });

		List<BlackNumber> lists = new ArrayList<BlackNumber>();

		while (cursor.moveToNext()) {

			BlackNumber blackNumber = new BlackNumber();

			blackNumber.setMode(cursor.getString(1));

			blackNumber.setNumber(cursor.getString(0));

			lists.add(blackNumber);
		}

		return lists;

	}

	/**
	 * 分批查询
	 * 
	 * @param startIndex
	 *            开始的索引的位置
	 * @param maxCount 20
	 *            每页展示多少条数据
	 * @return
	 */
	public List<BlackNumber> findPart2(int startIndex, int maxCount) {

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery(
				"select number,mode from blacknumber limit ? offset ? ",
				new String[] { String.valueOf(maxCount),
						String.valueOf(startIndex) });

		List<BlackNumber> lists = new ArrayList<BlackNumber>();

		while (cursor.moveToNext()) {

			BlackNumber blackNumber = new BlackNumber();

			blackNumber.setMode(cursor.getString(1));

			blackNumber.setNumber(cursor.getString(0));

			lists.add(blackNumber);
		}

		return lists;

	}

	/**
	 * 添加黑名单
	 * 
	 * @param number
	 *            黑名单的电话号码
	 * @param mode
	 *            黑名单的拦截模式
	 * @return
	 */
	public boolean add(String number, String mode) {
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("number", number);

		values.put("mode", mode);

		long rowID = db.insert("blacknumber", null, values);

		if (rowID == -1) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 删除黑名单
	 * 
	 * @param number
	 *            黑名单的电话号码
	 * @return
	 */
	public boolean delelte(String number) {

		SQLiteDatabase db = helper.getWritableDatabase();

		int rowNumber = db.delete("blacknumber", "number = ?",
				new String[] { number });

		if (rowNumber == 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 根据电话号码修改拦截的模式
	 * 
	 * @param number
	 * @param mode
	 * @return
	 */
	public boolean changeNumberMode(String number, String mode) {

		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("mode", mode);

		int rowNumber = db.update("blacknumber", values, "number = ?",
				new String[] { number });

		if (rowNumber == 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 根据电话号码查询拦截的模式
	 * 
	 * @param number
	 * @return
	 */
	public String find(String number) {
		String mode = "";
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("blacknumber", new String[] { "mode" },
				"number = ?", new String[] { number }, null, null, null);
		if (cursor.moveToNext()) {
			mode = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return mode;

	}

}
