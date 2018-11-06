package com.map_movil.map_movil.view.notificacion;

import java.util.ArrayList;

public interface NotificacionFragmentView {
    void getRegiones();
    void showRegiones(ArrayList<String> arrayListRegiones);
    void getOficionas(int intCodRegion);
    void showOficinas(ArrayList<String> arrayListOficinas);
    void getUsuarios(int intCodOficina);
    void showUsuarios(ArrayList<String> arrayListUsuarios);

    void sendNotificacion(ArrayList<String> arrayListUsuarios, String strNotificacion);
    void showMessage(String strMessage);
}