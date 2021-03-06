package com.map_movil.map_movil.api.hogar;

import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.HogarByTitular;
import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.Hogar_Validar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceHogar {
    @GET("hogares/hogar/ligth/{codigo}")
    Call<ArrayList<HogarLigth>> getLigthInfoHogar(@Path("codigo") Integer intCodHogar);

    @GET("hogares/hogar/titular/{identidad}")
    Call<ArrayList<HogarByTitular>> getHogarByTitular(@Path("identidad") String strCodIdentidad);

    @GET("hogares/validaciones/descarga/hogares/{aldea}/{usuario}")
    Call<ArrayList<Hogar_Validar>> getHogaresValidar(@Path("aldea") String aldea , @Path("usuario") int usuario);

    @GET("hogares/hogar/pagos/aldea/{aldea}")
    Call<ArrayList<HistorialPago>> getHistorialPago(@Path("aldea") String aldea );
}
