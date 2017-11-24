package com.langfox.langfoxandroid.data;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jens Wilke on 23.11.2017.
 */

public class LanguageHelper {

    private static List<Language> languages;

    private LanguageHelper() {
    }

    public static List<Language> getLanguages() {
        return LanguageHelper.languages;
    }

    public static void setLanguages(List<Language> languages) {
        LanguageHelper.languages = languages;
    }

    public static void updateLanguagesFromRest(final Context context) {
        Log.d("langfoxApp", "Languages getLanguages");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LangfoxLanguagesAPI repo = retrofit.create(LangfoxLanguagesAPI.class);
        Call<List<Language>> call = repo.LanguagesSimpleGetCall();
        Log.d("langfoxApp", "Languages call.toString(): " + call.request().url());
        call.enqueue(new Callback<List<Language>>() {
            @Override
            public void onResponse(Call<List<Language>> call, Response<List<Language>> response) {
                //Log.d("langfoxApp", "Languages onResponse: ");
                if(response.isSuccessful()) {
                    List<Language> languages = response.body();
                    LanguageHelper.setLanguages(languages);
                    for (Language language : languages) {
                        Log.d("langfoxApp", "Languages onResponse: " + language.getName());
                    }
                    LanguageHelper.createOrUpdateLanguages(context, languages);
                } else {
                    System.out.println(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {
                //DataBaseHelper.
                Log.d("langfoxApp", "Languages onFailure: ");
            }
        });
    }

    public static void createOrUpdateLanguages(Context context, List<Language> languages) {
        Log.d("langfoxApp", "Languages createOrUpdateLanguages");
        DataBaseHelper helper  = new DataBaseHelper(context);
        Dao<Language, Integer> languageDao = null;
        try {
            languageDao = helper.getLanguageDao();
            for (Language language : languages) {
                languageDao.createOrUpdate(language);
            }
        } catch (SQLException e) {
            Log.d("langfoxApp", "userDao EXCEPTION 1");
            e.printStackTrace();
        }
    }

}
