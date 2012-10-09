package com.num.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.database.Cursor;

public class DatabaseColumns {
	
	private HashMap<String,String> columns;
	private String tableName;
	
	public DatabaseColumns(String tablename) {
		columns = new HashMap<String, String>();
		this.tableName = tablename;
	}
	
	public void add(String name, String type) {
		columns.put(name,type);
	}
	
	public String getDatabaseCreateText() {
		String s = "create table " + tableName + " ("; 
		
		Iterator<String> iter = columns.keySet().iterator();
		 
		//iter.next();
		while(iter.hasNext()) {
			String key = iter.next();
			s+= key +" "+ columns.get(key) +",";
		}				
		s = s.substring(0,s.length()-1);
		s+=" );";
		
		return s;
	}	
	
	public String[] getColumnNames() {
		String[] array = new String[columns.size()];		
		Iterator<String> iter = columns.keySet().iterator();
		 
	
		int count = 0;
		while(iter.hasNext()) {
			array[count++] = iter.next();
		}							
		return array;			
	}
	
	public HashMap<String,Integer> fetch(Cursor cursor) {
		HashMap<String,Integer> map = new HashMap<String, Integer>();
		Iterator<String> iter = columns.keySet().iterator();
		
		while(iter.hasNext()) {
			String column = iter.next();
			int value = cursor.getColumnIndex(column);
			map.put(column, value);
		}							
		return map;
		
	}
	
	public Map<String,String> getDataStore(Cursor cursor) {
		Map<String,Integer> positions = fetch(cursor);
		Map<String,String> dataStore= new HashMap<String, String>();
		
		Iterator<String> iter = columns.keySet().iterator();
		
		while(iter.hasNext()) {
			String column = iter.next();
			int position = positions.get(column);
			dataStore.put(column, cursor.getString(position));
		}							
		return dataStore;
	}
	
}
