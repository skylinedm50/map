package com.map_movil.map_movil.repository.planilla;

import com.map_movil.map_movil.api.planilla.ApiAdapterPlanilla;
import com.map_movil.map_movil.api.planilla.ApiServicePlanilla;
import com.map_movil.map_movil.interactor.planilla.PlanillaInteractor;
import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.presenter.planilla.PlanillaPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanillaRepositoryImpl implements PlanillaRepository {
    private PlanillaPresenter planillaPresenter;
    private PlanillaInteractor planillaInteractor;
    private ApiServicePlanilla servicePlanilla;
    private ApiAdapterPlanilla apiAdapterPlanilla;

    public PlanillaRepositoryImpl(PlanillaPresenter planillaPresenter){
        this.planillaPresenter = planillaPresenter;
        this.apiAdapterPlanilla = new ApiAdapterPlanilla();
        this.servicePlanilla = this.apiAdapterPlanilla.getClientService();
    }

    @Override
    public void getPagos() {
        Call<List<Pagos>> pagos = this.servicePlanilla.getAllPagos();
        pagos.enqueue(new Callback<List<Pagos>>() {
            @Override
            public void onResponse(Call<List<Pagos>> call, Response<List<Pagos>> response) {
                if(response.isSuccessful()){
                    if(response.body().size()>0){
                        planillaPresenter.cargarPagos(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pagos>> call, Throwable t) {

            }
        });
    }
}
