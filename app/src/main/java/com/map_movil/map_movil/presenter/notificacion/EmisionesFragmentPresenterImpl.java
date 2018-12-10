package com.map_movil.map_movil.presenter.notificacion;

import android.content.Context;
import android.content.SharedPreferences;

import com.map_movil.map_movil.interactor.notificacion.EmisionesFragmentInteractor;
import com.map_movil.map_movil.interactor.notificacion.EmisionesFragmentInteractorImpl;
import com.map_movil.map_movil.model.Emision;
import com.map_movil.map_movil.view.notificacion.EmisionesFragmentView;

import java.util.ArrayList;

public class EmisionesFragmentPresenterImpl implements EmisionesFragmentPresenter {
    private Context context;
    private EmisionesFragmentView emisionesFragmentView;
    private EmisionesFragmentInteractor emisionesFragmentInteractor;
    private SharedPreferences sharedPreferences;
    private int intCodUser;

    public EmisionesFragmentPresenterImpl(EmisionesFragmentView emisionesFragmentView, Context context) {
        this.context = context;
        this.emisionesFragmentView = emisionesFragmentView;
        emisionesFragmentInteractor = new EmisionesFragmentInteractorImpl(this);
        sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        intCodUser = sharedPreferences.getInt("codigo", 0);
    }

    @Override
    public void getEmisionesSend() {
        emisionesFragmentInteractor.getEmisionesSend(intCodUser);
    }

    @Override
    public void getEmisionesReceive() {
        emisionesFragmentInteractor.getEmisionesReceive(intCodUser);
    }

    @Override
    public void getEmisiones(int intType) {
        if(intType == 1){
            getEmisionesReceive();
        }else {
            getEmisionesSend();
        }
    }

    @Override
    public void showEmisiones(ArrayList<Emision> emisiones) {
        emisionesFragmentView.showEmisiones(emisiones);
    }

    @Override
    public void showMessage(String strShowMessage) {
        emisionesFragmentView.showMessage(strShowMessage);
    }

    @Override
    public void showMessageForEmptyData(boolean bolValue) {
        emisionesFragmentView.showMessageForEmptyData(bolValue);
    }
}
