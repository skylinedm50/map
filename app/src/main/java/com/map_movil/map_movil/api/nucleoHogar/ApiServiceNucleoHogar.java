package com.map_movil.map_movil.api.nucleoHogar;

import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.Municipios;
import com.map_movil.map_movil.model.Hogar_Validar;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceNucleoHogar {
    @GET("hogares")
    Call<List<Hogar_Validar>> getDatosNucleo(@Query("json") String json);

    @GET("hogares/hogar/pagos/municipio/{usuario}")
    Call<List<HistorialPago>> getHistorialPago(@Path("usuario") int usuario, @Query("json") String json);

    @GET("geografia/municipios/{departamento}")
    Call<List<Municipios>> getMunicipios(@Path("departamento") String departamento);
}
