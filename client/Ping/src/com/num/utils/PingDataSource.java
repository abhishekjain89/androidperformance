package com.num.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.num.helpers.PingDatabaseHelper;
import com.num.models.Ping;
import com.num.models.PingData;

public class PingDataSource {

	// Database fields
	private SQLiteDatabase database;
	private PingDatabaseHelper dbHelper;
	private String[] allColumns = { 
			PingDatabaseHelper.COLUMN_ID,
			PingDatabaseHelper.COLUMN_TIME, 
			PingDatabaseHelper.COLUMN_AVG, 
			PingDatabaseHelper.COLUMN_MIN, 
			PingDatabaseHelper.COLUMN_MAX, 
			PingDatabaseHelper.COLUMN_STD, 
			PingDatabaseHelper.COLUMN_SRCIP, 
			PingDatabaseHelper.COLUMN_DSTIP, 
			PingDatabaseHelper.COLUMN_CONNECTION};

	public PingDataSource(Context context) {
		dbHelper = new PingDatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public void createPing(Ping p, String connectionType) {
		ContentValues value = new ContentValues();		
	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time = sdf.format(new Date());		
		value.put(PingDatabaseHelper.COLUMN_TIME, time);
		value.put(PingDatabaseHelper.COLUMN_AVG, p.getMeasure().getAverage());
		value.put(PingDatabaseHelper.COLUMN_MIN, p.getMeasure().getMin());
		value.put(PingDatabaseHelper.COLUMN_MAX, p.getMeasure().getMax());
		value.put(PingDatabaseHelper.COLUMN_STD, p.getMeasure().getStddev());
		value.put(PingDatabaseHelper.COLUMN_SRCIP, p.getSrcIp().substring(1));
		value.put(PingDatabaseHelper.COLUMN_DSTIP, p.getDst().getIp());
		value.put(PingDatabaseHelper.COLUMN_CONNECTION, connectionType);
		long insertId = database.insert(PingDatabaseHelper.TABLE_PING, null, value);
		Cursor cursor = database.query(PingDatabaseHelper.TABLE_PING,
				allColumns, PingDatabaseHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		PingData newPingData = cursorToPingData(cursor);
		cursor.close();
	}

	public void deletePingData(PingData ping) {
		long id = ping.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(PingDatabaseHelper.TABLE_PING, PingDatabaseHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<PingData> getAllPingData() {
		List<PingData> pings = new ArrayList<PingData>();

		Cursor cursor = database.query(PingDatabaseHelper.TABLE_PING,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PingData ping = cursorToPingData(cursor);
			pings.add(ping);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return pings;
	}

	private PingData cursorToPingData(Cursor cursor) {
		PingData ping = new PingData();
		ping.setId(cursor.getLong(0));
		ping.setTime(cursor.getString(1));
		ping.setAvg(cursor.getFloat(2));
		ping.setMin(cursor.getFloat(3));
		ping.setMax(cursor.getFloat(4));
		ping.setStd(cursor.getFloat(5));
		ping.setSrcip(cursor.getString(6));
		ping.setDstip(cursor.getString(7));
		ping.setConnection(cursor.getString(8));
		return ping;
	}
}
