package com.num.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	
	private static final String DATABASE_NAME = "PingDatabase.db";
	private static final String DATABASE_TABLE = "mainTable";
	private static final int DATABASE_VERSION = 1;
	 
	// The index (key) column name for use in where clauses.
	public static final String KEY_ID="_id";

	// The name and column index of each column in your database.
	public static final String KEY_NAME="name"; 
	public static final int NAME_COLUMN = 1;
	  
	// TODO: Create public field for each column in your table.
	  
	// SQL Statement to create a new database.
	private static final String DATABASE_CREATE = "create table " + 
	    DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
	    KEY_NAME + " text not null);";

	// Variable to hold the database instance
	private SQLiteDatabase db;
	  
	// Context of the application using the database.
	private final Context context;
	  
	// Database open/upgrade helper
	private myDbHelper dbHelper;

	public DBAdapter(Context _context) {
		context = _context;
		dbHelper = new myDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public DBAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	
	/*
	public int insertEntry(MyObject _object) {
		// TODO: Create a new ContentValues to represent my row
		// and insert it into the database.
		return 0;//index;
	}
	*/

	public boolean removeEntry(long _rowIndex) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
	}

	public Cursor getAllEntries () {
		return db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_NAME}, 
	                    null, null, null, null, null);
	}

	/*
	public MyObject getEntry(long _rowIndex) {
		// TODO: Return a cursor to a row from the database and
		// use the values to populate an instance of MyObject
		return objectInstance;
	}
	*/
	
	/*
	public boolean updateEntry(long _rowIndex, MyObject _myObject) {
		// TODO: Create a new ContentValues based on the new object
		// and use it to update a row in the database.
		return true;
	}
	*/

	private static class myDbHelper extends SQLiteOpenHelper {

		public myDbHelper(Context context, String name, 
	                      CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		// Called when no database exists in disk and the helper class needs
		// to create a new one. 
		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE);
		}

		// Called when there is a database version mismatch meaning that the version
		// of the database on disk needs to be upgraded to the current version.
		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
			// Log the version upgrade.
			Log.w("TaskDBAdapter", "Upgrading from version " + 
	                             _oldVersion + " to " +
	                             _newVersion + ", which will destroy all old data");
	        
			// Upgrade the existing database to conform to the new version. Multiple 
			// previous versions can be handled by comparing _oldVersion and _newVersion
			// values.

			// The simplest case is to drop the old table and create a new one.
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// Create a new one.
			onCreate(_db);
	    }
	  }
	}