package com.example.w24weatherappservice;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "weather.db";


    // Static Values for the table
    public static final String TABLE_NAME = "value_table";
    public static final String COLUMN_DATE = "value_date";
    public static final String COLUMN_LOCATION = "value_location";
    public static final String COLUMN_TEMP = "value_temp";
    public static final String COLUMN_DESC = "value_desc";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // this one only run when the db is created
    @Override
    public void onCreate(SQLiteDatabase db) {


        // Create the table
        String createQuery = "CREATE TABLE "
                + TABLE_NAME +
                " (value_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT, "
                + COLUMN_LOCATION + " TEXT, "
                + COLUMN_TEMP + " TEXT, "
                + COLUMN_DESC + " TEXT);";

        db.execSQL(createQuery);
    }

    // called when the Version number is updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // add into method
    public boolean addValue(String inputDate, String inputLocation, String inputTemp, String inputDesc) {

        SQLiteDatabase db = this.getWritableDatabase(); // this class that has the SQLiteOpenHelper

        // kinda an Intent for databases
        ContentValues contentValues = new ContentValues();

        // add the data
        contentValues.put(COLUMN_DATE, inputDate);
        contentValues.put(COLUMN_LOCATION, inputLocation);
        contentValues.put(COLUMN_TEMP, inputTemp);
        contentValues.put(COLUMN_DESC, inputDesc);

        // .insert returns the PK for the new row
        long value_id = db.insert(TABLE_NAME, null, contentValues);

        db.close();

        if (value_id == -1) {
            Log.d("SET", "addValue: Something didnt work");
            return false;
        } else {
            Log.d("SET", "addValue: All is well");
            return true;
        }

    }

    public ArrayList<WeatherBean> getValue(ArrayList<WeatherBean> valuesInDb) {

        SQLiteDatabase db = this.getWritableDatabase(); // this class that has the SQLiteOpenHelper


        String selectAll = "SELECT " + COLUMN_DATE + ", " + COLUMN_LOCATION + ", " + COLUMN_TEMP + ", " + COLUMN_DESC +" FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectAll, null);

            while (cursor.moveToNext()) {

                String value_first = cursor.getString(0);
                String value_second = cursor.getString(1);
                String value_third = cursor.getString(2);
                String value_fourth = cursor.getString(3);

                WeatherBean localBean = new WeatherBean();

                localBean.setDate(value_first);
                localBean.setLocation(value_second);
                localBean.setTemp(value_third);
                localBean.setDesc(value_fourth);

                valuesInDb.add(localBean);
            }

            cursor.close();
            db.close();

        return valuesInDb;
    }

    public void wholeTable() {

        SQLiteDatabase db = this.getWritableDatabase(); // this class that has the SQLiteOpenHelper


        String selectAll = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {


            while (cursor.moveToNext()) {

                int value_id = cursor.getInt(0);
                String value_first = cursor.getString(1);
                String value_second = cursor.getString(2);
                String value_third = cursor.getString(3);
                String value_fourth = cursor.getString(4);

            }

            cursor.close();
            db.close();
        }
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,null,null);
        db.execSQL("DELETE from "+ TABLE_NAME);
        db.close();
    }
}