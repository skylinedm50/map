package com.map_movil.map_movil.interactor.planilla;

import android.content.Context;

import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.presenter.planilla.PlanillaPresenter;
import com.map_movil.map_movil.repository.planilla.PlanillaRepository;
import com.map_movil.map_movil.repository.planilla.PlanillaRepositoryImpl;

public class PlanillaInteractorImpl implements PlanillaInteractor {
    private PlanillaPresenter planillaPresenter;
    private PlanillaRepository planillaRepository;

    public PlanillaInteractorImpl(PlanillaPresenter planillaPresenter , Context context){
        this.planillaPresenter = planillaPresenter;
        this.planillaRepository = new PlanillaRepositoryImpl(planillaPresenter , context);
    }

    @Override
    public void getPagos() {
        if(BroadCastInternet.isConnected){
            planillaRepository.getPagos();
        }else{
            planillaRepository.getPagosOffline();
        }

    }

    @Override
    public void getProgramados(String strCodAldea, String strCodpago) {
        if(BroadCastInternet.isConnected){
            this.planillaRepository.getProgramados(strCodAldea , strCodpago);
        }else{
            this.planillaRepository. getProgramadosOffline(strCodAldea , strCodpago);
        }
    }

}
