package com.map_movil.map_movil.interactor.notificacion;

import com.google.gson.JsonArray;

public interface MensajeFragmentInteractor {
    void sendMessage(JsonArray arrayMunicipio, int intCodUser, String strMessage);
}
