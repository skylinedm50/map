package com.map_movil.map_movil.repository;

import com.map_movil.map_movil.api.ApiAdapter;
import com.map_movil.map_movil.api.ApiService;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.presenter.LoginPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepositoryImpl implements LoginRepository{
    private LoginPresenter objLoginPresenter;

    public LoginRepositoryImpl(LoginPresenter objLoginPresenter){
        this.objLoginPresenter = objLoginPresenter;
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        ApiAdapter objApiAdapter = new ApiAdapter();
        ApiService objApiService = objApiAdapter.getClientService();
        Call<List<User>> call = objApiService.getLogin(strUser, strPassword);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               if(response.body() != null){
                    User objUser = response.body().get(0);
                    objLoginPresenter.showDataUser(objUser);
                }else{
                   objLoginPresenter.showError("Las credenciales son incorrectas.");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                objLoginPresenter.showError(t.getMessage());
            }
        });
    }
}