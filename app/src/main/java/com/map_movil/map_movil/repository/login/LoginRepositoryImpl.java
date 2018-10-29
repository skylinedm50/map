package com.map_movil.map_movil.repository.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.map_movil.map_movil.api.login.ApiAdapterLogin;
import com.map_movil.map_movil.api.login.ApiServiceLogin;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.presenter.login.LoginPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepositoryImpl implements LoginRepository{
    private LoginPresenter objLoginPresenter;
    private User user;
    private ApiAdapterLogin apiAdapterLogin;
    private ApiServiceLogin apiServiceLogin;

    public LoginRepositoryImpl(LoginPresenter objLoginPresenter){
        this.objLoginPresenter = objLoginPresenter;
        apiAdapterLogin = new ApiAdapterLogin();
        apiServiceLogin = apiAdapterLogin.getClientService();
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        Call<List<User>> call = apiServiceLogin.getLogin(strUser, strPassword);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               if(response.body() != null && response.body().size() > 0){
                   user = response.body().get(0);
                   setTokenNotification();
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


    @Override
    public void setTokenNotification() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            objLoginPresenter.showError(task.getException().getMessage());
                            return;
                        }
                        Call<ResponseApi> call = apiServiceLogin.setTokenNotification(user.getIntCodigo(), task.getResult().getToken());
                        call.enqueue(new Callback<ResponseApi>() {
                            @Override
                            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                                if(response.isSuccessful() && response.body().getIntState() == 1){
                                    objLoginPresenter.showDataUser(user);
                                }else{
                                    objLoginPresenter.showError("Error al ingresar token.");
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseApi> call, Throwable t) {
                                objLoginPresenter.showError(t.getMessage());
                            }
                        });
                    }
                });
    }
}