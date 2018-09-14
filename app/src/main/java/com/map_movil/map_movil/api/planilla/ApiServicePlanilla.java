package com.map_movil.map_movil.api.planilla;

import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.model.PagosExcluido;
import com.map_movil.map_movil.model.PagosProgramados;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServicePlanilla {
    @GET("hogares/excluidos/global/{aldea}/{pago}")
    Call<ArrayList<PagosExcluido>> getPlanillaExcluidoInfo(@Path("aldea") String stringCodaldeaSolicitud, @Path("pago") String stringCodPago);

    @GET("hogares/excluidos/mancomunidad/{aldea}/{pago}")
    Call<ArrayList<PagosExcluido>> getPlanillaExcluidomancInfo(@Path("aldea") String stringCodaldeaSolicitud,@Path("pago") String stringCodPago);

    @GET("planilla/pagos")
    Call<List<Pagos>> getAllPagos();

    @GET("hogares/programados/{pago}/{aldea}")
    Call<ArrayList<PagosProgramados>> getPagosProgramados(@Path("pago") String stringCodPago, @Path("aldea") String stringCodaldeaSolicitud);
}
