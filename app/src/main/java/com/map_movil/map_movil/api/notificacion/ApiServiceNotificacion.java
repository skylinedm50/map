package com.map_movil.map_movil.api.notificacion;

import com.google.gson.JsonObject;
import com.map_movil.map_movil.model.Emision;
import com.map_movil.map_movil.model.Oficiona;
import com.map_movil.map_movil.model.Region;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @POST("notificaciones/device/send")
    Call<ResponseApi> sendNotification(@Body JsonObject jsonObject);

    @POST("notificaciones/device/all/send")
    Call<ResponseApi> sendNotificationAll(@Body JsonObject jsonObject);

    @GET("notificaciones/usuario/{tipo}/{usuario}")
    Call<ArrayList<Emision>> getEmisionesByUsuario(@Path("tipo") String strTipoEmisiones, @Path("usuario") int intCodUser);

    @POST("notificaciones/mensaje/send")
    Call<ResponseApi> sendMessage(@Body JsonObject jsonObject);
}
