package com.map_movil.map_movil.interactor.notificacion;

import com.google.gson.JsonArray;
import com.map_movil.map_movil.presenter.notificacion.MensajeFragmentPresenter;
import com.map_movil.map_movil.repository.notificacion.MensajeFragmentRepository;
import com.map_movil.map_movil.repository.notificacion.MensajeFragmentRepositoryImpl;

public class MensajeFragmentInteractorImpl implements MensajeFragmentInteractor {
    private MensajeFragmentPresenter mensajeFragmentPresenter;
    private MensajeFragmentRepository mensajeFragmentRepository;

    public MensajeFragmentInteractorImpl(MensajeFragmentPresenter mensajeFragmentPresenter){
        this.mensajeFragmentPresenter = mensajeFragmentPresenter;
        mensajeFragmentRepository = new MensajeFragmentRepositoryImpl(this.mensajeFragmentPresenter);
    }

    @Override
    public void sendMessage(JsonArray arrayMunicipio, int intCodUser, String strMessage) {
        mensajeFragmentRepository.sendMessage(arrayMunicipio, intCodUser, strMessage);
    }
}
