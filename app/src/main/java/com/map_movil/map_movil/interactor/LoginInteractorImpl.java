package com.map_movil.map_movil.interactor;

import com.map_movil.map_movil.presenter.LoginPresenter;
import com.map_movil.map_movil.repository.LoginRepository;
import com.map_movil.map_movil.repository.LoginRepositoryImpl;

public class LoginInteractorImpl implements LoginInteractor{
    private LoginPresenter objLoginPresenter;
    private LoginRepository objLoginRepository;

    public LoginInteractorImpl(LoginPresenter objLoginPresenter) {
        this.objLoginPresenter = objLoginPresenter;
        objLoginRepository = new LoginRepositoryImpl(objLoginPresenter);
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        objLoginRepository.getDataUser(strUser, strPassword);
    }
}
