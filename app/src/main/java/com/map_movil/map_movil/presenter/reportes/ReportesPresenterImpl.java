package com.map_movil.map_movil.presenter.reportes;

import com.map_movil.map_movil.interactor.reportes.ReportesInteractor;
import com.map_movil.map_movil.interactor.reportes.ReportesInteractorImpl;
import com.map_movil.map_movil.model.Rendimiento;
import com.map_movil.map_movil.view.reportes.ReportesView;

import java.util.ArrayList;

public class ReportesPresenterImpl implements ReportesPresenter {
    private ReportesView reportesView;
    private ReportesInteractor reportesInteractor;

    public ReportesPresenterImpl(ReportesView reportesView) {
        this.reportesView = reportesView;
        reportesInteractor = new ReportesInteractorImpl(this);
    }

    @Override
    public void getRendimiento() {
        reportesInteractor.getRendimiento();
    }

    @Override
    public void showRendimiento(ArrayList<Rendimiento> arrayList) {
        reportesView.showRendimiento(arrayList);
    }

    @Override
    public void showMessage(String strMessage) {
        reportesView.showMessage(strMessage);
    }
}
