package com.map_movil.map_movil.interactor.solicitudes;

import android.content.Context;

import com.map_movil.map_movil.presenter.solicitud.SolicitudesPresenter;
import com.map_movil.map_movil.repository.solicitudes.SolicitudesRepository;
import com.map_movil.map_movil.repository.solicitudes.SolicitudesRepositoryImpl;

public class SolicitudesInteractorImpl implements SolicitudesInteractor {
    private SolicitudesPresenter objSolicitudesPresenter;
    private SolicitudesRepository objSolicitudesRepository;

    public SolicitudesInteractorImpl(SolicitudesPresenter objSolicitudesPresenter){
        this.objSolicitudesPresenter = objSolicitudesPresenter;
        this.objSolicitudesRepository = new SolicitudesRepositoryImpl(objSolicitudesPresenter);
    }

    @Override
    public void getSolicitudesGestionadasUser(Integer intCodUser, String strSimbolo, Context objConetxt){
        objSolicitudesRepository.getSolicitudesGestionadasUser(intCodUser, strSimbolo, objConetxt);
    }
}
