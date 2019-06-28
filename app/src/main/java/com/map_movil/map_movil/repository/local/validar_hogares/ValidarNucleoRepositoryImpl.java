package com.map_movil.map_movil.repository.local.validar_hogares;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.model.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.model.Hogar_Validar;
import com.map_movil.map_movil.presenter.validar_hogares.ValidarNucleoPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.RealmResults;
import io.realm.Sort;

public class ValidarNucleoRepositoryImpl implements ValidarNucleoRepository {

    private ValidarNucleoPresenter validarNucleoPresenter;
    private RealmConfig realmConfig;
    private Context context;
    private double latitud;
    private double longitud;

    public ValidarNucleoRepositoryImpl(ValidarNucleoPresenter validarNucleoPresenter, Context context, Activity activity) {
        this.validarNucleoPresenter = validarNucleoPresenter;
        this.context = context;
    }


    @Override
    public void BuscarDatos(String identidad) {

        this.realmConfig = new RealmConfig(context);
        this.realmConfig.getRealm().beginTransaction();

        ArrayList<Hogar_Validar> Arrypersonas = new ArrayList<>();
        ArrayList<Hogar_Validaciones_Realizadas> ArryHogar = new ArrayList<>();

        RealmResults<Hogar_Validar> hogar = this.realmConfig.getRealm().where(Hogar_Validar.class).distinct("hog_hogar").equalTo("per_identidad", identidad).findAll();
        RealmResults<Hogar_Validar> result = this.realmConfig.getRealm().where(Hogar_Validar.class)
                .equalTo("hog_hogar", hogar.get(0).getHog_hogar())
                .equalTo("per_estado_descripcion", "Activo")
                .findAll()
                .sort("per_titular", Sort.DESCENDING);

        RealmResults<Hogar_Validaciones_Realizadas> validaciones_realizadas = this.realmConfig.getRealm().where(Hogar_Validaciones_Realizadas.class)
                .equalTo("hog_hogar", hogar.get(0).getHog_hogar())
                .findAll();

        for (int x = 0; x < result.size(); x++) {
            Hogar_Validar datos = new Hogar_Validar();
            datos.setNombre(result.get(x).getNombre());
            datos.setPer_identidad(result.get((x)).getPer_identidad());
            datos.setSexo(result.get(x).getSexo());
            datos.setEdad(result.get(x).getEdad());
            datos.setPer_estado_descripcion(result.get(x).getPer_estado_descripcion());
            datos.setPer_titular(result.get(x).getPer_titular());
            datos.setPer_persona(result.get(x).getPer_persona());
            Arrypersonas.add(datos);
        }

        for (int y = 0; y < validaciones_realizadas.size(); y++) {
            Hogar_Validaciones_Realizadas hogares = new Hogar_Validaciones_Realizadas();
            hogares.setPer_persona(validaciones_realizadas.get(y).getPer_persona());
            hogares.setActa_compromiso(validaciones_realizadas.get(y).getActa_compromiso());
            hogares.setActualizar_datos(validaciones_realizadas.get(y).getActualizar_datos());
            hogares.setCambio_titular(validaciones_realizadas.get(y).getCambio_titular());
            hogares.setConstancia_educacion(validaciones_realizadas.get(y).getConstancia_educacion());
            hogares.setDebe_documentos(validaciones_realizadas.get(y).getDebe_documentos());
            hogares.setDesagregar(validaciones_realizadas.get(y).getDesagregar());
            hogares.setIncorporacion(validaciones_realizadas.get(y).getIncorporacion());
            hogares.setPartidad_nacimiento(validaciones_realizadas.get(y).getPartidad_nacimiento());
            hogares.setTarjeta_identidad(validaciones_realizadas.get(y).getTarjeta_identidad());
            hogares.setObservacion(validaciones_realizadas.get(y).getObservacion());
            ArryHogar.add(hogares);
        }

        this.validarNucleoPresenter.MostarDatos(Arrypersonas, ArryHogar);
        this.realmConfig.getRealm().close();
    }

    @Override
        public void GuardarValidacion(int per_persona, int hog_hogar, final int identidad, final int act_compromiso, final int actualizar,
                                      final int part_nacimiento, final int cons_educacion, final int desagregar, final int debe_documento,
                                      int incorporacion, int cambio_titular, String observacion) {

        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        latitud  = location.getLatitude();
        longitud = location.getLongitude();

        Geocoder geocoder = new Geocoder(this.context , Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitud , latitud , 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.realmConfig = new RealmConfig(context);
        this.realmConfig.getRealm().beginTransaction();

        final RealmResults<Hogar_Validaciones_Realizadas> validacion = this.realmConfig.getRealm().where(Hogar_Validaciones_Realizadas.class).
                equalTo("per_persona", per_persona).findAll();

        if(validacion.size() == 0){

            Hogar_Validaciones_Realizadas validacion_realizada = new Hogar_Validaciones_Realizadas
                    (   hog_hogar        , per_persona    , identidad       ,
                        act_compromiso   , actualizar     , part_nacimiento ,
                        cons_educacion   , desagregar     , debe_documento  ,
                        incorporacion    , cambio_titular , observacion     , latitud , longitud
                    );
            this.realmConfig.getRealm().copyToRealm(validacion_realizada);
        }else{
                validacion.get(0).setTarjeta_identidad(identidad);
                validacion.get(0).setActa_compromiso(act_compromiso);
                validacion.get(0).setActualizar_datos(actualizar);
                validacion.get(0).setPartidad_nacimiento(part_nacimiento);
                validacion.get(0).setConstancia_educacion(cons_educacion);
                validacion.get(0).setDesagregar(desagregar);
                validacion.get(0).setDebe_documentos(debe_documento);
                validacion.get(0).setIncorporacion(incorporacion);
                validacion.get(0).setCambio_titular(cambio_titular);
                validacion.get(0).setObservacion(observacion);
                validacion.get(0).setLatitud(latitud);
                validacion.get(0).setLongitud(longitud);
        }
        this.realmConfig.getRealm().commitTransaction();
        this.realmConfig.getRealm().close();
    }

}
