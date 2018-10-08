package com.map_movil.map_movil.presenter.sincronizar;

import android.content.Context;

import com.map_movil.map_movil.interactor.sincronizar.SincronizarInteractor;
import com.map_movil.map_movil.interactor.sincronizar.SincronizarInteractorImpl;
import com.map_movil.map_movil.view.sincronizar.SincronizarView;

public class SincronizarPresenterImpl implements SincronizarPresenter {

    private SincronizarView sincronizarView;
    private SincronizarInteractor sincronizarInteractor;

    public SincronizarPresenterImpl(SincronizarView sincronizarView , Context context){
        this.sincronizarView = sincronizarView;
        this.sincronizarInteractor = new SincronizarInteractorImpl(this, context);
    }

    @Override
    public void Sincronizar(int usuario) {
        this.sincronizarInteractor.Sincronizar(usuario);
    }

    @Override
    public void MensajeSincronizar(String Mensaje) {
        this.sincronizarView.MensajeSincronizar(Mensaje);
    }

    @Override
    public void EventoCompletado(int evento) {
        sincronizarView.EventoCompletado(evento);
    }
}
