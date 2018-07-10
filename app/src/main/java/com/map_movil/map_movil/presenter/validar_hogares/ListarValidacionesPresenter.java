package com.map_movil.map_movil.presenter.validar_hogares;

import com.map_movil.map_movil.model.Realm.Hogar_Validar;

import io.realm.RealmResults;

public interface ListarValidacionesPresenter {
    void BuscarValidaciones();
    void MostarValidaciones(RealmResults<Hogar_Validar> Personas);
}
