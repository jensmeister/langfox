package com.langfox.langfoxandroid;

//source of startActivity() from fragment
//http://blog.csdn.net/guolin_blog/article/details/8744943


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.langfox.cache.CategoryProxy;
import com.langfox.cache.ExerciseProxy;
import com.langfox.cache.FoxCache;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class WordbookFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView menuList;
    private ArrayAdapter<String> adapter;
    private String[] menuItems = {"Courses in another language", "Available Courses by Learning Path", "Available Courses by Categories"};
    private HashMap<String, List<CategoryProxy>> categoryProxies;
    private HashMap<String, List<ExerciseProxy>> pathProxies;
    private HashMap<String, List<ExerciseProxy>> exerciseProxies;
    private HashMap<String, String> exIdToWords;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, menuItems);
    }

    /**
     * Inflate the layout of this fragment
     * Set adapter to the ListView
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wordbook, container, false);
        menuList = (ListView) view.findViewById(R.id.menu_list);
        menuList.setAdapter(adapter);
        localizationTest();
        menuList.setOnItemClickListener(this);
        return view;
    }

    /**
     * deal with onItemClick event
     * start corresponding Activity
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        if (position == 0) {
            intent = new Intent(getActivity(), WorkbookActivityNewLan.class);
        } else if (position == 1) {
            intent = new Intent(getActivity(), WorkbookActivityPath.class);
        } else if (position == 2) {
            intent = new Intent(getActivity(), WorkbookActivityCat.class);
            testFoxCache();
        }
        startActivity(intent);
    }

    public WordbookFragment() {
        //FoxCache.getInstance().getExerciseViewCache().g
        // Required empty public constructor
    }

    private void testFoxCache() {
        //CacheInit.printOutExerciseProxies(FoxCache.getInstance().getPathViewCacheMap(), "en", "de");
        CacheInit.printOutExerciseProxies(FoxCache.getInstance().getExerciseViewCacheMap(), "en", "dec24");
        //CacheInit.printOutCategoryProxies("ende");
    }


    private void localizationTest() {
        String uiLanguageIso6391 = "de";
        Locale locale = new Locale(uiLanguageIso6391);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        String testText = bundle.getString("welcome.head.title");
        Log.d("langfoxApp", "Localization TEST: " + testText);
    }


}
