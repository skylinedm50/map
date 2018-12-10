package com.map_movil.map_movil.repository.notificacion;

import com.google.gson.JsonArray;

public interface MensajeFragmentRepository {
    void sendMessage(JsonArray arrayMunicipio, int intCodUser, String strMessage);
}
