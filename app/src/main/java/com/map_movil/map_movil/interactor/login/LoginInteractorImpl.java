package com.map_movil.map_movil.interactor.login;

import com.map_movil.map_movil.presenter.login.LoginPresenter;
import com.map_movil.map_movil.repository.login.LoginRepository;
import com.map_movil.map_movil.repository.login.LoginRepositoryImpl;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginPresenter objLoginPresenter;
    private LoginRepository objLoginRepository;

    public LoginInteractorImpl(LoginPresenter objLoginPresenter) {
        this.objLoginPresenter = objLoginPresenter;
        objLoginRepository = new LoginRepositoryImpl(this.objLoginPresenter);
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        objLoginRepository.getDataUser(strUser, strPassword);
    }
}
