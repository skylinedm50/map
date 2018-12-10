package com.map_movil.map_movil.view.notificacion;

import com.google.gson.JsonArray;

public interface MensajeFragmentView {
    void closeActivity();
    void showMessage(String strMessage);
    void showProgressBar(boolean bolValue);
}
