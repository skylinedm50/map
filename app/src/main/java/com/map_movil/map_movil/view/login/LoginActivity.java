package com.map_movil.map_movil.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.map_movil.map_movil.HomeActivity;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.presenter.login.LoginPresenter;
import com.map_movil.map_movil.presenter.login.LoginPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity  implements LoginView {
    private LoginPresenter loginPresenter;
    private EditText objEditUser;
    private EditText objEditPassword;
    private Button buttonLogin;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Intent intent;
    private LinearLayout linearLayoutFieldsLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        setContentView(R.layout.activity_login);
        buttonLogin = findViewById(R.id.btnLogin);
        objEditUser = findViewById(R.id.strUser);
        objEditPassword = findViewById(R.id.strPassword);
        linearLayoutFieldsLogin = findViewById(R.id.linearLayoutFieldsLogin);
        progressBar = findViewById(R.id.progressBar);
        loginPresenter = new LoginPresenterImpl(this);
        existLogin(sharedPreferences);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataUser(objEditUser.getText().toString(), objEditPassword.getText().toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        existLogin(sharedPreferences);
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        loginPresenter.getDataUser(strUser, strPassword);
    }

    @Override
    public void existLogin(SharedPreferences sharedPreferences) {
        loginPresenter.existLogin(sharedPreferences);
    }

    @Override
    public void goToHome() {
        intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToChangePassword() {
        intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void showDataUser(User user){
        saveLocalLogin(user.getIntCodigo(), user.getStrNombre() + " " + user.getStrsApellido(), user.getIntCantidadLogin(), user.getIntEstado());
        if(user.getIntEstado() == 2){//Cuando un login es por primera vez, por lo que se requiere modificar la contraseña.
            goToChangePassword();
        }else if(user.getIntEstado() == 1){//Cuando un ingreso es por segunda vez o más pero la contraseña ya fue modificada.
            goToHome();
        }
    }

    @Override
    public void showError(String strError) {
        Toast.makeText(getApplicationContext(), strError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveLocalLogin(int intCodUser, String strNombre, int intCantLogin, int inCodEstado) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        sharedPreferencesEditor.putInt("codigo", intCodUser);
        sharedPreferencesEditor.putString("nombre", strNombre);
        sharedPreferencesEditor.putInt("cantidadLogin", intCantLogin + 1);
        sharedPreferencesEditor.putInt("estadoLogin", inCodEstado);
        sharedPreferencesEditor.putString("fechaLogin",  simpleDateFormat.format(date.getTime()));
        sharedPreferencesEditor.putString("fechaDeleteData", simpleDateFormat.format(date.getTime()));
        sharedPreferencesEditor.commit();
    }

    @Override
    public void showProgressBar(Boolean bolShow) {
        if(bolShow){
            linearLayoutFieldsLogin.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            linearLayoutFieldsLogin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
