package com.example.googlemapsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Alarm extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//context.startService(intent);
		    Intent i = new Intent();
	        i.setClassName("com.android.alert", "com.android.alert.MediaplayerActivity");
	        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(i);
	}

}
