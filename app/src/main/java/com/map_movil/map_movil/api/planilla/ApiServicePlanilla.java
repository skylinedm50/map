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
    @GET("hogares/excluidos/global/{aldea}/{pago}/{usuario}")
    Call<ArrayList<PagosExcluido>> getPlanillaExcluidoInfo(@Path("aldea") String stringCodaldeaSolicitud, @Path("pago") String stringCodPago , @Path("usuario") int usuario);

    @GET("hogares/excluidos/mancomunidad/{aldea}/{pago}/{usuario}")
    Call<ArrayList<PagosExcluido>> getPlanillaExcluidomancInfo(@Path("aldea") String stringCodaldeaSolicitud,@Path("pago") String stringCodPago , @Path("usuario") int usuario);

    @GET("planilla/pagos")
    Call<List<Pagos>> getAllPagos();

    @GET("hogares/programados/{pago}/{aldea}/{usuario}")
    Call<ArrayList<PagosProgramados>> getPagosProgramados(@Path("pago") String stringCodPago, @Path("aldea") String stringCodaldeaSolicitud , @Path("usuario") int usuario);

    @GET("hogares/programados/titular/{pago}/{identidad}/{usuario}")
    Call<ArrayList<PagosProgramados>> getPagosProgamadosbyIdTitular(@Path("pago") int stringCodPago, @Path("identidad") String strIdentidad , @Path("usuario") int usuario);
}
