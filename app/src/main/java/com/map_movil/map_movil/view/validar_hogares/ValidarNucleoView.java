package com.map_movil.map_movil.view.validar_hogares;

import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import io.realm.RealmResults;

public interface ValidarNucleoView {

    void BuscarDatos(String identidad);
    void MostarDatos(RealmResults<Hogar_Validar> Personas);
    void MostarPopUp(int titular , String Persona);

}
