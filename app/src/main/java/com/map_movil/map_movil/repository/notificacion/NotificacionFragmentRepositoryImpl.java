package com.map_movil.map_movil.repository.notificacion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.api.notificacion.ApiAdapterNotificacion;
import com.map_movil.map_movil.api.notificacion.ApiServiceNotificacion;
import com.map_movil.map_movil.model.Oficiona;
import com.map_movil.map_movil.model.Region;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.presenter.notificacion.NotificacionFragmentPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionFragmentRepositoryImpl implements NotificacionFragmentRepository {
    private NotificacionFragmentPresenter notificacionFragmentPresenter;
    private ApiAdapterNotificacion apiAdapterNotificacion;
    private ApiServiceNotificacion apiServiceNotificacion;

    public NotificacionFragmentRepositoryImpl(NotificacionFragmentPresenter notificacionFragmentPresenter){
        this.notificacionFragmentPresenter = notificacionFragmentPresenter;
        apiAdapterNotificacion = new ApiAdapterNotificacion();
        apiServiceNotificacion = apiAdapterNotificacion.getClientService();
    }

    @Override
    public void getRegiones() {
        Call<ArrayList<Region>> call = apiServiceNotificacion.getRegiones();
        call.enqueue(new Callback<ArrayList<Region>>() {
            @Override
            public void onResponse(Call<ArrayList<Region>> call, Response<ArrayList<Region>> response) {
                if (response.body().size() > 0 && response.isSuccessful()) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    for(Region region: response.body()){
                        arrayList.add(String.valueOf(region.getIntCodRegion()) + "-" + region.getStrRegion());
                    }
                    notificacionFragmentPresenter.showRegiones(arrayList);
                }else{
                    notificacionFragmentPresenter.showMessage("No se detectarón regiones.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Region>> call, Throwable t) {
                notificacionFragmentPresenter.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getOficionas(int intCodRegion) {
        Call<ArrayList<Oficiona>> call = apiServiceNotificacion.getOficinas(intCodRegion);
        call.enqueue(new Callback<ArrayList<Oficiona>>() {
            @Override
            public void onResponse(Call<ArrayList<Oficiona>> call, Response<ArrayList<Oficiona>> response) {
                if (response.body().size() > 0 && response.isSuccessful()) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    for(Oficiona oficiona: response.body()){
                        arrayList.add(String.valueOf(oficiona.getIntCodigo()) + "-" + oficiona.getStrNombre());
                    }
                    notificacionFragmentPresenter.showOficinas(arrayList);
                }else{
                    notificacionFragmentPresenter.showOficinas(new ArrayList<String>());
                    notificacionFragmentPresenter.showMessage("No se detectarón regiones.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Oficiona>> call, Throwable t) {
                notificacionFragmentPresenter.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getUsuarios(int intCodOficina) {
        Call<ArrayList<User>> call = apiServiceNotificacion.getUsuariosWithTokenForNotification(intCodOficina);
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.body().size() > 0 && response.isSuccessful()) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (User user : response.body()) {
                        arrayList.add(String.valueOf(user.getIntCodigo()) + "-" + user.getStrNombre() + " " + user.getStrsApellido());
                    }
                    notificacionFragmentPresenter.showUsuarios(arrayList);
                }else{
                    notificacionFragmentPresenter.showUsuarios(new ArrayList<String>());
                    notificacionFragmentPresenter.showMessage("No se detectarón usuarios.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                notificacionFragmentPresenter.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void sendNotificacion(ArrayList<String> arrayListUsuarios, String strNotificacion, int intCodUser, int intCodOffice) {
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObjectDataSend = new JsonObject();

        for (String strElement : arrayListUsuarios) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user", strElement);
            jsonArray.add(jsonObject);
        }

        jsonObjectDataSend.addProperty("message", strNotificacion);
        jsonObjectDataSend.addProperty("type_broadcast", 1);
        jsonObjectDataSend.addProperty("office", intCodOffice);
        jsonObjectDataSend.addProperty("emitter", intCodUser);
        jsonObjectDataSend.add("users", jsonArray);

        Call<ResponseApi> call = apiServiceNotificacion.sendNotification(jsonObjectDataSend);
        call.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if (response.isSuccessful() && response.body().getIntState() == 1) {
                    notificacionFragmentPresenter.closeActivity();
                } else {
                    notificacionFragmentPresenter.showMessage("No se logro enviar la notificación.");
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                notificacionFragmentPresenter.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void sendNotificacionAll(String strNotificacion, int intCodUser) {
        JsonObject jsonObjectDataSend = new JsonObject();
        jsonObjectDataSend.addProperty("message", strNotificacion);
        jsonObjectDataSend.addProperty("type_broadcast", 1);
        jsonObjectDataSend.addProperty("emitter", intCodUser);

        Call<ResponseApi> call = apiServiceNotificacion.sendNotificationAll(jsonObjectDataSend);
        call.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if (response.isSuccessful() && response.body().getIntState() == 1) {
                    notificacionFragmentPresenter.closeActivity();
                } else {
                    notificacionFragmentPresenter.showMessage("No se logro enviar la notificación.");
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                notificacionFragmentPresenter.showMessage(t.getMessage());
            }
        });

    }
}
