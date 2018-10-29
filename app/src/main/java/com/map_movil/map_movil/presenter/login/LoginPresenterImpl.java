package com.map_movil.map_movil.presenter.login;

import android.content.SharedPreferences;

import com.google.gson.JsonObject;
import com.map_movil.map_movil.interactor.login.LoginInteractor;
import com.map_movil.map_movil.interactor.login.LoginInteractorImpl;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.view.login.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginInteractor objLoginInteractor;

    public LoginPresenterImpl(LoginView loginView){
       this.loginView = loginView;
        objLoginInteractor = new LoginInteractorImpl(this);
    }

    @Override
    public void existLogin(SharedPreferences sharedPreferences) {
        loginView.showProgressBar(false);
        if(sharedPreferences.getInt("codigo", 0) > 0 && sharedPreferences.getInt("estadoLogin", 0) == 1){
            loginView.goToHome();
        }else if(sharedPreferences.getInt("codigo", 0) > 0 && sharedPreferences.getInt("estadoLogin", 0) == 2){
            loginView.goToChangePassword();
        }
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        loginView.showProgressBar(true);
        getDetectError(strUser.replaceAll("\\s+",""), strPassword.replaceAll("\\s+",""));
    }

    @Override
    public void showDataUser(User ObjUser , JsonObject jsonObject){
        loginView.showDataUser(ObjUser, jsonObject);
    }

    @Override
    public void showError(String strError) {
        loginView.showProgressBar(false);
        loginView.showError(strError);
    }

    @Override
    public void getDetectError(String strUser, String strPassword) {
        if((strUser.isEmpty()) || (strPassword.isEmpty())){
            showError("Favor ingresar los datos solicitados");
        }else{
            objLoginInteractor.getDataUser(strUser, strPassword);
        }
    }
}
