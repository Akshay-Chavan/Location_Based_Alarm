package com.example.googlemapsdemo;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity{

	 GoogleMap gm;
	 
	 LocationManager locationManager;
	 PendingIntent pendingIntent;
	 SharedPreferences sharedPreferences;
	 int locationCount;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		

		 // Getting Google Play availability status
       int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

       // Showing status
       if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

           int requestCode = 10;
           Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
           dialog.show();

       }else { // Google Play Services are available        	

           // Getting reference to the SupportMapFragment of activity_main.xml
           SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

           // Getting GoogleMap object from the fragment
           gm = fm.getMap();

           // Enabling MyLocation Layer of Google Map
           gm.setMyLocationEnabled(true);           
           
           
           // Getting LocationManager object from System Service LOCATION_SERVICE
           locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
           
           
           // Opening the sharedPreferences object
   		sharedPreferences = getSharedPreferences("location", 0);
   		
   	// Getting number of locations already stored
        locationCount = sharedPreferences.getInt("locationCount", 0); 
   		
   		// Getting stored latitude if exists else return 0
   		String lat = sharedPreferences.getString("lat", "0");
   		
   		// Getting stored longitude if exists else return 0
   		String lng = sharedPreferences.getString("lng", "0");
   		
   		// Getting stored zoom level if exists else return 0
   		String zoom = sharedPreferences.getString("zoom", "0");
   		
   		
   		
   		// If coordinates are stored earlier
   		if(!lat.equals("0")){
   			
   			// Drawing circle on the map
   			drawCircle(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
   			
   			// Drawing marker on the map
   			drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
   			
   			// Moving CameraPosition to previously clicked position
   			gm.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));
   			
   			// Setting the zoom level in the map
   			gm.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom))); 
   		}
       
		
       
       gm.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				
				// Removes the existing marker from the Google Map
				gm.clear();		
									
				// Drawing marker on the map
				drawMarker(point);
				
				// Drawing circle on the map
				drawCircle(point);					
				
		        // This intent will call the activity ProximityActivity
		        Intent proximityIntent = new Intent(getBaseContext(), ProximityActivity.class);				
				
		        // Creating a pending intent which will be invoked by LocationManager when the specified region is
		        // entered or exited
		        pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent,Intent.FLAG_ACTIVITY_NEW_TASK);			        
		        
		        // Setting proximity alert 
		        // The pending intent will be invoked when the device enters or exits the region 20 meters
		        // away from the marked point
		        // The -1 indicates that, the monitor will not be expired
		        locationManager.addProximityAlert(point.latitude, point.longitude, 20, -1, pendingIntent);	
		        
		        /** Opening the editor object to write data to sharedPreferences */
		        SharedPreferences.Editor editor = sharedPreferences.edit();

		        /** Storing the latitude of the current location to the shared preferences */
		        editor.putString("lat", Double.toString(point.latitude));
		        
		        /** Storing the longitude of the current location to the shared preferences */
		        editor.putString("lng", Double.toString(point.longitude));
		        
		        /** Storing the zoom level to the shared preferences */
		        editor.putString("zoom", Float.toString(gm.getCameraPosition().zoom));		        

		        /** Saving the values stored in the shared preferences */
		        editor.commit();		        
		        
		        Toast.makeText(getBaseContext(), "Proximity Alert is added", Toast.LENGTH_SHORT).show();			        
		        
			}
		});    
       
       gm.setOnMapLongClickListener(new OnMapLongClickListener() {				
			@Override
			public void onMapLongClick(LatLng point) {
				Intent proximityIntent = new Intent(getBaseContext(), ProximityActivity.class);
				
				pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent,Intent.FLAG_ACTIVITY_NEW_TASK);
				
				// Removing the proximity alert					
				locationManager.removeProximityAlert(pendingIntent);
				
				// Removing the marker and circle from the Google Map
				gm.clear();
				
				// Opening the editor object to delete data from sharedPreferences
		        SharedPreferences.Editor editor = sharedPreferences.edit();
		        
		        // Clearing the editor
		        editor.clear();
				
		        // Committing the changes
				editor.commit();
				
				Toast.makeText(getBaseContext(), "Proximity Alert is removed", Toast.LENGTH_LONG).show();
			}
		}); 
       
       }
	}	
	

private void drawMarker(LatLng point){
	// Creating an instance of MarkerOptions
	MarkerOptions markerOptions = new MarkerOptions();					
	
	// Setting latitude and longitude for the marker
	markerOptions.position(point);
	
	// Adding marker on the Google Map
	gm.addMarker(markerOptions);
	
}


private void drawCircle(LatLng point){
	
	// Instantiating CircleOptions to draw a circle around the marker
	CircleOptions circleOptions = new CircleOptions();
	
	// Specifying the center of the circle
	circleOptions.center(point);
	
	// Radius of the circle
	circleOptions.radius(20);
	
	// Border color of the circle
	circleOptions.strokeColor(Color.BLACK);
	
	// Fill color of the circle
	circleOptions.fillColor(0x30ff0000);
	
	// Border width of the circle
	circleOptions.strokeWidth(2);
	
	// Adding the circle to the GoogleMap
	gm.addCircle(circleOptions);
	
}	

	
   		
   		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
