package com.example.andoidthreadtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static final int UPDATE_TEXT = 1;
	
	private TextView text;
	private Button changeText;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what){
			case UPDATE_TEXT:
				//可以在这里进行UI操作
				text.setText("Nice to meet you");
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (TextView) findViewById(R.id.text);
		changeText = (Button) findViewById(R.id.change_text);
		changeText.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				new Thread(new Runnable(){
					//在子线程中更新UI，运行结果会崩溃，因为UI操作只允许在主线程中执行
					/*@Override
					public void run(){
						text.setText("Nice to meet you");
					}*/
					
					//正确姿势
					@Override
					public void run() {
						Message msg = new Message();
						msg.what = UPDATE_TEXT;
						handler.sendMessage(msg);//将Message对象发送出去
					}
					
				}).start();
			}
			
		});
	}

}
