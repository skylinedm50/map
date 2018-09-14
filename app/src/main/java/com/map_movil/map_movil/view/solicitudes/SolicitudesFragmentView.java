package com.map_movil.map_movil.view.solicitudes;

import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;

public interface SolicitudesFragmentView {
    void downloadSolicitudes(String Aldea, int intCodUser);
    void getSolicitudesGestionadas();
    void showSolicitudesGestionadas(ArrayList<SolicitudesUsuario> arrayListSolicitudes);
    void finishDownloadSolicitudes();
    void changePorcentage(String strPorcentage);
    void showError(String strError);
    void synchronizeSolicitudesWithServer();
}
