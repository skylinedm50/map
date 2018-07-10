package com.map_movil.map_movil.view.login;

public interface ChangePasswordView {
    void setChangePassword(int intCodUser, String strNewPassword, String strRepetPassword);
    void closeChangePassword();
    void showError(String strError);
    void setSharedPreferences();
}
