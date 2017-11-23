package com.langfox.langfoxandroid.data;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.langfox.cache.Deserializer;
import com.langfox.cache.ExerciseAndCategoryProxy;
import com.langfox.cache.FoxCache;
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

/**
 * Created by Jens on 14.11.2017.
 */

public class CacheHelper {

    public CacheHelper() {
    }

    public static void init() {
        CacheHelper.getExIdToWords();
        CacheHelper.getCategoryProxies();
        CacheHelper.getPathProxies();
        CacheHelper.getExerciseProxies();
    }

    public static void getExIdToWords() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .build();
        LangfoxCacheAPI repo = retrofit.create(LangfoxCacheAPI.class);
        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall("7");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] serializedData = CacheHelper.getBytes(response);
                HashMap<String, String> map = Deserializer.deSerializeHashMapWithString(serializedData);
                FoxCache.getInstance().setMapExIdToWords(map);
                //Log.d("langfoxApp", "getExIdToWords finished");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("langfoxApp", "getExIdToWords onFailure");
                FoxCache.getInstance().setMapExIdToWords(new HashMap<String, String>());
            }
        });
    }

    public static void getCategoryProxies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .build();
        LangfoxCacheAPI repo = retrofit.create(LangfoxCacheAPI.class);
        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall("6");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] serializedData = getBytes(response);
                HashMap<String, List<ExerciseAndCategoryProxy>> map = Deserializer.deSerializeHashMapWithCategoryProxies(serializedData);
                FoxCache.getInstance().buildCategoryViewCache(map);
                //printOutCategoryProxies(categoryProxies, "ende");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                FoxCache.getInstance().buildCategoryViewCache(new HashMap<String, List<ExerciseAndCategoryProxy>>());
            }
        });
    }

    public static void getPathProxies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .build();
        LangfoxCacheAPI repo = retrofit.create(LangfoxCacheAPI.class);
        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall("5");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] serializedData = getBytes(response);
                HashMap<String, List<ExerciseAndCategoryProxy>> map = Deserializer.deSerializeHashMapWithExerciseProxies(serializedData);
                FoxCache.getInstance().buildPathViewCache(map);
                //Log.d("langfoxApp", "getPathProxies finished");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("langfoxApp", "getPathProxies onFailure");
                FoxCache.getInstance().buildPathViewCache(new HashMap<String, List<ExerciseAndCategoryProxy>>());
            }
        });
    }

    public static void getExerciseProxies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .build();
        LangfoxCacheAPI repo = retrofit.create(LangfoxCacheAPI.class);
        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall("4");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] serializedData = getBytes(response);
                HashMap<String, List<ExerciseAndCategoryProxy>> map = Deserializer.deSerializeHashMapWithExerciseProxies(serializedData);
                FoxCache.getInstance().buildExerciseViewCache(map);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                FoxCache.getInstance().buildExerciseViewCache(new HashMap<String, List<ExerciseAndCategoryProxy>>());
            }
        });
    }

    public static byte[] getBytes(Response<ResponseBody> response) {
        try {
            Gson gson = new Gson();
            Category_Proxies contributorsList = gson.fromJson(response.body().string(), Category_Proxies.class);
            //Log.d("modified:", contributorsList.getModified());
            //Log.d("name:", contributorsList.getName());
            String base64EncodedData = contributorsList.getObject_base_64_encoded();
            byte[] serializedData = Base64.decode(base64EncodedData, Base64.DEFAULT);
            String sha512Hash = new Sha512Hash(serializedData).toString();
            //Log.d("langfoxApp", "Data byteCount: " + serializedData.length + ", sha512Hash: " + sha512Hash);
            return serializedData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void printOutExerciseProxies(HashMap<String, List<ExerciseAndCategoryProxy>> map, String uiLanguageIso6391, String newLanguageIso6391) {
        String key = uiLanguageIso6391 + newLanguageIso6391;
        List<ExerciseAndCategoryProxy> exerciseProxies = map.get(key); //ui language iso6391 + new language iso 6391, e.g. "ende"
        if (exerciseProxies == null || exerciseProxies.isEmpty()) {
            Log.d("langfoxApp", "exerciseProxies is empty");
        } else {
            //Log.d("langfoxApp", "exerciseProxies size: " + exerciseProxies.size());
            Iterator iterator = exerciseProxies.iterator();
            while (iterator.hasNext()) {
                ExerciseAndCategoryProxy exerciseProxy = (ExerciseAndCategoryProxy) iterator.next();
                String imageRoot = "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/catex/";
                String compositeKey = exerciseProxy.getCompositeId(uiLanguageIso6391); //Get the key for getting the strings from the map
                String message = exerciseProxy.getTitle()
                        + ", " + exerciseProxy.getQuestionCount()
                        + ", " + imageRoot + exerciseProxy.getIcon()
                        + ", " + FoxCache.getInstance().getMapExIdToWords().get(compositeKey);
                Log.d("langfoxApp", message);
            }
        }
    }

    public static void printOutCategoryProxies(String key) {
        List<ExerciseAndCategoryProxy> categoryProxies = FoxCache.getInstance().getCategoryViewCache().getCategoryProxiesByKey(key);
        if (categoryProxies == null || categoryProxies.isEmpty()) {
            Log.d("langfoxApp", "categoryProxies is empty");
        } else {
            Log.d("langfoxApp", "categoryProxies size: " + categoryProxies.size());
            Iterator iterator = categoryProxies.iterator();
            while (iterator.hasNext()) {
                ExerciseAndCategoryProxy categoryProxy = (ExerciseAndCategoryProxy) iterator.next();
                String imageRoot = "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/catex/";
                String message = categoryProxy.getTitle() + ", " + categoryProxy.getExerciseCount() + ", " + imageRoot + categoryProxy.getIcon();
                Log.d("langfoxApp", message);
            }
        }
    }

}
