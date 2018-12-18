package com.map_movil.map_movil.presenter.notificacion;

import com.map_movil.map_movil.model.Emision;

import java.util.ArrayList;

public interface EmisionesFragmentPresenter {
    void getEmisiones(int intType);
    void getEmisionesSend();
    void getEmisionesReceive();
    void showEmisiones(ArrayList<Emision> emisiones);
    void showMessage(String strShowMessage);
    void showMessageForEmptyData(boolean bolValue);
}
