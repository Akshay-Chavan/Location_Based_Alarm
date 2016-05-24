package com.example.googlemapsdemo;

import java.util.Calendar;

import com.android.database.DBAlarm;
import com.android.database.DBAlarmTone;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetAlarm extends Activity{
	
	CheckBox setRepeating;
	TimePicker timePicker;
	Button setAlarm;
	Calendar cal;
	long setTime;
	int i;
	int hour,min;
	DBAlarm db=new DBAlarm(this);
	DBAlarmTone alarm=new DBAlarmTone(this);
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.setalarm);
	        
	        timePicker=(TimePicker)findViewById(R.id.timePickerAlarm);
	        setAlarm=(Button)findViewById(R.id.setAlarm);
	        setRepeating=(CheckBox)findViewById(R.id.setRepeating);
	        
	        final Intent intentAlarm = new Intent(this, Alarm.class);
	        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	        
	        setAlarm.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					db.open();
					Cursor c=db.getAlarm();
					if(c.getCount()>0){
						if(c.moveToFirst()){
							do{
								i=Integer.parseInt(c.getString(1));
							}while(c.moveToNext());
						}
					}
					else{
						i=0;
					}
					c.close();
					db.close();
					
					    cal=Calendar.getInstance();
					    hour=timePicker.getCurrentHour();
					    min=timePicker.getCurrentMinute();
				        cal.set(Calendar.HOUR_OF_DAY, hour);
				        cal.set(Calendar.MINUTE, min);
				        cal.set(Calendar.SECOND, 0);
				        setTime=cal.getTimeInMillis();
				        setAlarm(intentAlarm,alarmManager,i);
				        
				        db.open();
				        db.insertUserDetails(hour+":"+min, i+1, "alarm", "no", "no", "no", "no", "no", "no", "no", "no");
				        db.close();
				        
				        Toast.makeText(getBaseContext(), "Alarm Set", Toast.LENGTH_LONG).show();
				}
			});
	 }
	 
	  public boolean onCreateOptionsMenu(Menu menu) {
			menu.add(0, 0, 0, "Show Alarms");
			menu.add(0, 1, 1, "Set Tone");
			return true;
		}
	    
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case 0:
				startActivity(new Intent(getBaseContext(), ShowAlarm.class));
				break;
				
			case 1:
				Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
				startActivityForResult(intent, 5);
				break;
			}
			return true; 
	}
	    
	    @Override
		 protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent)
		 {
		     if (resultCode == Activity.RESULT_OK && requestCode == 5)
		     {
		          Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
	             
		          Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
		          if (uri != null)
		          {
		              alarm.open();
		              alarm.deleteAlarmTone();
		              alarm.insertUserDetails(uri.toString());
		              alarm.close();
		          }
		         
		      }            
		  }
	    
	    public void setAlarm(Intent intentAlarm,AlarmManager alarmManager,int id){
	    	alarmManager.set(AlarmManager.RTC_WAKEUP, setTime, PendingIntent.getBroadcast(this, id+1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
	    	if(setRepeating.isChecked()){
	    	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, setTime, AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(this, id+1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
	    	}
	    	else{
	    		alarmManager.set(AlarmManager.RTC_WAKEUP, setTime, PendingIntent.getBroadcast(this, id+1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
	    	}
	    }
	    
	    }
