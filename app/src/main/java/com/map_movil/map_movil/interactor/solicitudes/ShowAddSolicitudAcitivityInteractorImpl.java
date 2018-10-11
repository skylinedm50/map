package com.map_movil.map_movil.interactor.solicitudes;

import android.content.Context;

import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.presenter.solicitud.ShowAddSolicitudAcitivityPresenter;
import com.map_movil.map_movil.repository.solicitudes.ShowAddSolicitudAcitivityRepository;
import com.map_movil.map_movil.repository.solicitudes.ShowAddSolicitudAcitivityRepositoryImpl;

public class ShowAddSolicitudAcitivityInteractorImpl implements ShowAddSolicitudAcitivityInteractor {
    private ShowAddSolicitudAcitivityPresenter showAddSolicitudAcitivityPresenter;
    private ShowAddSolicitudAcitivityRepository showAddSolicitudAcitivityRepository;
    private Context context;

    public ShowAddSolicitudAcitivityInteractorImpl(ShowAddSolicitudAcitivityPresenter showAddSolicitudAcitivityPresenter, Context context) {
        this.context = context;
        this.showAddSolicitudAcitivityPresenter = showAddSolicitudAcitivityPresenter;
        this.showAddSolicitudAcitivityRepository = new ShowAddSolicitudAcitivityRepositoryImpl(showAddSolicitudAcitivityPresenter, context);
    }


    @Override
    public void findDataForCreateSolicitud(String strIdentidadTitular) {
        if (BroadCastInternet.isConnected) {
            showAddSolicitudAcitivityRepository.findDataServerForCreateSolicitud(strIdentidadTitular);
        } else {
            showAddSolicitudAcitivityRepository.findDataLocalForCreateSolicitud(strIdentidadTitular);
        }
    }

    @Override
    public void findSolicitudSaved(int intCodSolicitud) {
        if (BroadCastInternet.isConnected && intCodSolicitud > 0) {
            showAddSolicitudAcitivityRepository.findSolicitudSavedServe(intCodSolicitud);
        } else {
            showAddSolicitudAcitivityRepository.findSolicitudSavedLocal(intCodSolicitud);
        }
    }

    @Override
    public void saveSolicitud(SolicitudesDownload solicitudesDownload, HogarLigth hogarLigth, int intCodUser) {
        if (BroadCastInternet.isConnected) {
            showAddSolicitudAcitivityRepository.saveServerSolicitud(solicitudesDownload, hogarLigth, intCodUser);
        } else {
            showAddSolicitudAcitivityRepository.saveLocalSolicitud(solicitudesDownload, hogarLigth, intCodUser);
        }
    }
}
