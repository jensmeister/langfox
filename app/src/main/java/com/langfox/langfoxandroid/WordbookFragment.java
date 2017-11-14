package com.langfox.langfoxandroid;

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
import com.langfox.langfoxandroid.pojo.Category_Proxies;

import org.apache.shiro.crypto.hash.Sha512Hash;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


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
        this.getExIdToWords();
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
            getProxies(4); //4 (exercise view), 5 (path view), 6 (category view) are the relevant values
        }
        startActivity(intent);
    }

    public WordbookFragment() {
        // Required empty public constructor
    }

    private void getProxies(final Integer id) {
        if (id.equals(6)) {
            this.getCategoryProxies();
        } else if (id.equals(5)) {
            this.getPathProxies();
        } else if (id.equals(4)) {
            this.getExerciseProxies();
        }
    }

    private void getCategoryProxies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .build();
        LangfoxAPI repo = retrofit.create(LangfoxAPI.class);
        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall("6");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] serializedData = getBytes(response);
                categoryProxies = Deserializer.deSerializeHashMapWithCategoryProxies(serializedData);
                printOutCategoryProxies(categoryProxies, "ende");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                categoryProxies = new HashMap<>();
            }
        });
    }

    private void getPathProxies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .build();
        LangfoxAPI repo = retrofit.create(LangfoxAPI.class);
        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall("5");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] serializedData = getBytes(response);
                pathProxies = Deserializer.deSerializeHashMapWithExerciseProxies(serializedData);
                printOutExerciseProxies(pathProxies, "en", "de");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pathProxies = new HashMap<>();
            }
        });
    }

    private void getExerciseProxies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .build();
        LangfoxAPI repo = retrofit.create(LangfoxAPI.class);
        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall("4");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] serializedData = getBytes(response);
                exerciseProxies = Deserializer.deSerializeHashMapWithExerciseProxies(serializedData);
                printOutExerciseProxies(exerciseProxies, "en", "dec24"); //ui language iso 639-1 + new language iso 639-1 ... c+categoryId or e+exerciseId
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                exerciseProxies = new HashMap<>();
            }
        });
    }

    private void localizationTest() {
        Log.d("langfoxApp", "Before localization test");
        String uiLanguageIso6391 = "de";
        Locale locale = new Locale(uiLanguageIso6391);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        String testText = bundle.getString("welcome.head.title");
        Log.d("langfoxApp", "Localization test: " + testText);
        Log.d("langfoxApp", "After localization test");
    }

    private void getExIdToWords() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .build();
        LangfoxAPI repo = retrofit.create(LangfoxAPI.class);
        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall("7");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] serializedData = getBytes(response);
                exIdToWords = Deserializer.deSerializeHashMapWithString(serializedData);
                Log.d("langfoxApp", "setExIdToWords finished");
                //printOutExIdToWords(exIdToWords);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                exIdToWords = new HashMap<>();
            }
        });
    }

    private byte[] getBytes(Response<ResponseBody> response) {
        try {
            Gson gson = new Gson();
            Category_Proxies contributorsList = gson.fromJson(response.body().string(), Category_Proxies.class);
            Log.d("modified:", contributorsList.getModified());
            Log.d("name:", contributorsList.getName());
            String base64EncodedData = contributorsList.getObject_base_64_encoded();
            byte[] serializedData = Base64.decode(base64EncodedData, Base64.DEFAULT);
            String sha512Hash = new Sha512Hash(serializedData).toString();
            Log.d("langfoxApp", "Data byteCount: " + serializedData.length + ", sha512Hash: " + sha512Hash);
            return serializedData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void printOutCategoryProxies(HashMap<String, List<CategoryProxy>> map, String key) {
        List<CategoryProxy> categoryProxies = map.get(key); //ui language iso6391 + new language iso 6391
        if (categoryProxies == null || categoryProxies.isEmpty()) {
            Log.d("langfoxApp", "categoryProxies is empty");
        } else {
            Log.d("langfoxApp", "categoryProxies size: " + categoryProxies.size());
            Iterator iterator = categoryProxies.iterator();
            while (iterator.hasNext()) {
                CategoryProxy categoryProxy = (CategoryProxy) iterator.next();
                String imageRoot = "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/catex/";
                String message = categoryProxy.getCategoryTitle() + ", " + categoryProxy.getExerciseCount() + ", " + imageRoot + categoryProxy.geCategoryIcon();
                Log.d("langfoxApp", message);
            }
        }
    }

    private void printOutExerciseProxies(HashMap<String, List<ExerciseProxy>> map, String uiLanguageIso6391, String newLanguageIso6391) {
        String key = uiLanguageIso6391 + newLanguageIso6391;
        List<ExerciseProxy> exerciseProxies = map.get(key); //ui language iso6391 + new language iso 6391
        if (exerciseProxies == null || exerciseProxies.isEmpty()) {
            Log.d("langfoxApp", "exerciseProxies is empty");
        } else {
            Log.d("langfoxApp", "exerciseProxies size: " + exerciseProxies.size());
            Iterator iterator = exerciseProxies.iterator();
            while (iterator.hasNext()) {
                ExerciseProxy exerciseProxy = (ExerciseProxy) iterator.next();
                String imageRoot = "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/catex/";
                String compositeKey = exerciseProxy.getCompositeId(uiLanguageIso6391); //Get the key for getting the strings from the map
                String message = exerciseProxy.getTitleTranslated()
                        + ", " + exerciseProxy.getQuestionCount()
                        + ", " + imageRoot + exerciseProxy.getImageFileName()
                        + ", " + this.exIdToWords.get(compositeKey);
                Log.d("langfoxApp", message);
            }
        }
    }

    private void printOutExIdToWords(HashMap<String, String> map) {
        for (Map.Entry mapEntry : map.entrySet()) {
            Log.d("langfoxApp",  mapEntry.getKey() + ": " + mapEntry.getValue());
        }
    }

}
