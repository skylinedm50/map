package com.map_movil.map_movil.presenter.solicitud;

import android.content.Context;

import com.map_movil.map_movil.interactor.solicitudes.SolicitudesFragmentInteractor;
import com.map_movil.map_movil.interactor.solicitudes.SolicitudesFragmentInteractorImpl;
import com.map_movil.map_movil.model.SolicitudesUsuario;
import com.map_movil.map_movil.view.solicitudes.SolicitudesFragmentView;

import java.util.ArrayList;

public class SolicitudesFragmentPresentImpl implements SolicitudesFragmentPresenter {
    private SolicitudesFragmentView solicitudesFragmentView;
    private SolicitudesFragmentInteractor solicitudesFragmentInteractor;
    private Context context;

    public SolicitudesFragmentPresentImpl(SolicitudesFragmentView solicitudesFragmentView, Context context){
        this.solicitudesFragmentView = solicitudesFragmentView;
        this.solicitudesFragmentInteractor = new SolicitudesFragmentInteractorImpl(this, context);
        this.context = context;
    }

    @Override
    public void downloadSolicitudes(String Aldea, int intCodUser) {
        String[] arrayItem = Aldea.toString().split("-");
        this.solicitudesFragmentInteractor.downloadSolicitudes(arrayItem[0], intCodUser);
    }

    @Override
    public void getSolicitudesGestionadas(int intCodUser, String strSimbolo) {
        this.solicitudesFragmentInteractor.getSolicitudesGestionadas(intCodUser, strSimbolo);
    }

    @Override
    public void showSolicitudesGestionadas(ArrayList<SolicitudesUsuario> arrayListSolicitudes) {
        solicitudesFragmentView.showSolicitudesGestionadas(arrayListSolicitudes);
    }

    @Override
    public void finishDownloadSolicitudes() {
        solicitudesFragmentView.finishDownloadSolicitudes();
    }

    @Override
    public void changePorcentage(int intPorcentage) {
        this.solicitudesFragmentView.changePorcentage(String.valueOf(intPorcentage) + "%");
    }

    @Override
    public void showError(String strError) {
        this.solicitudesFragmentView.showError(strError);
    }

    @Override
    public void synchronizeWithServer(int intCodUser) {
        solicitudesFragmentInteractor.synchronizeWithServer(intCodUser);
    }

    @Override
    public void finishSynchronize() {
        solicitudesFragmentView.finishDownloadSolicitudes();
    }
}
