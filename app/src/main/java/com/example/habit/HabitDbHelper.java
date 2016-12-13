package com.example.habit;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;

import static android.database.DatabaseUtils.queryNumEntries;
import static com.example.habit.HabitTable.*;

/**
 * Created by nicoleenos on 12/12/16.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "habit_list.db";
    public static final String TABLE_NAME = "habits_table";
    public static final String _ID = "ID";
    public static final String COLUMN2 = "Habit";
    public static final String COLUMN3 = "Time";
    public static final String COLUMN4 = "Frequency";
    //public static final String COLUMN5 = "Completed";
    public static final int database_version = 1;
    public String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN2+" TEXT,"+COLUMN3+" TEXT,"+COLUMN4+" TEXT);";

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, database_version);
        //SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Database operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        //Log.d("Database operations", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String habit_name, String time, String frequency){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put(COLUMN2, habit_name);
        CV.put(COLUMN3, time);
        CV.put(COLUMN4, frequency);
        long valid = db.insert(TABLE_NAME, null ,CV);
        if (valid == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public int deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }

    public long getLength()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return queryNumEntries(db, TABLE_NAME);
    }
}
