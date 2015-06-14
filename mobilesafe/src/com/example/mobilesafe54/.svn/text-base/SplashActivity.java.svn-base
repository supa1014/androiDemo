package com.example.mobilesafe54;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.youmi.android.AdManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mobilesafe54.db.dao.AntivirusDao;
import com.example.mobilesafe54.utils.SharedPreferencesUtils;
import com.example.mobilesafe54.utils.StreamUtils;
import com.example.mobilesafe54.utils.ToastUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.Toast;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-21 上午10:14:08
 * 
 * 描 述 ：
 * 
 * 欢迎界面 修订历史 ：
 * 
 * ============================================================
 **/
public class SplashActivity extends Activity {
	// 进入主界面
	protected static final int SHOW_MAIN_UI = 0;
	// 展示对话框
	protected static final int SHOW_DIALOG = 1;
	// 版本号
	private int versionCode;
	// 下载的apk地址
	private String downloadurl;

	// 描述信息
	private String desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去掉标题 需要注意：必须在setcontview之前
		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		initUI();
		
		AdManager.getInstance(this).init("4f178a1ab7ee9d6b", "9f5bfaddac5d2f23", false);
		
		// 创建快捷方式
		createShortcut();

		// 复制数据库
		copyDB("address.db");
		
		copyDB("antivirus.db");
		//更新病毒数据库
		
