package com.langfox.langfoxandroid;

import android.app.Activity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> flagsIso1 = getFlagUrls();
    }

    public List<String> getFlagUrls() {
        Log.w("langfoxApp", "testSql 1");
        DataBaseHelper myDB = new DataBaseHelper(this);//getApplicationContext()
        Log.w("langfoxApp", "testSql 2");

        SQLiteDatabase dbTest = myDB.getWritableDatabase();

        Log.w("langfoxApp", "testSql 2.2, db size: " + DatabaseUtils.queryNumEntries(dbTest, "language"));
        myDB.writeTableAndData(dbTest);
        Log.w("langfoxApp", "testSql 2.4, db size: " + DatabaseUtils.queryNumEntries(dbTest, "language"));

        Log.w("langfoxApp", "testSql 2.5");

        dbTest = myDB.getReadableDatabase();
        Log.w("langfoxApp", "testSql 3");

        Cursor cursor = dbTest.query("language", new String[]{"language_id", "name", "native_name", "language_iso1"}, null, null, null, null, null);
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
        dbTest.close();
        return flagUrls;

    }
}