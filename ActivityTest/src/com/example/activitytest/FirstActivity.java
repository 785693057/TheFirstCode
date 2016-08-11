package com.example.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Toast.makeText(FirstActivity.this, "Task id is "+getTaskId(), Toast.LENGTH_SHORT).show();//在底部闪出提示
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
		//将这个活动创建时的视图设为first_layout
		setContentView(R.layout.first_layout);
		
		Button button1 = (Button) findViewById(R.id.button_1);//从视图中根据id获得对应组件
		button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * 添加菜单的方法
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);//从布局文件中获取需要的菜单进行添加
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.add_item:
			Toast.makeText(FirstActivity.this, "You clicked add", Toast.LENGTH_SHORT).show();
			break;
		case R.id.remove_item:
			Toast.makeText(FirstActivity.this, "You clicked add", Toast.LENGTH_SHORT).show();
			break;
		default:
		}
		return true;
	}
}
