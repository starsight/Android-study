package com.wenjiehe.android_study;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yiyuan on 2016/9/11.
 */
public class MyDbOpenHelper extends SQLiteOpenHelper{

    public MyDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("MyDbOpenHelper","create ing");
        sqLiteDatabase.execSQL("CREATE TABLE person(persionid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20))");
        Log.d("MyDbOpenHelper","create ok!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("Main","updating");
    }
}
