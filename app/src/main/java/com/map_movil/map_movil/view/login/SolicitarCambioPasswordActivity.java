package com.map_movil.map_movil.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.map_movil.map_movil.R;

public class SolicitarCambioPasswordActivity extends AppCompatActivity {

    private TextInputEditText TextViewstrUser;
    private Button            btnContinuar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_cambio_password);

        this.TextViewstrUser = findViewById(R.id.TextViewstrUser);
        this.btnContinuar = findViewById(R.id.btnContinuar);

        this.btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
