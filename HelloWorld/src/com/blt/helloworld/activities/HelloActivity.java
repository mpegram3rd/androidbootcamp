package com.blt.helloworld.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.blt.helloworld.db.AuditLogDBHelper;
import com.blt.helloworld.model.EventType;

public class HelloActivity extends Activity {
	
	private Intent auditLogIntent;
	private AuditLogDBHelper dbHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helloworld);
        Button button = (Button)findViewById(R.id.ok);
        button.setOnClickListener( new ButtonListener());
        auditLogIntent = new Intent(this, EventsActivity.class);	
    	getDBHelper().recordEvent(EventType.APP_STARTUP);
        
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

    // Behavior for button press event.
    class ButtonListener implements OnClickListener {
    	
    	public void  onClick(View aView) {
    		getDBHelper().recordEvent(EventType.BUTTON_PRESSED);    		
    		startActivity(auditLogIntent);
    	}
    	    	
    }
}

