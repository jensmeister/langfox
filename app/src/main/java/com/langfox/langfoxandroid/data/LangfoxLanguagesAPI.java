package com.langfox.langfoxandroid.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jens Wilke on 22/11/2017.
 */

public interface LangfoxLanguagesAPI {
    @GET("webresources/languages")
    Call<List<Language>> LanguagesSimpleGetCall();
}


