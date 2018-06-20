package com.map_movil.map_movil.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.map_movil.map_movil.HomeActivity;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.presenter.login.LoginPresenter;
import com.map_movil.map_movil.presenter.login.LoginPresenterImpl;


public class LoginActivity extends AppCompatActivity  implements LoginView {
    private LoginPresenter objLoginPresenter;
    private EditText objEditUser;
    private EditText objEditPassword;
    private Button objButLogin;
    private SharedPreferences objSharedPreferences;
    private SharedPreferences.Editor objEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        objSharedPreferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        objEditor = objSharedPreferences.edit();


        setContentView(R.layout.activity_login);
        objButLogin = findViewById(R.id.btnLogin);
        objEditUser = findViewById(R.id.strUser);
        objEditPassword = findViewById(R.id.strPassword);
        objLoginPresenter = new LoginPresenterImpl(this);

        if(objSharedPreferences.getInt("codigo", 0) > 0){
            Intent objIntent = new Intent(this, HomeActivity.class);
            startActivity(objIntent);
        }


        objButLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getDetectError(objEditUser.getText().toString(), objEditPassword.getText().toString())){
                    getDataUser(objEditUser.getText().toString(), objEditPassword.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "Favor ingrese los datos solicitados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        objSharedPreferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        objEditor = objSharedPreferences.edit();

        if(objSharedPreferences.getInt("codigo", 0) > 0){
            Intent objIntent = new Intent(this, HomeActivity.class);
            startActivity(objIntent);
        }
    }


    private boolean getDetectError(String strUser, String strPassword){
        if((strUser != null) && (strPassword != null)){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        objLoginPresenter.getDataUser(strUser, strPassword);
    }

    @Override
    public void showDataUser(User objUser){
        if(objUser.getIntEstado() == 2){//Cuando su login es por primera vez.
            Intent objIntent = new Intent(this, ChangePasswordActivity.class);
            startActivity(objIntent);

        }else if(objUser.getIntEstado() == 1){//Cuando su ingreso es la segunda vez o más pero la contraseña ya fue modificada.
            objEditor.putInt("codigo", objUser.getIntCodigo());
            objEditor.putString("nombre", objUser.getStrNombre());
            objEditor.putInt("cantidadLogin", objUser.getIntCantidadLogin() + 1);
            objEditor.commit();

            Intent objIntent = new Intent(this, HomeActivity.class);
            startActivity(objIntent);
        }else{
            Toast.makeText(getApplicationContext(), objUser.getStrNombre() +  " " + objUser.getStrsApellido(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(String strError) {
        Toast.makeText(getApplicationContext(), strError, Toast.LENGTH_SHORT).show();
    }


}
