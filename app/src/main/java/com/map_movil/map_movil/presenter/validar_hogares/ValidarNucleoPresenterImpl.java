package com.map_movil.map_movil.presenter.validar_hogares;

import android.content.Context;

import com.map_movil.map_movil.interactor.validar_hogares.ValidarNucleoInteractor;
import com.map_movil.map_movil.interactor.validar_hogares.ValidarNucleoInteractorImpl;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.view.validar_hogares.ValidarNucleoView;

import io.realm.RealmResults;

public class ValidarNucleoPresenterImpl implements ValidarNucleoPresenter {

    private ValidarNucleoView validarNucleoView;
    private ValidarNucleoInteractor validarNucleoInteractor;

    public ValidarNucleoPresenterImpl(ValidarNucleoView validarNucleoView, Context context){
        this.validarNucleoView = validarNucleoView;
        this.validarNucleoInteractor = new ValidarNucleoInteractorImpl(this , context);
    }

    @Override
    public void BuscarDatos(String identidad) {
        this.validarNucleoInteractor.BuscarDatos(identidad);
    }

    @Override
    public void MostarDatos(RealmResults<Hogar_Validar> Personas) {
        validarNucleoView.MostarDatos(Personas);
    }

    @Override
    public void GuardarValidacion(int per_persona, int hog_hogar, int identidad, int act_compromiso, int actualizar, int part_nacimiento, int cons_educacion, int desagregar, int debe_documento) {
        this.validarNucleoInteractor.GuardarValidacion(per_persona,hog_hogar , identidad,
                                                        act_compromiso , actualizar , part_nacimiento ,
                                                        cons_educacion , desagregar , debe_documento);
    }

}
