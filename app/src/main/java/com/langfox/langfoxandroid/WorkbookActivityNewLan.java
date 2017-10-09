package com.langfox.langfoxandroid;

//use git in Android Studio
//https://www.coursera.org/learn/internet-of-things-dragonboard/lecture/EfKqi/using-git-with-android-studio

//source
//https://www.learn2crack.com/2016/02/image-loading-recyclerview-picasso.html

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class WorkbookActivityNewLan extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String [] language_names_new = getLanguageNameArray();
        setContentView(R.layout.activity_workbook_new_lan);
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
            "http://julia-travel.com/uploads/vendor_category/7/184x184.gif",
            "http://julia-travel.com/uploads/vendor_category/18/184x184.jpg",
            "http://julia-travel.com/uploads/vendor_category/13/184x184.jpg",
            "http://julia-travel.com/uploads/vendor_category/5/184x184.gif",
            "http://www.fyidenmark.com/images/finland.gif",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6AxXAD36sHrlYU5Td8T_bte8wIknH5zLNvW9dfN2MDeRzfEa6",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT63xseKnCub6NoNJA6QL5QMCyWwAOMUFntVn1ivTHeC4XFMAuNfA"
    };

    public String[] getLanguageNameArray() {

//        List<String> sTest = new ArrayList<>();
//        sTest.add("Cat");
//        sTest.contains("dog");
        Log.w("langfoxApp", "getLanguageNameArray 0.05");
        //Log.w("langfoxApp", "getLanguageNameArray 0.1: " + (Toast.makeText(WorkbookActivityNewLan.this, " now good ", Toast.LENGTH_LONG) == null));
        //Toast.makeText(WorkbookActivityNewLan.this, " now good ", Toast.LENGTH_LONG).show();
        Log.w("langfoxApp", "getLanguageNameArray 0.3"); //: " + (new DataBaseHelper(this));
        DataBaseHelper myDB = new DataBaseHelper(this);//getApplicationContext()
        Log.w("langfoxApp", "getLanguageNameArray 0.4");
        //SQLiteDatabase dbTest = myDB.getWritableDatabase();
        Log.w("langfoxApp", "getLanguageNameArray 0.7");
        //Toast.makeText(WorkbookActivityNewLan.this, "now bad ", Toast.LENGTH_LONG).show();
        Log.w("langfoxApp", "getLanguageNameArray 0.9");
//        SQLiteDatabase mDb = myDB.getReadableDatabase();

        Log.w("langfoxApp", "getLanguageNameArray 1");
        String[] strings = new String[10];
        Log.w("langfoxApp", "getLanguageNameArray 2");
        strings[0] = "cat";
        strings[1] = "dog";
        strings[2] = "bird";
        return strings;

//        Cursor cursor = mDb.query("language", new String[]{"language_id"}, null, null, null, null, null);
//        String[] languageNameArray = new String[cursor.getCount()];
//
//        int i = 0;
//
//        while (cursor.isAfterLast() == false) {
//            languageNameArray[i] = cursor.getString(0);
//            i++;
//            //System.out.println("lalalalala");//languageNameArray
//            cursor.moveToNext();
//        }
//        //cursor.close();
//        //mDb.close();
//
//        return languageNameArray;

    }

    //String [] language_names_new = getLanguageNameArray();
    //NullPointerException Attempt to invoke virtual method 'android.content.res.Resources android.content.Context.getResources()'
    // on a null object reference
}