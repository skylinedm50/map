package com.map_movil.map_movil.interactor.reportes;

import com.map_movil.map_movil.presenter.reportes.ReportesPresenter;
import com.map_movil.map_movil.repository.reportes.ReportesRepository;
import com.map_movil.map_movil.repository.reportes.ReportesRepositoryImpl;

public class ReportesInteractorImpl  implements  ReportesInteractor{
    private ReportesPresenter reportesPresenter;
    private ReportesRepository reportesRepository;

    public ReportesInteractorImpl(ReportesPresenter reportesPresenter) {
        this.reportesPresenter = reportesPresenter;
        reportesRepository = new ReportesRepositoryImpl(this.reportesPresenter);
    }

    @Override
    public void getRendimiento() {
        reportesRepository.getRendimiento();
    }
}
