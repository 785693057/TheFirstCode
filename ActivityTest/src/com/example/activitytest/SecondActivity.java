package com.example.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Toast.makeText(SecondActivity.this, "Task id is "+getTaskId(), Toast.LENGTH_SHORT).show();//在底部闪出提示
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
		//将这个活动创建时的视图设为first_layout
		setContentView(R.layout.second_layout);
		
		Button button2 = (Button) this.findViewById(R.id.button_2);
		button2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
				startActivity(intent);
			}
		});		
	}
}
