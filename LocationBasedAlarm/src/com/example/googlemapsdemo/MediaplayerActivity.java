package com.example.googlemapsdemo;

import java.util.Timer;
import java.util.TimerTask;

import com.android.database.DBAlarmTone;
import com.android.database.DBInsertContact;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MediaplayerActivity extends Activity{
	int flag;
	MediaPlayer media1;
	TextView text;
	Uri uri;
	DBInsertContact db=new DBInsertContact(this);
	DBAlarmTone alarm=new DBAlarmTone(this);
	boolean isRinger=false,isStarted=false;
	Handler handler=new Handler();
//	Timer timer=new Timer();
//	TimerTask doAsynchronousTask;
	Thread t;
   
	public MediaplayerActivity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MediaplayerActivity(int flag) {
		super();
		this.flag = flag;
	}
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmalert);
        
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
        text=(TextView)findViewById(R.id.textView1);
        
        text.setText(alert);
        
        Button stop=(Button)findViewById(R.id.stop);
        Button snooze=(Button)findViewById(R.id.snooze);
        media1.setLooping(true);
        media1.start();
        
        //startService(new Intent(getBaseContext(), MyServic.class));
       
        
        stop.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				media1.stop();
				finish();
			}
			
		});
        
        snooze.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				if(isStarted){
//					System.out.println("started");
//					Toast.makeText(getBaseContext(), "started", Toast.LENGTH_LONG).show();
//					t.stop();
//					isStarted=false;
//				}
//				media1.pause();
//				t.start();
				media1.pause();
				 TimerTask timer=new TimerTask() {
						
						@Override
						public void run() {
							handler.post(new Runnable() {
			                    public void run() {

			                        try {
			                        
			                        media1.start();

			                        } catch (Exception e) {
			                            // TODO Auto-generated catch block
			                        }

			                    }
			                });
						}
					};
			        
			        new Timer().schedule(timer, 10000);
			}
		});
     
 

    }
    
  
}