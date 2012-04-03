package com.blt.helloworld.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.blt.helloworld.db.AuditLogDBHelper;

public class HelloActivity extends Activity {
	
	private Intent auditLogIntent;
	private AuditLogDBHelper dbHelper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helloworld);
        Button button = (Button)findViewById(R.id.ok);
        button.setOnClickListener( new ButtonListener());
        auditLogIntent = new Intent(this, EventsActivity.class);	
    	getDBHelper().recordEvent("App Started");
        
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
    
    class ButtonListener implements OnClickListener {
    	
    	public void  onClick(View aView) {
    		getDBHelper().recordEvent("Button Pressed");    		
    		startActivity(auditLogIntent);
    	}
    	    	
    }
}

