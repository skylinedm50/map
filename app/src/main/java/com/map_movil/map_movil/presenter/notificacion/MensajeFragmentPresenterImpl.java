package com.map_movil.map_movil.presenter.notificacion;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.map_movil.map_movil.interactor.notificacion.MensajeFragmentInteractor;
import com.map_movil.map_movil.interactor.notificacion.MensajeFragmentInteractorImpl;
import com.map_movil.map_movil.view.notificacion.MensajeFragmentView;

public class MensajeFragmentPresenterImpl implements MensajeFragmentPresenter {
    private Context context;
    private MensajeFragmentView mensajeFragmentView;
    private MensajeFragmentInteractor mensajeFragmentInteractor;
    private SharedPreferences sharedPreferences;

    public MensajeFragmentPresenterImpl(Context context, MensajeFragmentView mensajeFragmentView){
        this.context = context;
        this.mensajeFragmentView = mensajeFragmentView;
        mensajeFragmentInteractor = new MensajeFragmentInteractorImpl(this);
        sharedPreferences = context.getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
    }

    @Override
    public void sendMessage(JsonArray arrayMunicipio, String strMessage) {
        if(arrayMunicipio.size() < 1 || strMessage.isEmpty()){
            showMessage("Favor seleccionar uno o más municipios y agregar el texto que se enviará.");
        }else{
            mensajeFragmentInteractor.sendMessage(arrayMunicipio, sharedPreferences.getInt("codigo", 0), strMessage);
        }
    }

    @Override
    public void closeActivity() {
        mensajeFragmentView.closeActivity();
    }

    @Override
    public void showMessage(String strMessage) {
        mensajeFragmentView.showMessage(strMessage);
    }

    @Override
    public void validData() {

    }
}
