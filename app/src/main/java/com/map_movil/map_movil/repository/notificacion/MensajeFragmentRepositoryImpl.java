package com.map_movil.map_movil.repository.notificacion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.api.notificacion.ApiAdapterNotificacion;
import com.map_movil.map_movil.api.notificacion.ApiServiceNotificacion;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.presenter.notificacion.MensajeFragmentPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensajeFragmentRepositoryImpl implements MensajeFragmentRepository {
    private MensajeFragmentPresenter mensajeFragmentPresenter;
    private ApiAdapterNotificacion apiAdapterNotificacion;
    private ApiServiceNotificacion apiServiceNotificacion;

    public MensajeFragmentRepositoryImpl(MensajeFragmentPresenter mensajeFragmentPresenter){
        this.mensajeFragmentPresenter = mensajeFragmentPresenter;
        apiAdapterNotificacion = new ApiAdapterNotificacion();
        apiServiceNotificacion = apiAdapterNotificacion.getClientService();
    }

    @Override
    public void sendMessage(JsonArray arrayMunicipio, int intCodUser, String strMessage) {
        JsonObject jsonObjectDataSend = new JsonObject();
        jsonObjectDataSend.add("municipios", arrayMunicipio);
        jsonObjectDataSend.addProperty("type_broadcast", 4);
        jsonObjectDataSend.addProperty("emitter", intCodUser);
        jsonObjectDataSend.addProperty("message", strMessage);

        Call<ResponseApi> call = apiServiceNotificacion.sendMessage(jsonObjectDataSend);
        call.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if(response.isSuccessful() && response.body().getIntState() == 1){
                    mensajeFragmentPresenter.closeActivity();
                }else {
                    mensajeFragmentPresenter.showMessage("Ocurrio un error al intentar enviar el mensaje.");
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                mensajeFragmentPresenter.showMessage(t.getMessage());
            }
        });
    }
}
