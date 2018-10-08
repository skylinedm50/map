package com.map_movil.map_movil.presenter.solicitud;

import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;

public interface SolicitudesFragmentPresenter {
    void getSolicitudesGestionadas(int intCodUser, String strSimbolo);
    void showSolicitudesGestionadas(ArrayList<SolicitudesUsuario> arrayListSolicitudes);
    void showError(String strError);
}
