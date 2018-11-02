package com.map_movil.map_movil.repository.login;

public interface LoginRepository {
    void getDataUser(String strUser, String strPassword);
    void setTokenNotification();
}
