package com.map_movil.map_movil.api.quejas;

import com.google.gson.JsonObject;
import com.map_movil.map_movil.model.QuejasDenuncias;
import com.map_movil.map_movil.model.ResponseApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServicesQuejas {

    @GET("quejas_denuncias/usuario/{usuario}")
    Call<ArrayList<QuejasDenuncias>> getQuejasDenuncias(@Path("usuario") int usuario);

    @FormUrlEncoded()
    @POST("quejas_denuncias/nueva")
    Call<ResponseApi> IngregarQueja(@Field("json")JsonObject json);

}
