package com.example.bandtest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bandtest.bean.CurrentData;

import java.util.ArrayList;

/**
 * Created by Harry on 2017/9/5.
 */

public class DataHelper extends SQLiteOpenHelper {
    private static DataHelper sInstance;
    private SQLiteDatabase db;

    public static synchronized DataHelper getsInstance(Context context){
        if (sInstance==null){
            sInstance=new DataHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    public DataHelper(Context context) {
        super(context, DataInfo.DATABASE_NAME, null, DataInfo.DATABASE_VERSION);
        Log.i("zgy","DataHelper---");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataInfo.CREATE_CURRENTDATA);
        db.execSQL(DataInfo.CREATE_HOURLYDATA);
        Log.i("zgy","onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i("zgy","onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS "+DataInfo.TABLE_CURRENT_DATA);
        db.execSQL("DROP TABLE IF EXISTS "+DataInfo.TABLE_HOURLY_DATA);
        onCreate(db);


    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("zgy","onDowngrade");
    }

    /**
     * insert current data
     * @param time
     * @param steps
     * @param calories
     * @param distance
     * @param mac
     */
    public void insertCurrentData(String time,int steps,int calories,float distance,String mac){
        db = getWritableDatabase();
        if (db.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataInfo.TIMEINMILLIS,time);
            contentValues.put(DataInfo.STEPCOUNT,steps);
            contentValues.put(DataInfo.CALORIES,calories);
            contentValues.put(DataInfo.DISTANCE,distance);
            contentValues.put(DataInfo.MACADDRESS,mac);
            db.insert(DataInfo.TABLE_CURRENT_DATA,null,contentValues);
            db.close();
        }
    }

    public ArrayList<CurrentData> getCurrentData(){
        ArrayList<CurrentData> lists = new ArrayList<>();
        db=getReadableDatabase();
        Cursor cursor = db.query(DataInfo.TABLE_CURRENT_DATA, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            CurrentData currentData = new CurrentData();
            currentData.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DataInfo.TIMEINMILLIS)));
            currentData.setStepCount(cursor.getInt(cursor.getColumnIndex(DataInfo.STEPCOUNT)));
            currentData.setCalories(cursor.getInt(cursor.getColumnIndex(DataInfo.CALORIES)));
            currentData.setDistance(cursor.getFloat(cursor.getColumnIndex(DataInfo.DISTANCE)));
            currentData.setMacAddress(cursor.getString(cursor.getColumnIndex(DataInfo.MACADDRESS)));
            lists.add(currentData);
        }
        cursor.close();
        db.close();
        return lists;
    }
}
