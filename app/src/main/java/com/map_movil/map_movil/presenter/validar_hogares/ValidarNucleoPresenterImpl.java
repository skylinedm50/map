package com.map_movil.map_movil.presenter.validar_hogares;

import android.app.Activity;
import android.content.Context;
import com.map_movil.map_movil.interactor.validar_hogares.ValidarNucleoInteractor;
import com.map_movil.map_movil.interactor.validar_hogares.ValidarNucleoInteractorImpl;
import com.map_movil.map_movil.model.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.model.Hogar_Validar;
import com.map_movil.map_movil.view.validar_hogares.ValidarNucleoView;
import java.util.ArrayList;

public class ValidarNucleoPresenterImpl implements ValidarNucleoPresenter {

    private ValidarNucleoView validarNucleoView;
    private ValidarNucleoInteractor validarNucleoInteractor;

    public ValidarNucleoPresenterImpl(ValidarNucleoView validarNucleoView, Context context, Activity activity){
        this.validarNucleoView = validarNucleoView;
        this.validarNucleoInteractor = new ValidarNucleoInteractorImpl(this , context, activity);
    }

    @Override
    public void BuscarDatos(String identidad) {
        this.validarNucleoInteractor.BuscarDatos(identidad);
    }

    @Override
    public void MostarDatos(ArrayList<Hogar_Validar> Personas , ArrayList<Hogar_Validaciones_Realizadas> hogar_validaciones_realizadas) {
        ArrayList<String[]> persona_validar = new ArrayList<>();
        int validado   = 0;

        for(int x = 0; x < Personas.size(); x++)
        {
            validado = 0;
            for(int y = 0; y < hogar_validaciones_realizadas.size(); y++){
                if(Personas.get(x).getPer_persona() == hogar_validaciones_realizadas.get(y).getPer_persona()){
                    validado = 1;
                    break;
                }
            }
            persona_validar.add(new String[]{ Personas.get(x).getNombre()                        ,
                                              Personas.get(x).getPer_identidad()                 ,
                                              Personas.get(x).getSexo()                          ,
                                              Personas.get(x).getEdad()                          ,
                                              Personas.get(x).getPer_estado_descripcion()        ,
                                              String.valueOf( Personas.get(x).getPer_titular() ) ,
                                              String.valueOf(validado)                           ,
                                              String.valueOf( Personas.get(x).getPer_persona() )

            });
        }
        validarNucleoView.MostarDatos(persona_validar , hogar_validaciones_realizadas);
    }

    @Override
    public void GuardarValidacion(int per_persona, int hog_hogar, int identidad, int act_compromiso, int actualizar, int part_nacimiento,
                                  int cons_educacion, int desagregar, int debe_documento , int incorporacion , int cambio_titular , String observacion) {
        this.validarNucleoInteractor.GuardarValidacion(per_persona,hog_hogar , identidad,
                                                       act_compromiso , actualizar , part_nacimiento ,
                                                       cons_educacion , desagregar , debe_documento  ,
                                                       incorporacion  , cambio_titular  , observacion
                );
    }
}
