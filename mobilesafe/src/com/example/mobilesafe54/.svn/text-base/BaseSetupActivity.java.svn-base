package com.example.mobilesafe54;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public abstract class BaseSetupActivity extends Activity {

	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 初始化手势识别器

		gestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {

					/**
					 * e1 : 表示当前我的手指第一次触摸到屏幕的位置 e2 : 表示当我的手指离开屏幕的时候的位置 velocityX
					 * : 表示横着滑动x轴的距离 单位 px/s velocityY : 表示滚动y轴的距离
					 */
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {

						// e2.getRawX() 获取手指距离屏幕的位置
						// e1.getX() 手指距离控件的位置
						if (e1.getRawX() - e2.getRawX() > 200) {
							
							
							
							
							// 下一页
							System.out.println("下一页");

							// 使用过渡动画。必须先跳转然后在设置动画
							showNext();

							// 两个activity页面在跳转的时候。实现的一个过渡动画
							overridePendingTransition(R.anim.translate_in,
									R.anim.translate_out);

							return true;
						}

						if (e1.getRawX() - e2.getRawX() < -200) {
							System.out.println("上一页");

							showPre();
							// 两个activity页面在跳转的时候。实现的一个过渡动画
							overridePendingTransition(R.anim.pre_page_in,
									R.anim.pre_page_out);
							return true;
						}

						return super.onFling(e1, e2, velocityX, velocityY);
					}

				});
	}

	/**
	 * 滑动到上一页
	 */
	protected abstract void showPre();

	/**
	 * 滑动到下一页
	 */
	protected abstract void showNext();

	// 当手指触摸屏幕滑动的时候。会调用的方法
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
