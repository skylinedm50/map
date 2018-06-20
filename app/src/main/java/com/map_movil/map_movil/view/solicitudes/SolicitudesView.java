package com.map_movil.map_movil.view.solicitudes;

import android.content.Context;
import android.view.View;

import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;
import java.util.List;

public interface SolicitudesView {
    void getSolicitudesGestionadasUser(Integer intCodUser, String strSimbolo);//
    void showDataSolicitudesGestionadasUser(ArrayList<SolicitudesUsuario> objSolicitudesUsuario); //View
    void showError(String strError);//View
}
