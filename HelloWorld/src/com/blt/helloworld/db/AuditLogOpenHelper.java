package com.blt.helloworld.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AuditLogOpenHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "auditlog";
		
	private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME 
			+ " (event_type text, timestamp integer)";
	
			
	public AuditLogOpenHelper(Context context) {
		super(context, "audit_db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table " + TABLE_NAME);
		db.execSQL(CREATE_TABLE);
	}
	
	
}
