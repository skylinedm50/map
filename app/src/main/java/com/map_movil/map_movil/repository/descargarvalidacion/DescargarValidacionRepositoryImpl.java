package com.map_movil.map_movil.repository.descargarvalidacion;


import com.map_movil.map_movil.api.hogar.ApiAdapterHogar;
import com.map_movil.map_movil.api.hogar.ApiServiceHogar;
import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.HogaresValidar;
import com.map_movil.map_movil.presenter.descargarvalidacion.DescargarValidacionPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescargarValidacionRepositoryImpl implements DescargarValidacionRepository {

    private DescargarValidacionPresenter descargarValidacionPresenter;

    public DescargarValidacionRepositoryImpl(DescargarValidacionPresenter descargarValidacionPresenter){
        this.descargarValidacionPresenter = descargarValidacionPresenter;
    }


    @Override
    public void SolicitarDatos(String aldea, int Usuario) {
        ApiAdapterHogar objAdapter = new ApiAdapterHogar();
        ApiServiceHogar objServicesAdapter = objAdapter.getClientService();
        Call<ArrayList<HogaresValidar>> call = objServicesAdapter.getHogaresValidar(aldea , Usuario);

        call.enqueue(new Callback<ArrayList<HogaresValidar>>() {
            @Override
            public void onResponse(Call<ArrayList<HogaresValidar>> call, Response<ArrayList<HogaresValidar>> response) {
                if(response.isSuccessful()){
                    descargarValidacionPresenter.DescargarDatos(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HogaresValidar>> call, Throwable t) {
                int i = 0;
            }
        });
    }

    @Override
    public void SolicitarHistorialPago(String aldea) {
        ApiAdapterHogar objAdapter = new ApiAdapterHogar();
        ApiServiceHogar objServidesAdapter = objAdapter.getClientService();
        Call<ArrayList<HistorialPago>> call = objServidesAdapter.getHistorialPago(aldea);
        call.enqueue(new Callback<ArrayList<HistorialPago>>() {
            @Override
            public void onResponse(Call<ArrayList<HistorialPago>> call, Response<ArrayList<HistorialPago>> response) {
                if(response.isSuccessful()){
                    descargarValidacionPresenter.DescargarHistorial(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HistorialPago>> call, Throwable t) {

            }
        });
    }


}
