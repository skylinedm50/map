package com.map_movil.map_movil.presenter.login;

import com.map_movil.map_movil.interactor.login.ChangePasswordInteractor;
import com.map_movil.map_movil.interactor.login.ChangePasswordInteractorImpl;
import com.map_movil.map_movil.view.login.ChangePasswordView;

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {
    private ChangePasswordView changePasswordView;
    private ChangePasswordInteractor changePasswordInteractor;

    public ChangePasswordPresenterImpl(ChangePasswordView changePasswordView){
        this.changePasswordView = changePasswordView;
        changePasswordInteractor = new ChangePasswordInteractorImpl(this);
    }

    @Override
    public void setChangePassword(int intCodUser, String strNewPassword, String strRepetPassword) {
        checkPasswords(intCodUser, strNewPassword.replaceAll("\\s+",""), strRepetPassword.replaceAll("\\s+",""));
    }

    @Override
    public void closeChangePassword() {
        changePasswordView.closeChangePassword();
    }

    @Override
    public void checkPasswords(int intCodUser, String strNewPassword, String strRepetPassword) {
        if(strNewPassword.isEmpty() || strRepetPassword.isEmpty()){
            showError("Uno o varios campos se encuentran en blanco.");
        }else if(strNewPassword.equals(strRepetPassword)){
            changePasswordInteractor.setChangePassword(intCodUser, strNewPassword);
        }else{
            showError("Las contraseñas no coinciden.");
        }
    }

    @Override
    public void showError(String strError) {
        changePasswordView.showError(strError);
    }
}
