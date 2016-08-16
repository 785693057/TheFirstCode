package com.example.servicetest;

import com.example.servicetest.MyService.DownloadBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button startService, stopService;
	
	private Button bindService, unbindService;
	private MyService.DownloadBinder downloadBinder;
	private ServiceConnection connection = new ServiceConnection(){
		//服务与活动解除绑定时调用
		@Override
		public void onServiceDisconnected(ComponentName name){
		}

		//活动与服务绑定时调用
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			//通过Binder来指挥服务
			downloadBinder = (DownloadBinder) service;
			downloadBinder.startDownload();
			downloadBinder.getProgress();
		}
	};
	
	private Button startIntentService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService = (Button) findViewById(R.id.start_service);
		stopService = (Button) findViewById(R.id.stop_service);
		startService.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent startIntent = new Intent(MainActivity.this, MyService.class);
				startService(startIntent);//启动服务
			}
			
		});
		
		stopService.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent stopIntent = new Intent(MainActivity.this, MyService.class);
				stopService(stopIntent);//停止服务
			}
			
		});
		
		bindService = (Button) findViewById(R.id.bind_service);
		unbindService = (Button) findViewById(R.id.unbind_service);
		bindService.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent bindIntent = new Intent(MainActivity.this, MyService.class);
				bindService(bindIntent, connection, BIND_AUTO_CREATE);//绑定服务
			}
			
		});
		unbindService.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				unbindService(connection);//解除绑定服务
			}
			
		});
		
		startIntentService = (Button) findViewById(R.id.start_intent_service);
		startIntentService.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//打印主线程的id
				Log.d("MainActivity", "Thread id is " + Thread.currentThread().getId());
				Intent intentService = new Intent(MainActivity.this, MyIntentService.class);
				startService(intentService);
			}
			
		});
	}

}
