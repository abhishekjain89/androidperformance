package com.num.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ThroughputDataSource {
	// Database fields
	private SQLiteDatabase database;
	private ThroughputDatabaseHelper dbHelper;
	private String[] allColumns = { ThroughputDatabaseHelper.COLUMN_ID,
			ThroughputDatabaseHelper.COLUMN_DOWNLINK, ThroughputDatabaseHelper.COLUMN_UPLINK };

	public ThroughputDataSource(Context context) {
		dbHelper = new ThroughputDatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public ThroughputData createThroughput(String downlink, String uplink) {
		ContentValues values = new ContentValues();
		values.put(ThroughputDatabaseHelper.COLUMN_DOWNLINK, downlink);
		values.put(ThroughputDatabaseHelper.COLUMN_UPLINK, uplink);
		long insertId = database.insert(ThroughputDatabaseHelper.TABLE_THROUGHPUT, null,
				values);
		Cursor cursor = database.query(ThroughputDatabaseHelper.TABLE_THROUGHPUT,
				allColumns, ThroughputDatabaseHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		ThroughputData newThroughputData = cursorToThroughputData(cursor);
		cursor.close();
		return newThroughputData;
	}

	public void deleteThroughputData(ThroughputData throughput) {
		long id = throughput.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(ThroughputDatabaseHelper.TABLE_THROUGHPUT, ThroughputDatabaseHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<ThroughputData> getAllThroughputData() {
		List<ThroughputData> throughputs = new ArrayList<ThroughputData>();

		Cursor cursor = database.query(ThroughputDatabaseHelper.TABLE_THROUGHPUT,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ThroughputData throughput = cursorToThroughputData(cursor);
			throughputs.add(throughput);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return throughputs;
	}

	private ThroughputData cursorToThroughputData(Cursor cursor) {
		ThroughputData throughput = new ThroughputData();
		throughput.setId(cursor.getLong(0));
		throughput.setDownlink(cursor.getString(1));
		throughput.setUplink(cursor.getString(2));
		return throughput;
	}
}
