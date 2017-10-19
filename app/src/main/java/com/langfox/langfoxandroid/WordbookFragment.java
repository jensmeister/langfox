package com.langfox.langfoxandroid;

//source of startActivity() from fragment
//http://blog.csdn.net/guolin_blog/article/details/8744943


import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.google.gson.Gson;
import com.langfox.langfoxandroid.pojo.Category_Proxies;


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
            getCategory_Proxies();

        }
        startActivity(intent);


    }

//lalalalala
    public WordbookFragment() {
        // Required empty public constructor
    }


    private void getCategory_Proxies() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")

                .build();

        LangfoxAPI repo = retrofit.create(LangfoxAPI.class);

        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    Gson gson = new Gson();
                    Category_Proxies contributorsList = gson.fromJson(response.body().string(), Category_Proxies.class);
                    Log.d("modified:", contributorsList.getModified());
                    Log.d("name:", contributorsList.getName());
                    Log.d("object_base_64_encoded:", contributorsList.getObject_base_64_encoded());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
