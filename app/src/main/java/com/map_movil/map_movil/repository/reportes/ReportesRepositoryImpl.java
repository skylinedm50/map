package com.map_movil.map_movil.repository.reportes;

import com.map_movil.map_movil.api.reportes.ApiAdapterReportes;
import com.map_movil.map_movil.api.reportes.ApiServiceReportes;
import com.map_movil.map_movil.model.Rendimiento;
import com.map_movil.map_movil.presenter.reportes.ReportesPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportesRepositoryImpl implements ReportesRepository{
    private ReportesPresenter reportesPresenter;
    private ApiServiceReportes serviceReportes;
    private ApiAdapterReportes adapterReportes;
    private String strNotData = "No se detectaron datos.";

    public ReportesRepositoryImpl(ReportesPresenter reportesPresenter) {
        this.reportesPresenter = reportesPresenter;
        adapterReportes = new ApiAdapterReportes();
        serviceReportes = adapterReportes.getClientService();
    }

    @Override
    public void getRendimiento() {
        Call<ArrayList<Rendimiento>> call = serviceReportes.getRendimiento();
        call.enqueue(new Callback<ArrayList<Rendimiento>>() {
            @Override
            public void onResponse(Call<ArrayList<Rendimiento>> call, Response<ArrayList<Rendimiento>> response) {
                if(response.isSuccessful() && !response.body().isEmpty()){
                    reportesPresenter.showRendimiento(response.body());
                }else{
                    reportesPresenter.showMessage(strNotData);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rendimiento>> call, Throwable t) {
                reportesPresenter.showMessage(t.getMessage());
            }
        });
    }
}
