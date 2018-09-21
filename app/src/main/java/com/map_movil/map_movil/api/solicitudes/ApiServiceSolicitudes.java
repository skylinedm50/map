package com.map_movil.map_movil.api.solicitudes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServiceSolicitudes {
    @GET("solicitudes/gestionadas/usuario/{usuario}/{simbolo}")
    Call<ArrayList<SolicitudesUsuario>> getSolicitudesFinalizadasUsuario(@Path("usuario") Integer intCodUser, @Path("simbolo") String strSimbolo);

    @GET("solicitudes/solicitud/{codigo}")
    Call<ArrayList<InfoSolicitud>> getSolicitudInfo(@Path("codigo") Integer intCodSolicitud);


    @POST("solicitudes/solicitud/nueva")
    Call<ResponseApi> createSolicitud(@Body JsonArray jsonArray);

    @GET("solicitudes/gestionadas/download/{usuario}/{aldea}")
    Call<ArrayList<SolicitudesDownload>> getSolicitudesDownload(@Path("usuario") int intCodUser, @Path("aldea") String strCodAldea);
}
