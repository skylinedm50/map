package com.map_movil.map_movil.presenter.notificacion;

import java.util.ArrayList;

public interface NotificacionFragmentPresenter {
    void getRegiones();
    void showRegiones(ArrayList<String> arrayListRegiones);
    void getOficionas(int intCodRegion);
    void showOficinas(ArrayList<String> arrayListOficinas);
    void getUsuarios(int intCodOficina);
    void showUsuarios(ArrayList<String> arrayListUsuarios);

    void showMessage(String strMessage);
    void validDataSend(ArrayList<String> arrayListUser, String strNotification);
    void showProgressBar(boolean bolValue);
}
