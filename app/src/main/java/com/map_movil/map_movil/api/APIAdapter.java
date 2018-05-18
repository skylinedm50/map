package com.map_movil.map_movil.api;

import android.util.JsonReader;

import org.json.JSONObject;

import retrofit2.Retrofit;

public class APIAdapter {

    public service getLoginService(){
        Retrofit objRetrofit = new Retrofit.Builder()
                .baseUrl(constants.strURL).build();

        return objRetrofit.create(service.class);
    }
}
