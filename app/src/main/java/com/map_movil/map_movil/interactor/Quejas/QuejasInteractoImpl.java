package com.map_movil.map_movil.interactor.Quejas;

import com.google.gson.JsonObject;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenter;
import com.map_movil.map_movil.repository.Quejas.QuejasRepository;
import com.map_movil.map_movil.repository.Quejas.QuejasRepositoryImpl;

public class QuejasInteractoImpl implements QuejasInteractor {


    private QuejasPresenter quejasPresenter;
    private QuejasRepository quejasRepository;

    public QuejasInteractoImpl(QuejasPresenter quejasPresenter){
            this.quejasPresenter = quejasPresenter;
            this.quejasRepository = new QuejasRepositoryImpl(this.quejasPresenter);
    }

    @Override
    public void SolicitarQuejas(int usuario) {
        this.quejasRepository.SolicitarQuejas(usuario);
    }

    @Override
    public void RegistrarQueja(JsonObject JsonQuejaDenuncia) {
        this.quejasRepository.RegistrarQueja(JsonQuejaDenuncia);
    }
}
