package com.example.playvideotest;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends Activity implements OnClickListener{
	
	private VideoView videoView;
	
	private Button play, pause, replay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		play = (Button) findViewById(R.id.play);
		pause = (Button) findViewById(R.id.pause);
		replay = (Button) findViewById(R.id.replay);
		videoView = (VideoView) findViewById(R.id.video_view);
		
		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		replay.setOnClickListener(this);
		
		initVideoPath();
	}

	private void initVideoPath() {
		File file = new File(Environment.getExternalStorageDirectory(), "movie.mkv");
		videoView.setVideoPath(file.getPath());//指定视频文件的路径
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.play:
			if(!videoView.isPlaying()){
				videoView.start();//开始播放
			}
			break;
		case R.id.pause:
			if(videoView.isPlaying()){
				videoView.pause();//暂停播放
			}
			break;
		case R.id.replay:
			if(videoView.isPlaying()){
				videoView.resume();//重新播放
			}
			break;
		}
	}

	protected void onDestroy(){
		super.onDestroy();
		if(videoView != null){
			videoView.suspend();//释放占用的资源
		}
	}
}
