package com.langfox.langfoxandroid.fragment;

//source of startActivity() from fragment
//http://blog.csdn.net/guolin_blog/article/details/8744943


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.langfox.cache.CategoryProxy;
import com.langfox.cache.Deserializer;
import com.langfox.cache.ExerciseProxy;
import com.langfox.langfoxandroid.R;
import com.langfox.langfoxandroid.WorkbookActivityCat;
import com.langfox.langfoxandroid.WorkbookActivityNewLan;
import com.langfox.langfoxandroid.WorkbookActivityPath;
import com.langfox.langfoxandroid.pojo.Category_Proxies;

import org.apache.shiro.crypto.hash.Sha512Hash;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class WordbookFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * there is one ListView in the menu layout
     */

    private ListView menuList;

    /**
     * create an adapter for the ListView
     */

    private ArrayAdapter<String> adapter;

    /**
     * data to fill up the ListView
     */

    private String[] menuItems = {"Courses in another language", "Available Courses by Learning Path", "Available Courses by Categories"};

    /**
     * Initialize the data in adapter when fragment is attached to activity
     */

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

        }
        startActivity(intent);


    }

    public WordbookFragment() {
        // Required empty public constructor
    }


}
