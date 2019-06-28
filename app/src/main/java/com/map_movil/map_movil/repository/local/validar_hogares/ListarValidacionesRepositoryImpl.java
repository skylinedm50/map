package com.map_movil.map_movil.repository.local.validar_hogares;

import android.content.Context;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.model.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.model.Hogar_Validar;
import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ListarValidacionesRepositoryImpl implements ListarValidacionesRepository {

    private ListarValidacionesPresenter listarValidacionesPresenter;
    private RealmConfig realmConfig;
    private Context context;

    public ListarValidacionesRepositoryImpl(ListarValidacionesPresenter ListarValidacionesPresenter , Context context){

        this.listarValidacionesPresenter = ListarValidacionesPresenter;
        this.context = context;
    }

    @Override
    public void BuscarValidaciones() {

        ArrayList<Hogar_Validar> ListaHogares = new ArrayList();
        this.realmConfig = new RealmConfig(context);
        this.realmConfig.getRealm().beginTransaction();

        RealmResults<Hogar_Validar>  hogares  =  this.realmConfig.getRealm().where(Hogar_Validar.class)
                .distinct("hog_hogar","per_identidad",
                        "nombre"        ,"hogar_direccion","desc_departamento",
                        "desc_municipio","desc_aldea"     ,"hog_umbral"       , "cod_municipio")
                .equalTo("per_titular",1)
                .equalTo("hog_validar",1)
                .sort("cod_municipio")
                .findAll();

        String municipio="";
        for(int i = 0; i < hogares.size(); i++){

            Hogar_Validar val  =  new Hogar_Validar();
            val.setHog_hogar        (   hogares.get(i).getHog_hogar()                            );
            val.setPer_identidad    (   hogares.get(i).getPer_identidad()                        );
            val.setNombre           (   hogares.get(i).getNombre()                               );
            val.setHogar_direccion  (   hogares.get(i).getHogar_direccion()                      );
            val.setDesc_departamento(   hogares.get(i).getDesc_departamento()                    );
            val.setDesc_municipio   (   hogares.get(i).getDesc_municipio()                       );
            val.setDesc_aldea       (   hogares.get(i).getDesc_aldea()                           );
            val.setHog_umbral       (   hogares.get(i).getHog_umbral()                           );
            val.setPer_titular      (   hogares.get(i).getPer_titular()                          );
            val.setHeader           (   (municipio.equals(hogares.get(i).getCod_municipio()))?0:1);
            ListaHogares.add(val);
            municipio = hogares.get(i).getCod_municipio();
        }
        this.listarValidacionesPresenter.MostarValidaciones( ListaHogares );
        this.realmConfig.getRealm().close();
    }

    @Override
    public int[] validacion_hogar(int hog_hogar) {

        int[] validacion_hogar = new int [2];

        this.realmConfig = new RealmConfig(context);
        RealmResults<Hogar_Validar> personas_hogares =  this.realmConfig.getRealm().where(Hogar_Validar.class)
                .distinct("hog_hogar","per_persona")
                .equalTo("hog_hogar", hog_hogar)
                .equalTo("per_estado_descripcion","Activo")
                .findAll();

        validacion_hogar[0] = personas_hogares.size();

        RealmResults<Hogar_Validaciones_Realizadas> validaciones_realizadas =  this.realmConfig.getRealm().where(Hogar_Validaciones_Realizadas.class)
                .distinct("hog_hogar", "per_persona")
                .equalTo("hog_hogar", hog_hogar)
                .findAll();

        this.realmConfig.getRealm().close();
        validacion_hogar[1] = validaciones_realizadas.size();

        return validacion_hogar;
    }


}
