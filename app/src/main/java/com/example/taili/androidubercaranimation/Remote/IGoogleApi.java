package com.example.taili.androidubercaranimation.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by taili on 2/8/2018.
 */

public interface IGoogleApi {
    @GET
    public Call<String>  getDataFromGoogleApi(@Url String url);
}
