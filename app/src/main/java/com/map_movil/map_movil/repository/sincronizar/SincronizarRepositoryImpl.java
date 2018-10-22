package com.map_movil.map_movil.repository.sincronizar;

import android.content.Context;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.api.quejas.ApiAdapterQuejas;
import com.map_movil.map_movil.api.quejas.ApiServicesQuejas;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.model.Realm.QuejasDenuncias;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.presenter.sincronizar.SincronizarPresenter;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SincronizarRepositoryImpl implements SincronizarRepository {

    private SincronizarPresenter sincronizarPresenter;
    private Context context;
    private RealmConfig realmConfig;
    private JsonObject jsonObject;
    private ApiAdapterSolicitudes apiAdapterSolicitudes;
    private ApiServiceSolicitudes apiServiceSolicitudes;

    public SincronizarRepositoryImpl(SincronizarPresenter sincronizarPresenter, Context context){
        this.sincronizarPresenter = sincronizarPresenter;
        this.context = context;
    }

    @Override
    public void SincronizarQuejas(int usuario) {

        this.realmConfig = new RealmConfig(this.context);
        final RealmResults<QuejasDenuncias> queja = this.realmConfig.getRealm().where(QuejasDenuncias.class)
                .equalTo("Offline" , 1)
                .findAll();
        final JsonArray JsonArrayQuejasDenuncias = new JsonArray();

        if(queja.size() == 0){
            realmConfig.getRealm().close();
            sincronizarPresenter.EventoCompletado(2);
            sincronizarPresenter.MensajeSincronizar("\n- No se detectaron quejas a sincronizar.");
        }else{

            for(int i = 0; i < queja.size() ; i++){

                String [] NombreSolicitante = queja.get(i).getNombre_solicitante().split(" ");

                JsonObject jsonQuejasDenuncias = new JsonObject();
                jsonQuejasDenuncias.addProperty("Usuario" , String.valueOf(usuario));
                jsonQuejasDenuncias.addProperty("Observacion_solicitud" , queja.get(i).getObservacion());
                jsonQuejasDenuncias.addProperty("Tipo_gestion"          , String.valueOf(queja.get(i).getCodigo_gestion()));
                jsonQuejasDenuncias.addProperty("Caserio"     , queja.get(i).getCaserio());
                jsonQuejasDenuncias.addProperty("Identidad" , queja.get(i).getIdentidad());
                jsonQuejasDenuncias.addProperty("Nombre1"   ,
                        (queja.get(i).getAnonimo() == 1 || NombreSolicitante.length < 1)?"": NombreSolicitante[0].toUpperCase());
                jsonQuejasDenuncias.addProperty("Nombre2"   ,
                        (queja.get(i).getAnonimo() == 1 || NombreSolicitante.length < 2)?"": NombreSolicitante[1].toUpperCase());
                jsonQuejasDenuncias.addProperty("Apellido1" ,
                        (queja.get(i).getAnonimo() == 1 || NombreSolicitante.length < 3)?"": NombreSolicitante[2].toUpperCase());
                jsonQuejasDenuncias.addProperty("Apellido2" ,
                        (queja.get(i).getAnonimo() == 1 || NombreSolicitante.length < 4)?"": NombreSolicitante[3].toUpperCase());
                jsonQuejasDenuncias.addProperty("Telefono"  , queja.get(i).getTelefono());
                jsonQuejasDenuncias.addProperty("Anonimo"   , queja.get(i).getAnonimo());

                JsonArrayQuejasDenuncias.add(jsonQuejasDenuncias);
            }

            ApiAdapterQuejas apiAdapterQuejas = new ApiAdapterQuejas();
            ApiServicesQuejas apiServicesQuejas = apiAdapterQuejas.getClientService();

            Call<ResponseApi> call = apiServicesQuejas.IngregarQueja(JsonArrayQuejasDenuncias);
            call.enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                    if(response.isSuccessful()){
                        realmConfig.getRealm().executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                queja.deleteAllFromRealm();
                                sincronizarPresenter.EventoCompletado(2);
                                int result;
                                RealmResults<QuejasDenuncias> queja = realmConfig.getRealm().where(QuejasDenuncias.class)
                                        .equalTo("Offline" , 1)
                                        .findAll();

                                RealmResults<SolicitudesDownload> solicitudes  = realmConfig.getRealm()
                                        .where(SolicitudesDownload.class)
                                        .equalTo("isLocal", true)
                                        .findAll();

                                result = (queja.size() > 0 || solicitudes.size()>0)?10:11;

                                sincronizarPresenter.EventoCompletado(result);

                                sincronizarPresenter.MensajeSincronizar("\n- Se realizó la sincronización de "+
                                                            String.valueOf(JsonArrayQuejasDenuncias.size())+" quejas y denuncias registradas local.");
                            }
                        });
                        realmConfig.getRealm().close();
                    }else{
                        realmConfig.getRealm().close();
                        sincronizarPresenter.EventoCompletado(2);
                        sincronizarPresenter.MensajeSincronizar("\n- Error en el servidor, no se pudo sincronizar las quejas y denuncias.");
                    }
                }

                @Override
                public void onFailure(Call<ResponseApi> call, Throwable t) {
                    realmConfig.getRealm().close();
                    sincronizarPresenter.EventoCompletado(2);
                    sincronizarPresenter.MensajeSincronizar("\n- "+t.getMessage());
                }
            });
        }


    }

    @Override
    public void synchronizeRequestWithServer(int intCodUser) {

        final JsonArray jsonArray = new JsonArray();
        realmConfig = new RealmConfig(context);
        realmConfig.getRealm().beginTransaction();
        RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;
        RealmResults<Hogar_Validar> nucleoHogar;
        solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class).equalTo("isLocal", true).findAll();
        realmConfig.getRealm().commitTransaction();

        if(solicitudesDownloadRealmResults.size() == 0){
            realmConfig.getRealm().close();
            this.sincronizarPresenter.EventoCompletado(1);
            this.sincronizarPresenter.MensajeSincronizar("\n- No se detectaron solicitudes a sincronizar.");
        }else{
            for(SolicitudesDownload item: solicitudesDownloadRealmResults){
                jsonObject = new JsonObject();
                nucleoHogar = realmConfig.getRealm().where(Hogar_Validar.class).equalTo("per_persona", item.getPer_persona()).findAll();
                jsonObject.addProperty("identidad", nucleoHogar.get(0).getPer_identidad());
                jsonObject.addProperty("cod_user", intCodUser);
                jsonObject.addProperty("observacion", item.getObservacion());
                jsonObject.addProperty("actualizacion_datos",(item.isActualizacion_datos())? 1 : 0);
                jsonObject.addProperty("cambio_titular",(item.isCambio_titular())? 1 : 0);
                jsonObject.addProperty("nuevo_miembro",(item.isNuevo_integrante())? 1 : 0);
                jsonObject.addProperty("baja_miembro",(item.isBaja_integrante())? 1 : 0);
                jsonObject.addProperty("cambio_domicilio",(item.isCambio_domicilio())? 1 : 0);
                jsonObject.addProperty("baja_programa",(item.isBaja_programa())? 1 : 0);
                jsonObject.addProperty("reactiva_programa",(item.isReactiva_programa())? 1 : 0);
                jsonObject.addProperty("correccion_sancion",(item.isCorreccion_sancion())? 1 : 0);
                jsonArray.add(jsonObject);
            }
            apiAdapterSolicitudes = new ApiAdapterSolicitudes();
            apiServiceSolicitudes = apiAdapterSolicitudes.getClientService();

            Call<ResponseApi> call = apiServiceSolicitudes.createSolicitud(jsonArray);
            call.enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                    if (response.body() != null && response.body().getIntState() == 1) {
                        realmConfig = new RealmConfig(context);
                        realmConfig.getRealm().beginTransaction();
                        final RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;
                        solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class).equalTo("isLocal", true).findAll();
                        realmConfig.getRealm().commitTransaction();
                        realmConfig.getRealm().executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                solicitudesDownloadRealmResults.deleteAllFromRealm();
                                sincronizarPresenter.EventoCompletado(1);
                                int result;
                                RealmResults<QuejasDenuncias> queja = realmConfig.getRealm().where(QuejasDenuncias.class)
                                        .equalTo("Offline" , 1)
                                        .findAll();

                                RealmResults<SolicitudesDownload> solicitudes  = realmConfig.getRealm()
                                        .where(SolicitudesDownload.class)
                                        .equalTo("isLocal", true)
                                        .findAll();

                                result = (queja.size() > 0 || solicitudes.size()>0)?10:11;

                                sincronizarPresenter.EventoCompletado(result);
                                sincronizarPresenter.MensajeSincronizar("\n- Se realizó la sincronización de "+String.valueOf(jsonArray.size())+" solicitudes registradas local");
                            }
                        });
                        realmConfig.getRealm().close();
                    }else{
                        realmConfig.getRealm().close();
                        sincronizarPresenter.EventoCompletado(1);
                        sincronizarPresenter.MensajeSincronizar("\n- Error en el servidor, no se pudo sincronizar las solicitudes");
                    }
                }

                @Override
                public void onFailure(Call<ResponseApi> call, Throwable t) {
                    realmConfig.getRealm().close();
                    sincronizarPresenter.EventoCompletado(1);
                    sincronizarPresenter.MensajeSincronizar("\n- "+t.getMessage());
                }
            });
        }

    }



}
