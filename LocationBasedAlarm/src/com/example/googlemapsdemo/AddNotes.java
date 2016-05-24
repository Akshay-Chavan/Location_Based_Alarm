package com.example.googlemapsdemo;

import java.util.ArrayList;

import com.android.database.DBNote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddNotes extends Activity{
	Button addNote;
	EditText txtPlace,txtTask;
	DBNote db=new DBNote(this);
	ArrayList<String> place=new ArrayList<String>();
	ArrayList<String> task=new ArrayList<String>();
	ArrayList<String> array=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnotes);
		
		addNote=(Button)findViewById(R.id.addNote);
		txtPlace=(EditText)findViewById(R.id.txtPlace);
		txtTask=(EditText)findViewById(R.id.txtTask);
		
		addNote.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			if(txtPlace.getText().toString().equals("")||txtTask.getText().toString().equals("")){
				Toast.makeText(getBaseContext(), "Pls enter all details",Toast.LENGTH_LONG).show();
			}
			else{
				db.open();
				long i=db.addNote(txtPlace.getText().toString(), txtTask.getText().toString());
				txtPlace.setText("");
				txtTask.setText("");
				if(i>0){
					Toast.makeText(getBaseContext(), "Note added successfully", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(getBaseContext(), "Error adding note", Toast.LENGTH_LONG).show();
				}
				db.close();
			}
			}
		});
	}
}
