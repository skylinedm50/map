package com.map_movil.map_movil.presenter.Quejas;

import com.google.gson.JsonObject;
import com.map_movil.map_movil.interactor.Quejas.QuejasInteractoImpl;
import com.map_movil.map_movil.interactor.Quejas.QuejasInteractor;
import com.map_movil.map_movil.model.QuejasDenuncias;
import com.map_movil.map_movil.view.Quejas.QuejasView;
import com.map_movil.map_movil.view.Quejas.RefrescarDatos;

import java.util.ArrayList;

public class QuejasPresenterImpl implements QuejasPresenter {

    private QuejasView quejasView;
    private QuejasInteractor QuejasInteractor;

    public QuejasPresenterImpl(QuejasView quejasView){
        this.quejasView = quejasView;
        this.QuejasInteractor = new QuejasInteractoImpl(this);

    }

    @Override
    public void MostarQuejas(ArrayList<QuejasDenuncias> respuesta) {

        int RealizadosLenght = 0;
        int noRealizadosLenght = 0;

        for(int x = 0 ; x < respuesta.size(); x++){
            if(respuesta.get(x).getEstado().equals("Realizada")){
                RealizadosLenght = RealizadosLenght+1;
            }else{
                noRealizadosLenght = noRealizadosLenght+1;
            }
        }

        this.quejasView.MostarQuejas(respuesta, RealizadosLenght , noRealizadosLenght);
    }

    @Override
    public void SolicitarQuejas(int usuario) {
        this.QuejasInteractor.SolicitarQuejas(usuario);
    }

    @Override
    public void RegistrarQueja( int usuario      , String Observacio_solicitud , int tipo_gestion ,
                                String municipio , String Identidad            , String Nombre1   ,
                                String Nombre2   , String Apellido1            , String Apellido2 ,
                                String Telefono  , int anonimo ) {

        JsonObject jsonQuejaDenuncia = new JsonObject();
        jsonQuejaDenuncia.addProperty("Usuario" , String.valueOf(usuario));
        jsonQuejaDenuncia.addProperty("Observacion_solicitud" , Observacio_solicitud);
        jsonQuejaDenuncia.addProperty("Tipo_gestion"          , String.valueOf(tipo_gestion));
        jsonQuejaDenuncia.addProperty("Municipio" , municipio);
        jsonQuejaDenuncia.addProperty("Identidad" , Identidad);
        jsonQuejaDenuncia.addProperty("Nombre1"   , Nombre1);
        jsonQuejaDenuncia.addProperty("Nombre2"   , Nombre2);
        jsonQuejaDenuncia.addProperty("Apellido1" , Apellido1);
        jsonQuejaDenuncia.addProperty("Apellido2" , Apellido2);
        jsonQuejaDenuncia.addProperty("Telefono"  , Telefono);
        jsonQuejaDenuncia.addProperty("Anonimo"   , String.valueOf(anonimo));

        this.QuejasInteractor.RegistrarQueja(jsonQuejaDenuncia);
    }

    @Override
    public void ActualizarDatos() {
        this.quejasView.ActualizarDatos();
    }


}
