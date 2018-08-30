package com.map_movil.map_movil.api.solicitudes;

import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;

import retrofit2.Call;
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

    @FormUrlEncoded
    @POST("solicitudes/solicitud/nueva")
    Call<ResponseApi> createSolicitud(@Field("identidad") String identidad,
                                     @Field("cod_user") int cod_user,
                                     @Field("observacion") String observacion,
                                     @Field("actualizacion_datos") int actualizacion_datos,
                                     @Field("cambio_titular") int cambio_titular,
                                     @Field("nuevo_miembro") int nuevo_miembro,
                                     @Field("baja_miembro") int baja_miembro,
                                     @Field("cambio_domicilio") int cambio_domicilio,
                                     @Field("baja_programa") int baja_programa,
                                     @Field("reactiva_programa") int reactiva_programa,
                                     @Field("correccion_sancion") int correcion_sancion);

    @GET("solicitudes/solicitud/download/{usuario}/{aldea}")
    Call<ArrayList<SolicitudesDownload>> getSolicitudesDownload(@Path("usuario") int intCodUser, @Path("aldea") String strCodAldea);
}
