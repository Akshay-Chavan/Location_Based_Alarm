package com.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DBNote {
	public static final String PLACE="place";
	public static final String TASK="task";
    private static final String TAG="DBAdapter";
	private static final String DATABASE_NAME="NOTES";
	private static final String DATABASE_TABLE="note";
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_CREATE=
			"create table note ( place text not null,task text not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db2;
	
	public DBNote(Context ctx)
	{
		this.context=ctx;
		DBHelper=new DatabaseHelper(context);
	}
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		public DatabaseHelper(Context context) {
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try
			{
				db.execSQL(DATABASE_CREATE);
			}catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		@Override
		public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
		{
			Log.w(TAG,"Upgrading database from version"+oldVersion+"to"
					+newVersion+",which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS note");
			
			onCreate(db);
		}
	}
	//---opens the database----
	public DBNote open() throws SQLException
	{
		db2=DBHelper.getWritableDatabase();
		return this;
	}
			
		//---closes the database---
	public void close()
	{
		DBHelper.close();
	}
	//inserts into database
	public long addNote(String place,String task)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(PLACE,place);
		initialValues.put(TASK,task);
		return db2.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public Cursor getNoteDetails()
	{
		return db2.query(DATABASE_TABLE, new String[]{PLACE,TASK},null, null, null,null, null);
	}
	
	public int deleteParticularNote(String place,String task){
		return db2.delete(DATABASE_TABLE, "place=? AND task=?", new String[]{place,task});
	}
	
	public void deleteNote()
	{
	
		db2.execSQL("DELETE FROM note");
	}
}
