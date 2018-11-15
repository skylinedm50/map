package com.map_movil.map_movil.presenter.Quejas;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.interactor.Quejas.QuejasInteractoImpl;
import com.map_movil.map_movil.interactor.Quejas.QuejasInteractor;
import com.map_movil.map_movil.model.QuejasDenuncias;
import com.map_movil.map_movil.view.Quejas.QuejasOfflineView;
import com.map_movil.map_movil.view.Quejas.QuejasView;

import java.util.ArrayList;

public class QuejasPresenterImpl implements QuejasPresenter {

    private QuejasView quejasView;
    private QuejasOfflineView quejasOfflineView;
    private QuejasInteractor QuejasInteractor;

    public QuejasPresenterImpl(QuejasView quejasView , QuejasOfflineView offlineView , Context context){
        this.quejasOfflineView = offlineView;
        this.quejasView = quejasView;
        this.QuejasInteractor = new QuejasInteractoImpl(this , context);
    }

    @Override
    public void MostarQuejas(ArrayList<QuejasDenuncias> respuesta , int proceso) {

        int RealizadosLenght = 0;
        int noRealizadosLenght = 0;

        for(int x = 0 ; x < respuesta.size(); x++){
            if( respuesta.get(x).getEstado().equals("No Aplicable")          ||
                respuesta.get(x).getEstado().equals("Resuelta Conforme")     ||
                respuesta.get(x).getEstado().equals("Resuelta No Conforme")      ){
                RealizadosLenght = RealizadosLenght+1;
            }else{
                noRealizadosLenght = noRealizadosLenght+1;
            }
        }

        if(proceso == 1){
            this.quejasView.MostarQuejas(respuesta, RealizadosLenght , noRealizadosLenght);
        }else{
            this.quejasView.MostarQuejas(respuesta, RealizadosLenght , noRealizadosLenght);
        }
    }

    @Override
    public void SolicitarQuejas(int usuario) {
        this.QuejasInteractor.SolicitarQuejas(usuario);
    }

    @Override
    public void RegistrarQueja( int usuario      , String Observacio_solicitud , int tipo_gestion ,
                                String caserio     , String Identidad            , String Nombre1   ,
                                String Nombre2   , String Apellido1            , String Apellido2 ,
                                String Telefono  , int anonimo ) {

        JsonObject jsonQuejaDenuncia = new JsonObject();
        jsonQuejaDenuncia.addProperty("Usuario" , String.valueOf(usuario));
        jsonQuejaDenuncia.addProperty("Observacion_solicitud" , Observacio_solicitud);
        jsonQuejaDenuncia.addProperty("Tipo_gestion"          , String.valueOf(tipo_gestion));
        jsonQuejaDenuncia.addProperty("Caserio"     , caserio);
        jsonQuejaDenuncia.addProperty("Identidad" , Identidad);
        jsonQuejaDenuncia.addProperty("Nombre1"   , Nombre1.toUpperCase());
        jsonQuejaDenuncia.addProperty("Nombre2"   , Nombre2.toUpperCase());
        jsonQuejaDenuncia.addProperty("Apellido1" , Apellido1.toUpperCase());
        jsonQuejaDenuncia.addProperty("Apellido2" , Apellido2.toUpperCase());
        jsonQuejaDenuncia.addProperty("Telefono"  , Telefono);
        jsonQuejaDenuncia.addProperty("Anonimo"   , String.valueOf(anonimo));

        JsonArray jsonArrayQueja = new JsonArray();
        jsonArrayQueja.add(jsonQuejaDenuncia);

        this.QuejasInteractor.RegistrarQueja(jsonArrayQueja);
    }

    @Override
    public void ActualizarDatos(int offline) {
        this.quejasView.ActualizarDatos(offline);
    }

    @Override
    public void DescargarQuejas(int usuario, String aldea) {
        this.QuejasInteractor.DescargarQuejas(usuario , aldea);
    }

    @Override
    public void CambiarPorcentaje(int porcentaje) {
        this.quejasOfflineView.CambiarPorcentaje(porcentaje);
    }


}
