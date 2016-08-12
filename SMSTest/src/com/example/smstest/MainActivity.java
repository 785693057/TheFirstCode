package com.example.smstest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView sender;
	private TextView content;
	private IntentFilter receiveFilter;
	private MessageReceiver messageReceiver;
	
	private EditText to;
	private EditText msgInput;
	private Button send;
	
	private IntentFilter sendFilter;
	private SendStatusReceiver sendStatusReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sender = (TextView) findViewById(R.id.sender);
		content = (TextView) findViewById(R.id.content);
		
		receiveFilter = new IntentFilter();
		receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		messageReceiver = new MessageReceiver();
		registerReceiver(messageReceiver, receiveFilter);//注册使其能够接收到短信广播
		
		to = (EditText) findViewById(R.id.to);
		msgInput = (EditText) findViewById(R.id.msg_input);
		send = (Button) findViewById(R.id.send);
		
		sendFilter = new IntentFilter();
		sendFilter.addAction("SEND_SMS_ACTION");
		sendStatusReceiver = new SendStatusReceiver();
		registerReceiver(sendStatusReceiver, sendFilter);//注册使其能接收到短信发送状态广播
		
		send.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SmsManager smsManager = SmsManager.getDefault();
				Intent sendIntent = new Intent("SEND_SMS_ACTION");
				PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, sendIntent, 0);
				smsManager.sendTextMessage(to.getText().toString(), null, msgInput.getText().toString(), pi, null);
			}
			
		});
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		unregisterReceiver(messageReceiver);
		unregisterReceiver(sendStatusReceiver);
	}

	class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();//取出bundle对象
			Object[] pdus = (Object[]) bundle.get("pdus");//使用pdu密钥提取SMS pdus数组
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for(int i=0; i<messages.length; i++){
				//将每一个pdu字节数组转换为SmsMessage对象
				
				//下面书上的方法已经弃用
				messages[i] = SmsMessage.createFromPdu( (byte[]) pdus[i] );
				
				//从知乎上找到的获取第二个参数format的方法，API23以后才能使用
				intent.getStringExtra("format");
				//messages[i] = SmsMessage.createFromPdu( (byte[]) pdus[i], format);
			}
			String address = messages[0].getOriginatingAddress();
			
			String fullMessage = "";
			for (SmsMessage message:messages){
				fullMessage += message.getMessageBody(); //将短信内容拼接起来
			}
			sender.setText(address);
			content.setText(fullMessage);
		}

	}
	
	class SendStatusReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(getResultCode() == RESULT_OK){
				Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
