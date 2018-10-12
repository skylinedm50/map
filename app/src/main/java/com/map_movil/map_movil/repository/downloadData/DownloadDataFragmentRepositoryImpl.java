package com.map_movil.map_movil.repository.downloadData;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.api.nucleoHogar.ApiAdapterNucleoHogar;
import com.map_movil.map_movil.api.nucleoHogar.ApiServiceNucleoHogar;
import com.map_movil.map_movil.api.quejas.ApiAdapterQuejas;
import com.map_movil.map_movil.api.quejas.ApiServicesQuejas;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.model.Realm.QuejasDenuncias;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.presenter.downloadData.DownloadDataFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadDataFragmentRepositoryImpl implements DownloadDataFragmentRepository {
    private DownloadDataFragmentPresenter downloadDataFragmentPresenter;
    private Context context;
    private RealmConfig realmConfig;
    private int[] arrayIntCant;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private int intCodUser;
    private ArrayList<String> arrayListMunicipiosSelect;
    private ApiAdapterNucleoHogar apiAdapterNucleoHogar;
    private ApiServiceNucleoHogar apiServiceNucleoHogar;
    private ApiAdapterSolicitudes apiAdapterSolicitudes;
    private ApiServiceSolicitudes apiServiceSolicitudes;
    private ApiAdapterQuejas apiAdapterQuejas;
    private ApiServicesQuejas apiServicesQuejas;

    public DownloadDataFragmentRepositoryImpl(DownloadDataFragmentPresenter downloadDataFragmentPresenter, Context context){
        this.downloadDataFragmentPresenter = downloadDataFragmentPresenter;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        apiAdapterNucleoHogar = new ApiAdapterNucleoHogar();
        apiServiceNucleoHogar = apiAdapterNucleoHogar.getClientService();
        apiAdapterSolicitudes = new ApiAdapterSolicitudes();
        apiServiceSolicitudes = apiAdapterSolicitudes.getClientService();
        apiAdapterQuejas = new ApiAdapterQuejas();
        apiServicesQuejas = apiAdapterQuejas.getClientService();
    }

    @Override
    public boolean existLocalData() {
        Boolean bolExistLocalData = false;
        RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;
        RealmResults<QuejasDenuncias> quejasDenunciasRealmResults;
        realmConfig = new RealmConfig(context);
        realmConfig.getRealm().beginTransaction();
        solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class).equalTo("isLocal", true).findAll();
        quejasDenunciasRealmResults = realmConfig.getRealm().where(QuejasDenuncias.class).equalTo("Offline" , 1).findAll();
        if(solicitudesDownloadRealmResults.size() > 0 || quejasDenunciasRealmResults.size() > 0){
            bolExistLocalData = true;
        }
        realmConfig.getRealm().commitTransaction();
        realmConfig.getRealm().close();

        return bolExistLocalData;
    }

    @Override
    public void findDetailDataLocal() {
        RealmResults<Hogar_Validar> nucleoRealmResults;
        RealmResults<HistorialPago> historialPagoRealmResults;
        RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;
        RealmResults<QuejasDenuncias> quejasDenunciasRealmResults;
        ArrayList<String> arrayListMunicicpios = new ArrayList<>();
        arrayIntCant = new int[]{0, 0, 0, 0};
        realmConfig = new RealmConfig(context);
        realmConfig.getRealm().beginTransaction();

        nucleoRealmResults = realmConfig.getRealm().where(Hogar_Validar.class).findAll();
        arrayIntCant[0] = nucleoRealmResults.size();
        historialPagoRealmResults = realmConfig.getRealm().where(HistorialPago.class).findAll();
        arrayIntCant[1] = historialPagoRealmResults.size();
        solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class).notEqualTo("isLocal", true).findAll();
        arrayIntCant[2] = solicitudesDownloadRealmResults.size();
        quejasDenunciasRealmResults = realmConfig.getRealm().where(QuejasDenuncias.class).notEqualTo("Offline" , 1).findAll();
        arrayIntCant[3] = quejasDenunciasRealmResults.size();

        realmConfig.getRealm().commitTransaction();
        realmConfig.getRealm().close();


        for(String strElemnt: sharedPreferences.getString("municipiosSelect", "ND").split(",")){
            arrayListMunicicpios.add(strElemnt);
        }

        downloadDataFragmentPresenter.showDetailDataLocal(arrayIntCant, arrayListMunicicpios);
    }

    @Override
    public void downloadData(ArrayList<String> arrayListMunicipiosSelect) {
        if(existLocalData()){
            downloadDataFragmentPresenter.showMessage("Imposible realizar acción, existen datos localmente que no se han sincronizado.");
            findDetailDataLocal();
        }else{
            this.arrayListMunicipiosSelect = arrayListMunicipiosSelect;
            intCodUser = sharedPreferences.getInt("codigo",0);
            deleteAllData();
            downloadHoagres();
        }
    }

    @Override
    public void downloadHoagres() {
        Call<List<Hogar_Validar>> call = apiServiceNucleoHogar.getDatosNucleo(convertArrayListToJsonArray(arrayListMunicipiosSelect).toString());
        call.enqueue(new Callback<List<Hogar_Validar>>() {
            @Override
            public void onResponse(Call<List<Hogar_Validar>> call, Response<List<Hogar_Validar>> response) {
                if(response.isSuccessful() && response.body().size() > 0){
                    realmConfig = new RealmConfig(context);
                    realmConfig.getRealm().beginTransaction();
                    realmConfig.getRealm().insert(response.body());
                    realmConfig.getRealm().commitTransaction();
                    realmConfig.getRealm().close();
                    downloadHistorialPago();
                }else{
                    downloadDataFragmentPresenter.showMessage("No se detectaron datos.");
                    findDetailDataLocal();
                }
            }

            @Override
            public void onFailure(Call<List<Hogar_Validar>> call, Throwable t) {
                downloadDataFragmentPresenter.showMessage(t.getMessage());
                findDetailDataLocal();
            }
        });
    }

    @Override
    public void downloadHistorialPago() {
        Call<List<HistorialPago>> call = apiServiceNucleoHogar.getHistorialPago(convertArrayListToJsonArray(arrayListMunicipiosSelect).toString());
        call.enqueue(new Callback<List<HistorialPago>>() {
            @Override
            public void onResponse(Call<List<HistorialPago>> call, Response<List<HistorialPago>> response) {
                if(response.isSuccessful() && response.body().size() > 0){
                    realmConfig = new RealmConfig(context);
                    realmConfig.getRealm().beginTransaction();
                    realmConfig.getRealm().insert(response.body());
                    realmConfig.getRealm().commitTransaction();
                    realmConfig.getRealm().close();
                }
                downloadSolictudes();
            }

            @Override
            public void onFailure(Call<List<HistorialPago>> call, Throwable t) {
                downloadDataFragmentPresenter.showMessage(t.getMessage());
                downloadSolictudes();
            }
        });
    }

    @Override
    public void downloadSolictudes() {
       Call<ArrayList<SolicitudesDownload>> call = apiServiceSolicitudes.getSolicitudesDownload(convertArrayListToJsonArray(arrayListMunicipiosSelect).toString());
        call.enqueue(new Callback<ArrayList<SolicitudesDownload>>() {
            @Override
            public void onResponse(Call<ArrayList<SolicitudesDownload>> call, Response<ArrayList<SolicitudesDownload>> response) {
                if(response.isSuccessful() && response.body().size() > 0){
                    realmConfig = new RealmConfig(context);
                    realmConfig.getRealm().beginTransaction();
                    realmConfig.getRealm().insert(response.body());
                    realmConfig.getRealm().commitTransaction();
                    realmConfig.getRealm().close();
                }
                downloadQuejasDenuncias();
            }
            @Override
            public void onFailure(Call<ArrayList<SolicitudesDownload>> call, Throwable t) {
                downloadDataFragmentPresenter.showMessage(t.getMessage());
                downloadQuejasDenuncias();
            }
        });
    }

    @Override
    public void downloadQuejasDenuncias() {
        Call<ArrayList<QuejasDenuncias>>  call = apiServicesQuejas.getDownloadQuejas(convertArrayListToJsonArray(arrayListMunicipiosSelect).toString());
        call.enqueue(new Callback<ArrayList<QuejasDenuncias>>() {
            @Override
            public void onResponse(Call<ArrayList<QuejasDenuncias>> call, Response<ArrayList<QuejasDenuncias>> response) {
                if(response.isSuccessful() && response.body().size() > 0){
                    realmConfig = new RealmConfig(context);
                    realmConfig.getRealm().beginTransaction();
                    realmConfig.getRealm().insert(response.body());
                    realmConfig.getRealm().commitTransaction();
                    realmConfig.getRealm().close();
                }
                findDetailDataLocal();
            }

            @Override
            public void onFailure(Call<ArrayList<QuejasDenuncias>> call, Throwable t) {
                downloadDataFragmentPresenter.showMessage(t.getMessage());
                findDetailDataLocal();
            }
        });
    }

    @Override
    public void deleteAllData() {
        realmConfig = new RealmConfig(context);
        realmConfig.getRealm().beginTransaction();

        final RealmResults<SolicitudesDownload> solicitudesDownloadRealmResults;
        final RealmResults<QuejasDenuncias> quejasDenunciasRealmResults;
        solicitudesDownloadRealmResults = realmConfig.getRealm().where(SolicitudesDownload.class).notEqualTo("isLocal", true).findAll();
        quejasDenunciasRealmResults = realmConfig.getRealm().where(QuejasDenuncias.class).notEqualTo("Offline" , 1).findAll();

        realmConfig.getRealm().delete(Hogar_Validar.class);
        realmConfig.getRealm().delete(HistorialPago.class);
        realmConfig.getRealm().commitTransaction();
        realmConfig.getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(solicitudesDownloadRealmResults.size() > 0) {
                    solicitudesDownloadRealmResults.deleteAllFromRealm();
                }

                if(quejasDenunciasRealmResults.size() > 0) {
                    quejasDenunciasRealmResults.deleteAllFromRealm();
                }
            }
        });
        realmConfig.getRealm().close();
    }

    private JsonArray convertArrayListToJsonArray(ArrayList<String> arrayList){
        JsonArray jsonArrayFinal = new JsonArray();
        JsonArray jsonArrayMunicipios = new JsonArray();
        JsonObject jsonObjectData = new JsonObject();
        String strMuniSelectForSharePreferen = new String();
        int intCounter = 0;

        for(String strElement: arrayList){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("municipio", strElement.split("-")[0]);
            jsonArrayMunicipios.add(jsonObject);
            strMuniSelectForSharePreferen += (arrayList.size() == intCounter + 1)? strElement : strElement + ",";
            intCounter ++;
        }

        jsonObjectData.add("municipios", jsonArrayMunicipios);
        jsonObjectData.addProperty("user", intCodUser);
        jsonArrayFinal.add(jsonObjectData);
        sharedPreferencesEditor.putString("municipiosSelect", strMuniSelectForSharePreferen);
        sharedPreferencesEditor.commit();

        return jsonArrayFinal;
    }
}
