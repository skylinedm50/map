package com.map_movil.map_movil.api.corresponsabilidad;

import com.map_movil.map_movil.model.Corresponsabilidades;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceCorresponsabilidad {

    @GET("hogares/hogar/corresponsabilidad/titular/{codigo}")
    Call<ArrayList<Corresponsabilidades>> getCorresponsabilidadInfo(@Path("codigo") String intCodSolicitud);

}
