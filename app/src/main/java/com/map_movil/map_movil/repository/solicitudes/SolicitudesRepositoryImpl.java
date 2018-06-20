package com.map_movil.map_movil.repository.solicitudes;

import android.content.Context;

import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.model.SolicitudesUsuario;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudesRepositoryImpl implements SolicitudesRepository{
    private SolicitudesPresenter objSolicitudesPresenter;
    private ApiAdapterSolicitudes objApiAdapterSolicitudes;
    private ApiServiceSolicitudes objApiServiceSolicitudes;

    public SolicitudesRepositoryImpl(SolicitudesPresenter objSolicitudesPresenter) {
        this.objSolicitudesPresenter = objSolicitudesPresenter;
        objApiAdapterSolicitudes = new ApiAdapterSolicitudes();
        objApiServiceSolicitudes = objApiAdapterSolicitudes.getClientService();
    }

    @Override
    public void getSolicitudesGestionadasUser(Integer intCodUser, String strSimbolo, Context objConext) {

        Call<ArrayList<SolicitudesUsuario>> call = objApiServiceSolicitudes.getSolicitudesFinalizadasUsuario(intCodUser, strSimbolo);

        call.enqueue(new Callback<ArrayList<SolicitudesUsuario>>() {
            @Override
            public void onResponse(Call<ArrayList<SolicitudesUsuario>> call, Response<ArrayList<SolicitudesUsuario>> response) {
                if(response.body() != null){
                    ArrayList<SolicitudesUsuario> listSolicitudes = response.body();
                   objSolicitudesPresenter.showDataSolicitudesGestionadasUser(listSolicitudes);

                }else{
                    objSolicitudesPresenter.showError("No se detectaron datos.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SolicitudesUsuario>> call, Throwable t) {
                objSolicitudesPresenter.showError(t.getMessage());
            }
        });
        objSolicitudesPresenter.showDataSolicitudesGestionadasUser(new ArrayList<SolicitudesUsuario>());
    }

}
