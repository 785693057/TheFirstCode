package com.example.networktest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ContentHandler extends DefaultHandler {
	
	private String nodeName;
	private StringBuilder id, name, version;
	
	private Handler handler;
	private String result = "";
	
	public ContentHandler(Handler handler){
		this.handler = handler;
	}
	
	@Override
	public void startDocument() throws SAXException{
		id = new StringBuilder();
		name = new StringBuilder();
		version = new StringBuilder();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
		//记录当前节点名
		nodeName = localName;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException{
		//根据当前节点名判断将内容添加到哪一个StringBuilder中
		if("id".equals(nodeName)){
			id.append(ch, start, length);
		}
		else if("name".equals(nodeName)){
			name.append(ch, start, length);
		}
		else if("version".equals(nodeName)){
			version.append(ch, start, length);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException{
		if("app".equals(localName)){
			Log.d("ContentHandler", "id is " + id.toString().trim());
			Log.d("ContentHandler", "name is " + name.toString().trim());
			Log.d("ContentHandler", "version is " + version.toString().trim());
			
			result = result
					+ "id is " + id.toString().trim() + "\n"
					+ "name is " + name.toString().trim() + "\n"
					+ "version is " + version.toString().trim() + "\n";
			
			id.setLength(0);
			name.setLength(0);
			version.setLength(0);
		}		
	}
	
	@Override
	public void endDocument() throws SAXException{
		//与书上不同，这里也尽量让解析结果显示到屏幕上
		Message msg = new Message();
		msg.what = MainActivity.SHOW_RESPONSE;
		msg.obj = result;
		handler.sendMessage(msg);
	}
}
