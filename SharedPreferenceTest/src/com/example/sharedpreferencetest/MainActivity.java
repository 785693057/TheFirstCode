package com.example.sharedpreferencetest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button saveData;
	private Button restoreData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		saveData = (Button) findViewById(R.id.save_data);
		saveData.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
				editor.putString("name", "Tom");
				editor.putInt("age", 28);
				editor.putBoolean("married", false);
				editor.commit();
			}
		});
		
		restoreData = (Button) findViewById(R.id.restore_data);
		restoreData.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
				String name = pref.getString("name", "");
				int age = pref.getInt("age", 0);
				boolean married = pref.getBoolean("married", false);
				Toast.makeText(MainActivity.this, name + " " + age + " " + married, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
