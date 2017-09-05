package com.example.bandtest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Harry on 2017/9/5.
 */

public class DataHelper extends SQLiteOpenHelper {
    private static DataHelper sInstance;

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
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DataInfo.CREATE_CURRENTDATA);
        Log.i("zgy","onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("zgy","onUpgrade");

    }
}
