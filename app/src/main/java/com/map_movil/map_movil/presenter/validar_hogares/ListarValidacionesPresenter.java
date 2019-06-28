package com.map_movil.map_movil.presenter.validar_hogares;

import com.map_movil.map_movil.model.Hogar_Validar;

import java.util.ArrayList;

import io.realm.RealmResults;

public interface ListarValidacionesPresenter {
    void BuscarValidaciones();
    void MostarValidaciones(ArrayList<Hogar_Validar> Personas_Hogares);
}
