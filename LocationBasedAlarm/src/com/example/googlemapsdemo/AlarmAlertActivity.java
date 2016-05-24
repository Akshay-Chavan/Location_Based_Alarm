package com.example.googlemapsdemo;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.android.database.DBAlarm;
import com.android.database.DBAlarmTone;
import com.android.database.DBInsertContact;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmAlertActivity extends Activity {
	DatePicker datepicker;
	TimePicker time;
	long setTime;
	Button setAlarm;
	EditText editText;
	Uri chosenRingtone;
	DBInsertContact db=new DBInsertContact(this);
	DBAlarmTone alarm=new DBAlarmTone(this);
	DBAlarm data=new DBAlarm(this);
	int i;
	 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setAlarm=(Button)findViewById(R.id.button1);
        editText=(EditText)findViewById(R.id.editText1);
        datepicker=(DatePicker)findViewById(R.id.datePicker1);
        time=(TimePicker)findViewById(R.id.timePicker1);
        
         
        final Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        
        setAlarm.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				
				    if(editText.getText().toString().equals("")){
				    	Toast.makeText(getBaseContext(), "Please enter alert message", Toast.LENGTH_LONG).show();
				    }
				    
				    else{
				    db.open();
				    db.insertUserDetails(editText.getText().toString());
				    db.close();
				    
				    data.open();
					Cursor c=data.getAlarm();
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
					data.close();
				    Calendar calendar=Calendar.getInstance();
				    calendar.clear();
				    int year=datepicker.getYear();
				    int month=datepicker.getMonth();
				    int day=datepicker.getDayOfMonth();
				    int hour=time.getCurrentHour();
				    int min=time.getCurrentMinute();
			        calendar.set(Calendar.YEAR, year);
			        calendar.set(Calendar.MONTH, month);
			        calendar.set(Calendar.DAY_OF_MONTH, day);
			        calendar.set(Calendar.HOUR_OF_DAY, hour);
			        calendar.set(Calendar.MINUTE, min);
			        calendar.set(Calendar.SECOND, 0);
			        setTime=calendar.getTimeInMillis();
			        setAlarm(intentAlarm,alarmManager,i);
			        
			        data.open();
			        data.insertUserDetails(hour+":"+min, i+1, "reminder", day+"/"+month+"/"+year, "no", "no", "no", "no", "no", "no", "no");
			        data.close();
			        editText.setText("");
			        
			        Toast.makeText(getBaseContext(), "Reminder Set", Toast.LENGTH_LONG).show();
				    }
				
			}
		});
        
        
    }
    
    
    public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Show Reminders");
		menu.add(0, 1, 1, "Set Tone");
		return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			startActivity(new Intent(getBaseContext(), ShowReminder.class));
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
    	
    }
    
    public void removeAlarm(Intent intentAlarm,AlarmManager alarmManager){
    	alarmManager.cancel(PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT));
		Toast.makeText(getBaseContext(), "Alarm Removed", Toast.LENGTH_LONG).show();
    }
}
