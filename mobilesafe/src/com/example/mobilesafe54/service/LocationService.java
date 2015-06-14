package com.example.mobilesafe54.service;

import com.example.mobilesafe54.utils.SharedPreferencesUtils;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;

public class LocationService extends Service {

	private LocationManager lm;
	private MyLocationListener listener;


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//获取地址位置的管理者
		
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		//查询条件
		Criteria criteria = new Criteria();
		
		//允许产生开销
		criteria.setCostAllowed(true);
		//设置定位为精确的位置
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		
		//获取到最好的Provider
		/**
		 * criteria 表示查询条件
		 * true 表示如果为ture。那么就只会返回一个Provider
		 */
		String provider = lm.getBestProvider(criteria, true);
		
		listener = new MyLocationListener();
		
		lm.requestLocationUpdates(provider, 0, 0, listener);
	}
	
	
	private class MyLocationListener implements LocationListener{
        /**
         * 位置发生改变的时候调用的方法
         */
		@Override
		public void onLocationChanged(Location location) {
			
			//当小偷改变位置的时候。就把地理位置发送到安全的手机号码
			  //1 先得到安全号码
			String safePhone = SharedPreferencesUtils.getString(LocationService.this, "phone", "");
			// 2获取到小偷的位置
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("jingdu:" + location.getLongitude() + "\n");
			sb.append("weidu:" + location.getLatitude() + "\n");
			sb.append("jingquezhi:" + location.getAccuracy() + "\n");
			sb.append("haiba:" + location.getAltitude() + "\n");
			
			//把位置发送到安全的号码上面
			
			SmsManager.getDefault().sendTextMessage(safePhone, null, sb.toString(), null, null);
			//防止手机停机了
		    stopSelf();
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

	@Override
	public void onDestroy() {
		
		lm.removeUpdates(listener);
		
		lm = null;
		
		super.onDestroy();
	}

}
