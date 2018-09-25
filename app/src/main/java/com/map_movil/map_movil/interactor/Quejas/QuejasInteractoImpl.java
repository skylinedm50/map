package com.map_movil.map_movil.interactor.Quejas;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenter;
import com.map_movil.map_movil.repository.Quejas.QuejasRepository;
import com.map_movil.map_movil.repository.Quejas.QuejasRepositoryImpl;

public class QuejasInteractoImpl implements QuejasInteractor {


    private QuejasPresenter quejasPresenter;
    private QuejasRepository quejasRepository;

    public QuejasInteractoImpl(QuejasPresenter quejasPresenter , Context context){
            this.quejasPresenter = quejasPresenter;
            this.quejasRepository = new QuejasRepositoryImpl(this.quejasPresenter , context);
    }

    @Override
    public void SolicitarQuejas(int usuario) {
        if(BroadCastInternet.isConnected){
            this.quejasRepository.SolicitarQuejas(usuario);
        }else{
            this.quejasRepository.ListarQuejasOffline();
        }
    }

    @Override
    public void RegistrarQueja(JsonArray JsonQuejaDenuncia) {
        if(BroadCastInternet.isConnected){
            this.quejasRepository.RegistrarQueja(JsonQuejaDenuncia);
        }else{
            this.quejasRepository.RegistrarQuejaOffline(JsonQuejaDenuncia);
        }
    }

    @Override
    public void DescargarQuejas(int usuario, String aldea) {
        this.quejasRepository.DescargarQuejas(usuario , aldea);
    }

    @Override
    public void SincronizarQuejas(int usuario) {
        this.quejasRepository.SincronizarQuejas(usuario);
    }
}
