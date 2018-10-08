package com.map_movil.map_movil.repository.Quejas;

import android.content.Context;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.api.quejas.ApiAdapterQuejas;
import com.map_movil.map_movil.api.quejas.ApiServicesQuejas;
import com.map_movil.map_movil.model.Realm.QuejasDenuncias;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenter;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuejasRepositoryImpl implements QuejasRepository {

  private QuejasPresenter quejasPresenter;
   private RealmConfig realmConfig;
   private Context context;

   public QuejasRepositoryImpl(QuejasPresenter quejasPresenter , Context context){
       this.quejasPresenter = quejasPresenter;
       this.context = context;
   }

    @Override
    public void SolicitarQuejas(int usuario) {

        ApiAdapterQuejas apiAdapterQuejas = new ApiAdapterQuejas();
        ApiServicesQuejas apiServiceQuejas = apiAdapterQuejas.getClientService();

        Call<ArrayList<QuejasDenuncias>> call = apiServiceQuejas.getQuejasDenuncias(usuario);
        call.enqueue(new Callback<ArrayList<QuejasDenuncias>>() {
            @Override
            public void onResponse(Call<ArrayList<QuejasDenuncias>> call, Response<ArrayList<QuejasDenuncias>> response) {
                if(response.isSuccessful()){
                    ArrayList<QuejasDenuncias> lista = new ArrayList<>();
                    for(int i=0; i < response.body().size(); i++){
                        QuejasDenuncias quejasDenunciasVista = new QuejasDenuncias();
                            quejasDenunciasVista.setCodigo_solicitud(response.body().get(i).getCodigo_solicitud());
                            quejasDenunciasVista.setFecha_alta(response.body().get(i).getFecha_alta());
                            quejasDenunciasVista.setObservacion(response.body().get(i).getObservacion());
                            quejasDenunciasVista.setTipo_gestion(response.body().get(i).getTipo_gestion());
                            quejasDenunciasVista.setEstado(response.body().get(i).getEstado());
                            quejasDenunciasVista.setIdentidad(response.body().get(i).getIdentidad());
                            quejasDenunciasVista.setCodigo_solicitante(response.body().get(i).getCodigo_solicitante());
                            quejasDenunciasVista.setNombre_solicitante(response.body().get(i).getNombre_solicitante());
                            quejasDenunciasVista.setAldea(response.body().get(i).getAldea());

                            lista.add(quejasDenunciasVista);
                    }
                    quejasPresenter.MostarQuejas(lista);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<QuejasDenuncias>> call, Throwable t) { }
        });
   }

    @Override
    public void RegistrarQueja(JsonArray jsonQuejaDenuncia) {

       ApiAdapterQuejas apiAdapterQuejas = new ApiAdapterQuejas();
       ApiServicesQuejas apiServicesQuejas = apiAdapterQuejas.getClientService();

       Call<ResponseApi> call = apiServicesQuejas.IngregarQueja(jsonQuejaDenuncia);
       call.enqueue(new Callback<ResponseApi>() {
           @Override
           public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
               if(response.isSuccessful()){  quejasPresenter.ActualizarDatos();  }
           }

           @Override
           public void onFailure(Call<ResponseApi> call, Throwable t) { }
       });

    }

    @Override
    public void RegistrarQuejaOffline(JsonArray jsonQuejasDenuncia) {

        this.realmConfig = new RealmConfig(this.context);
        int UltimaQueja;

        RealmResults<QuejasDenuncias> queja = this.realmConfig.getRealm().where(QuejasDenuncias.class)
                .distinct("codigo_solicitud")
                .equalTo("Offline" , 1)
                .findAll();

        if(queja.size() == 0){
            UltimaQueja = -1;
        }else{
            UltimaQueja = queja.last().getCodigo_solicitud() - 1;
        }

        QuejasDenuncias quejasDenuncias = new QuejasDenuncias();
            quejasDenuncias.setCodigo_solicitud(UltimaQueja);
            quejasDenuncias.setFecha_alta( null );
            quejasDenuncias.setObservacion(jsonQuejasDenuncia.get(0).getAsJsonObject().get("Observacion_solicitud").getAsString());
            quejasDenuncias.setCodigo_gestion(jsonQuejasDenuncia.get(0).getAsJsonObject().get("Tipo_gestion").getAsInt());
            quejasDenuncias.setEstado("Ingresada");
            quejasDenuncias.setAldea(jsonQuejasDenuncia.get(0).getAsJsonObject().get("Aldea").getAsString());
            quejasDenuncias.setIdentidad(jsonQuejasDenuncia.get(0).getAsJsonObject().get("Identidad").getAsString());
            quejasDenuncias.setCodigo_solicitante( Integer.valueOf(0) );
            quejasDenuncias.setNombre_solicitante(
                            jsonQuejasDenuncia.get(0).getAsJsonObject().get("Nombre1").getAsString().toUpperCase()+" "+
                            jsonQuejasDenuncia.get(0).getAsJsonObject().get("Nombre2").getAsString().toUpperCase()+" "+
                            jsonQuejasDenuncia.get(0).getAsJsonObject().get("Apellido1").getAsString().toUpperCase()+" "+
                            jsonQuejasDenuncia.get(0).getAsJsonObject().get("Apellido2").getAsString().toUpperCase()
            );
            quejasDenuncias.setTelefono(jsonQuejasDenuncia.get(0).getAsJsonObject().get("Telefono").getAsString());
            quejasDenuncias.setAnonimo(jsonQuejasDenuncia.get(0).getAsJsonObject().get("Anonimo").getAsInt());
            quejasDenuncias.setOffline(1);

        this.realmConfig.getRealm().beginTransaction();
        this.realmConfig.getRealm().insert(quejasDenuncias);
        this.realmConfig.getRealm().commitTransaction();
        this.realmConfig.getRealm().close();
        quejasPresenter.ActualizarDatos();

   }

    @Override
    public void DescargarQuejas(int usuario, String aldea) {
        /**
        ApiAdapterQuejas  apiAdapterQuejas = new ApiAdapterQuejas();
        ApiServicesQuejas apiServicesQuejas = apiAdapterQuejas.getClientService();
        Call<ArrayList<QuejasDenuncias>>  call = apiServicesQuejas.getDownloadQuejas(usuario , aldea);
        this.quejasPresenter.CambiarPorcentaje(30);
        call.enqueue(new Callback<ArrayList<QuejasDenuncias>>() {
            @Override
            public void onResponse(Call<ArrayList<QuejasDenuncias>> call, Response<ArrayList<QuejasDenuncias>> response) {
                if(response.isSuccessful()){
                    quejasPresenter.CambiarPorcentaje(65);
                    realmConfig = new RealmConfig(context);
                    realmConfig.getRealm().beginTransaction();
                    realmConfig.getRealm().delete(QuejasDenuncias.class);
                    realmConfig.getRealm().insert(response.body());
                    realmConfig.getRealm().commitTransaction();
                    realmConfig.getRealm().close();
                    quejasPresenter.CambiarPorcentaje(100);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuejasDenuncias>> call, Throwable t) {  }
        });*/
    }

    @Override
    public void ListarQuejasOffline() {

        realmConfig = new RealmConfig(context);
        realmConfig.getRealm().beginTransaction();
        final RealmResults<QuejasDenuncias> quejasDenuncias = realmConfig.getRealm().where(QuejasDenuncias.class).findAll();

        realmConfig.getRealm().commitTransaction();
        realmConfig.getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ArrayList<QuejasDenuncias> lista = new ArrayList<>();
                for(int i = 0; i < quejasDenuncias.size() ; i++){

                    String Gestion="";
                    switch(  quejasDenuncias.get(i).getCodigo_gestion() ){
                        case 1: Gestion ="Quejas";
                            break;
                        case 2: Gestion = "Denuncias";
                            break;
                        default: Gestion = "Solicitud";
                    }

                    QuejasDenuncias quejasDenuncias_1 = new QuejasDenuncias();
                    quejasDenuncias_1.setCodigo_solicitud(quejasDenuncias.get(i).getCodigo_solicitud());
                    quejasDenuncias_1.setFecha_alta(quejasDenuncias.get(i).getFecha_alta());
                    quejasDenuncias_1.setObservacion(quejasDenuncias.get(i).getObservacion());
                    quejasDenuncias_1.setTipo_gestion(Gestion);
                    quejasDenuncias_1.setEstado(quejasDenuncias.get(i).getEstado());
                    quejasDenuncias_1.setIdentidad(quejasDenuncias.get(i).getIdentidad());
                    quejasDenuncias_1.setCodigo_solicitante(quejasDenuncias.get(i).getCodigo_solicitante());
                    quejasDenuncias_1.setNombre_solicitante(
                            (quejasDenuncias.get(i).getAnonimo() == 1)?"ANONIMO":quejasDenuncias.get(i).getNombre_solicitante()
                    );
                    quejasDenuncias_1.setAldea(quejasDenuncias.get(i).getAldea());
                    lista.add(quejasDenuncias_1);
                }
                quejasPresenter.ListarQuejasOffline(lista);
            }
        });
        realmConfig.getRealm().close();
   }

    @Override
    public void SincronizarQuejas(int usuario) {

        this.realmConfig = new RealmConfig(this.context);
        final RealmResults<QuejasDenuncias> queja = this.realmConfig.getRealm().where(QuejasDenuncias.class)
                .equalTo("Offline" , 1)
                .findAll();
        JsonArray JsonArrayQuejasDenuncias = new JsonArray();

        for(int i = 0; i < queja.size() ; i++){

            String [] NombreSolicitante = queja.get(i).getNombre_solicitante().split(" ");

            JsonObject jsonQuejasDenuncias = new JsonObject();
            jsonQuejasDenuncias.addProperty("Usuario" , String.valueOf(usuario));
            jsonQuejasDenuncias.addProperty("Observacion_solicitud" , queja.get(i).getObservacion());
            jsonQuejasDenuncias.addProperty("Tipo_gestion"          , String.valueOf(queja.get(i).getCodigo_gestion()));
            jsonQuejasDenuncias.addProperty("Aldea"     , queja.get(i).getAldea());
            jsonQuejasDenuncias.addProperty("Identidad" , queja.get(i).getIdentidad());
            jsonQuejasDenuncias.addProperty("Nombre1"   ,
                    (queja.get(i).getAnonimo() == 1 || NombreSolicitante.length < 1)?"": NombreSolicitante[0]);
            jsonQuejasDenuncias.addProperty("Nombre2"   ,
                    (queja.get(i).getAnonimo() == 1 || NombreSolicitante.length < 2)?"": NombreSolicitante[1]);
            jsonQuejasDenuncias.addProperty("Apellido1" ,
                    (queja.get(i).getAnonimo() == 1 || NombreSolicitante.length < 3)?"": NombreSolicitante[2]);
            jsonQuejasDenuncias.addProperty("Apellido2" ,
                    (queja.get(i).getAnonimo() == 1 || NombreSolicitante.length < 4)?"": NombreSolicitante[3]);
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

                    realmConfig.getRealm().beginTransaction();
                    realmConfig.getRealm().commitTransaction();
                    realmConfig.getRealm().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            queja.deleteAllFromRealm();
                        }
                    });
                    realmConfig.getRealm().close();
                    quejasPresenter.FinalizarSincronizacion();
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                int l = 0;
            }
        });


    }
}
