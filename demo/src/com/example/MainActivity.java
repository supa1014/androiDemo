package com.example;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	
	public void click(View view){
		
//		View.inflate(context, resource, root)
		
		TextView textView = new TextView(this);
		
		textView.setBackgroundColor(Color.RED);
		
		textView.setText("我是弹出窗体");
		
		//初始化弹出窗体
		PopupWindow popupWindow = new PopupWindow(textView, -2, -2);
		//展示当前弹出窗体的位置
		popupWindow.showAtLocation(findViewById(R.id.root), Gravity.LEFT + Gravity.TOP, 100, 200);
		
	}
	

}
