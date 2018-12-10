package com.map_movil.map_movil.interactor.notificacion;

import java.util.ArrayList;

public interface NotificacionFragmentInteractor {
    void getRegiones();
    void getOficionas(int intCodRegion);
    void getUsuarios(int intCodOficina);

    void sendNotificacion(ArrayList<String> arrayListUsuarios, String strNotificacion, int intCodUser, int intCodOffice);
}
