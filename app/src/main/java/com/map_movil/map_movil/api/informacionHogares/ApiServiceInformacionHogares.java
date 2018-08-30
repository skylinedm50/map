package com.map_movil.map_movil.api.informacionHogares;

import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.HogarActualizaciones;
import com.map_movil.map_movil.model.HogarInformacion;
import com.map_movil.map_movil.model.NucleoHogar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceInformacionHogares {
    @GET("hoagres/actualizaciones/cambio/agregar/menor/{identidad_titular}")
    Call<ArrayList<HogarActualizaciones>> getAgregacionMenores(@Path("identidad_titular") String identidad_titular);

    @GET("hoagres/actualizaciones/cambio/titular/{identidad_titular}")
    Call< ArrayList<HogarActualizaciones>> getCambio_Titulares(@Path("identidad_titular") String identidad_titular);

    @GET("hogares/hogar/informacion/detalle/{identidad}")
    Call< ArrayList<HogarInformacion>> getDatos_Hogar(@Path("identidad") String identidad);

    @GET("hogares/hogar/informacion/nucleo/{hogar}")
    Call< ArrayList<NucleoHogar>> getDatos_Personas(@Path("hogar") String hogar);

    @GET("hogares/hogar/pagos/titular/{identidad}")
    Call< ArrayList<HistorialPago>> getHistorial_Pagos(@Path("identidad") String Identidad);
}
