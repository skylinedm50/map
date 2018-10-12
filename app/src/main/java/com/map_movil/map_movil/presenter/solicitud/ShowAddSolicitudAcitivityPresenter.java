package com.map_movil.map_movil.presenter.solicitud;

import com.map_movil.map_movil.model.HogarByTitular;
import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.SolicitudesDownload;

import java.util.ArrayList;

public interface ShowAddSolicitudAcitivityPresenter {
    void findDataForCreateSolicitud(String strIdentidadTitular);
    void findSolicitudSave(int intCodSolicitud);
    void saveSolicitud(SolicitudesDownload solicitudesDownload, HogarLigth hogarLigth, int intCodUser);
    void finishCreationSolicitud(int offline);
    void showSolicitud(InfoSolicitud infoSolicitud, ArrayList<HogarLigth> hogarLigthArrayList);
    void showOnlyInforHogForCreateSolicitud(HogarByTitular hogarByTitular, ArrayList<HogarLigth> hogarLigthArrayList);
    void showMessage(String strMessage);
}
