package com.map_movil.map_movil.api.notificacion;

import com.google.gson.JsonObject;
import com.map_movil.map_movil.model.Oficiona;
import com.map_movil.map_movil.model.Region;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServiceNotificacion {
    @GET("global/regiones")
    Call<ArrayList<Region>> getRegiones();

    @GET("global/oficinas/region/{region}")
    Call<ArrayList<Oficiona>> getOficinas(@Path("region") int intCodRegion);

    @GET("notificaciones/usuarios/oficina/{oficina}")
    Call<ArrayList<User>> getUsuariosWithTokenForNotification(@Path("oficina") int intCodOficina);

    @POST("notificaciones/send")
    Call<ResponseApi> sendNotification(@Body String strJson);

    @GET("notificaciones/token/{usuario}")
    Call<String> getTokenByUser(@Path("usuario") int intCodUser);
}
