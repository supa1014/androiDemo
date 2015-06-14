package com.example.mobilesafe54;

import java.util.List;

import com.example.mobilesafe54.db.dao.AntivirusDao;
import com.example.mobilesafe54.utils.Md5Utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AntivirusActivity extends Activity {
    //病毒扫描前
	protected static final int SCAN_BENGIN = 1;
	//病毒扫描中...
	protected static final int SCANING = 2;
	//扫描结束
	protected static final int SCAN_FINISH = 3;
	private PackageManager packageManager;
	
	private ScanInfo scanInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initTitleBar();
		scanAntivirus();
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SCAN_BENGIN:
				tv_scan_status.setText("初始化双核杀毒引擎");
				break;

			case SCANING:
				tv_scan_status.setText("初始化双核杀毒引擎");
				
				scanInfo =  (ScanInfo) msg.obj;
				
				TextView textView = new TextView(AntivirusActivity.this);
				//如果当前的apk有病毒
				if(scanInfo.isAntivirus){
					textView.setText(scanInfo.appName +"" + scanInfo.desc);
					textView.setTextColor(Color.RED);
				}else{
					textView.setText(scanInfo.appName);
					textView.setTextColor(Color.BLACK);
				}
				ll_content.addView(textView, 0);
				
				System.out.println("正在扫描:" +scanInfo.appName);
				
				break;
			case SCAN_FINISH:
				tv_scan_status.setText("扫描完毕");
				//让动画停下来
				iv_scan.clearAnimation();
				break;
			}
		};
	};
	private TextView tv_scan_status;
	private ImageView iv_scan;
	private LinearLayout ll_content;
	private ProgressBar pb;
	
    /**
     * 扫描病毒
     */
	private void scanAntivirus() {
		new Thread(){
			

			public void run() {
				
				Message message = handler.obtainMessage();
				
				message.what = SCAN_BENGIN;
				
				handler.sendMessage(message);
				//扫描的全部都是手机上面安装的apk
				int progress = 0;
				List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
				
				pb.setMax(installedPackages.size());
				//迭代所有安装到手机上面的包
				for (PackageInfo packageInfo : installedPackages) {
					
					String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
					
					
					//1 获取到当前手机安装的位置
					  //获取到当前手机里面apk的资源目录
					String apkPath = packageInfo.applicationInfo.sourceDir;
					System.out.println("apkPath======" + apkPath);
					
					//2 把当前的apk进行md5.就可以获取到md5的值。
					String md5 = Md5Utils.getMd5(apkPath);
					System.out.println("----------------------------");
					System.out.println("appName=====" + appName);
					System.out.println("md5=========" + md5);
//					04-04 03:27:08.701: I/System.out(9804): appName=====安智市场
//							04-04 03:27:08.701: I/System.out(9804): md5=========d41d8cd98f00b204e9800998ecf8427e
//					04-04 03:36:12.179: I/System.out(16996): appName=====安智市场
//							04-04 03:36:12.179: I/System.out(16996): md5=========eb037ad6ac8de1017d65452fc7cfbc13

					
					//3 把当前md5的值放入到数据库里面进行查询。看看病毒数据库里面是有匹配值
					
					String desc =  AntivirusDao.getDesc(md5);
					
					scanInfo = new ScanInfo();
					
					//4判断当前的md5查询出来是否有值
					//如果等于表示没有病毒
					if(desc == null){
						
						scanInfo.isAntivirus = false;
						
						scanInfo.appName = appName;
					}else{
						//说明有病毒
						scanInfo.isAntivirus = true;
						scanInfo.appName = appName;
						scanInfo.desc = desc;
					}
					
//					
					
					message = handler.obtainMessage();
					message.what = SCANING;
					message.obj = scanInfo;
					progress++;
					pb.setProgress(progress);
					handler.sendMessage(message);
					SystemClock.sleep(50);
					
				}
				message = handler.obtainMessage();
				message.what = SCAN_FINISH;
				
				handler.sendMessage(message);
			};
		}.start();
		
	}
	
	static class ScanInfo{
		String packageName;
		String appName;
		boolean isAntivirus;
		String desc;
	}

	private void initTitleBar() {
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText("病毒查杀");
	}

	private void initUI() {
		setContentView(R.layout.activity_antivirus);
		
		packageManager = getPackageManager();
		
		iv_scan = (ImageView) findViewById(R.id.iv_scan);
		
		tv_scan_status = (TextView) findViewById(R.id.tv_scan_status);
		
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		
		pb = (ProgressBar) findViewById(R.id.progressBar2);
		//初始化一个转圈的动画
		RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(2000);
		//给当前动画设置循环播放
		animation.setRepeatCount(Animation.INFINITE);
		iv_scan.startAnimation(animation);
	
	}
}
