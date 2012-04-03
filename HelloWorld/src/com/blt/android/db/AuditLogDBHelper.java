package com.blt.android.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

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
	public long recordEvent(String eventName) {
		
		Date now = GregorianCalendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
    	SQLiteStatement stmt = db.compileStatement("insert into " + AuditLogOpenHelper.TABLE_NAME + " (event_type, time_text)  VALUES (?,?)");
    	stmt.bindString(1, eventName);
    	stmt.bindString(2, formatter.format(now));
    	long rowsInserted = stmt.executeInsert();
    	stmt.close();
    	
    	return rowsInserted;
	}
	
	public Cursor getAuditData() {
		
    	Cursor cursor = db.rawQuery("select rowid _id, event_type, time_text from " + AuditLogOpenHelper.TABLE_NAME + " order by time_text desc", null);
    	cursor.moveToFirst();
    	return cursor;
	}
	
}