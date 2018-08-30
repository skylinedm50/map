package com.map_movil.map_movil.api.corresponsabilidad;

import com.map_movil.map_movil.api.ApiConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapterCorresponsabilidad {
    public ApiServiceCorresponsabilidad getClientService(){
        Retrofit objRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.strURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return objRetrofit.create(ApiServiceCorresponsabilidad.class);
    }
}
