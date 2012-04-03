package com.blt.helloworld.activities;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import com.blt.helloworld.db.AuditLogDBHelper;

public class EventsActivity extends ListActivity {

	private AuditLogDBHelper dbHelper;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("=== EventsActivity:onCreate()");	
        
        Cursor dataCursor = getDBHelper().getAuditData();
        startManagingCursor(dataCursor);

        ListAdapter adapter = new SimpleCursorAdapter (
                this, 
                R.layout.events,  
                dataCursor,       
                new String[] {"event_type", "time_text"},      
                new int[] { R.id.eventType, R.id.timeStamp});  
        

        // Bind to our new adapter.
        setListAdapter(adapter);
    }

	
	@Override
	public void onStart() {
		super.onStart();
		
	}
	
    private AuditLogDBHelper getDBHelper() {
    	if (dbHelper == null) {
            dbHelper = new AuditLogDBHelper(this.getApplicationContext());
            dbHelper.open();
    	}
    	return dbHelper;
    }
    
    public void onDestroy() {
    	super.onDestroy();
    	dbHelper.close();
    	
    }
        
}
