package com.map_movil.map_movil.repository.local.validar_hogares;

import android.content.Context;

import com.map_movil.map_movil.model.Realm.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.presenter.validar_hogares.ValidarNucleoPresenter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ValidarNucleoRepositoryImpl implements ValidarNucleoRepository {

    private ValidarNucleoPresenter validarNucleoPresenter;
    private Realm realm;

    public ValidarNucleoRepositoryImpl(ValidarNucleoPresenter validarNucleoPresenter, Context context){
        this.validarNucleoPresenter = validarNucleoPresenter;
        Realm.init(context);
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public void BuscarDatos(String identidad) {

        RealmResults<Hogar_Validar>  hogar  = this.realm.where(Hogar_Validar.class).distinct("hog_hogar").equalTo("per_identidad",identidad).findAll();
        RealmResults<Hogar_Validar>  result = this.realm.where(Hogar_Validar.class)
                .equalTo("hog_hogar",hogar.get(0).getHog_hogar())
                .equalTo("per_estado_descripcion","Activo")
                .findAll()
                .sort("per_titular", Sort.DESCENDING);

        ArrayList<String[]> personas_hogar = new ArrayList<>();

        this.validarNucleoPresenter.MostarDatos(result);
    }

    @Override
    public void GuardarValidacion(int per_persona, int hog_hogar, final int identidad, final int act_compromiso, final int actualizar,
                                  final int part_nacimiento, final int cons_educacion, final int desagregar, final int debe_documento) {
        this.realm.beginTransaction();

        final RealmResults<Hogar_Validaciones_Realizadas> validacion = this.realm.where(Hogar_Validaciones_Realizadas.class).
                equalTo("per_persona", per_persona).findAll();

        if(validacion.size() == 0){

            Hogar_Validaciones_Realizadas validacion_realizada = new Hogar_Validaciones_Realizadas
                    (   hog_hogar        , per_persona , identidad ,
                        act_compromiso   , actualizar  , part_nacimiento ,
                        cons_educacion   , desagregar  , debe_documento
                    );
            this.realm.copyToRealm(validacion_realizada);
        }else{
                validacion.get(0).setTarjeta_identidad(identidad);
                validacion.get(0).setActa_compromiso(act_compromiso);
                validacion.get(0).setActualizar_datos(actualizar);
                validacion.get(0).setPartidad_nacimiento(part_nacimiento);
                validacion.get(0).setConstancia_educacion(cons_educacion);
                validacion.get(0).setActa_compromiso(act_compromiso);
                validacion.get(0).setDesagregar(desagregar);
                validacion.get(0).setDebe_documentos(debe_documento);
        }
        this.realm.commitTransaction();

    }

}
