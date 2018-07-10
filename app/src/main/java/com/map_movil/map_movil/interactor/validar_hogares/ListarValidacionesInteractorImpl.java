package com.map_movil.map_movil.interactor.validar_hogares;

import android.content.Context;

import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenter;
import com.map_movil.map_movil.repository.local.validar_hogares.ListarValidacionesRepository;
import com.map_movil.map_movil.repository.local.validar_hogares.ListarValidacionesRepositoryImpl;

public class ListarValidacionesInteractorImpl implements ListarValidacionesInteractor {

    private ListarValidacionesPresenter listarValidacionesPresenter;
    private ListarValidacionesRepository listarValidacionesRepository;

    public  ListarValidacionesInteractorImpl(ListarValidacionesPresenter listarValidacionesPresenter , Context context){
        this.listarValidacionesPresenter  = listarValidacionesPresenter;
        this.listarValidacionesRepository = new ListarValidacionesRepositoryImpl(this.listarValidacionesPresenter , context);
    }

    @Override
    public void BuscarValidaciones() {
        this.listarValidacionesRepository.BuscarValidaciones();
    }
}
