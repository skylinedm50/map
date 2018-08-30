package com.map_movil.map_movil.repository.local.validar_hogares;

import android.content.Context;
import com.map_movil.map_movil.model.Realm.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenter;
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


        this.listarValidacionesPresenter.MostarValidaciones(hogares );
    }

    @Override
    public int[] validacion_hogar(int hog_hogar) {

        int[] validacion_hogar = new int [2];

        RealmResults<Hogar_Validar> personas_hogares = this.realm.where(Hogar_Validar.class)
                .distinct("hog_hogar","per_persona")
                .equalTo("hog_hogar", hog_hogar)
                .equalTo("per_estado_descripcion","Activo")
                .findAll();

        validacion_hogar[0] = personas_hogares.size();

        RealmResults<Hogar_Validaciones_Realizadas> validaciones_realizadas = this.realm.where(Hogar_Validaciones_Realizadas.class)
                .distinct("hog_hogar", "per_persona")
                .equalTo("hog_hogar", hog_hogar)
                .findAll();

        validacion_hogar[1] = validaciones_realizadas.size();

        return validacion_hogar;
    }
}
