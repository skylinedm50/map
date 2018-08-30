package com.map_movil.map_movil.repository.solicitudes;

import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesFragmentPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudesFragmentRepositoryImpl implements  SolicitudesFragmentRepository {
    private SolicitudesFragmentPresenter solicitudesFragmentPresenter;
    private ApiAdapterSolicitudes apiAdapterSolicitudes;
    private ApiServiceSolicitudes apiServiceSolicitudes;

    public SolicitudesFragmentRepositoryImpl(SolicitudesFragmentPresenter solicitudesFragmentPresenter){
        this.solicitudesFragmentPresenter = solicitudesFragmentPresenter;
        apiAdapterSolicitudes = new ApiAdapterSolicitudes();
        apiServiceSolicitudes = apiAdapterSolicitudes.getClientService();
    }

    @Override
    public void downloadSolicitudes(String strCodAldea, int intCodUser) {
        Call<ArrayList<SolicitudesDownload>> call = apiServiceSolicitudes.getSolicitudesDownload(intCodUser, strCodAldea);
        call.enqueue(new Callback<ArrayList<SolicitudesDownload>>() {
            @Override
            public void onResponse(Call<ArrayList<SolicitudesDownload>> call, Response<ArrayList<SolicitudesDownload>> response) {
                if(response.isSuccessful() && response.body().size() > 0){
                    ArrayList<SolicitudesDownload> arrayList = response.body();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<SolicitudesDownload>> call, Throwable t) {

            }
        });
    }
}
