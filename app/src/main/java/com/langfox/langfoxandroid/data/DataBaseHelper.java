package com.langfox.langfoxandroid.data;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pengchengliu on 05/10/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "language.db";

    public static final String TABLE_NAME = "language";


    public DataBaseHelper(Context context) {
        //    constructor, used to create database
        //    db is created when this constructor is called
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //override onCreate to create table
        //take the instance of SQLiteDatabase and call execSQL
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); /////////////////////////////////////////////////Starting from clean table

        Log.w("langfoxApp", "Create table");
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                "language_id TINYINT UNSIGNED NOT NULL PRIMARY KEY, " +
                "name VARCHAR(31) NOT NULL," +
                "native_name VARCHAR(31) NOT NULL," +
                "language_iso1 VARCHAR(2) NOT NULL, " +
                "language_iso2b VARCHAR(3) NOT NULL," +
                "rank TINYINT UNSIGNED NOT NULL," +
                "status TINYINT UNSIGNED NOT NULL)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}