package com.example.uiweighttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private int x = 0;
	private Button button;
	private EditText edittext;
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button = (Button) this.findViewById(R.id.button);
		edittext = (EditText) this.findViewById(R.id.edit_text);
		progressBar = (ProgressBar) findViewById(R.id.process_bar);
		
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				x++;
				TextView text = (TextView) findViewById(R.id.text_view);
				text.setText("Clicked "+x);
				
				String inputText = edittext.getText().toString();
				Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_SHORT).show();
				
				int progress = progressBar.getProgress();
				progress += 10;
				progressBar.setProgress(progress);
				
				if(progress >= 100)
					progressBar.setVisibility(View.GONE);
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
				dialog.setTitle("This is a Dialog");
				dialog.setMessage("Something important");
				dialog.setCancelable(true);
				dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				
				dialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
