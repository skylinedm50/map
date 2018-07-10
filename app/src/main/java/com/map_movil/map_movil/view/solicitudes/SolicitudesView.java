package com.map_movil.map_movil.view.solicitudes;


import com.map_movil.map_movil.model.SolicitudesUsuario;
import java.util.ArrayList;

public interface SolicitudesView {
    void getSolicitudesGestionadasUser(Integer intCodUser, String strSimbolo);//
    void showDataSolicitudesGestionadasUser(ArrayList<SolicitudesUsuario> objSolicitudesUsuario); //View
    void showError(String strError);//View
}
