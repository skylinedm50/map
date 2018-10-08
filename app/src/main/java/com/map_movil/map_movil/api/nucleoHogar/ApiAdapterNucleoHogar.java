package com.map_movil.map_movil.api.nucleoHogar;

import com.map_movil.map_movil.api.ApiConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapterNucleoHogar {
    public ApiServiceNucleoHogar getClientService(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(0,TimeUnit.MILLISECONDS)
                .writeTimeout(0,TimeUnit.MILLISECONDS)
                .connectTimeout(0,TimeUnit.MILLISECONDS)
                .build();

        Retrofit objRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.strURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return objRetrofit.create(ApiServiceNucleoHogar.class);
    }
}
