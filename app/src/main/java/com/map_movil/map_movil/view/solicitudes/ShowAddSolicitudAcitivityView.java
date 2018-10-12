package com.map_movil.map_movil.view.solicitudes;

import com.map_movil.map_movil.model.HogarByTitular;
import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.SolicitudesDownload;

import java.util.ArrayList;

public interface ShowAddSolicitudAcitivityView {
    void findDataCreateSolicitud(String strIdentidadTitular);
    void findSolicitudSave(int intCodSolicitud);
    void saveSolicitud();
    void showSolicitud(InfoSolicitud infoSolicitud, ArrayList<HogarLigth> hogarLigthArrayList);
    void showOnlyInforHogForCreateSolicitud(HogarByTitular hogarByTitular, ArrayList<HogarLigth> hogarLigthArrayList);
    void showMessage(String strMessage);
    void finishCreationSolicitud(int offline);
}
