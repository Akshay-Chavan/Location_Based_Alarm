package com.example.googlemapsdemo;

import java.util.ArrayList;

import com.android.database.DBAlarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowAlarm extends Activity{
	ListView list;
	ArrayList<String> array;
	DBAlarm db=new DBAlarm(this);
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.showalarm);
	        
	        final Intent intentAlarm = new Intent(this, Alarm.class);
	        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	        
	        list=(ListView)findViewById(R.id.listViewAlarm);
	        
	        setListContents();
	        
	        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int pos, long arg3) {
					String id = null;
					String alarm=array.get(pos);
					db.open();
					Cursor c=db.getAlarmUsingIdAndTime("alarm", alarm);
					if(c.moveToFirst()){
						do{
							id=c.getString(1);
						}while(c.moveToNext());
					}
					c.close();
					db.close();
					
					db.open();
					db.deleteAlarmUsingId(id, "alarm");
					db.close();
					
					removeAlarm(intentAlarm, alarmManager, Integer.parseInt(id));
					
				return false;	
				}
				});
	        
	 }
	 
	 public void setListContents(){
		 array=new ArrayList<String>();
	       db.open();
	       Cursor c=db.getAlarmUsingId("alarm");
	       if(c.getCount()>0){
	       if(c.moveToFirst()){
	    	   do{
	    		  array.add(c.getString(0));
	    	   }while(c.moveToNext());
	       }
	       }
	       else{
	    	   array.add("No alarms to display");
	       }
	       c.close();
	       db.close();
	       
	       list.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_expandable_list_item_1, array));
	 }
	 
	 public void removeAlarm(Intent intentAlarm,AlarmManager alarmManager,int id){
	    	alarmManager.cancel(PendingIntent.getBroadcast(this, id, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT));
	    	setListContents();
			Toast.makeText(getBaseContext(), "Alarm Removed", Toast.LENGTH_LONG).show();
	    }

}
