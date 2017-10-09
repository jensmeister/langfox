package com.langfox.langfoxandroid;

//use git in Android Studio
//https://www.coursera.org/learn/internet-of-things-dragonboard/lecture/EfKqi/using-git-with-android-studio

//source
//https://www.learn2crack.com/2016/02/image-loading-recyclerview-picasso.html

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.langfox.langfoxandroid.LangfoxContract.LanguageEntry;

import java.util.ArrayList;

public class WorkbookActivityNewLan extends AppCompatActivity {


    private DataBaseHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbook_new_lan);
        //call initViews to retrieve data in the recyclerView
        initViews();

        mDbHelper = new DataBaseHelper(this);
        //insertLanguage();

        //displayDatabaseInfo();
        //displayDatabase();
        //displayDatabaseColumns();

    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Language> language = prepareData();
        DataAdapter adapter = new DataAdapter(getApplicationContext(), language);
        recyclerView.setAdapter(adapter);

    }
    //original, use local data

//    private ArrayList<Language> prepareData() {
//
//        ArrayList<Language> language_array = new ArrayList<>();
//
//        for (int i = 0; i < language_names.length; i++) {
//            Language language = new Language();
//            language.setName(language_names[i]);
//            language.setImageURL(language_image_urls[i]);
//            language_array.add(language);
//        }
//        return language_array;
//    }

    private ArrayList<Language> prepareData() {

        ArrayList<Language> language_array = new ArrayList<>();

        for (int i = 0; i < language_names.length; i++) {
            Language language = new Language();
            language.setName(language_names[i]);
            language.setImageURL(language_image_urls[i]);
            language_array.add(language);
        }
        return language_array;
    }

    //data source
    private final String language_names[] = {
            "German",
            "Chinese",
            "Spanish",
            "English",
            "Finnish",
            "Swedish",
            "Japanese"

    };

    private final String language_image_urls[] = {
            "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flags/de.svg",
            "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flags/zh.svg",
            "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flags/ja.svg",
            "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flags/fi.svg",
            "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flags/en.svg",
            "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flags/es.svg",
            "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flags/sv.svg"
    };

    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + LanguageEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertLanguage() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(LanguageEntry.COLUMN_LANGUAGE_NAME, "english");
        values.put(LanguageEntry.COLUMN_LANGUAGE_NATIVE_NAME, "English");
        values.put(LanguageEntry.COLUMN_LANGUAGE_LANGUAGE_ISO1, "en");
        values.put(LanguageEntry.COLUMN_LANGUAGE_LANGUAGE_ISO2b, "eng");
        values.put(LanguageEntry.COLUMN_LANGUAGE_RANK, 5);
        values.put(LanguageEntry.COLUMN_LANGUAGE_STATUS, 1);

        long newRowId = db.insert(LanguageEntry.TABLE_NAME, null, values);
    }

//    private ArrayList<String> getRows() {
//        // To access our database, we instantiate our subclass of SQLiteOpenHelper
//        // and pass the context, which is the current activity.
//
//        // Create and/or open a database to read from it
//        String query = "SELECT * FROM " + LanguageEntry.TABLE_NAME;
//
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery(query,null);
//
//        ArrayList<String> list = new ArrayList<>();
//
//        if (cursor != null && cursor.getCount() != 0) {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                values.add(cursor.getInt(cursor.getColumnIndex(LanguageEntry.COLUMN_LANGUAGE_NAME)));
//                cursor.moveToNext();
//            }
//        }
//        TextView displayView = (TextView) findViewById(R.id.text_view_pet);
//        displayView.setText("Number of rows in pets database table: " + values);
//        cursor.close();
//    }
private void displayDatabaseColumns() {
    // To access our database, we instantiate our subclass of SQLiteOpenHelper
    // and pass the context, which is the current activity.

    // Create and/or open a database to read from it
    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    // Perform this raw SQL query "SELECT * FROM pets"
    // to get a Cursor that contains all rows from the pets table.
    Cursor cursor = db.query( LanguageEntry.TABLE_NAME, null, null, null, null, null, null);
    //String[] columnNames = cursor.getColumnNames();
    try {
        // Display the number of rows in the Cursor (which reflects the number of rows in the
        // pets table in the database).
        TextView displayView = (TextView) findViewById(R.id.text_view_pet);
        //String columnNames  = cursor.getColumnName(3);
        String columnName  = cursor.getColumnName(4);
        String[] columnNames  = cursor.getColumnNames();

        displayView.setText( String.valueOf(columnNames));
    } finally {
        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.
        cursor.close();
    }
}
}










