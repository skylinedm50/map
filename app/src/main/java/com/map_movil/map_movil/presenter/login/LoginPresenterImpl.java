package com.map_movil.map_movil.presenter.login;

import com.map_movil.map_movil.interactor.login.LoginInteractor;
import com.map_movil.map_movil.interactor.login.LoginInteractorImpl;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.view.login.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView objLoginView;
    private LoginInteractor objLoginInteractor;

    public LoginPresenterImpl(LoginView objLoginView){
       this.objLoginView = objLoginView;
        objLoginInteractor = new LoginInteractorImpl(this);
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        objLoginInteractor.getDataUser(strUser, strPassword);
    }

    @Override
    public void showDataUser(User ObjUser){
        objLoginView.showDataUser(ObjUser);
    }

    @Override
    public void showError(String strError) {
        objLoginView.showError(strError);
    }
}
