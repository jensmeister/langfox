package com.langfox.langfoxandroid.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pengchengliu on 19/10/2017.
 */

public interface LangfoxCacheAPI {
    @GET("webresources/caches/{id}?data=true")
    Call<ResponseBody> CategoryProxiesBySimpleGetCall(@Path("id") String id);
}


