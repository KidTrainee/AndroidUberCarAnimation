package com.example.taili.androidubercaranimation;

import com.example.taili.androidubercaranimation.Remote.IGoogleApi;
import com.example.taili.androidubercaranimation.Remote.RetrofitClient;

/**
 * Created by taili on 2/8/2018.
 */

public class Common {
    public static final String baseUrl = "https://googleapis.com";
    public static IGoogleApi getGoogleApi() {
        return RetrofitClient.getClient(baseUrl).create(IGoogleApi.class);
    }
}
