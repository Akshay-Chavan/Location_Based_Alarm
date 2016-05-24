package com.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DbRinger {
	public static final String KEY_ALARM="uri";
    private static final String TAG="DBAdapter";
	private static final String DATABASE_NAME="Ringer";
	private static final String DATABASE_TABLE="alarm";
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_CREATE=
			"create table alarm ( uri text not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db2;
	
	public DbRinger(Context ctx)
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
			db.execSQL("DROP TABLE IF EXISTS alarm");
			
			onCreate(db);
		}
	}
	//---opens the database----
	public DbRinger open() throws SQLException
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
	public long insertUserDetails(String alarm)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ALARM,alarm);
		return db2.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public Cursor getContactDetails()
	{
		return db2.query(DATABASE_TABLE, new String[]{KEY_ALARM},null, null, null,null, null);
	}
	
	
	public void deleteAlarmTone()
	{
	
		db2.execSQL("DELETE FROM alarm");
	}
}
