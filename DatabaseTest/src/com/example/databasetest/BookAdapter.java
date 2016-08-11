package com.example.databasetest;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BookAdapter extends ArrayAdapter<Book> {
	private int resourceId;

	public BookAdapter(Context context, int resource, List<Book> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Book book = getItem(position);
		View view;
		TextView text;
		
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			text = (TextView) view.findViewById(R.id.book_item);
			view.setTag(text);
		}
		else{
			view = convertView;
			text = (TextView) view.getTag();
		}
		
		text.setText(book.getName()+ "|" + book.getAuthor() + "|" + book.getPrice() + "元| " + book.getPages());
		return view;
	}
}
