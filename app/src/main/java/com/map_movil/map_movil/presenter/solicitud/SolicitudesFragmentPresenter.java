package com.map_movil.map_movil.presenter.solicitud;

import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;

public interface SolicitudesFragmentPresenter {
    void downloadSolicitudes(String Aldea, int intCodUser);
    void getSolicitudesGestionadas(int intCodUser, String strSimbolo);
    void showSolicitudesGestionadas(ArrayList<SolicitudesUsuario> arrayListSolicitudes);
    void finishDownloadSolicitudes();
    void changePorcentage(int intPorcentage);
    void showError(String strError);
    void synchronizeWithServer();
}
