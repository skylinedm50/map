package com.map_movil.map_movil.api.solicitudes;

import com.google.gson.JsonArray;
import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.model.SolicitudesUsuario;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceSolicitudes {
    @GET("solicitudes/gestionadas/usuario/{usuario}/{simbolo}")
    Call<ArrayList<SolicitudesUsuario>> getSolicitudesFinalizadasUsuario(@Path("usuario") Integer intCodUser, @Path("simbolo") String strSimbolo);

    @GET("solicitudes/solicitud/{codigo}")
    Call<ArrayList<InfoSolicitud>> getSolicitudInfo(@Path("codigo") Integer intCodSolicitud);

    @POST("solicitudes/solicitud/nueva")
    Call<ResponseApi> createSolicitud(@Body JsonArray jsonArray);

    @GET("solicitudes/gestionadas/download")
    Call<ArrayList<SolicitudesDownload>> getSolicitudesDownload(@Query("json") String json);
}
