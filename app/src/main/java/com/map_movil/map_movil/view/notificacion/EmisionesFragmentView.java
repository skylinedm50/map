package com.map_movil.map_movil.view.notificacion;

import com.map_movil.map_movil.model.Emision;

import java.util.ArrayList;

public interface EmisionesFragmentView {
    void getEmisiones(int intType);
    void showEmisiones(ArrayList<Emision> emisiones);
    void showMessage(String strMessage);
    void showProgressBar(boolean bolValue);
    void showMessageForEmptyData(boolean bolValue);
}
