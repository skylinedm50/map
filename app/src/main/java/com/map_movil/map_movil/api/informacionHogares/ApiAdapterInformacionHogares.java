package com.map_movil.map_movil.api.informacionHogares;

import com.map_movil.map_movil.api.ApiConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapterInformacionHogares {
    public ApiServiceInformacionHogares getClientService(){
        Retrofit objRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.strURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return objRetrofit.create(ApiServiceInformacionHogares.class);
    }
}
