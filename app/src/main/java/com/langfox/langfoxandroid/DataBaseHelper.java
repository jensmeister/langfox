package com.langfox.langfoxandroid;

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
        Log.w("langfoxApp", "DataBaseHelper, constructor, done");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public void writeTableAndData(SQLiteDatabase db) {
        //override onCreate to create table
        //take the instance of SQLiteDatabase and call execSQL
        Log.w("langfoxApp", "Drop table");
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
        //Toast doesn't work,because DataBaseHelper is not an activity
        //Toast.makeText(DataBaseHelper.this, "table created ", Toast.LENGTH_LONG).show();

        Log.w("langfoxApp", "Insert data");
        db.execSQL("INSERT INTO language VALUES(1,'english','English','en','eng',5,1);");
        Log.w("langfoxApp", "Insert data, test 1: " + DatabaseUtils.queryNumEntries(db, "language"));

        db.execSQL("INSERT INTO language VALUES(2,'german','Deutsch','de','ger',1,1);");
        Log.w("langfoxApp", "Insert data, test 2: " + DatabaseUtils.queryNumEntries(db, "language"));

        db.execSQL("INSERT INTO language VALUES(3,'spanish','Español','es','spa',6,1);");
        db.execSQL("INSERT INTO language VALUES(4,'finnish','Suomi','fi','fin',4,1);");
        db.execSQL("INSERT INTO language VALUES(5,'swedish','Svenska','sv','swe',7,1);");
        db.execSQL("INSERT INTO language VALUES(6,'japanese','&#26085;&#26412;&#35486;','ja','jpn',3,1);");
        db.execSQL("INSERT INTO language VALUES(7,'chinese','Chinese','zh','chi',2,2);");

//        insert = "INSERT INTO language VALUES(3,'spanish','Español','es','spa',6,1);\n" +
//                "INSERT INTO language VALUES(4,'finnish','Suomi','fi','fin',4,1);\n" +
//                "INSERT INTO language VALUES(5,'swedish','Svenska','sv','swe',7,1);\n" +
//                "INSERT INTO language VALUES(6,'japanese','&#26085;&#26412;&#35486;','ja','jpn',3,1);\n" +
//                "INSERT INTO language VALUES(7,'chinese','Chinese','zh','chi',2,2);";
        Log.w("langfoxApp", "Insert data, test 3: " + DatabaseUtils.queryNumEntries(db, "language"));

        Log.w("langfoxApp", "Insert data, Done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}