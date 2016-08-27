package com.example.accelerometersensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SensorManager sensorManager;
	private TextView xValue, yValue, zValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		
		xValue = (TextView) findViewById(R.id.xValue);
		yValue = (TextView) findViewById(R.id.yValue);
		zValue = (TextView) findViewById(R.id.zValue);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(sensorManager != null){
			sensorManager.unregisterListener(listener);
		}
	}
	
	private SensorEventListener listener = new SensorEventListener(){
		@Override
		public void onSensorChanged(SensorEvent event){
			//将三个加速度显示到屏幕上
			xValue.setText("x = " + event.values[0]);
			yValue.setText("y = " + event.values[1]);
			zValue.setText("z = " + event.values[2]);
			//加速度可能是负值，所以要取它的绝对值
			float x = Math.abs(event.values[0]);
			float y = Math.abs(event.values[1]);
			float z = Math.abs(event.values[2]);
			if(x>15 || y>15 || z>15){
				Toast.makeText(MainActivity.this, "摇一摇", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

}
