package com.map_movil.map_movil.repository.solicitudes;

import android.widget.Adapter;

import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.interactor.solicitudes.AdapterRecyclerViewSolicitudes;
import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudesRepo {
    private ApiAdapterSolicitudes objApiAdapterSolicitudes;
    private ApiServiceSolicitudes objApiServiceSolicitudes;
    private ArrayList<SolicitudesUsuario> arrSolicitudesUser;
    private AdapterRecyclerViewSolicitudes objAdapter;

    public SolicitudesRepo(AdapterRecyclerViewSolicitudes objAdapter){
        objApiAdapterSolicitudes = new ApiAdapterSolicitudes();
        objApiServiceSolicitudes = objApiAdapterSolicitudes.getClientService();
        this.objAdapter = objAdapter;
    }


    public ArrayList<SolicitudesUsuario> getSolicitudesGestionadasUser(Integer intCodUser, String strSimbolo) {
        arrSolicitudesUser = new ArrayList<SolicitudesUsuario>();

        Call<ArrayList<SolicitudesUsuario>> call = objApiServiceSolicitudes.getSolicitudesFinalizadasUsuario(intCodUser, strSimbolo);

        call.enqueue(new Callback<ArrayList<SolicitudesUsuario>>() {
            @Override
            public void onResponse(Call<ArrayList<SolicitudesUsuario>> call, Response<ArrayList<SolicitudesUsuario>> response) {
                if(response.body() != null){
                    arrSolicitudesUser = response.body();

                }else{
                    arrSolicitudesUser = new ArrayList<SolicitudesUsuario>();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SolicitudesUsuario>> call, Throwable t) {
                arrSolicitudesUser = new ArrayList<SolicitudesUsuario>();
            }
        });

        return arrSolicitudesUser;
    }

}
