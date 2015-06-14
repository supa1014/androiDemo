package com.example.mobilesafe54.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-21 上午11:34:48
 * 
 * 描 述 ：
 * 
 * 把留对象写到客户端。返回服务器的json数据 修订历史 ：
 * 
 * ============================================================
 **/
public class StreamUtils {

	public static String readStream(InputStream is) {

		try {
			// 获取一个写的留对象
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];

			int len = -1;
			// 如果长度不等于-1成功
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			return new String(baos.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

}
