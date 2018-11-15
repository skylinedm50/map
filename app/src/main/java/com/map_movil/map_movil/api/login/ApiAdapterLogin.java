package com.map_movil.map_movil.api.login;


import com.map_movil.map_movil.api.ApiConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiAdapterLogin {
    public ApiServiceLogin getClientService(){

        Retrofit objRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.strURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return objRetrofit.create(ApiServiceLogin.class);
    }
}
