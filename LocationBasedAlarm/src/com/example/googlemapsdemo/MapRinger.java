package com.example.googlemapsdemo;



import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MapRinger extends Activity{
	MediaPlayer media;
	Button stop;
	
	MediaPlayer media1;
	Uri uri;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mapringer);
	        
	        stop=(Button)findViewById(R.id.ringerStop);
	       
	        media1=MediaPlayer.create(this, R.raw.sound);
	        
	        
	       
	        
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
