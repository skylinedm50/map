package com.map_movil.map_movil.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.presenter.LoginPresenter;
import com.map_movil.map_movil.presenter.LoginPresenterImpl;


public class LoginActivity extends AppCompatActivity  implements LoginView{
    private LoginPresenter objLoginPresenter;
    private EditText objEditUser;
    private EditText objEditPassword;
    private Button objButLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        objButLogin = findViewById(R.id.btnLogin);
        objEditUser = findViewById(R.id.strUser);
        objEditPassword = findViewById(R.id.strPassword);
        objLoginPresenter = new LoginPresenterImpl(this);

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
        Toast.makeText(getApplicationContext(), objUser.getStrNombre() +  " " + objUser.getStrsApellido(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String strError) {
        Toast.makeText(getApplicationContext(), strError, Toast.LENGTH_SHORT).show();
    }


}
