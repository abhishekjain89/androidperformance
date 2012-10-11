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

public class LatencyMapping extends BaseMapping {

	public LatencyMapping(Context context) {
		super(context);
	}

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_AVG = "avg";
	public static final String COLUMN_MIN = "min";
	public static final String COLUMN_MAX = "max";
	public static final String COLUMN_STD = "std";
	public static final String COLUMN_SRCIP = "srcip";
	public static final String COLUMN_DSTIP = "dstip";
	public static final String COLUMN_CONNECTION = "connection";
	public static final String COLUMN_TYPE = "type";
	
	public void setColumnMap() {
		columns = new DatabaseColumns(getTableName());
		columns.add(COLUMN_ID,"integer primary key autoincrement");
		columns.add(COLUMN_TIME,"text not null");
		columns.add(COLUMN_TYPE,"text not null");
		columns.add(COLUMN_AVG,"real");
		columns.add(COLUMN_MIN,"real");
		columns.add(COLUMN_MAX,"real");
		columns.add(COLUMN_STD,"real");
		columns.add(COLUMN_SRCIP,"text not null");
		columns.add(COLUMN_DSTIP,"text not null");
		columns.add(COLUMN_CONNECTION,"text not null");
				
	}

	@Override
	public String getTableName() {
		return "latency";
	}
	
}