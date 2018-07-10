package com.map_movil.map_movil.interactor.DescargarValidacion;

import com.map_movil.map_movil.presenter.descargarvalidacion.DescargarValidacionPresenter;
import com.map_movil.map_movil.repository.descargarvalidacion.DescargarValidacionRepository;
import com.map_movil.map_movil.repository.descargarvalidacion.DescargarValidacionRepositoryImpl;

public class DescargarValidacionInteractorImpl implements DescargarValidacionInteractor {

    private DescargarValidacionPresenter descargarValidacionPresenter;
    private DescargarValidacionRepository descargarValidacionRepository;

    public DescargarValidacionInteractorImpl(DescargarValidacionPresenter descargarValidacionPresenter){
        this.descargarValidacionPresenter = descargarValidacionPresenter;
        this.descargarValidacionRepository = new DescargarValidacionRepositoryImpl(this.descargarValidacionPresenter);
    }

    @Override
    public void SolicitarDatos(String aldea, int Usuario) {
        this.descargarValidacionRepository.SolicitarDatos(aldea , Usuario);
    }

    @Override
    public void SolicitarHistorialPago(String aldea) {
        this.descargarValidacionRepository.SolicitarHistorialPago(aldea);
    }

}
