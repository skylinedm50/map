package com.map_movil.map_movil.api.notificacion;

import com.map_movil.map_movil.api.ApiConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapterNotificacion {
    public ApiServiceNotificacion getClientService(){
        Retrofit objRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.strURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return objRetrofit.create(ApiServiceNotificacion.class);
    }
}