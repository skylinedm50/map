package com.map_movil.map_movil.interactor.planilla;

import com.map_movil.map_movil.presenter.planilla.PlanillaPresenter;
import com.map_movil.map_movil.repository.planilla.PlanillaRepository;
import com.map_movil.map_movil.repository.planilla.PlanillaRepositoryImpl;

public class PlanillaInteractorImpl implements PlanillaInteractor {
    private PlanillaPresenter planillaPresenter;
    private PlanillaRepository planillaRepository;

    public PlanillaInteractorImpl(PlanillaPresenter planillaPresenter){
        this.planillaPresenter = planillaPresenter;
        this.planillaRepository = new PlanillaRepositoryImpl(planillaPresenter);
    }

    @Override
    public void getPagos() {
        planillaRepository.getPagos();
    }
}
