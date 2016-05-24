package com.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBInsertContact {
	public static final String KEY_MESSAGE="msg";
    private static final String TAG="DBAdapter";
	private static final String DATABASE_NAME="Message";
	private static final String DATABASE_TABLE="messagedetails";
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_CREATE=
			"create table messagedetails ( msg text not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db2;
	
	public DBInsertContact(Context ctx)
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
			db.execSQL("DROP TABLE IF EXISTS userdetails");
			
			onCreate(db);
		}
	}
	//---opens the database----
	public DBInsertContact open() throws SQLException
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
	public long insertUserDetails(String messag)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_MESSAGE,messag);
		return db2.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public Cursor getContactDetails()
	{
		return db2.query(DATABASE_TABLE, new String[]{KEY_MESSAGE},null, null, null,null, null);
	}
	//retrieve a particular contact
	public Cursor getParticularContact(String number) throws SQLException
	{
		Cursor mCursor=
				db2.query(DATABASE_TABLE, new String[]{KEY_MESSAGE},"groupid=?", new String[]{number}, null,null, null);
		return mCursor;
	}
	
	public Cursor getParticularContactUsingTime(String groupid,String time) throws SQLException
	{

		Cursor mCursor=
				db2.query(DATABASE_TABLE, new String[]{KEY_MESSAGE},"groupid=? AND time>?", new String[]{groupid,time}, null,null, null);
		return mCursor;
	}
	
	public void deleteIP()
	{
	
		db2.execSQL("DELETE FROM messagedetails");
	}
	
}
