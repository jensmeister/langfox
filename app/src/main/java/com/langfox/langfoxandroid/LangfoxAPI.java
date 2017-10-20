package com.langfox.langfoxandroid;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pengchengliu on 19/10/2017.
 */

public interface LangfoxAPI {
    @GET("webresources/caches/{id}?data=true")
    //String idForDataRow = @Path("id");
    Call<ResponseBody> CategoryProxiesBySimpleGetCall(@Path("id") String id);
}


