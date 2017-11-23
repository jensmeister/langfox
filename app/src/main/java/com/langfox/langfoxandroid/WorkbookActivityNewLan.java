package com.langfox.langfoxandroid;

//use git in Android Studio
//https://www.coursera.org/learn/internet-of-things-dragonboard/lecture/EfKqi/using-git-with-android-studio

//source
//https://www.learn2crack.com/2016/02/image-loading-recyclerview-picasso.html

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.langfox.langfoxandroid.data.DataAdapter;
import com.langfox.langfoxandroid.data.DataBaseHelper;
import com.langfox.langfoxandroid.data.Language;

import java.util.ArrayList;

public class WorkbookActivityNewLan extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    public String language_image_urls[];
    public String language_names_database[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbook_new_lan);

        dataBaseHelper = new DataBaseHelper(this);//used to make mistake on instantiation of DataBaseHelper in onCreate()

        language_image_urls = getFlagUrlsArray();
        for (String s : language_image_urls)
            System.out.println(s);

        language_names_database = getLanguageNames();
        for (String s : language_names_database)
            System.out.println(s);

        //call initViews to retrieve data in the recyclerView
        initViews();

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

    private ArrayList<Language> prepareData() {

        ArrayList<Language> language_array = new ArrayList<>();

        for (int i = 0; i < language_names_database.length; i++) {
            Language language = new Language();
            //language.setName(language_names_database[i]);
            //language.setImageURL(language_image_urls[i]);
            language_array.add(language);
        }
        return language_array;
    }


    private String[] getFlagUrlsArray() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.

        // Create and/or open a database to read from it

        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();//instance of database, created by DataBaseHelper onCreate()

        // Perform this raw SQL query "SELECT * FROM language"
        // to get a Cursor that contains all rows from the language table.
        String[] project = {
                Language.COLUMN_LANGUAGE_ID,
                Language.COLUMN_LANGUAGE_NAME,
                Language.COLUMN_LANGUAGE_NATIVE_NAME,
                Language.COLUMN_LANGUAGE_LANGUAGE_ISO1
        };

        Cursor cursor = db.query(
                Language.TABLE_NAME_LANGUAGE,
                project,
                null,
                null,
                null,
                null,
                Language.COLUMN_LANGUAGE_LANGUAGE_ISO1 + " DESC");

        String[] flagUrlsArray = new String[cursor.getCount()];
        String flagUrlBase = "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flags/";
        int index = 0;
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String flagUrl = flagUrlBase + cursor.getString(3) + ".svg";
            flagUrlsArray[index] = flagUrl;
            cursor.moveToNext();
            index++;
        }

//        for (String s : flagUrlsArray)
//            System.out.println(s);

        cursor.close();
        return flagUrlsArray;
    }

    private String[] getLanguageNames() {

        // Create and/or open a database to read from it

        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();//instance of database, created by DataBaseHelper onCreate()

        // Perform this raw SQL query "SELECT * FROM language"
        // to get a Cursor that contains all rows from the language table.
        String[] project = {
                Language.COLUMN_LANGUAGE_ID,
                Language.COLUMN_LANGUAGE_NAME,
                Language.COLUMN_LANGUAGE_NATIVE_NAME,
                Language.COLUMN_LANGUAGE_LANGUAGE_ISO1
        };

        Cursor cursor = db.query(
                Language.TABLE_NAME_LANGUAGE,
                project,
                null,
                null,
                null,
                null,
                Language.COLUMN_LANGUAGE_LANGUAGE_ISO1 + " DESC");

        String[] languageNameArray = new String[cursor.getCount()];
        int index = 0;
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String languageName = cursor.getString(1);
            languageNameArray[index] = languageName;
            cursor.moveToNext();
            index++;
        }

//        for (String s : flagUrlsArray)
//            System.out.println(s);

        cursor.close();
        return languageNameArray;
    }

    //invoke only once
//    public void insertLanguage() {
//        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
//        db.execSQL("INSERT INTO language VALUES(1,'english','English','en','eng',5,1);");
//        db.execSQL("INSERT INTO language VALUES(2,'german','Deutsch','de','ger',1,1);");
//        db.execSQL("INSERT INTO language VALUES(3,'spanish','Español','es','spa',6,1);");
//        db.execSQL("INSERT INTO language VALUES(4,'finnish','Suomi','fi','fin',4,1);");
//        db.execSQL("INSERT INTO language VALUES(5,'swedish','Svenska','sv','swe',7,1);");
//        db.execSQL("INSERT INTO language VALUES(6,'japanese','&#26085;&#26412;&#35486;','ja','jpn',3,1);");
//        db.execSQL("INSERT INTO language VALUES(7,'chinese','Chinese','zh','chi',2,2);");
//    }

}










