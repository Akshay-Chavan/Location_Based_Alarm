package com.example.googlemapsdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectType extends Activity{
	Button reminder,alarm,locationAlert,multipleLocationAlert;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.selecttype);
	        
	        reminder=(Button)findViewById(R.id.reminder);
	        alarm=(Button)findViewById(R.id.alarm);
	        locationAlert=(Button)findViewById(R.id.locationAlert);
	        multipleLocationAlert=(Button)findViewById(R.id.multipleLocationAlert);
	        
	        reminder.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getBaseContext(), AlarmAlertActivity.class));
				}
			});
	        
	        alarm.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getBaseContext(), SetAlarm.class));
				}
			});
	        
	        locationAlert.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getBaseContext(), MainActivity.class));
				}
			});
	        
	        multipleLocationAlert.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getBaseContext(), MultipleProximityActivity.class));
				}
			});
	 }
}
