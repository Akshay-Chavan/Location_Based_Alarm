package com.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DBAlarm {
	public static final String KEY_ALARM="alarm";
	public static final String ID="id";
	public static final String GROUP_ID="groupid";
	public static final String DATE="date";
	public static final String SUNDAY="sunday";
	public static final String MONDAY="monday";
	public static final String TUESDAY="tuesday";
	public static final String WENESDAY="wenesday";
	public static final String THURSDAY="thursday";
	public static final String FRIDAY="friday";
	public static final String SATURDAY="saturday";
    private static final String TAG="DBAdapter";
	private static final String DATABASE_NAME="AlarmTime";
	private static final String DATABASE_TABLE="alarmtime";
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_CREATE=
			"create table alarmtime ( alarm text not null,id Integer not null,groupid text not null,date text not null,sunday text not null," +
			"monday text not null,tuesday text not null,wenesday text not null,thursday text not null,friday text not null,saturday text not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db2;
	
	public DBAlarm(Context ctx)
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
			db.execSQL("DROP TABLE IF EXISTS alarmtime");
			
			onCreate(db);
		}
	}
	//---opens the database----
	public DBAlarm open() throws SQLException
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
	public long insertUserDetails(String alarm,int id,String groupid,String date,String sunday,String monday,String tuesday,String wenesday,String thursday,String friday,String saturday)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ALARM,alarm);
		initialValues.put(ID,id);
		initialValues.put(GROUP_ID,groupid);
		initialValues.put(DATE,date);
		initialValues.put(SUNDAY,sunday);
		initialValues.put(MONDAY,monday);
		initialValues.put(TUESDAY,tuesday);
		initialValues.put(WENESDAY,wenesday);
		initialValues.put(THURSDAY,thursday);
		initialValues.put(FRIDAY,friday);
		initialValues.put(SATURDAY,saturday);
		return db2.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public Cursor getAlarm()
	{
		return db2.query(DATABASE_TABLE, new String[]{KEY_ALARM,ID,GROUP_ID,DATE,SUNDAY,MONDAY,TUESDAY,WENESDAY,THURSDAY,FRIDAY,SATURDAY},null, null, null,null, null);
	}
	
	public Cursor getAlarmUsingId(String groupid)
	{
		return db2.query(DATABASE_TABLE, new String[]{KEY_ALARM,ID,GROUP_ID,DATE,SUNDAY,MONDAY,TUESDAY,WENESDAY,THURSDAY,FRIDAY,SATURDAY},"groupid=?", new String[]{groupid}, null,null, null);
	}
	
	public Cursor getAlarmUsingIdAndTime(String groupid,String alarm)
	{
		return db2.query(DATABASE_TABLE, new String[]{KEY_ALARM,ID,GROUP_ID,DATE,SUNDAY,MONDAY,TUESDAY,WENESDAY,THURSDAY,FRIDAY,SATURDAY},"alarm=? AND groupid=?", new String[]{alarm,groupid}, null,null, null);
	}
	
	public Cursor getAlarmUsingIdAndDate(String groupid,String alarm,String date)
	{
		return db2.query(DATABASE_TABLE, new String[]{KEY_ALARM,ID,GROUP_ID,DATE,SUNDAY,MONDAY,TUESDAY,WENESDAY,THURSDAY,FRIDAY,SATURDAY},"alarm=? AND groupid=? AND date=?", new String[]{alarm,groupid,date}, null,null, null);
	}
	
	public int deleteAlarmUsingId(String id,String groupid) throws SQLException
	{
		return db2.delete(DATABASE_TABLE, "id=? AND groupid=?", new String[]{id,groupid});
	}
	
	
	public void deleteAlarmTime()
	{
		db2.execSQL("DELETE FROM alarmtime");
	}
}
