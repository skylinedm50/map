package com.map_movil.map_movil.presenter.planilla;

import com.map_movil.map_movil.interactor.planilla.PlanillaInteractor;
import com.map_movil.map_movil.interactor.planilla.PlanillaInteractorImpl;
import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.view.planilla.PlanillaView;

import java.util.List;

public class PlanillaPresenterImpl implements PlanillaPresenter {
    private PlanillaView planillaView;
    private PlanillaInteractor planillaInteractor;


    public PlanillaPresenterImpl(PlanillaView planillaView){
        this.planillaView = planillaView;
        this.planillaInteractor = new PlanillaInteractorImpl(this);
    }


    @Override
    public void getPagos() {
        planillaInteractor.getPagos();
    }

    @Override
    public void cargarPagos(List<Pagos> pagos) {
        planillaView.cargarPagos(pagos);
    }
}
