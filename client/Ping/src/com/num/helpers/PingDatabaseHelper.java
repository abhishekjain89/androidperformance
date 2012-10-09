package com.num.helpers;

import com.num.database.mapping.ThroughputMapping;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class PingDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ping.db";
	private static final int DATABASE_VERSION = 1;

	// Database table
	public static final String TABLE_PING = "ping";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_AVG = "avg";
	public static final String COLUMN_MIN = "min";
	public static final String COLUMN_MAX = "max";
	public static final String COLUMN_STD = "std";
	public static final String COLUMN_SRCIP = "srcip";
	public static final String COLUMN_DSTIP = "dstip";
	public static final String COLUMN_CONNECTION = "connection";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_PING
			+ "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_TIME + " text not null, " 
			+ COLUMN_AVG + " real, " 
			+ COLUMN_MIN + " real, " 
			+ COLUMN_MAX + " real, " 
			+ COLUMN_STD + " real, " 
			+ COLUMN_SRCIP + " text not null, " 
			+ COLUMN_DSTIP + " text not null, "
			+ COLUMN_CONNECTION + " text not null);";

	public PingDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(ThroughputMapping .class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PING);
		onCreate(database);
	}
}