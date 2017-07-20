package com.langfox.langfoxandroid;

//use git in Android Studio
//https://www.coursera.org/learn/internet-of-things-dragonboard/lecture/EfKqi/using-git-with-android-studio

//source
//https://www.learn2crack.com/2016/02/image-loading-recyclerview-picasso.html


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        initViews();
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Language> languages = prepareData();
        DataAdapter adapter = new DataAdapter(getApplicationContext(), languages);
        recyclerView.setAdapter(adapter);

    }

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

}




