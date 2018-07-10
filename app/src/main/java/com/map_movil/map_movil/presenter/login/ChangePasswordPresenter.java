package com.map_movil.map_movil.presenter.login;

public interface ChangePasswordPresenter {
    void setChangePassword(int intCodUser, String strNewPassword, String strRepetPassword);
    void closeChangePassword();
    void checkPasswords(int intCodUser, String strNewPassword, String strRepetPassword);
    void showError(String strError);
}
