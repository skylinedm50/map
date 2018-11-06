package com.map_movil.map_movil.interactor.notificacion;

import com.map_movil.map_movil.presenter.notificacion.NotificacionFragmentPresenter;
import com.map_movil.map_movil.repository.notificacion.NotificacionFragmentRepository;
import com.map_movil.map_movil.repository.notificacion.NotificacionFragmentRepositoryImpl;

import java.util.ArrayList;

public class NotificacionFragmentInteractorImpl implements  NotificacionFragmentInteractor {
    private NotificacionFragmentPresenter notificacionFragmentPresenter;
    private NotificacionFragmentRepository notificacionFragmentRepository;


    public NotificacionFragmentInteractorImpl(NotificacionFragmentPresenter notificacionFragmentPresenter){
        this.notificacionFragmentPresenter = notificacionFragmentPresenter;
        notificacionFragmentRepository = new NotificacionFragmentRepositoryImpl(notificacionFragmentPresenter);
    }

    @Override
    public void getRegiones() {
        notificacionFragmentRepository.getRegiones();
    }

    @Override
    public void getOficionas(int intCodRegion) {
        notificacionFragmentRepository.getOficionas(intCodRegion);
    }

    @Override
    public void getUsuarios(int intCodOficina) {
        notificacionFragmentRepository.getUsuarios(intCodOficina);
    }

    @Override
    public void sendNotificacion(ArrayList<String> arrayListUsuarios, String strNotificacion) {
        notificacionFragmentRepository.sendNotificacion(arrayListUsuarios, strNotificacion);
    }
}
