package com.example.googlemapsdemo;

import com.android.database.DBAlarmTone;
import com.android.database.DBInsertContact;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlarmRinger extends Activity{
	MediaPlayer media;
	Button stop;
	TextView text;
	boolean isRinger=false;
	DBAlarmTone alarm=new DBAlarmTone(this);
	DBInsertContact db=new DBInsertContact(this);
	MediaPlayer media1;
	Uri uri;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mapringer);
	        
	        stop=(Button)findViewById(R.id.ringerStop);
	        text=(TextView)findViewById(R.id.ringerText);
	        
	        
	        String alert = null;
	        
	        alarm.open();
	        Cursor cursor=alarm.getContactDetails();
	        int count=cursor.getCount();
	        if(count>0){
	        isRinger=true;
	        if(cursor.moveToFirst()){
	        	do{
	        		uri=Uri.parse(cursor.getString(0));
	        	}while(cursor.moveToNext());
	        }
	        }
	        alarm.close();
	        
	        db.open();
	        Cursor c=db.getContactDetails();
	        if(c.moveToFirst()){
	        	do{
	        		alert=c.getString(0);
	        	}while(c.moveToNext());
	        }
	        db.close();
	        if(isRinger){
	        	media1=MediaPlayer.create(this, uri);
	        }
	        else{
	        media1=MediaPlayer.create(this, R.raw.sound);
	        }
	        
	        //text.setText(alert);
	        
	        media1.setLooping(true);
	        media1.start();
	        
	        stop.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					media1.stop();
					finish();
				}
				
			});
	 }
}
