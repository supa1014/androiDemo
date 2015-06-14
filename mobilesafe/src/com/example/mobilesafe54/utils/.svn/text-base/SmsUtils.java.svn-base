package com.example.mobilesafe54.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Xml;
import android.widget.ProgressBar;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-25 上午10:10:52
 * 
 * 描 述 ：
 * 
 * 短信备份工具类 修订历史 ：
 * 
 * ============================================================
 **/
public class SmsUtils {
	
	public interface SmsBackUP{
		//备份短信之前
		public void beforeSmsUp(int size);
		//备份短信中
		public void onSmsUp(int process);
	}
	
	
	

	public static boolean getSmsBackup(Activity context, SmsBackUP smsBackUP) {

		/**
		 * 短信备份 1 至少有一张sd卡 2使用内容解析者读取短信 3初始化一个输出留写到sd卡 4初始化一个file文件 5初始化xml序列化器
		 * 
		 * 
		 * 
		 */
		// 判断用户是否有sd卡
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			try {
				// 获取到序列化器

				XmlSerializer serializer = Xml.newSerializer();

				// 初始化文件
				File file = new File(Environment.getExternalStorageDirectory(),
						"backup.xml");

				FileOutputStream fos = new FileOutputStream(file);

				// 设置流
				serializer.setOutput(fos, "utf-8");

				// 第二个参数表示当前文件是否独立 ture的话。表示文件独立
				serializer.startDocument("utf-8", true);
				// 设置开始的标记 第一个参数表示命名空间
				serializer.startTag(null, "smss");

				// 得到内容解析者
				ContentResolver resolver = context.getContentResolver();
				// 短信的uri
				Uri uri = Uri.parse("content://sms/");

				Cursor cursor = resolver.query(uri, new String[] { "address",
						"date", "type", "body" }, null, null, null);
				// 获取数据库里面一共有多少条短信
				int count = cursor.getCount();
//				progressDialog.setMax(size);
				//设置进度条的大小
				smsBackUP.beforeSmsUp(count);

				// 设置属性
				/**
				 * 第一个参数表示命名空间 第二个参数表示当前属性的名字
				 */
				serializer.attribute(null, "size", String.valueOf(count));
				//进度条
				int process = 0;

				// 判断当前的游标能否移动到下一位
				while (cursor.moveToNext()) {

					
					
					System.out.println("-------------------------");
					System.out.println(cursor.getString(0));
					System.out.println(cursor.getString(1));
					System.out.println(cursor.getString(2));
					System.out.println(cursor.getString(3));
					
					
					serializer.startTag(null, "sms");

					serializer.startTag(null, "address");

					serializer.text(cursor.getString(0));

					serializer.endTag(null, "address");

					serializer.startTag(null, "date");

					serializer.text(cursor.getString(1));

					serializer.endTag(null, "date");

					serializer.startTag(null, "type");

					serializer.text(cursor.getString(2));

					serializer.endTag(null, "type");

					serializer.startTag(null, "body");

					serializer.text(Crypto.encrypt("123", cursor.getString(3)));

					serializer.endTag(null, "body");
					
					serializer.endTag(null, "sms");
					
					SystemClock.sleep(200);
					
					process++;
					//设置对话框的进度条
//					smsBackUP.setProgress(process);
					smsBackUP.onSmsUp(process);
				}

				serializer.endTag(null, "smss");

				serializer.endDocument();

				// 刷新
				fos.flush();
				// 关闭流
				fos.close();
				
				
				
				return true;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			ToastUtils.showSafeTost(context, "请插入sd卡");
		}

		return false;
	}

}
