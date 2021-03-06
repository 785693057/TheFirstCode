package com.example.databasetest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private MyDatabaseHelper dbHelper;
	
	private List<Book> books;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
		Button createDatabase = (Button) findViewById(R.id.create_database);
		createDatabase.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				dbHelper.getWritableDatabase();
			}
		});
		
		Button addData = (Button) findViewById(R.id.add_data);
		addData.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				
				values.put("name", "The Da Vinci Code");
				values.put("author", "Dan Brown");
				values.put("pages", 454);
				values.put("price", 16.96);
				db.insert("Book", null, values);
				
				values.clear();
				
				values.put("name", "The Lost Symbol");
				values.put("author", "Dan Brown");
				values.put("pages", 510);
				values.put("price", 16.95);
				db.insert("Book", null, values);
				
				values.clear();
				
				values.put("name", "The First Code");
				values.put("author", "CSDN");
				values.put("pages", 552);
				values.put("price", 40.32);
				db.insert("Book", null, values);
				
				searchData();
			}
		});
		
		Button updateData = (Button) findViewById(R.id.update_data);
		updateData.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("price", 10.99);
				db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});
				
				searchData();
			}
		});
		
		Button deleteData = (Button) findViewById(R.id.delete_data);
		deleteData.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.delete("Book", "pages > ?", new String[]{"500"});
				
				searchData();
			}
		});
		
		Button replaceData = (Button) findViewById(R.id.replace_data);
		replaceData.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				db.beginTransaction();//开始事务
				try{
					db.delete("Book", null, null);
					if(true){
						throw new NullPointerException();
					}
					ContentValues values = new ContentValues();
					values.put("name", "Game of Thrones");
					values.put("author", "George Martin");
					values.put("pages", 720);
					values.put("price", 20.85);
					db.insert("Book", null, values);
					db.setTransactionSuccessful();//表示事务执行成功
				} catch (Exception e){
					e.printStackTrace();
				} finally {
					db.endTransaction();//结束事务
					searchData();
				}
			}
		});
	}
	
	private void searchData(){
		books = new ArrayList<Book>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		Cursor cursor = db.query("Book", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String author = cursor.getString(cursor.getColumnIndex("author"));
				int pages = cursor.getInt(cursor.getColumnIndex("pages"));
				double price = cursor.getDouble(cursor.getColumnIndex("price"));
				books.add(new Book(name, author, pages, price));
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		
		BookAdapter adapter = new BookAdapter(this, R.layout.book_item, books);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		
		Toast.makeText(this, "size of list:"+books.size(), Toast.LENGTH_SHORT).show();;
	}
}
