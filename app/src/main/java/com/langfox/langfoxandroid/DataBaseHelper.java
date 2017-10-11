package com.langfox.langfoxandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    private String[] getFlagUrlsArray() {
        List<String> flagUrls = this.getFlagUrls();
        String[] flagUrlsArray = new String[flagUrls.size()];
        flagUrlsArray = flagUrls.toArray(flagUrlsArray);
        return flagUrlsArray;
    }

    public List<String> getFlagUrls() {
        //DataBaseHelper myDB = new DataBaseHelper(this);//getApplicationContext()
        Log.w("langfoxApp", "testSql 2");

        SQLiteDatabase database = this.getWritableDatabase();

        Log.w("langfoxApp", "testSql 2.2, db size: " + DatabaseUtils.queryNumEntries(database, "language"));
        this.writeTableAndData(database);
        Log.w("langfoxApp", "testSql 2.4, db size: " + DatabaseUtils.queryNumEntries(database, "language"));

        Log.w("langfoxApp", "testSql 2.5");

        database = this.getReadableDatabase();
        Log.w("langfoxApp", "testSql 3");

        Cursor cursor = database.query("language", new String[]{"language_id", "name", "native_name", "language_iso1"}, null, null, null, null, null);
        //String[] languageNameArray = new String[cursor.getCount()];
        Log.w("langfoxApp", "testSql 4, cursor.getCount(): " + cursor.getCount());
        List<String> strings = new ArrayList<>();

        int index = 0;
        cursor.moveToFirst();
        //while (cursor.isAfterLast() == false) {
        List<String> flagUrls = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            //languageNameArray[i] = cursor.getString(0);
            //Log.w("langfoxApp", "testSql 4.5, index: " + index);
            Log.w("langfoxApp", "testSql 4.7, index: " + cursor.toString());
            Log.w("langfoxApp", "testSql 4.7, index: " + cursor.getInt(0) + " / " + cursor.getString(1) + " / " + cursor.getString(2) + " / " + cursor.getString(3));
            Log.w("langfoxApp", "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flagUrls/" + cursor.getString(3) + ".svg");
            String flagUrl = "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flagUrls/" + cursor.getString(3) + ".svg";
            flagUrls.add(flagUrl);
            //strings.add(cursor.getString(0));
            strings.add("cat is dog");
            //Log.w("langfoxApp", "testSql 5: " + cursor.getString(0));
            cursor.moveToNext();
            index++;
        }
        cursor.close();
        database.close();

        return flagUrls;

    }

}