package com.map_movil.map_movil.repository.solicitudes;

import com.map_movil.map_movil.model.SolicitudesDownload;

import java.util.ArrayList;

public interface SolicitudesFragmentRepository {
    void downloadSolicitudes(String strCodAldea, int intCodUser);
    void getSolicitudesGestionadas(int intCodUser, String strSimbolo);
    void saveLocalSolicitud(ArrayList<SolicitudesDownload> arrayListSolicitudesDownload);
    void getSolicitudesGestionadasLocalDB(int intCodUser, String strSimbolo);
    void synchronizeWithServer(int intCodUser);
}
