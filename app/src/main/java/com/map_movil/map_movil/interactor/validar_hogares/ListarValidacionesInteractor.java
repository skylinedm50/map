package com.map_movil.map_movil.interactor.validar_hogares;


import io.realm.RealmResults;

public interface ListarValidacionesInteractor {
    void BuscarValidaciones();
    int [] validacion_hogar(int hog_hogar);
}
