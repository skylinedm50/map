package com.map_movil.map_movil.interactor.solicitudes;

import com.map_movil.map_movil.presenter.solicitud.SolicitudesFragmentPresenter;
import com.map_movil.map_movil.repository.solicitudes.SolicitudesFragmentRepository;
import com.map_movil.map_movil.repository.solicitudes.SolicitudesFragmentRepositoryImpl;

public class SolicitudesFragmentInteractorImpl implements SolicitudesFragmentInteractor {
    private SolicitudesFragmentPresenter solicitudesFragmentPresenter;
    private SolicitudesFragmentRepository solicitudesFragmentRepository;

    public SolicitudesFragmentInteractorImpl(SolicitudesFragmentPresenter solicitudesFragmentPresenter){
        this.solicitudesFragmentPresenter = solicitudesFragmentPresenter;
        this.solicitudesFragmentRepository = new SolicitudesFragmentRepositoryImpl(solicitudesFragmentPresenter);
    }

    @Override
    public void downloadSolicitudes(String Aldea, int intCodUser) {
        this.solicitudesFragmentRepository.downloadSolicitudes(Aldea, intCodUser);
    }
}
