package com.map_movil.map_movil.presenter.solicitud;

import android.content.Context;

import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;
import java.util.List;

public interface SolicitudesPresenter{
        void getSolicitudesGestionadasUser(Integer intCodUser, String strSimbolo, Context objContext);//Repository
        void showDataSolicitudesGestionadasUser(ArrayList<SolicitudesUsuario> objSolicitudesUsuario); //View
        void showError(String strError);//View
}
