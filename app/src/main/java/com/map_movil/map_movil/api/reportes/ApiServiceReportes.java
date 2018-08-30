package com.map_movil.map_movil.api.reportes;

import com.map_movil.map_movil.model.EstadosCantidadSolicitudes;
import com.map_movil.map_movil.model.SolicitudesCantidadPorEstado;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceReportes {
    @GET("solicitudes/cifras/cantidad/estados")
    Call< ArrayList<EstadosCantidadSolicitudes>> getCifrasCantidadDatos();

    @GET("solicitudes/cifras/cantidad/estados/{FechaInicio}/{FechaFinal}")
    Call< ArrayList<EstadosCantidadSolicitudes>>getCifrasCantidadDatosPorFechas(@Path("FechaInicio") String FechaInicio, @Path("FechaFinal") String FechaFinal);

    @GET("solicitudes/cifras/cantidad/estados/{usuario}/{fecha_inicio}/{fecha_fin}")
    Call<ArrayList<EstadosCantidadSolicitudes>>getCantidadEstadosPorUsuario(@Path("usuario") int Usuario,@Path("fecha_inicio") String FechaInicio, @Path("fecha_fin") String FechaFinal);

    @GET("http://192.168.90.23:3004/index.php/solicitudes/estado/5")
    Call<ArrayList<SolicitudesCantidadPorEstado>>getSolicitudesRealizadas();
}
