package com.map_movil.map_movil.repository.notificacion;

import java.util.ArrayList;

public interface NotificacionFragmentRepository {
    void getRegiones();
    void getOficionas(int intCodRegion);
    void getUsuarios(int intCodOficina);

    void sendNotificacion(ArrayList<String> arrayListUsuarios, String strNotificacion);
}
