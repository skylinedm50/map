package com.map_movil.map_movil.repository.local.validar_hogares;

import android.content.Context;

import com.map_movil.map_movil.model.HogaresValidar;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenter;
import com.map_movil.map_movil.presenter.validar_hogares.ValidarNucleoPresenter;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListarValidacionesRepositoryImpl implements ListarValidacionesRepository {

    private ListarValidacionesPresenter listarValidacionesPresenter;
    private Realm realm;

    public ListarValidacionesRepositoryImpl(ListarValidacionesPresenter ListarValidacionesPresenter , Context context){

        this.listarValidacionesPresenter = ListarValidacionesPresenter;
        Realm.init(context);
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public void BuscarValidaciones() {
        RealmResults<Hogar_Validar>  hogares  = this.realm.where(Hogar_Validar.class)
                .distinct("hog_hogar","per_identidad",
                        "nombre","hogar_direccion","desc_departamento",
                        "desc_municipio","desc_aldea" , "hog_umbral")
                .equalTo("per_titular",1).findAll();
        this.listarValidacionesPresenter.MostarValidaciones(hogares);
    }
}
