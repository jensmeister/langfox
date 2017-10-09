package com.langfox.langfoxandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.langfox.langfoxandroid.LangfoxContract.LanguageEntry;
/**
 * Created by pengchengliu on 05/10/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "language.db";

    private static final int DATABASE_VERSION = 2;

    public DataBaseHelper(Context context) {
        //    constructor, used to create database
        //    db is created when this constructor is called
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //override onCreate to create table
        //take the instance of SQLiteDatabase and call execSQL

        String SQL_CREATE_LANGUAGE_TABLE =  "CREATE TABLE "+ LanguageEntry.TABLE_NAME + " ("
                + LanguageEntry.language_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LanguageEntry.COLUMN_LANGUAGE_NAME + " TEXT NOT NULL, "
                + LanguageEntry.COLUMN_LANGUAGE_NATIVE_NAME + " TEXT NOT NULL, "
                + LanguageEntry.COLUMN_LANGUAGE_LANGUAGE_ISO1 + " TEXT NOT NULL, "
                + LanguageEntry.COLUMN_LANGUAGE_LANGUAGE_ISO2b + " TEXT NOT NULL, "
                + LanguageEntry.COLUMN_LANGUAGE_RANK + " INTEGER NOT NULL, "
                + LanguageEntry.COLUMN_LANGUAGE_STATUS + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_LANGUAGE_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.

    }

}
