package com.map_movil.map_movil.interactor.sincronizar;

import android.content.Context;

import com.map_movil.map_movil.presenter.sincronizar.SincronizarPresenter;
import com.map_movil.map_movil.repository.sincronizar.SincronizarRepository;
import com.map_movil.map_movil.repository.sincronizar.SincronizarRepositoryImpl;

public class SincronizarInteractorImpl implements SincronizarInteractor {

    private SincronizarPresenter sincronizarPresenter;
    private SincronizarRepository sincronizarRepository;

    public SincronizarInteractorImpl(SincronizarPresenter sincronizarPresenter , Context context){
        this.sincronizarPresenter = sincronizarPresenter;
        this.sincronizarRepository = new SincronizarRepositoryImpl(this.sincronizarPresenter , context);
    }

    @Override
    public void Sincronizar(int usuario) {
        this.sincronizarRepository.synchronizeRequestWithServer(usuario);
        this.sincronizarRepository.SincronizarQuejas(usuario);
    }

}
