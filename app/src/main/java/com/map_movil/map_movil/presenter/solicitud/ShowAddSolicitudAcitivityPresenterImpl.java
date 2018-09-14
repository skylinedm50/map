package com.map_movil.map_movil.presenter.solicitud;

import android.content.Context;

import com.map_movil.map_movil.interactor.solicitudes.ShowAddSolicitudAcitivityInteractor;
import com.map_movil.map_movil.interactor.solicitudes.ShowAddSolicitudAcitivityInteractorImpl;
import com.map_movil.map_movil.model.HogarByTitular;
import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.view.solicitudes.ShowAddSolicitudAcitivityView;

import java.util.ArrayList;

public class ShowAddSolicitudAcitivityPresenterImpl implements ShowAddSolicitudAcitivityPresenter {
    private ShowAddSolicitudAcitivityView showAddSolicitudAcitivityView;
    private ShowAddSolicitudAcitivityInteractor showAddSolicitudAcitivityInteractor;
    private Context context;

    public ShowAddSolicitudAcitivityPresenterImpl(ShowAddSolicitudAcitivityView showAddSolicitudAcitivityView, Context context){
        this.context = context;
        this.showAddSolicitudAcitivityView = showAddSolicitudAcitivityView;
        showAddSolicitudAcitivityInteractor = new ShowAddSolicitudAcitivityInteractorImpl(this, context);
    }


    @Override
    public void findDataForCreateSolicitud(String strIdentidadTitular) {
        showAddSolicitudAcitivityInteractor.findDataForCreateSolicitud(strIdentidadTitular);
    }

    @Override
    public void findSolicitudSave(int intCodSolicitud) {
        showAddSolicitudAcitivityInteractor.findSolicitudSaved(intCodSolicitud);
    }

    @Override
    public void saveSolicitud(SolicitudesDownload solicitudesDownload, HogarLigth hogarLigth, int intCodUser) {
        showAddSolicitudAcitivityInteractor.saveSolicitud(solicitudesDownload, hogarLigth, intCodUser);
    }

    @Override
    public void showSolicitud(InfoSolicitud infoSolicitud, ArrayList<HogarLigth> hogarLigthArrayList) {
        showAddSolicitudAcitivityView.showSolicitud(infoSolicitud, hogarLigthArrayList);
    }

    @Override
    public void showOnlyInforHogForCreateSolicitud(HogarByTitular hogarByTitular, ArrayList<HogarLigth> hogarLigthArrayList) {
        showAddSolicitudAcitivityView.showOnlyInforHogForCreateSolicitud(hogarByTitular, hogarLigthArrayList);
    }

    @Override
    public void showMessage(String strMessage) {
        showAddSolicitudAcitivityView.showMessage(strMessage);
    }

    @Override
    public void finishCreationSolicitud() {
        showAddSolicitudAcitivityView.finishCreationSolicitud();
    }
}
