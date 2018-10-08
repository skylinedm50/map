package com.map_movil.map_movil.repository.solicitudes;

import android.content.Context;
import android.content.SharedPreferences;

import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.model.SolicitudesUsuario;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesFragmentPresenter;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudesFragmentRepositoryImpl implements  SolicitudesFragmentRepository {
    private SolicitudesFragmentPresenter solicitudesFragmentPresenter;
    private ApiAdapterSolicitudes apiAdapterSolicitudes;
    private ApiServiceSolicitudes apiServiceSolicitudes;
    private Context context;
    private RealmConfig realmConfig;
    private String strMessageNoData = "No se encontraron datos en el servidor";



    public SolicitudesFragmentRepositoryImpl(SolicitudesFragmentPresenter solicitudesFragmentPresenter, Context context){
        this.solicitudesFragmentPresenter = solicitudesFragmentPresenter;
        this.context = context;
        apiAdapterSolicitudes = new ApiAdapterSolicitudes();
        apiServiceSolicitudes = apiAdapterSolicitudes.getClientService();
    }

    @Override
    public void downloadSolicitudes(String strCodAldea, int intCodUser) {
        solicitudesFragmentPresenter.changePorcentage(30);
        Call<ArrayList<SolicitudesDownload>> call = apiServiceSolicitudes.getSolicitudesDownload(intCodUser, strCodAldea);
        call.enqueue(new Callback<ArrayList<SolicitudesDownload>>() {
            @Override
            public void onResponse(Call<ArrayList<SolicitudesDownload>> call, Response<ArrayList<SolicitudesDownload>> response) {
                solicitudesFragmentPresenter.changePorcentage(65);
                if(response.isSuccessful() && response.body().size() > 0){
                    ArrayList<SolicitudesDownload> arrayList = response.body();
                    saveLocalSolicitud(arrayList);
                }else {
                    solicitudesFragmentPresenter.showError(strMessageNoData);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<SolicitudesDownload>> call, Throwable t) {
                solicitudesFragmentPresenter.showError(t.getMessage());
            }
        });
    }

    @Override
    public void getSolicitudesGestionadas(int intCodUser, String strSimbolo) {
        Call<ArrayList<SolicitudesUsuario>> call = apiServiceSolicitudes.getSolicitudesFinalizadasUsuario(intCodUser, strSimbolo);
        call.enqueue(new Callback<ArrayList<SolicitudesUsuario>>() {
            @Override
            public void onResponse(Call<ArrayList<SolicitudesUsuario>> call, Response<ArrayList<SolicitudesUsuario>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    realmConfig = new RealmConfig(context);
                    ArrayList<SolicitudesUsuario> solicitudesUsuarioArrayList = response.body();
                    realmConfig.getRealm().beginTransaction();
                    RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;

                    if(solicitudesUsuarioArrayList.get(0).getIntCodEstado() == 4 || solicitudesUsuarioArrayList.get(0).getIntCodEstado() == 6
                            || solicitudesUsuarioArrayList.get(0).getIntCodEstado() == 7 ){
                        solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class)
                                .equalTo("codigo_estado", 4)
                                .or().equalTo("codigo_estado", 6)
                                .or().equalTo("codigo_estado", 7)
                                .findAll();
                    }else{
                        solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class)
                                .notEqualTo("codigo_estado", 4)
                                .and().notEqualTo("codigo_estado", 6)
                                .and().notEqualTo("codigo_estado", 7)
                                .findAll();
                    }
                    realmConfig.getRealm().commitTransaction();
                    for(SolicitudesDownload solicitudItem: solicitudesDownloadRealmResults){
                        SolicitudesUsuario solicitudesUsuario = new SolicitudesUsuario(
                                solicitudItem.getCodigo_solicitud(),
                                solicitudItem.getNombre_solicitante(),
                                solicitudItem.getObservacion(),
                                solicitudItem.getFecha_alta(),
                                solicitudItem.getFecha_baja(),
                                solicitudesUsuarioArrayList.get(0).getIntCodUsuario(),
                                solicitudItem.getCodigo_estado(),
                                solicitudItem.getEstado_solicitud()
                        );
                        solicitudesUsuarioArrayList.add(solicitudesUsuario);
                    }
                    realmConfig.getRealm().close();
                    solicitudesFragmentPresenter.showSolicitudesGestionadas(solicitudesUsuarioArrayList);
                } else {
                    solicitudesFragmentPresenter.showError(strMessageNoData);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<SolicitudesUsuario>> call, Throwable t) {
                solicitudesFragmentPresenter.showError(t.getMessage());
                solicitudesFragmentPresenter.showSolicitudesGestionadas(new ArrayList<SolicitudesUsuario>());
            }
        });
    }

    @Override
    public void saveLocalSolicitud(ArrayList<SolicitudesDownload> arrayListSolicitudesDownload){
        try{
            realmConfig = new RealmConfig(context);
            final ArrayList<SolicitudesDownload> arrayListSolicitudesDownloadFinal = arrayListSolicitudesDownload;
            realmConfig.getRealm().beginTransaction();
            final RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;
            solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class).notEqualTo("isLocal", true).findAll();
            realmConfig.getRealm().commitTransaction();
            realmConfig.getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if(solicitudesDownloadRealmResults.size() > 0) {
                        solicitudesDownloadRealmResults.deleteAllFromRealm();
                    }
                    realm.insert(arrayListSolicitudesDownloadFinal);
                    solicitudesFragmentPresenter.changePorcentage(100);
                    solicitudesFragmentPresenter.finishDownloadSolicitudes();
                    solicitudesFragmentPresenter.showError("Las solicitudes fueron descargadas");/////////////////////////////
                }
            });
            realmConfig.getRealm().close();
        }catch (Exception e){
            solicitudesFragmentPresenter.showError(e.getMessage());
        }
    }

    @Override
    public void getSolicitudesGestionadasLocalDB(int intCodUser, String strSimbolo) {
        realmConfig = new RealmConfig(context);
        ArrayList<SolicitudesUsuario> solicitudesUsuarioArrayList = new ArrayList<>();
        realmConfig.getRealm().beginTransaction();
        RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;
        SolicitudesUsuario solicitudesUsuario;

        if(strSimbolo == "resueltas"){
            solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class)
                    .equalTo("codigo_estado", 4)
                    .or().equalTo("codigo_estado", 6)
                    .or().equalTo("codigo_estado", 7)
                    .findAll();
        }else{
            solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class)
                    .notEqualTo("codigo_estado", 4)
                    .and().notEqualTo("codigo_estado", 6)
                    .and().notEqualTo("codigo_estado", 7)
                    .findAll();
        }
        realmConfig.getRealm().commitTransaction();

        for(SolicitudesDownload solicitudItem: solicitudesDownloadRealmResults){
            solicitudesUsuario = new SolicitudesUsuario(
                    solicitudItem.getCodigo_solicitud(),
                    solicitudItem.getNombre_solicitante(),
                    solicitudItem.getObservacion(),
                    solicitudItem.getFecha_alta(),
                    solicitudItem.getFecha_baja(),
                    intCodUser,
                    solicitudItem.getCodigo_estado(),
                    solicitudItem.getEstado_solicitud()
            );
            solicitudesUsuarioArrayList.add(solicitudesUsuario);
        }
        realmConfig.getRealm().close();
        solicitudesFragmentPresenter.showSolicitudesGestionadas(solicitudesUsuarioArrayList);
    }
}
