package com.map_movil.map_movil.interactor.validar_hogares;

import android.app.Activity;
import android.content.Context;

import com.map_movil.map_movil.presenter.validar_hogares.ValidarNucleoPresenter;
import com.map_movil.map_movil.repository.local.validar_hogares.ValidarNucleoRepository;
import com.map_movil.map_movil.repository.local.validar_hogares.ValidarNucleoRepositoryImpl;

public class ValidarNucleoInteractorImpl implements ValidarNucleoInteractor {

    private ValidarNucleoPresenter validarNucleoPresenter;
    private ValidarNucleoRepository validarNucleoRepository;

    public ValidarNucleoInteractorImpl(ValidarNucleoPresenter validarNucleoPresenter, Context context, Activity activity){
        this.validarNucleoPresenter = validarNucleoPresenter;
        this.validarNucleoRepository = new ValidarNucleoRepositoryImpl(this.validarNucleoPresenter, context , activity);
    }

    @Override
    public void BuscarDatos(String identidad) {
        this.validarNucleoRepository.BuscarDatos(identidad);
    }

    @Override
    public void GuardarValidacion(int per_persona, int hog_hogar, int identidad, int act_compromiso, int actualizar, int part_nacimiento,
                                  int cons_educacion, int desagregar, int debe_documento, int incorporacion , int cambio_titular,  String observacion) {
        this.validarNucleoRepository.GuardarValidacion(  per_persona, hog_hogar, identidad,
                                                         act_compromiso, actualizar, part_nacimiento,
                                                         cons_educacion, desagregar, debe_documento ,
                                                         incorporacion , cambio_titular , observacion
                );
    }

}
