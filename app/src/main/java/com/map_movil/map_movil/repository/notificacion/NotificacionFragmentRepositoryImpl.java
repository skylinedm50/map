package com.map_movil.map_movil.repository.notificacion;

import com.map_movil.map_movil.presenter.notificacion.NotificacionFragmentPresenter;

import java.util.ArrayList;

public class NotificacionFragmentRepositoryImpl implements NotificacionFragmentRepository {
    private NotificacionFragmentPresenter notificacionFragmentPresenter;

    public NotificacionFragmentRepositoryImpl(NotificacionFragmentPresenter notificacionFragmentPresenter){
        this.notificacionFragmentPresenter = notificacionFragmentPresenter;
    }

    @Override
    public void getRegiones() {

    }

    @Override
    public void getOficionas(int intCodRegion) {

    }

    @Override
    public void getUsuarios(int intCodOficina) {

    }

    @Override
    public void sendNotificacion(ArrayList<String> arrayListUsuarios, String strNotificacion) {

    }
}
