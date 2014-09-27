package com.blt.helloworld.db;

import java.util.GregorianCalendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.blt.helloworld.model.EventType;

public class AuditLogDBHelper {
		
	private SQLiteDatabase db;
	private AuditLogOpenHelper dbHelper;
	
	public AuditLogDBHelper(Context context) {
		dbHelper = new AuditLogOpenHelper(context);
	}	

	public void open() { 
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		db.close();
	}
	public long recordEvent(EventType event) {
		
		long timestamp = GregorianCalendar.getInstance().getTimeInMillis();
		
    	SQLiteStatement stmt = db.compileStatement("insert into " + AuditLogOpenHelper.TABLE_NAME + " (event_type, timestamp)  VALUES (?,?)");
    	stmt.bindString(1, event.toString());
    	stmt.bindLong(2, timestamp);
    	long rowsInserted = stmt.executeInsert();
    	stmt.close();
    	
    	return rowsInserted;
	}
	
	public Cursor getAuditData() {
		
    	Cursor cursor = db.rawQuery("select rowid _id, event_type, timestamp from " + AuditLogOpenHelper.TABLE_NAME + " order by timestamp", null);
    	cursor.moveToFirst();
    	return cursor;
	}
	
}