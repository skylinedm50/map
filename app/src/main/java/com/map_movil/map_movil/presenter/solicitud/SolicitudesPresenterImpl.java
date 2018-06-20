package com.map_movil.map_movil.presenter.solicitud;

import android.content.Context;

import com.map_movil.map_movil.interactor.solicitudes.SolicitudesInteractor;
import com.map_movil.map_movil.interactor.solicitudes.SolicitudesInteractorImpl;
import com.map_movil.map_movil.model.SolicitudesUsuario;
import com.map_movil.map_movil.view.solicitudes.SolicitudesView;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesPresenterImpl implements SolicitudesPresenter{
    private SolicitudesView objSolicitudesView;
    private SolicitudesInteractor objSolicitudesInteractor;

    public SolicitudesPresenterImpl(SolicitudesView objSolicitudesView){
        this.objSolicitudesView = objSolicitudesView;
        this.objSolicitudesInteractor = new SolicitudesInteractorImpl(this);
    }

    @Override
    public void getSolicitudesGestionadasUser(Integer intCodUser, String strSimbolo, Context objContext) {

        objSolicitudesInteractor.getSolicitudesGestionadasUser(intCodUser, strSimbolo, objContext);
    }

    @Override
    public void showDataSolicitudesGestionadasUser(ArrayList<SolicitudesUsuario> objSolicitudesUsuario) {
        objSolicitudesView.showDataSolicitudesGestionadasUser(objSolicitudesUsuario);
    }

    @Override
    public void showError(String strError) {
        objSolicitudesView.showError(strError);
    }
}
