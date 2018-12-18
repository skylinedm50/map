package com.map_movil.map_movil.repository.notificacion;


import com.map_movil.map_movil.api.notificacion.ApiAdapterNotificacion;
import com.map_movil.map_movil.api.notificacion.ApiServiceNotificacion;
import com.map_movil.map_movil.model.Emision;
import com.map_movil.map_movil.presenter.notificacion.EmisionesFragmentPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmisionesFragmentRepositoryImpl implements EmisionesFragmentRepository {
    private EmisionesFragmentPresenter emisionesFragmentPresenter;
    private ApiServiceNotificacion apiServiceNotificacion;
    private ApiAdapterNotificacion apiAdapterNotificacion;

    public EmisionesFragmentRepositoryImpl(EmisionesFragmentPresenter emisionesFragmentPresenter) {
        this.emisionesFragmentPresenter = emisionesFragmentPresenter;
        apiAdapterNotificacion = new ApiAdapterNotificacion();
        apiServiceNotificacion = apiAdapterNotificacion.getClientService();
    }

    @Override
    public void getEmisionesSend(int intCodUser) {
        Call<ArrayList<Emision>> call = apiServiceNotificacion.getEmisionesByUsuario("enviadas", intCodUser);
        call.enqueue(new Callback<ArrayList<Emision>>() {
            @Override
            public void onResponse(Call<ArrayList<Emision>> call, Response<ArrayList<Emision>> response) {
                if(response.isSuccessful() && response.body().size() > 0){
                    emisionesFragmentPresenter.showEmisiones(response.body());
                    emisionesFragmentPresenter.showMessageForEmptyData(false);
                }else{
                    emisionesFragmentPresenter.showMessage("No se detectaron emisiones enviadas.");
                    emisionesFragmentPresenter.showMessageForEmptyData(true);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Emision>> call, Throwable t) {
                emisionesFragmentPresenter.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getEmisionesReceive(int intCodUser) {
        Call<ArrayList<Emision>> call = apiServiceNotificacion.getEmisionesByUsuario("recibidas", intCodUser);
        call.enqueue(new Callback<ArrayList<Emision>>() {
            @Override
            public void onResponse(Call<ArrayList<Emision>> call, Response<ArrayList<Emision>> response) {
                if(response.isSuccessful() && response.body().size() > 0){
                    emisionesFragmentPresenter.showEmisiones(response.body());
                    emisionesFragmentPresenter.showMessageForEmptyData(false);
                }else{
                    emisionesFragmentPresenter.showMessage("No se detectaron emisiones recibidas.");
                    emisionesFragmentPresenter.showMessageForEmptyData(true);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Emision>> call, Throwable t) {
                emisionesFragmentPresenter.showMessage(t.getMessage());
            }
        });
    }
}
