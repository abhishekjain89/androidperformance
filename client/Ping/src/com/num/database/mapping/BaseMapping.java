package com.num.database.mapping;

import java.util.HashMap;

import com.num.database.DatabaseColumns;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public abstract class BaseMapping extends SQLiteOpenHelper {
	
	String TABLE_NAME;
	int DATABASE_VERSION;

	public BaseMapping(Context context,String tableName, int version) {
		super(context, tableName+".db", null, version);
		TABLE_NAME = tableName;
		DATABASE_VERSION = version;
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
		Log.w(BaseMapping .class.getName(), "Upgrading database " + getDBName()+" from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + getTableName());
		onCreate(database);
	}
	
	public String getCreateText() {
		return getDatabaseColumns().getDatabaseCreateText();
	}
	
	public DatabaseColumns getDatabaseColumns() {
		setColumnMap();
		return columns;
	}

	public abstract void setColumnMap();
	
	public String getTableName() {
		return TABLE_NAME;
	}
	
	public String getDBName() {
		return getTableName()+".db";
	}
	
}