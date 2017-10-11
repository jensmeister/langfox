package com.langfox.langfoxandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class SQLiteTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);//getApplicationContext()
        List<String> flagUrls = dataBaseHelper.getFlagUrls();
        for (String flagUrl : flagUrls) {
            Log.w("langfoxApp", "flagUrl: " + flagUrl);
        }
        setContentView(R.layout.activity_sqlite_test);
    }

}
