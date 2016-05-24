package com.example.googlemapsdemo;

import java.util.ArrayList;

import com.android.database.DBNote;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowNotes extends Activity{
	ListView list;
	DBNote db=new DBNote(this);
	ArrayList<String> place;
	ArrayList<String> task;
	ArrayList<String> array;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.shownotes);
	    
	    list=(ListView)findViewById(R.id.addNotesList);
	    
	    setListContents();
	    
	    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				db.open();
				long i=db.deleteParticularNote(place.get(pos), task.get(pos));
				if(i>0){
					Toast.makeText(getBaseContext(), "Note removed successfully", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(getBaseContext(), "Error removing note", Toast.LENGTH_LONG).show();
				}
				db.close();
				
				setListContents();
				
			return false;	
			}
			});
	    
	 }
	 
	 public void setListContents(){
		 array=new ArrayList<String>();
		 task=new ArrayList<String>();
		 place=new ArrayList<String>();
		 db.open();
		    Cursor c=db.getNoteDetails();
		    if(c.moveToFirst()){
		    	do{
		    		place.add(c.getString(0));
		    		task.add(c.getString(1));
		    		array.add("Place:-"+c.getString(0)+"\nTask:-"+c.getString(1));
		    	}while(c.moveToNext());
		    }
		    c.close();
		    db.close();
	       
	       list.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_expandable_list_item_1, array));
	 }
}
