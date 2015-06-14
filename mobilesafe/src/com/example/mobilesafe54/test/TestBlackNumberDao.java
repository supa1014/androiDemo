package com.example.mobilesafe54.test;

import java.util.Random;

import com.example.mobilesafe54.db.dao.BlackNumberDao;

import android.content.Context;
import android.test.AndroidTestCase;
/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-27 上午9:26:25
 * 
 * 描 述 ：
 * 
 * 
 * 修订历史 ：
 * 
 * ============================================================
 **/
public class TestBlackNumberDao extends AndroidTestCase {

	private Context context;

	@Override
	protected void setUp() throws Exception {
		context = getContext();
		super.setUp();
	}
	
	public void testAdd(){
		BlackNumberDao dao = new BlackNumberDao(context);
		Random random = new Random();
		for (int i = 0; i < 200; i++) {
			//注意这个地方是L, 不是数字1
			long number = 13100000000l + i;
			dao.add(number + "", String.valueOf(random.nextInt(3) + 1));
		}
		
	}
	
	public void testDelete(){
		BlackNumberDao dao = new BlackNumberDao(context);
		boolean result = dao.delelte("13100000001");
		//断言
		assertEquals(true, result);
	}
	
	public void testChangeNumberMode(){
		BlackNumberDao dao = new BlackNumberDao(context);
		boolean changeNumberMode = dao.changeNumberMode("13100000002", "3");
	}
}
