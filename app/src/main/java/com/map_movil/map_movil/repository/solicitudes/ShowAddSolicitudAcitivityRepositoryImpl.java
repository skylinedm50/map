package com.map_movil.map_movil.repository.solicitudes;

import android.content.Context;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.api.hogar.ApiAdapterHogar;
import com.map_movil.map_movil.api.hogar.ApiServiceHogar;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.model.HogarByTitular;
import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.Hogar_Validar;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.presenter.solicitud.ShowAddSolicitudAcitivityPresenter;
import java.util.ArrayList;
import java.util.Date;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAddSolicitudAcitivityRepositoryImpl implements ShowAddSolicitudAcitivityRepository{
    private ShowAddSolicitudAcitivityPresenter showAddSolicitudAcitivityPresenter;
    private ApiAdapterSolicitudes objApiAdapterSolicitudes;
    private ApiServiceSolicitudes objApiServiceSolicitudes;
    private ApiAdapterHogar adapterHogar;
    private ApiServiceHogar serviceHogar;
    private InfoSolicitud infoSolicitud;
    private HogarByTitular hogarByTitular;
    private Context context;
    private RealmConfig realmConfig;
    private boolean isForCreateSolicitud;//Identificar si la informacíón del hogar a buscar es para una nueva solicitud o de una ya existente.

    public ShowAddSolicitudAcitivityRepositoryImpl(ShowAddSolicitudAcitivityPresenter showAddSolicitudAcitivityPresenter, Context context) {
        this.context = context;
        this.showAddSolicitudAcitivityPresenter = showAddSolicitudAcitivityPresenter;
        objApiAdapterSolicitudes = new ApiAdapterSolicitudes();
        objApiServiceSolicitudes = objApiAdapterSolicitudes.getClientService();
        adapterHogar = new ApiAdapterHogar();
        serviceHogar = adapterHogar.getClientService();
    }

    @Override
    public void findDataServerForCreateSolicitud(String strIdentidadTitular) {
        Call<ArrayList<HogarByTitular>> call = serviceHogar.getHogarByTitular(strIdentidadTitular);
        call.enqueue(new Callback<ArrayList<HogarByTitular>>() {
            @Override
            public void onResponse(Call<ArrayList<HogarByTitular>> call, Response<ArrayList<HogarByTitular>> response) {
                if (response.body().size() > 0) {
                    isForCreateSolicitud = true;
                    hogarByTitular = response.body().get(0);
                    findNucleoHogar(hogarByTitular.getIntCodHogar());
                }else{
                    showAddSolicitudAcitivityPresenter.showMessage("No se detectaron datos");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HogarByTitular>> call, Throwable t) {
                showAddSolicitudAcitivityPresenter.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void findDataLocalForCreateSolicitud(String strIdentidadTitular) {
        realmConfig = new RealmConfig(context);
        realmConfig.getRealm().beginTransaction();
        RealmResults<Hogar_Validar> nucleoHogarResultsHogAndTitular;
        RealmResults<Hogar_Validar> nucleoHogarResultsNucleo;
        HogarLigth hogarLigth;
        ArrayList<HogarLigth> hogarLigthArrayList = new ArrayList<>();
        nucleoHogarResultsHogAndTitular = realmConfig.getRealm().where(Hogar_Validar.class)
                .equalTo("per_identidad", strIdentidadTitular)
                .and().equalTo("per_titular", 1).findAll();

        if(nucleoHogarResultsHogAndTitular.size() > 0){
            hogarByTitular = new HogarByTitular();
            hogarByTitular.setIntCodHogar(nucleoHogarResultsHogAndTitular.get(0).getHog_hogar());
            hogarByTitular.setStrEstadoHogar(nucleoHogarResultsHogAndTitular.get(0).getHog_estado_descripcion());
            hogarByTitular.setStrUmbralHogar(nucleoHogarResultsHogAndTitular.get(0).getHog_umbral());
            hogarByTitular.setStrDepartamento(nucleoHogarResultsHogAndTitular.get(0).getDesc_departamento());
            hogarByTitular.setStrMunicipio(nucleoHogarResultsHogAndTitular.get(0).getDesc_municipio());
            hogarByTitular.setStrAldea(nucleoHogarResultsHogAndTitular.get(0).getDesc_aldea());
            hogarByTitular.setStrCaserio(nucleoHogarResultsHogAndTitular.get(0).getDesc_caserio());

            nucleoHogarResultsNucleo = realmConfig.getRealm().where(Hogar_Validar.class).equalTo("hog_hogar", hogarByTitular.getIntCodHogar()).findAll();
            for(Hogar_Validar item: nucleoHogarResultsNucleo){
                hogarLigth = new HogarLigth(
                        item.getNombre(),
                        item.getEdad(),
                        item.getSexo(),
                        item.getPer_identidad(),
                        (item.getPer_titular() == 1)? true: false
                );
                hogarLigthArrayList.add(hogarLigth);
            }
            realmConfig.getRealm().commitTransaction();
            showAddSolicitudAcitivityPresenter.showOnlyInforHogForCreateSolicitud(hogarByTitular, hogarLigthArrayList);
        }else{
            showAddSolicitudAcitivityPresenter.showMessage("Hogar no disponible localmente");
        }
        realmConfig.getRealm().close();
    }

    @Override
    public void findNucleoHogar(int intCodHogar) {
        Call<ArrayList<HogarLigth>> call = serviceHogar.getLigthInfoHogar(intCodHogar);
        call.enqueue(new Callback<ArrayList<HogarLigth>>() {
            @Override
            public void onResponse(Call<ArrayList<HogarLigth>> call, Response<ArrayList<HogarLigth>> response) {
                if (response.body().size() > 0) {
                    if(isForCreateSolicitud){
                        showAddSolicitudAcitivityPresenter.showOnlyInforHogForCreateSolicitud(hogarByTitular, response.body());
                    }else{
                        showAddSolicitudAcitivityPresenter.showSolicitud(infoSolicitud, response.body());
                    }
                }else{
                    showAddSolicitudAcitivityPresenter.showMessage("No se detectaron datos");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HogarLigth>> call, Throwable t) {
                showAddSolicitudAcitivityPresenter.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void findSolicitudSavedLocal(int intCodSolicitud) {
        realmConfig = new RealmConfig(context);
        realmConfig.getRealm().beginTransaction();
        ArrayList<HogarLigth> hogarLigthArrayList = new ArrayList<>();
        HogarLigth hogarLigth;
        RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;
        RealmResults<Hogar_Validar> nucleosHogareRealmResulyts;
        solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class).equalTo("codigo_solicitud", intCodSolicitud).findAll();

        InfoSolicitud infoSolicitud = new InfoSolicitud();
        infoSolicitud.setIntCodSolicitud(solicitudesDownloadRealmResults.get(0).getCodigo_solicitud());
        infoSolicitud.setIntCodHogar(solicitudesDownloadRealmResults.get(0).getCodigo_hogar());
        infoSolicitud.setStrEstadoSolicitud(solicitudesDownloadRealmResults.get(0).getEstado_solicitud());
        infoSolicitud.setStrEstadoHogar(solicitudesDownloadRealmResults.get(0).getEstado_hogar());
        infoSolicitud.setStrUmbral(solicitudesDownloadRealmResults.get(0).getUmbral_hogar());
        infoSolicitud.setStrDepartamento(solicitudesDownloadRealmResults.get(0).getDepartamento());
        infoSolicitud.setStrMunicipio(solicitudesDownloadRealmResults.get(0).getMunicipio());
        infoSolicitud.setStrAldea(solicitudesDownloadRealmResults.get(0).getAldea());
        infoSolicitud.setStrCaserio(solicitudesDownloadRealmResults.get(0).getCaserio());
        infoSolicitud.setBolActualizacionDatos(solicitudesDownloadRealmResults.get(0).isActualizacion_datos());
        infoSolicitud.setBolCambioTitular(solicitudesDownloadRealmResults.get(0).isCambio_titular());
        infoSolicitud.setBolNuevoIntegrante(solicitudesDownloadRealmResults.get(0).isNuevo_integrante());
        infoSolicitud.setBolBajaIntegrante(solicitudesDownloadRealmResults.get(0).isBaja_integrante());
        infoSolicitud.setBolCambioDomicilio(solicitudesDownloadRealmResults.get(0).isCambio_domicilio());
        infoSolicitud.setBolBajaPrograma(solicitudesDownloadRealmResults.get(0).isBaja_programa());
        infoSolicitud.setBolReactivaPrograma(solicitudesDownloadRealmResults.get(0).isReactiva_programa());
        infoSolicitud.setBolCorreccionSancion(solicitudesDownloadRealmResults.get(0).isCorreccion_sancion());
        infoSolicitud.setStrObservacion(solicitudesDownloadRealmResults.get(0).getObservacion());

        nucleosHogareRealmResulyts = realmConfig.getRealm().where(Hogar_Validar.class).equalTo("hog_hogar", infoSolicitud.getIntCodHogar()).findAll();
        for(Hogar_Validar item: nucleosHogareRealmResulyts){
            hogarLigth = new HogarLigth(
                    item.getNombre(),
                    item.getEdad(),
                    item.getSexo(),
                    item.getPer_identidad(),
                    (item.getPer_titular() == 1)? true: false
            );
            hogarLigthArrayList.add(hogarLigth);
        }
        realmConfig.getRealm().commitTransaction();

        if(nucleosHogareRealmResulyts.size() > 0){
            showAddSolicitudAcitivityPresenter.showSolicitud(infoSolicitud, hogarLigthArrayList);
        }else{
            showAddSolicitudAcitivityPresenter.showMessage("La solicitud seleccionada contiene un hogar no disponible localmente");
        }
        realmConfig.getRealm().close();
    }

    @Override
    public void findSolicitudSavedServe(int intCodSolicitud) {
        Call<ArrayList<InfoSolicitud>> call = objApiServiceSolicitudes.getSolicitudInfo(intCodSolicitud);
        call.enqueue(new Callback<ArrayList<InfoSolicitud>>() {
            @Override
            public void onResponse(Call<ArrayList<InfoSolicitud>> call, Response<ArrayList<InfoSolicitud>> response) {
                if (response.body().size() > 0) {
                    isForCreateSolicitud = false;
                    infoSolicitud = response.body().get(0);
                    findNucleoHogar(infoSolicitud.getIntCodHogar());
                }else{
                    showAddSolicitudAcitivityPresenter.showMessage("No se detectaron datos");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<InfoSolicitud>> call, Throwable t) {
                showAddSolicitudAcitivityPresenter.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void saveServerSolicitud(SolicitudesDownload solicitudesDownload, HogarLigth hogarLigth, int intCodUser) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonObject.addProperty("hog_hogar",hogarLigth.getIntHogHogar());
        jsonObject.addProperty("per_persona",hogarLigth.getIntPerPersona());
        jsonObject.addProperty("cod_user",intCodUser);
        jsonObject.addProperty("observacion",solicitudesDownload.getObservacion());
        jsonObject.addProperty("actualizacion_datos",(solicitudesDownload.isActualizacion_datos())? 1 : 0);
        jsonObject.addProperty("cambio_titular",(solicitudesDownload.isCambio_titular())? 1 : 0);
        jsonObject.addProperty("nuevo_miembro",(solicitudesDownload.isNuevo_integrante())? 1 : 0);
        jsonObject.addProperty("baja_miembro",(solicitudesDownload.isBaja_integrante())? 1 : 0);
        jsonObject.addProperty("cambio_domicilio",(solicitudesDownload.isCambio_domicilio())? 1 : 0);
        jsonObject.addProperty("baja_programa",(solicitudesDownload.isBaja_programa())? 1 : 0);
        jsonObject.addProperty("reactiva_programa",(solicitudesDownload.isReactiva_programa())? 1 : 0);
        jsonObject.addProperty("correccion_sancion",(solicitudesDownload.isCorreccion_sancion())? 1 : 0);
        jsonArray.add(jsonObject);
        Call<ResponseApi> call = objApiServiceSolicitudes.createSolicitud(jsonArray);

        call.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if (response.body() != null && response.body().getIntState() == 1) {
                    showAddSolicitudAcitivityPresenter.finishCreationSolicitud(0);
                }else{
                    showAddSolicitudAcitivityPresenter.showMessage("Imposible almacenar la solicitud.");
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                showAddSolicitudAcitivityPresenter.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void saveLocalSolicitud(SolicitudesDownload solicitudesDownload, HogarLigth hogarLigth, int intCodUser) {
        realmConfig = new RealmConfig(context);
        realmConfig.getRealm().beginTransaction();
        RealmResults<Hogar_Validar> nucleoHogarReamlResult;
        RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;
        int intCodSolicitud = -1;

        solicitudesDownload.setEstado_solicitud("Ingresada");
        solicitudesDownload.setLocal(true);
        solicitudesDownload.setCodigo_estado(1);
        solicitudesDownload.setFecha_alta(new Date());

        solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class)
                .equalTo("isLocal", true)
                .findAll().sort("codigo_solicitud", Sort.ASCENDING);

        if(solicitudesDownloadRealmResults.size() > 0) {
            intCodSolicitud = (solicitudesDownloadRealmResults.get(0).getCodigo_solicitud() - 1);
        }
        solicitudesDownload.setCodigo_solicitud(intCodSolicitud);

        nucleoHogarReamlResult = realmConfig.getRealm().where(Hogar_Validar.class)
                .equalTo("per_identidad", hogarLigth.getStrIdentidad())
                .and().equalTo("per_titular", 1).findAll();

        solicitudesDownload.setNombre_solicitante(nucleoHogarReamlResult.get(0).getNombre());
        solicitudesDownload.setCaserio(nucleoHogarReamlResult.get(0).getDesc_caserio());
        solicitudesDownload.setAldea(nucleoHogarReamlResult.get(0).getDesc_aldea());
        solicitudesDownload.setMunicipio(nucleoHogarReamlResult.get(0).getDesc_municipio());
        solicitudesDownload.setDepartamento(nucleoHogarReamlResult.get(0).getDesc_departamento());
        solicitudesDownload.setUmbral_hogar(nucleoHogarReamlResult.get(0).getHog_umbral());
        solicitudesDownload.setEstado_hogar(nucleoHogarReamlResult.get(0).getHog_estado_descripcion());
        solicitudesDownload.setCodigo_hogar(nucleoHogarReamlResult.get(0).getHog_hogar());
        solicitudesDownload.setPer_persona(nucleoHogarReamlResult.get(0).getPer_persona());
        solicitudesDownload.setHog_hogar(nucleoHogarReamlResult.get(0).getHog_hogar());

        realmConfig.getRealm().insert(solicitudesDownload);
        realmConfig.getRealm().commitTransaction();
        realmConfig.getRealm().close();
        showAddSolicitudAcitivityPresenter.finishCreationSolicitud(1);
    }

}
