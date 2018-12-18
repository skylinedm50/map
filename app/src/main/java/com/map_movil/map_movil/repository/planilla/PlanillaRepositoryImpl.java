package com.map_movil.map_movil.repository.planilla;

import android.content.Context;
import android.widget.Toast;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.api.planilla.ApiAdapterPlanilla;
import com.map_movil.map_movil.api.planilla.ApiServicePlanilla;
import com.map_movil.map_movil.interactor.planilla.PlanillaInteractor;
import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.Hogar_Validar;
import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.model.PagosExcluido;
import com.map_movil.map_movil.model.PagosProgramados;
import com.map_movil.map_movil.presenter.planilla.PlanillaPresenter;
import java.util.ArrayList;
import java.util.List;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanillaRepositoryImpl implements PlanillaRepository {

    private PlanillaPresenter planillaPresenter;
    private PlanillaInteractor planillaInteractor;
    private ApiServicePlanilla servicePlanilla;
    private ApiAdapterPlanilla apiAdapterPlanilla;
    private ArrayList<PagosProgramados> listPagos;
    private Context context;
    private RealmConfig realmConfig;

    public PlanillaRepositoryImpl(PlanillaPresenter planillaPresenter , Context context){
        this.planillaPresenter = planillaPresenter;
        this.apiAdapterPlanilla = new ApiAdapterPlanilla();
        this.servicePlanilla = this.apiAdapterPlanilla.getClientService();
        this.context = context;
    }

    @Override
    public void getPagos() {
        Call<List<Pagos>> pagos = this.servicePlanilla.getAllPagos();
        pagos.enqueue(new Callback<List<Pagos>>() {
            @Override
            public void onResponse(Call<List<Pagos>> call, Response<List<Pagos>> response) {
                if(response.isSuccessful()){
                    if(response.body().size()>0){
                        planillaPresenter.cargarPagos(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pagos>> call, Throwable t) {
                int l = 0;
            }
        });
    }

    @Override
    public void getProgramados(String strCodAldea, String strCodpago) {
        listPagos = new ArrayList<PagosProgramados>();
        Call<ArrayList<PagosProgramados>> programados = this.servicePlanilla.getPagosProgramados(strCodpago,strCodAldea);
        programados.enqueue(new Callback<ArrayList<PagosProgramados>>() {
            @Override
            public void onResponse(Call<ArrayList<PagosProgramados>> call, Response<ArrayList<PagosProgramados>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && response.body().size()>0){
                        listPagos = response.body();
                    }
                        planillaPresenter.MostarDatosProgramados(listPagos);
                }else{
                    Toast.makeText(context , "Error en el servidor al solicitar la información" ,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PagosProgramados>> call, Throwable t) {
                Toast.makeText(context , "Error en el servidor al solicitar la información" ,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void getExcluidosGlobal(String strCodAldea, String strCodpago) {
        Call<ArrayList<PagosExcluido>> call = servicePlanilla.getPlanillaExcluidoInfo(strCodAldea,strCodpago);
        call.enqueue(new Callback<ArrayList<PagosExcluido>>() {
            public void onResponse(Call<ArrayList<PagosExcluido>> call, Response<ArrayList<PagosExcluido>> response) {
                if(response.code() == 200){
                    /**/
                    planillaPresenter.MostarExcluidos(response.body());
                }
                else{
                    Toast.makeText(context,"Error al solicitar la información al servidor", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PagosExcluido>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getExcluidosMancomunidad(String strCodAldea, String strCodpago) {
        Call<ArrayList<PagosExcluido>> call = servicePlanilla.getPlanillaExcluidomancInfo(strCodAldea,strCodpago);
        call.enqueue(new Callback<ArrayList<PagosExcluido>>() {
            public void onResponse(Call<ArrayList<PagosExcluido>> call, Response<ArrayList<PagosExcluido>> response) {
                if(response.code() == 200){
                  /*  listexcluidos = response.body();
                      adaptadorExcluidos.changeAdapater(listexcluidos);
                      loading("datos"); */
                    planillaPresenter.MostarExcluidos(response.body());
                }
                else{
                    Toast.makeText(context,"Error al solicitar la información al servidor", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PagosExcluido>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getProgramadosOffline( String strCodAldea , String codPago) {
        listPagos = new ArrayList<PagosProgramados>();
        this.realmConfig = new RealmConfig(this.context);
        this.realmConfig.getRealm().beginTransaction();

      /*-------------------------------------------------------------------------------------------------------*/
        RealmResults<Hogar_Validar> hogares = this.realmConfig.getRealm().where(Hogar_Validar.class)
                                                        .equalTo("per_titular", 1).equalTo("cod_aldea" , strCodAldea).findAll();

        for(int i = 0; i < hogares.size() ; i++){

            RealmResults<HistorialPago> historialPagos = this.realmConfig.getRealm().where(HistorialPago.class)
                    .equalTo("tit_planilla",true)
                    .equalTo("pag_codigo", Integer.valueOf( codPago ))
                    .equalTo("tit_hogar",hogares.get(i).getHog_hogar()).findAll();

            if(historialPagos.size()>0){
                listPagos.add(new PagosProgramados( historialPagos.get(0).getPag_nombre()               ,
                        hogares.get(i).getHog_hogar()         , hogares.get(i).getCod_departamento()    ,
                        hogares.get(i).getDesc_departamento() , hogares.get(i).getCod_municipio()       ,
                        hogares.get(i).getDesc_municipio()    , hogares.get(i).getDesc_caserio()        ,
                        hogares.get(i).getDesc_aldea()        , hogares.get(i).getNombre()              ,
                        hogares.get(i).getPer_identidad()     , String.valueOf( historialPagos.get(0).getMonto() )
                ));
            }
        }
        planillaPresenter.MostarDatosProgramados(listPagos);
        this.realmConfig.getRealm().commitTransaction();
        this.realmConfig.getRealm().close();
        /*--------------------------------------------------------------------------------------------------------*/
    }

    @Override
    public void getProgramado_By_ID(String strIdentidad, String strCodpago) {
        int intCodpago = Integer.parseInt(strCodpago);

        listPagos = new ArrayList<PagosProgramados>();
        Call<ArrayList<PagosProgramados>> programados = this.servicePlanilla.getPagosProgamadosbyIdTitular(intCodpago, strIdentidad);

        programados.enqueue(new Callback<ArrayList<PagosProgramados>>() {
            @Override
            public void onResponse(Call<ArrayList<PagosProgramados>> call, Response<ArrayList<PagosProgramados>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && response.body().size()>0){
                        listPagos = response.body();
                    }
                }else{
                       // Toast.makeText(context , "Error en el servidor al solicitar la información" ,Toast.LENGTH_LONG).show();
                }
                planillaPresenter.MostarDatosProgramados(listPagos);
            }

            @Override
            public void onFailure(Call<ArrayList<PagosProgramados>> call, Throwable t) {
                Toast.makeText(context , "Error en el servidor al solicitar la información" ,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void getProgramado_By_ID_Offline(String strIdentidad, String strCodpago) {
        listPagos = new ArrayList<PagosProgramados>();
        this.realmConfig = new RealmConfig(this.context);
        this.realmConfig.getRealm().beginTransaction();

        /*-------------------------------------------------------------------------------------------------------*/
        RealmResults<Hogar_Validar> hogares = this.realmConfig.getRealm().where(Hogar_Validar.class)
                .equalTo("per_titular", 1).equalTo("per_identidad" , strIdentidad).findAll();

        for(int i = 0; i < hogares.size() ; i++){

            RealmResults<HistorialPago> historialPagos = this.realmConfig.getRealm().where(HistorialPago.class)
                    .equalTo("tit_planilla",true)
                    .equalTo("pag_codigo", Integer.valueOf( strCodpago ))
                    .equalTo("tit_hogar",hogares.get(i).getHog_hogar()).findAll();

            if(historialPagos.size()>0){
                listPagos.add(new PagosProgramados( historialPagos.get(0).getPag_nombre()               ,
                        hogares.get(i).getHog_hogar()         , hogares.get(i).getCod_departamento()    ,
                        hogares.get(i).getDesc_departamento() , hogares.get(i).getCod_municipio()       ,
                        hogares.get(i).getDesc_municipio()    , hogares.get(i).getDesc_caserio()        ,
                        hogares.get(i).getDesc_aldea()        , hogares.get(i).getNombre()              ,
                        hogares.get(i).getPer_identidad()     , String.valueOf( historialPagos.get(0).getMonto() )
                ));
            }
        }
        planillaPresenter.MostarDatosProgramados(listPagos);
        this.realmConfig.getRealm().commitTransaction();
        this.realmConfig.getRealm().close();
        /*--------------------------------------------------------------------------------------------------------*/
    }

    @Override
    public void getPagosOffline() {

        List<Pagos> pagos = new ArrayList<>();
        this.realmConfig = new RealmConfig(this.context);
        this.realmConfig.getRealm().beginTransaction();
        RealmResults<HistorialPago> RealmPagos = this.realmConfig.getRealm().where(HistorialPago.class)
                                        .distinct("pag_nombre","pag_codigo")
                                        .sort("pag_codigo" , Sort.DESCENDING).findAll();

        for(int i = 0 ; i<RealmPagos.size(); i++){
            pagos.add(new Pagos(String.valueOf( RealmPagos.get(i).getPag_codigo() ) ,
                                String.valueOf( RealmPagos.get(i).getPag_nombre() ) ));
        }
        planillaPresenter.cargarPagos(pagos);
        this.realmConfig.getRealm().commitTransaction();
        this.realmConfig.getRealm().close();
    }


}
