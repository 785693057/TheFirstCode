package com.example.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	
	private DownloadBinder mBinder = new DownloadBinder();
	
	class DownloadBinder extends Binder{
		public void startDownload(){
			Log.d("MyService", "startDownload executed");
		}
		
		public int getProgress(){
			Log.d("MyService", "getProgress executed");
			return 0;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onCreate(){
		super.onCreate();
		//使用前台服务，在下拉菜单上显示运行
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		
		Notification notification = new Notification.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setTicker("Notification comes")
				.setContentIntent(pendingIntent)
				.setContentTitle("This is title")
				.setContentText("This is content")
				.build();
		
		startForeground(1, notification);
		Log.d("MyService", "onCreate executed");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d("MyService", "onStartCommand executed");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d("MyService", "onDestroy executed");
	}
}
