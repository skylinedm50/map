package com.map_movil.map_movil.presenter.validar_hogares;

import android.content.Context;

import com.map_movil.map_movil.interactor.validar_hogares.ListarValidacionesInteractor;
import com.map_movil.map_movil.interactor.validar_hogares.ListarValidacionesInteractorImpl;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.view.validar_hogares.ListarValidacionesView;

import io.realm.RealmResults;

public class ListarValidacionesPresenterImpl implements ListarValidacionesPresenter {

    private ListarValidacionesView listarValidacionesView;
    private ListarValidacionesInteractor listarValidacionesInteractor;

    public ListarValidacionesPresenterImpl(ListarValidacionesView listarValidacionesView , Context context){

        this.listarValidacionesView = listarValidacionesView;
        this.listarValidacionesInteractor = new ListarValidacionesInteractorImpl(this,context);
    }

    @Override
    public void BuscarValidaciones() {
        this.listarValidacionesInteractor.BuscarValidaciones();
    }

    @Override
    public void MostarValidaciones(RealmResults<Hogar_Validar> Personas) {
        this.listarValidacionesView.MostarValidaciones(Personas);
    }
}
