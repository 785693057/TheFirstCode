package com.example.choosepictest;

import java.io.File;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int CHOOSE_PHOTO = 3;
	
	private Button takePhoto;
	private ImageView picture;
	private Uri imageUri;
	
	private Button chooseFromAlbum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		takePhoto = (Button) findViewById(R.id.take_photo);
		chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
		picture = (ImageView) findViewById(R.id.picture);
		takePhoto.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//创建File对象，用于存储拍照后的图片
				File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
				try{
					if(outputImage.exists()){
						outputImage.delete();
					}
					outputImage.createNewFile();
				} catch (Exception e){
					e.printStackTrace();
				}
				imageUri = Uri.fromFile(outputImage);
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, TAKE_PHOTO);//启动相机程序
			}
			
		});
		chooseFromAlbum.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.GET_CONTENT");
				intent.setType("image/*");
				startActivityForResult(intent, CHOOSE_PHOTO);//启动相册程序选择图片，第二个值为返回值
			}
			
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch (requestCode){
		case TAKE_PHOTO:
			if(resultCode == RESULT_OK){
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;
		case CROP_PHOTO:
			if(resultCode == RESULT_OK){
				try{
					Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					picture.setImageBitmap(bitmap);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			break;
		case CHOOSE_PHOTO:
			if(resultCode == RESULT_OK){
				//判断手机系统版本号
				if(Build.VERSION.SDK_INT >= 19){
					handleImageOnKitKat(data);
				}
				else{
					handleImageBeforeKitKat(data);
				}
			}
		default:
			break;
		}
	}

	private void handleImageBeforeKitKat(Intent data) {
		Uri uri = data.getData();
		String imagePath = getImagePath(uri, null);
		displayImage(imagePath);
	}

	//从4.4版本开始，相册中的图片不再返回真实的Uri，需要解析
	private void handleImageOnKitKat(Intent data) {
		String imagePath = null;
		Uri uri = data.getData();
		if(DocumentsContract.isDocumentUri(this, uri)){
			//如果是document类型的uri，则通过document id处理
			String docId = DocumentsContract.getDocumentId(uri);
			if("com.android.providers.media.documents".equals(uri.getAuthority())){
				String id = docId.split(":")[1];
				String selection = MediaStore.Images.Media._ID + "=" + id;
				imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
			}
			else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
				imagePath = getImagePath(contentUri, null);
			}
		}
		else if("content".equalsIgnoreCase(uri.getScheme())){
			//如果不是document类型的Uri，则使用普通方式处理
			imagePath = getImagePath(uri, null);
		}
		displayImage(imagePath);
	}

	//将图片显示到界面上
	private void displayImage(String imagePath) {
		if(imagePath != null){
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			picture.setImageBitmap(bitmap);
		}
		else{
			Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
		}
		
	}

	private String getImagePath(Uri uri, String selection) {
		String path = null;
		//通过Uri和selection来获取真实路径
		Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				path = cursor.getString(cursor.getColumnIndex(Media.DATA));
				cursor.close();
			}
		}
		return path;
	}
}
