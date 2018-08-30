package com.map_movil.map_movil.api.ubicaciones;

import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Departamentos;
import com.map_movil.map_movil.model.Municipios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServicesUbicacion {
    @GET("geografia/aldeas/{municipio}")
    Call<List<Aldeas>> getAllAldeas(@Path("municipio") String municipio);

    @GET("geografia/caserios/{aldea}")
    Call<List<Caserios>> getAllCaserios(@Path("aldea") String aldea);

}
