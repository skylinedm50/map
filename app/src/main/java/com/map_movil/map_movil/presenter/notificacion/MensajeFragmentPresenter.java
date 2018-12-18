package com.map_movil.map_movil.presenter.notificacion;

import com.google.gson.JsonArray;

public interface MensajeFragmentPresenter {
    void sendMessage(JsonArray arrayMunicipio, String strMessage);
    void closeActivity();
    void showMessage(String strMessage);
    void validData();
}
