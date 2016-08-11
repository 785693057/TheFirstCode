package com.example.uiweighttest;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TitleLayout extends LinearLayout {

	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.title, this);
		
		Button titleBack = (Button) this.findViewById(R.id.title_back);
		Button titleEdit = (Button) this.findViewById(R.id.title_edit);
		
		titleBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Activity activity = (Activity) getContext();
				activity.finish();
			}
		});
		
		titleEdit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Toast.makeText(getContext(), "You clicked Edit Button", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
