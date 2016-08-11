package com.example.activitytest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class ThirdActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Toast.makeText(ThirdActivity.this, "Task id is "+this.getTaskId(), Toast.LENGTH_SHORT).show();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.third_layout);
		
		Button button3 = (Button) this.findViewById(R.id.button_3);
		button3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				//系统调用，调用系统拨号界面
//				Intent intent = new Intent(Intent.ACTION_DIAL);
//				intent.setData(Uri.parse("tel:10086"));
//				startActivity(intent);
//				
//				/*销毁当前活动，并且向上一个活动返回数据*/
//				Intent intent = new Intent();
//				intent.putExtra("data_return", "I'm back, SecondActivity");
//				setResult(RESULT_OK, intent);
//				finish();
			}
		});
	}
}
