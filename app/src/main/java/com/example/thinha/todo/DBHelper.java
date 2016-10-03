package com.example.thinha.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ThiNha on 9/29/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "ItemList";
    public static final String COLUMN_ID = "id";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+ COLUMN_ID + " INTEGER,name VARCHAR,hour INTEGER,minute INTEGER,second INTEGER,date INTEGER,month INTEGER,YEAR INTEGER)");

    }
    public boolean insertContact  (int id,String name, int hour, int minute, int second,int date, int month, int year)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("hour", hour);
        contentValues.put("minute", minute);
        contentValues.put("second", second);
        contentValues.put("date", date);
        contentValues.put("month", month);
        contentValues.put("year", year);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME , null );
        return res;
    }

    public void parseData(Cursor c)
    {
        if (c.getCount()==0)
        {
            return;
        }
        c.moveToFirst();
        do{
            MainActivity.arrayItemID.add(Integer.valueOf(c.getString(0)));
            MainActivity.nameArrayList.add(c.getString(1));
            MainActivity.arrayHour.add(c.getString(2));
            MainActivity.arrayMinute.add(c.getString(3));
            MainActivity.arraySecond.add(c.getString(4));
            MainActivity.arrayDate.add(c.getString(5));
            MainActivity.arrayMonth.add(c.getString(6));
            MainActivity.arrayYear.add(c.getString(7));
        }while (c.moveToNext());
    }

    public int getLastId()
    {
        int lastId= -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME , null );
       if(res.getCount()> 0)
       {
           res.moveToLast();
           lastId=Integer.valueOf(res.getString(0));
       }
        return lastId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean updateContact (int id, String name, int hour, int minute, int second,int date, int month, int year)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("hour", hour);
        contentValues.put("minute", minute);
        contentValues.put("second", second);
        contentValues.put("date", date);
        contentValues.put("month", month);
        contentValues.put("year", year);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null )>0;
    }
    public boolean deleteContact (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,COLUMN_ID+ "=" + id, null)>0;
    }
}
