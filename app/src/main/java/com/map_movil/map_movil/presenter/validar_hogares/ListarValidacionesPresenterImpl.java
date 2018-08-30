package com.map_movil.map_movil.presenter.validar_hogares;

import android.content.Context;

import com.map_movil.map_movil.interactor.validar_hogares.ListarValidacionesInteractor;
import com.map_movil.map_movil.interactor.validar_hogares.ListarValidacionesInteractorImpl;
import com.map_movil.map_movil.model.Realm.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.view.validar_hogares.ListarValidacionesView;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ListarValidacionesPresenterImpl implements ListarValidacionesPresenter {

    private ListarValidacionesView listarValidacionesView;
    private ListarValidacionesInteractor listarValidacionesInteractor;

    public ListarValidacionesPresenterImpl(ListarValidacionesView listarValidacionesView , Context context){

        this.listarValidacionesView = listarValidacionesView;
        this.listarValidacionesInteractor = new ListarValidacionesInteractorImpl(this,context);
    }

    @Override
    public void BuscarValidaciones() {
        this.listarValidacionesInteractor.BuscarValidaciones();
    }

    @Override
    public void MostarValidaciones(RealmResults<Hogar_Validar> Personas_Hogares) {

        ArrayList<String[]> Hogares_Validar = new ArrayList<>();
        int [] personas_validadas = new int [2];

        for(int x = 0 ; x < Personas_Hogares.size() ; x++){

           personas_validadas = this.listarValidacionesInteractor.validacion_hogar(Personas_Hogares.get(x).getHog_hogar());

            Hogares_Validar.add(new String[]{Personas_Hogares.get(x).getNombre()                        ,
                                             Personas_Hogares.get(x).getHogar_direccion()               ,
                                             Personas_Hogares.get(x).getDesc_departamento()+" - "+
                                                     Personas_Hogares.get(x).getDesc_municipio()+" - "+
                                                     Personas_Hogares.get(x).getDesc_aldea()            ,
                                             String.valueOf( Personas_Hogares.get(x).getPer_persona() ) ,
                                             Personas_Hogares.get(x).getPer_identidad()                 ,
                                             String.valueOf( Personas_Hogares.get(x).getHog_hogar() )   ,
                                             Personas_Hogares.get(x).getHog_umbral()                    ,
                                             Personas_Hogares.get(x).getHog_estado_descripcion()        ,
                                             String.valueOf(personas_validadas[1])+"/"+String.valueOf(personas_validadas[0])
            });
        }

        this.listarValidacionesView.MostarValidaciones(Hogares_Validar);
    }
}
