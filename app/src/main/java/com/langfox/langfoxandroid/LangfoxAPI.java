package com.langfox.langfoxandroid;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pengchengliu on 19/10/2017.
 */

public interface LangfoxAPI {
    @GET("webresources/caches/6?data=true")
    Call<ResponseBody> CategoryProxiesBySimpleGetCall();
}
