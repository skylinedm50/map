package com.map_movil.map_movil.api.hogar;

import com.map_movil.map_movil.api.ApiConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapterHogar {
    public ApiServiceHogar getClientService(){
        Retrofit objRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.strURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return objRetrofit.create(ApiServiceHogar.class);
    }
}