		updateAntivirusDB();
		initData();

	}

	private void updateAntivirusDB() {
		// xx神器
		
		HttpUtils httpUtils = new HttpUtils();
		String url = "http://192.168.20.195:8080/virus.json";
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				System.out.println("病毒==========" + arg0.result);
				
				try {
					JSONObject jsonObject = new JSONObject(arg0.result);
					
					String md5 = jsonObject.getString("md5");
					
					String desc = jsonObject.getString("desc");
					
					AntivirusDao.add(md5, desc);
					
					ToastUtils.showSafeTost(SplashActivity.this, "更新完毕");
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
	}

	/**
	 * 创建快捷方式
	 */
	private void createShortcut() {

		Intent intent = new Intent();

		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		//false表示不允许重复创建
		intent.putExtra("duplicate", false);

		// 必须使用隐式意图
		Intent doWhat = new Intent();

		doWhat.setAction("aaa.bbb.ccc");

		doWhat.addCategory("android.intent.category.DEFAULT");

//		startActivity(doWhat);

		// 1 你先干什么事情

		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, doWhat);

		// 2你的快捷方式叫什么名字

		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "哈哈");

		// 3你的快捷方式长成什么样子

		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory
				.decodeResource(getResources(), R.drawable.ic_launcher));

		sendBroadcast(intent);

	}

	/**
	 * 把地理位置的数据库拷贝到当前的包名底下
	 * 
	 * @param dbName
	 *            数据库的名字
	 */
	private void copyDB(final String dbName) {
		new Thread() {
			public void run() {

				try {
					// 初始化一个文件
					/**
					 * getFilesDir() 文件的目录 dbName 文件的名字
					 */
					File file = new File(getFilesDir(), dbName);
					// 判断当前的文件是否存在。以及判断当前文件的长度是否大于0
					if (file.exists() && file.length() > 0) {
						System.out.println("文件已经存在");
						return;
					}

					// 获取到当前的输入流
					InputStream is = getAssets().open(dbName);
					// 得到一个写的流
					FileOutputStream fos = openFileOutput(dbName, 0);

					byte[] buffer = new byte[1024];

					int len = -1;

					while ((len = is.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
					System.out.println("已经拷贝完毕");
					fos.close();
					is.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			};
		}.start();

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 获取到包的管理者
		PackageManager packageManager = getPackageManager();

		try {
			// 获取到包的基本信息
			/**
			 * 第一个参数表示包名 第二个参数表示标记。一般用不上就给一个0 注意：不能把包名写死
			 */
			PackageInfo packageInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			// 给开发人员用的

			versionCode = packageInfo.versionCode;
			// 让用户看的
			String versionName = packageInfo.versionName;
			System.out.println("===========================");
			System.out.println("versionCode:" + versionCode);
			System.out.println("versionName:" + versionName);

			// 链接服务器检查版本号
			// 判断当前的版本是否需要更新
			boolean result = SharedPreferencesUtils.getBoolean(this,
					"isupdate", false);

			if (result) {
				checkVersionCode();
			} else {
				loadMainUI();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 初始化handler
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 直接进入主界面
			case SHOW_MAIN_UI:
				loadMainUI();
				break;
			// 展示更新对话框
			case SHOW_DIALOG:

				/**
				 * 需要注意： SplashActivity.this 表示当前界面的上下文 getApplicationContext()
				 * 表示全局的上下文
				 */
				AlertDialog.Builder builder = new Builder(SplashActivity.this);
				// 设置对话框的提示
				builder.setTitle("提示");
				// 设置描述信息
				builder.setMessage(desc);
				// 设置取消监听
				builder.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						loadMainUI();
					}
				});

				// 注意：如果当前没有提示。只有第一个方法才有提示。需要把方法放到前面
				builder.setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 直接进入主界面
						loadMainUI();

					}
				});

				// 确定
				builder.setPositiveButton("更新", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 下载apk
						/**
						 * 1 需要把apk下载到sd卡 2 安装apk
						 */
						download();
					}

				});

				// show出对话框
				builder.show();

				break;
			}
		};
	};

	/**
	 * 下载apk
	 */
	private void download() {
		HttpUtils httpUtils = new HttpUtils();
		/**
		 * 第一个参数表示请求的url地址 第二个参数表示存放的位置 一般目前市面上的软件都是存放到sd卡 第三个参数表示回调是否成功或者失败
		 */
		httpUtils.download(downloadurl, "/mnt/sdcard/temp.apk",
				new RequestCallBack<File>() {
					// 当成功的时候回调的方法
					@Override
					public void onSuccess(ResponseInfo<File> arg0) {

						// 如果下载成功之后。需要安装软件
						// <intent-filter>
						// <action android:name="android.intent.action.VIEW" />
						// <category
						// android:name="android.intent.category.DEFAULT" />
						// <data android:scheme="content" />
						// <data android:scheme="file" />
						// <data
						// android:mimeType="application/vnd.android.package-archive"
						// />
						// </intent-filter>
						// 安装软件
						Intent intent = new Intent();
						intent.setAction("android.intent.action.VIEW");
						intent.addCategory("android.intent.category.DEFAULT");
						intent.setDataAndType(
								Uri.fromFile(new File("/mnt/sdcard/temp.apk")),
								"application/vnd.android.package-archive");
						// startActivity(intent);
						startActivityForResult(intent, 0);
					}

					// 失败的时候回调的方法
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(SplashActivity.this, "下载失败", 0).show();

					}
				});
	}

	/**
	 * 当用户点击取消的时候调用的方法
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		loadMainUI();
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 检查版本号
	 */
	private void checkVersionCode() {

		new Thread() {

			public void run() {

				Message message = Message.obtain();

				try {
					URL url = new URL("http://192.168.20.195:8080/info.json");
					// 获取到url链接
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					// 设置请求的方法
					connection.setRequestMethod("GET");
					// 设置链接超时时间
					connection.setConnectTimeout(2000);
					// 获取服务器返回来的code
					int code = connection.getResponseCode();
					// 如果等于200表示跟服务器交互成功
					if (code == 200) {
						// 获取服务器返回过来的留对象
						InputStream inputStream = connection.getInputStream();

						String json = StreamUtils.readStream(inputStream);

						System.out.println(json);

						// 如果服务器返回过来的json数据是大括号开始的。就必须初始化JSONObject
						// /如果服务器返回的数据是中括号。那么就使用JSONArray
						JSONObject obj = new JSONObject(json);
						// 必须getkey才能拿到值
						// apk的下载地址

						downloadurl = obj.getString("downloadurl");
						// apk的版本号
						int version = obj.getInt("version");
						// apk的描述信息

						desc = obj.getString("desc");

						System.out.println("------------------------");
						System.out.println("downloadurl===" + downloadurl);
						System.out.println("version===" + version);
						System.out.println("desc===" + desc);

						// 如果服务器的版本号和本地的版本号相同就不升级
						if (version == versionCode) {
							// 不升级

							message.what = SHOW_MAIN_UI;
						} else {
							// 升级

							message.what = SHOW_DIALOG;
						}

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// 发送消息
					handler.sendMessage(message);
				}

			};
		}.start();

	}

	/**
	 * 进入主界面
	 */
	protected void loadMainUI() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		setContentView(R.layout.activity_splash);

	}
}
