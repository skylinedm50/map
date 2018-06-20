package com.map_movil.map_movil.repository.solicitudes;

import android.content.Context;

public interface SolicitudesRepository {
    void getSolicitudesGestionadasUser(Integer intCodUser,  String strSimbolo, Context objContext);
}
