package com.example.bandtest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bandtest.bean.BandTestData;
import com.example.bandtest.bean.CurrentData;
import com.example.bandtest.bean.HourlyData;
import com.example.bandtest.bean.RemindData;

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
        db.execSQL(DataInfo.CREATE_BANDTESTDATA);
        db.execSQL(DataInfo.CREATE_REMINDDATA);
        Log.i("zgy","onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i("zgy","onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS "+DataInfo.TABLE_CURRENT_DATA);
        db.execSQL("DROP TABLE IF EXISTS "+DataInfo.TABLE_HOURLY_DATA);
        db.execSQL("DROP TABLE IF EXISTS "+DataInfo.TABLE_BAND_TEST_DATA);
        db.execSQL("DROP TABLE IF EXISTS "+DataInfo.TABLE_REMIND_DATA );
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
    public void insertCurrentData(long time,int steps,int calories,float distance,String mac){
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

    /**
     * get current data
     * @return
     */
    public ArrayList<CurrentData> getCurrentData(){
        ArrayList<CurrentData> lists = new ArrayList<>();
        db=getReadableDatabase();
        Cursor cursor = db.query(DataInfo.TABLE_CURRENT_DATA, null, null, null, null, null, DataInfo.TIMEINMILLIS);
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

    /**
     * inset hourly data
     * @param time
     * @param steps
     * @param calories
     * @param distance
     * @param hearRate
     * @param bloodOxygen
     * @param bloodPresurre_high
     * @param bloodPresurre_low
     * @param shallow
     * @param deep
     * @param sleep
     * @param wakeupTimes
     * @param mac
     */
    public void insertHourlyData(long time,int steps,int calories,float distance,int hearRate,
                                 int bloodOxygen,int bloodPresurre_high,int bloodPresurre_low,long shallow,long deep,long sleep,int wakeupTimes, String mac){
        db = getWritableDatabase();
        if (db.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataInfo.TIMEINMILLIS,time);
            contentValues.put(DataInfo.STEPCOUNT,steps);
            contentValues.put(DataInfo.CALORIES,calories);
            contentValues.put(DataInfo.DISTANCE,distance);
            contentValues.put(DataInfo.HEARTRATE,hearRate);
            contentValues.put(DataInfo.BLOODOXYGEN,bloodOxygen);
            contentValues.put(DataInfo.BLOODPRESSUREHIGH,bloodPresurre_high);
            contentValues.put(DataInfo.BLOODPRESSURELOW,bloodPresurre_low);
            contentValues.put(DataInfo.SHALLOWSLEEPTIME,shallow);
            contentValues.put(DataInfo.DEEPSLEEPTIME,deep);
            contentValues.put(DataInfo.SLEEPTIME,sleep);
            contentValues.put(DataInfo.WAKEUPTIMES,wakeupTimes);
            contentValues.put(DataInfo.MACADDRESS,mac);
            db.insert(DataInfo.TABLE_HOURLY_DATA,null,contentValues);
            db.close();
        }
    }

    /**
     * get hourly data
     * @return
     */
    public ArrayList<HourlyData> getHourlyData(){
        ArrayList<HourlyData> lists = new ArrayList<>();
        db=getReadableDatabase();
        Cursor cursor = db.query(DataInfo.TABLE_HOURLY_DATA, null, null, null, null, null, DataInfo.TIMEINMILLIS);
        while (cursor.moveToNext()){
            HourlyData hourlyData = new HourlyData();
            hourlyData.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DataInfo.TIMEINMILLIS)));
            hourlyData.setStepCount(cursor.getInt(cursor.getColumnIndex(DataInfo.STEPCOUNT)));
            hourlyData.setCalories(cursor.getInt(cursor.getColumnIndex(DataInfo.CALORIES)));
            hourlyData.setDistance(cursor.getFloat(cursor.getColumnIndex(DataInfo.DISTANCE)));
            hourlyData.setHeartRate(cursor.getInt(cursor.getColumnIndex(DataInfo.HEARTRATE)));
            hourlyData.setBloodOxygen(cursor.getInt(cursor.getColumnIndex(DataInfo.BLOODOXYGEN)));
            hourlyData.setBloodPressureHigh(cursor.getInt(cursor.getColumnIndex(DataInfo.BLOODPRESSUREHIGH)));
            hourlyData.setBloodPressureLow(cursor.getInt(cursor.getColumnIndex(DataInfo.BLOODPRESSURELOW)));
            hourlyData.setShallowSleepTime(cursor.getLong(cursor.getColumnIndex(DataInfo.SHALLOWSLEEPTIME)));
            hourlyData.setDeepSleepTime(cursor.getLong(cursor.getColumnIndex(DataInfo.DEEPSLEEPTIME)));
            hourlyData.setSleepTime(cursor.getLong(cursor.getColumnIndex(DataInfo.SLEEPTIME)));
            hourlyData.setWakeupTimes(cursor.getInt(cursor.getColumnIndex(DataInfo.WAKEUPTIMES)));
            hourlyData.setMacAddress(cursor.getString(cursor.getColumnIndex(DataInfo.MACADDRESS)));
            lists.add(hourlyData);
        }
        cursor.close();
        db.close();
        return lists;
    }

    /**
     * update sleep data form hourly data
     * @param shallow
     * @param deep
     * @param sleep
     * @param wakeupTimes
     * @param timeInMillis
     * @return
     */
   public int updateHourlySleepData(long shallow,long deep,long sleep,int wakeupTimes,long timeInMillis){
       db=getWritableDatabase();
       ContentValues values=new ContentValues();
       values.put(DataInfo.SHALLOWSLEEPTIME,shallow);
       values.put(DataInfo.DEEPSLEEPTIME,deep);
       values.put(DataInfo.SLEEPTIME,sleep);
       values.put(DataInfo.WAKEUPTIMES,wakeupTimes);
       return db.update(DataInfo.TABLE_HOURLY_DATA,values,DataInfo.TIMEINMILLIS+" = ?",new String[]{String.valueOf(timeInMillis)});
   }

    /**
     * inset bandTest data
     * @param timeInMillis
     * @param heartRate
     * @param bloodOxygen
     * @param bloodPressureHigh
     * @param bloodPressureLow
     * @param mac
     */
    public void insertBandTestData(long timeInMillis,int heartRate,int bloodOxygen,int bloodPressureHigh,int bloodPressureLow,String mac){
        db = getWritableDatabase();
        if (db.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataInfo.TIMEINMILLIS,timeInMillis);
            contentValues.put(DataInfo.HEARTRATE,heartRate);
            contentValues.put(DataInfo.BLOODOXYGEN,bloodOxygen);
            contentValues.put(DataInfo.BLOODPRESSUREHIGH,bloodPressureHigh);
            contentValues.put(DataInfo.BLOODPRESSURELOW,bloodPressureLow);
            contentValues.put(DataInfo.MACADDRESS,mac);
            db.insert(DataInfo.TABLE_BAND_TEST_DATA,null,contentValues);
            db.close();
        }
    }

    /**
     * get current data
     * @return
     */
    public ArrayList<BandTestData> getBandTestData(){
        ArrayList<BandTestData> lists = new ArrayList<>();
        db=getReadableDatabase();
        Cursor cursor = db.query(DataInfo.TABLE_BAND_TEST_DATA, null, null, null, null, null, DataInfo.TIMEINMILLIS);
        while (cursor.moveToNext()){
            BandTestData bandTestData = new BandTestData();
            bandTestData.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DataInfo.TIMEINMILLIS)));
            bandTestData.setHeartRate(cursor.getInt(cursor.getColumnIndex(DataInfo.HEARTRATE)));
            bandTestData.setBloodOxygen(cursor.getInt(cursor.getColumnIndex(DataInfo.BLOODOXYGEN)));
            bandTestData.setBloodPressureHigh(cursor.getInt(cursor.getColumnIndex(DataInfo.BLOODPRESSUREHIGH)));
            bandTestData.setBloodPressureLow(cursor.getInt(cursor.getColumnIndex(DataInfo.BLOODPRESSURELOW)));
            bandTestData.setMacAddress(cursor.getString(cursor.getColumnIndex(DataInfo.MACADDRESS)));
            lists.add(bandTestData);
        }
        cursor.close();
        db.close();
        return lists;
    }

    public void inserRemindData(String day,String time,String repeat,String remindId,String switch1,String number){
        db = getWritableDatabase();
        if (db.isOpen()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataInfo.DAY,day);
            contentValues.put(DataInfo.TIME,time);
            contentValues.put(DataInfo.REPEAT,repeat);
            contentValues.put(DataInfo.REMIND_ID,remindId);
            contentValues.put(DataInfo.SWITCH,switch1);
            contentValues.put(DataInfo.NUMBER,number);
            db.insert(DataInfo.TABLE_REMIND_DATA,null,contentValues);
            db.close();
        }
    }

    public ArrayList<RemindData> getRemindData() {
        ArrayList<RemindData> list = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.query(DataInfo.TABLE_REMIND_DATA, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            RemindData remindData = new RemindData();
            remindData.setDay(cursor.getString(cursor.getColumnIndex(DataInfo.DAY)));
            remindData.setTime(cursor.getString(cursor.getColumnIndex(DataInfo.TIME)));
            remindData.setRepeat(cursor.getString(cursor.getColumnIndex(DataInfo.REPEAT)));
            remindData.setRemindId(cursor.getString(cursor.getColumnIndex(DataInfo.REMIND_ID)));
            remindData.setSwitch1(cursor.getString(cursor.getColumnIndex(DataInfo.SWITCH)));
            remindData.setNumber(cursor.getInt(cursor.getColumnIndex(DataInfo.NUMBER)));
            list.add(remindData);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteAll(){
        db = getWritableDatabase();
        int delete = db.delete(DataInfo.TABLE_REMIND_DATA, null, null);
        Log.i("zgy","delete: "+delete);
        db.close();
    }

    public void deleteDataBase ()  {
        db = getWritableDatabase ();
        db.execSQL("DROP TABLE IF EXISTS "+DataInfo.TABLE_REMIND_DATA );
        db.execSQL(DataInfo.CREATE_REMINDDATA);
        db.close();

    }

    public void deleteByDayAndId(String dayofMonth, String id){
        db = getWritableDatabase();
        String whereClause = "day=? and remindId=?";
        String[] whereArgs = new String[]{dayofMonth,id};
        db.delete(DataInfo.TABLE_REMIND_DATA,whereClause,whereArgs);
        db.close();
    }

    public void updatePray(String dayofMonth, String id,int sw){
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataInfo.SWITCH,sw);
        String whereClause = "day=? and remindId=?";
        String[] whereArgs = new String[]{dayofMonth,id};
        db.update(DataInfo.TABLE_REMIND_DATA,contentValues,whereClause,whereArgs);
        db.close();

    }
}
