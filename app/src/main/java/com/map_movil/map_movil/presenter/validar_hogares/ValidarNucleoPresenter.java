package com.map_movil.map_movil.presenter.validar_hogares;

import com.map_movil.map_movil.model.Realm.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;

import io.realm.RealmResults;

public interface ValidarNucleoPresenter {
    void BuscarDatos(String identidad);
    void MostarDatos(RealmResults<Hogar_Validar> Personas , RealmResults<Hogar_Validaciones_Realizadas> validaciones_realizadas);
    void GuardarValidacion(int per_persona , int hog_hogar , int identidad ,
                           int act_compromiso , int actualizar , int part_nacimiento ,
                           int cons_educacion , int desagregar , int debe_documento  ,
                           int incorporacion  , int cambio_titular);


}
