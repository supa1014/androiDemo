package com.example.mobilesafe54.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class AddressNumberDao {

	/**
	 * 根据电话号码查询归属地的位置
	 * 
	 * @param phone
	 * @return
	 */
	public static String getLocation(String phone) {


		String location = phone;

		// select location from data2 where id = '3'
		// select outkey from data1 where id = '1111'

		// 打开当前的数据库
		/**
		 * 第一个参数表示当前数据库的路径 第二个参数是工厂 第三个参数表示类型。当前设置为只读
		 */
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				"/data/data/com.example.mobilesafe54/files/address.db", null,
				SQLiteDatabase.OPEN_READONLY);

		// 手机查询
		// 判断当前的电话号码是否是手机
		/*
		 * 13 5 8 4 7 6 后面接9位数字
		 */
		// 使用正则表达式进行匹配手机
		if (phone.matches("^1[345678]\\d{9}$")) {
			// 进行链表查询
			Cursor cursor = db
					.rawQuery(
							"select location from data2 where id = (select outkey from data1 where id = ?)",
							new String[] { phone.substring(0, 7) });

			if (cursor.moveToNext()) {
				location = cursor.getString(0);
			}
		} else {

			switch (location.length()) {

			case 3:
				if ("110".equals(phone)) {
					location = "匪警";
				} else if ("114".equals(phone)) {
					location = "人工查询";
				}

				break;

			default:
				// 判断当前的号码是否是电话号码
				if (phone.startsWith("0") && phone.length() >= 3) {

					String address = "";

					Cursor cursor = db.rawQuery(
							"select location from data2 where area = ?",
							new String[] { phone.substring(1, 3) });

					if (cursor.moveToNext()) {
						String string = cursor.getString(0);
						address = string.substring(0, string.length() - 2);
					}

					cursor.close();

					cursor = db.rawQuery(
							"select location from data2 where area = ?",
							new String[] { phone.substring(1, 4) });

					if (cursor.moveToNext()) {
						String string = cursor.getString(0);
						address = string.substring(0, string.length() - 2);
					}

					cursor.close();

					if (!TextUtils.isEmpty(address)) {
						location = address;
					}

				}

				break;
			}

		}

		return location;
	}
}
