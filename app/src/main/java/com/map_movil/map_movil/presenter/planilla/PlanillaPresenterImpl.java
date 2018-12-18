package com.map_movil.map_movil.presenter.planilla;

import android.content.Context;
import com.map_movil.map_movil.interactor.planilla.PlanillaInteractor;
import com.map_movil.map_movil.interactor.planilla.PlanillaInteractorImpl;
import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.model.PagosExcluido;
import com.map_movil.map_movil.model.PagosProgramados;
import com.map_movil.map_movil.view.planilla.PlanillaView;
import java.util.ArrayList;
import java.util.List;

public class PlanillaPresenterImpl implements PlanillaPresenter {

    private PlanillaView planillaView;
    private PlanillaInteractor planillaInteractor;

    public PlanillaPresenterImpl(PlanillaView planillaView , Context context){
        this.planillaView = planillaView;
        this.planillaInteractor = new PlanillaInteractorImpl(this , context);
    }

    @Override
    public void getPagos() {
        planillaInteractor.getPagos();
    }

    @Override
    public void cargarPagos(List<Pagos> pagos) {
        planillaView.cargarPagos(pagos);
    }

    @Override
    public void getProgramados(String strCodAldea, String strCodpago) {
        this.planillaInteractor.getProgramados(strCodAldea , strCodpago);
    }

    @Override
    public void getExcluidos(String strCodAldea, String strCodpago, String tipoGM) {
        this.planillaInteractor.getExcluidos(strCodAldea , strCodpago , tipoGM);
    }

    @Override
    public void MostarDatosProgramados(ArrayList<PagosProgramados> pagosProgramados) {
        this.planillaView.MostarDatosProgramados(pagosProgramados);
    }

    @Override
    public void MostarExcluidos(ArrayList<PagosExcluido> listexcluidos) {
        this.planillaView.MostarExcluidos(listexcluidos);
    }

}
