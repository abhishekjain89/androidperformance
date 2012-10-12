package com.num.database.mapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.num.database.DatabaseColumns;
import com.num.models.MainModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ApplicationMapping extends BaseMapping {

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PACKAGE = "package";
	public static final String COLUMN_TOTAL_SENT = "t_sent";
	public static final String COLUMN_TOTAL_RECV = "t_recv";
	public static final String COLUMN_IS_RUNNING = "is_running";
	
	
	public ApplicationMapping(Context context) {		
		super(context);				
	}
	
	public void setColumnMap() {
		columns = new DatabaseColumns(getTableName());
		columns.add(COLUMN_ID,"integer primary key autoincrement");
		columns.add(COLUMN_TIME,"text not null");
		columns.add(COLUMN_NAME,"text not null");
		columns.add(COLUMN_PACKAGE,"text not null");
		columns.add(COLUMN_TOTAL_SENT,"long not null");
		columns.add(COLUMN_TOTAL_RECV,"long not null");
		columns.add(COLUMN_IS_RUNNING,"boolean not null");
					
	}

	@Override
	public String getTableName() {
		return "application";
	}
	
}