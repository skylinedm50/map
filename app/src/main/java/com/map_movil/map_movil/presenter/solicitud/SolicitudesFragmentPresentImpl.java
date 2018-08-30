package com.map_movil.map_movil.presenter.solicitud;

import android.content.Context;

import com.map_movil.map_movil.interactor.solicitudes.SolicitudesFragmentInteractor;
import com.map_movil.map_movil.interactor.solicitudes.SolicitudesFragmentInteractorImpl;
import com.map_movil.map_movil.view.solicitudes.SolicitudesFragmentView;

public class SolicitudesFragmentPresentImpl implements SolicitudesFragmentPresenter {
    private SolicitudesFragmentView solicitudesFragmentView;
    private SolicitudesFragmentInteractor solicitudesFragmentInteractor;
    private Context context;

    public SolicitudesFragmentPresentImpl(SolicitudesFragmentView solicitudesFragmentView, Context context){
        this.solicitudesFragmentView = solicitudesFragmentView;
        this.solicitudesFragmentInteractor = new SolicitudesFragmentInteractorImpl(this);
        this.context = context;
    }

    @Override
    public void downloadSolicitudes(String Aldea, int intCodUser) {
        this.solicitudesFragmentInteractor.downloadSolicitudes(Aldea, intCodUser);
    }

    @Override
    public void finishDownloadSolicitudes() {

    }
}
