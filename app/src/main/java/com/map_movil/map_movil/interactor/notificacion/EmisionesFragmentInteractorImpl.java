package com.map_movil.map_movil.interactor.notificacion;

import com.map_movil.map_movil.presenter.notificacion.EmisionesFragmentPresenter;
import com.map_movil.map_movil.repository.notificacion.EmisionesFragmentRepository;
import com.map_movil.map_movil.repository.notificacion.EmisionesFragmentRepositoryImpl;

public class EmisionesFragmentInteractorImpl implements EmisionesFragmentInteractor {
    private EmisionesFragmentPresenter emisionesFragmentPresenter;
    private EmisionesFragmentRepository emisionesFragmentRepository;

    public EmisionesFragmentInteractorImpl(EmisionesFragmentPresenter emisionesFragmentPresenter) {
        this.emisionesFragmentPresenter = emisionesFragmentPresenter;
        emisionesFragmentRepository = new EmisionesFragmentRepositoryImpl(emisionesFragmentPresenter);
    }

    @Override
    public void getEmisionesSend(int intCodUser) {
        emisionesFragmentRepository.getEmisionesSend(intCodUser);
    }

    @Override
    public void getEmisionesReceive(int intCodUser) {
        emisionesFragmentRepository.getEmisionesReceive(intCodUser);
    }
}
