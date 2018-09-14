package com.map_movil.map_movil.repository.solicitudes;

import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.SolicitudesDownload;

public interface ShowAddSolicitudAcitivityRepository {
    void findDataServerForCreateSolicitud(String strIdentidadTitular);
    void findDataLocalForCreateSolicitud(String strIdentidadTitular);
    void findNucleoHogar(int intCodHogar);
    void findSolicitudSavedLocal(int intCodSolicitud);
    void findSolicitudSavedServe(int intCodSolicitud);
    void saveServerSolicitud(SolicitudesDownload solicitudesDownload, HogarLigth hogarLigth, int intCodUser);
    void saveLocalSolicitud(SolicitudesDownload solicitudesDownload, HogarLigth hogarLigth, int intCodUser);
}
