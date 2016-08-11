package com.example.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button sendNotice = (Button) findViewById(R.id.send_notice);
		sendNotice.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				
				//现在真正使用的通知的构建方法，书上的已被弃用
				//Notification notification = new Notification(R.drawable.ic_launcher, "This is tick text", System.currentTimeMillis());
				Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				
				Notification notification = new Notification.Builder(MainActivity.this)
						.setSmallIcon(R.drawable.ic_launcher)
						.setTicker("TickerText: this is ticker text")
						.setContentTitle("this is content title")
						.setContentText("This is content text")
						.setContentIntent(pi)
						.build();
				
				notification.flags = Notification.FLAG_AUTO_CANCEL;
				manager.notify(1, notification);
			}
			
		});
	}
}
