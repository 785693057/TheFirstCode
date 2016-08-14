package com.example.playaudiotest;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button play;
	private Button pause;
	private Button stop;
	
	private MediaPlayer mediaPlayer = new MediaPlayer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		play = (Button) findViewById(R.id.play);
		pause = (Button) findViewById(R.id.pause);
		stop = (Button) findViewById(R.id.stop);
		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		stop.setOnClickListener(this);
		initMediaPlayer();
	}

	private void initMediaPlayer() {
		try {
			File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
			mediaPlayer.setDataSource(file.getPath());//指定音频文件的路径
			mediaPlayer.prepare();//让音频进入到准备状态
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.play:
			if(!mediaPlayer.isPlaying()){
				mediaPlayer.start();//开始播放
			}
			break;
		case R.id.pause:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.pause();//暂停播放
			}
			break;
		case R.id.stop:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.reset();//停止播放
				initMediaPlayer();//重新初始化准备下次播放
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(mediaPlayer != null){
			mediaPlayer.stop();
			mediaPlayer.release();//将mediaPlayer相关资源释放
		}
	}
}
