package com.example.googlemapsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.sax.StartElementListener;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		    
		    Intent i = new Intent();
	        i.setClassName("com.android.alert", "com.android.alert.AlarmRinger");
	        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(i);
	}

}
