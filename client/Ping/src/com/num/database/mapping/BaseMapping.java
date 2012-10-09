package com.num.database.mapping;

import java.util.HashMap;

import com.num.database.DatabaseColumns;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public abstract class BaseMapping extends SQLiteOpenHelper {

	public static String DATABASE_NAME="networkusage.db";
	public static int DATABASE_VERSION = 1;
	public String TABLE_NAME;

	public BaseMapping(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	
	}
	
	public DatabaseColumns columns;

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(getCreateText());
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(BaseMapping .class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + getTableName());
		onCreate(database);
	}
	
	public String getCreateText() {
		return columns.getDatabaseCreateText();
	}
	
	public DatabaseColumns getDatabaseColumns() {
		return columns;
	}

	public abstract void setColumnMap();
	
	public String getTableName() {
		return TABLE_NAME;
	}
	
}