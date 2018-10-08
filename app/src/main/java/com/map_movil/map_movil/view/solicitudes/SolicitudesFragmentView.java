package com.map_movil.map_movil.view.solicitudes;

import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;

public interface SolicitudesFragmentView {
    void getSolicitudesGestionadas();
    void showSolicitudesGestionadas(ArrayList<SolicitudesUsuario> arrayListSolicitudes);
    void showError(String strError);
}
