package com.blt.helloworld.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListActivity;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.blt.helloworld.db.AuditLogDBHelper;
import com.blt.helloworld.model.EventType;

public class EventsActivity extends ListActivity {

	private AuditLogDBHelper dbHelper;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Cursor dataCursor = getDBHelper().getAuditData();
        startManagingCursor(dataCursor);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter (
                this, 
                R.layout.events,  
                dataCursor,       
                new String[] {"event_type", "timestamp"},      
                new int[] { R.id.eventType, R.id.timeStamp});  
        
       adapter.setViewBinder(new AuditViewBinder());

        // Bind to our new adapter.
        setListAdapter(adapter);
    }

		
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	if (dbHelper != null)
    		dbHelper.close();
    	
    }
    
    private AuditLogDBHelper getDBHelper() {
    	if (dbHelper == null) {
            dbHelper = new AuditLogDBHelper(this.getApplicationContext());
            dbHelper.open();
    	}
    	return dbHelper;
    }
    
    
    // Handles formatting for columns in the view.
    class AuditViewBinder implements SimpleCursorAdapter.ViewBinder {

		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			
			if (view instanceof TextView) {
				if ("event_type".equalsIgnoreCase(cursor.getColumnName(columnIndex))) {
					return handleEventDisplay((TextView)view, cursor, columnIndex);
				}
				if ("timestamp".equalsIgnoreCase(cursor.getColumnName(columnIndex))) {
					return handleTimestampDisplay((TextView)view, cursor, columnIndex);
				}			
			}
			return false;
		}
		
		private boolean handleEventDisplay(TextView view, Cursor cursor, int columnIndex) {
			String value = cursor.getString(columnIndex);
			EventType e = EventType.find(value);
			if (e != null) {

				switch (e) {
					case APP_STARTUP :  
						view.setTextColor(ColorStateList.valueOf(Color.rgb(255, 255, 0)));
						break;
					case BUTTON_PRESSED :  
						view.setTextColor(ColorStateList.valueOf(Color.rgb(0, 128, 255)));
						break;					
				}
				view.setText(value);
				
				return true;
					
			}
			return false;
			
		}
		
		private boolean handleTimestampDisplay(TextView view, Cursor cursor, int columnIndex) {
			long timeval = cursor.getLong(columnIndex);
			
			TextView tv = (TextView)view;
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss-z '@' MM/dd/yyyy");
			tv.setText(formatter.format(new Date(timeval)));
			
			return true;
			
		}
    	
    	
    }
        
}
