package com.map_movil.map_movil.api.descargaNucleo;

import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.Municipios;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceDescargaNucleo {
    @GET("hogares/{municipio}")
    Call<List<Hogar_Validar>> getDatos(@Path("municipio") String municipio);

    @GET("hogares/hogar/pagos/municipio/{municipio}")
    Call<List<HistorialPago>> getHistorialPago(@Path("municipio") String municipio);

    @GET("geografia/municipios/{departamento}")
    Call<List<Municipios>> getMunicipios(@Path("departamento") String departamento);
}
