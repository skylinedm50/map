package com.map_movil.map_movil.interactor.solicitudes;

public interface SolicitudesFragmentInteractor {
    void downloadSolicitudes(String Aldea, int intCodUser);
    void getSolicitudesGestionadas(int intCodUser, String strSimbolo);
    void synchronizeWithServer();
}
