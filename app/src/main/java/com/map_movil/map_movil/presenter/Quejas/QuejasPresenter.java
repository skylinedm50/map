package com.map_movil.map_movil.presenter.Quejas;

import com.map_movil.map_movil.model.Realm.QuejasDenuncias;
import java.util.ArrayList;

public interface QuejasPresenter {
        void MostarQuejas(ArrayList<QuejasDenuncias> respuesta);
        void SolicitarQuejas(int usuario);
        void RegistrarQueja( int usuario      , String Observacio_solicitud , int tipo_gestion ,
                             String municipio , String Identidad            , String Nombre1   ,
                             String Nombre2   , String Apellido1            , String Apellido2 ,
                             String Telefono  , int anonimo );
        void ActualizarDatos();
        void DescargarQuejas(int usuario , String aldea);
        void SincronizarQuejas(int usuario);
        void FinalizarSincronizacion();
        void ListarQuejasOffline(ArrayList<QuejasDenuncias> quejasDenuncias);
        void CambiarPorcentaje(int porcentaje);
}
