package com.map_movil.map_movil.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiAdapter {
    public ApiService getClientService(){
        Retrofit objRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.strURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return objRetrofit.create(ApiService.class);
    }
}
