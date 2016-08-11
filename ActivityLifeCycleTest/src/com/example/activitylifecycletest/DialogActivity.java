package com.example.activitylifecycletest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class DialogActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_layout);
	}
}
