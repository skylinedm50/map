package com.map_movil.map_movil.interactor.solicitudes;

import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.SolicitudesDownload;

public interface ShowAddSolicitudAcitivityInteractor {
    void findDataForCreateSolicitud(String strIdentidadTitular);
    void findSolicitudSaved(int intCodSolicitud);
    void saveSolicitud(SolicitudesDownload solicitudesDownload, HogarLigth hogarLigth, int intCodUser);
}
