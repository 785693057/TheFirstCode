package com.example.activitylifecycletest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class NormalActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.normal_layout);
	}
}
