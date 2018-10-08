package com.map_movil.map_movil.interactor.solicitudes;

import android.content.Context;

import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesFragmentPresenter;
import com.map_movil.map_movil.repository.solicitudes.SolicitudesFragmentRepository;
import com.map_movil.map_movil.repository.solicitudes.SolicitudesFragmentRepositoryImpl;

public class SolicitudesFragmentInteractorImpl implements SolicitudesFragmentInteractor {
    private SolicitudesFragmentPresenter solicitudesFragmentPresenter;
    private SolicitudesFragmentRepository solicitudesFragmentRepository;

    public SolicitudesFragmentInteractorImpl(SolicitudesFragmentPresenter solicitudesFragmentPresenter, Context context){
        this.solicitudesFragmentPresenter = solicitudesFragmentPresenter;
        this.solicitudesFragmentRepository = new SolicitudesFragmentRepositoryImpl(solicitudesFragmentPresenter, context);
    }

    @Override
    public void downloadSolicitudes(String Aldea, int intCodUser) {
        this.solicitudesFragmentRepository.downloadSolicitudes(Aldea, intCodUser);
    }

    @Override
    public void getSolicitudesGestionadas(int intCodUser, String strSimbolo) {
        if(BroadCastInternet.isConnected){
            this.solicitudesFragmentRepository.getSolicitudesGestionadas(intCodUser, strSimbolo);
        }else{
            this.solicitudesFragmentRepository.getSolicitudesGestionadasLocalDB(intCodUser, strSimbolo);
        }
    }

}
