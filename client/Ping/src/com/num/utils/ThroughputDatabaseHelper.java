package com.num.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ThroughputDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "todotable.db";
	private static final int DATABASE_VERSION = 1;

	// Database table
	public static final String TABLE_THROUGHPUT = "throughput";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DOWNLINK = "downlink";
	public static final String COLUMN_UPLINK = "uplink";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_THROUGHPUT
			+ "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_DOWNLINK + " text not null, " 
			+ COLUMN_UPLINK + " text not null);";

	public ThroughputDatabaseHelper(Context context) {
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
		Log.w(ThroughputDatabaseHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_THROUGHPUT);
		onCreate(database);
	}
}