package com.map_movil.map_movil.view.validar_hogares;

import com.map_movil.map_movil.model.Realm.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;

import java.util.ArrayList;

import io.realm.RealmResults;

public interface ValidarNucleoView {

    void BuscarDatos(String identidad);
    void MostarDatos(ArrayList<String[]> Personas , RealmResults<Hogar_Validaciones_Realizadas> hogar_validaciones_realizadas);
    void MostarPopUp(int titular , String Persona , int index);

}
