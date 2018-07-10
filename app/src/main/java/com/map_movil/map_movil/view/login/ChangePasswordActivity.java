package com.map_movil.map_movil.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.presenter.login.ChangePasswordPresenter;
import com.map_movil.map_movil.presenter.login.ChangePasswordPresenterImpl;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordView {
    private TextInputEditText textInputEditTextNewPassword;
    private TextInputEditText textInputEditTextRepetPassword;
    private Button buttonChangePassword;
    private ChangePasswordPresenter changePasswordPresenter;
    private Intent intent;
    private int intCodUser;
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        textInputEditTextNewPassword = findViewById(R.id.editNewPassword);
        textInputEditTextRepetPassword = findViewById(R.id.editRepitNewPassword);
        buttonChangePassword = findViewById(R.id.btnChangePassword);
        changePasswordPresenter = new ChangePasswordPresenterImpl(this);
        intent = getIntent();
        intCodUser = sharedPreferences.getInt("codigo", 0);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChangePassword(intCodUser, textInputEditTextNewPassword.getText().toString(), textInputEditTextRepetPassword.getText().toString());
            }
        });
    }

    @Override
    public void setChangePassword(int intCodUser, String strNewPassword, String strRepetPassword) {
        changePasswordPresenter.setChangePassword(intCodUser, strNewPassword, strRepetPassword);
    }

    @Override
    public void closeChangePassword() {
        setSharedPreferences();
        finish();
    }

    @Override
    public void showError(String strError) {
        Toast.makeText(getApplicationContext(), strError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSharedPreferences() {
        sharedPreferencesEditor = sharedPreferences.edit();

        sharedPreferencesEditor.putInt("estadoLogin", 1);
        sharedPreferencesEditor.commit();
    }
}
