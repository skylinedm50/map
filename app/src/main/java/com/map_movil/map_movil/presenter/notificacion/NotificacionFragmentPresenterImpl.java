package com.map_movil.map_movil.presenter.notificacion;

import com.map_movil.map_movil.interactor.notificacion.NotificacionFragmentInteractor;
import com.map_movil.map_movil.interactor.notificacion.NotificacionFragmentInteractorImpl;
import com.map_movil.map_movil.view.notificacion.NotificacionFragmentView;

import java.util.ArrayList;

public class NotificacionFragmentPresenterImpl implements NotificacionFragmentPresenter {
    private NotificacionFragmentView notificacionFragmentView;
    private NotificacionFragmentInteractor notificacionFragmentInteractor;

    public NotificacionFragmentPresenterImpl(NotificacionFragmentView notificacionFragmentView){
        this.notificacionFragmentView = notificacionFragmentView;
        notificacionFragmentInteractor = new NotificacionFragmentInteractorImpl(this);
    }


    @Override
    public void getRegiones() {
        notificacionFragmentInteractor.getRegiones();
    }

    @Override
    public void showRegiones(ArrayList<String> arrayListRegiones) {
        notificacionFragmentView.showRegiones(arrayListRegiones);
    }

    @Override
    public void getOficionas(int intCodRegion) {
        notificacionFragmentInteractor.getOficionas(intCodRegion);
    }

    @Override
    public void showOficinas(ArrayList<String> arrayListOficinas) {
        notificacionFragmentView.showOficinas(arrayListOficinas);
    }

    @Override
    public void getUsuarios(int intCodOficina) {
        notificacionFragmentInteractor.getUsuarios(intCodOficina);
    }

    @Override
    public void showUsuarios(ArrayList<String> arrayListUsuarios) {
        notificacionFragmentView.showUsuarios(arrayListUsuarios);
    }


    @Override
    public void showMessage(String strMessage) {
        notificacionFragmentView.showMessage(strMessage);
    }

    @Override
    public void validDataSend(ArrayList<String> arrayListUser, String strNotification, int intCodUser, int intCodOffice) {
        if(arrayListUser.size() > 0 && !strNotification.isEmpty() && intCodUser > 0 && intCodOffice > 0){
            notificacionFragmentInteractor.sendNotificacion(arrayListUser, strNotification, intCodUser, intCodOffice);
        }else{
            notificacionFragmentView.showMessage("Asegúrese de seleccionar uno o más usuarios y de redactar la notificación.");
        }
    }


    @Override
    public void closeActivity() {
        notificacionFragmentView.closeActivity();
    }
}
