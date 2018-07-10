package com.map_movil.map_movil.view.validar_hogares;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.nucleo_hogar_validar_Nucleo_adapter;

public class ValidarNucleoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        RecyclerView Personas_Validar = findViewById(R.id.rv_hogar_validar);
        TextView NumeroHogar = findViewById(R.id.numero_hogar);
        TextView UmbralHogar = findViewById(R.id.umbral_hogar);
        TextView Aldea       = findViewById(R.id.aldea_hogar);
        TextView Direccion   = findViewById(R.id.direccion_hogar);

        NumeroHogar.setText(String.valueOf(getIntent().getIntExtra("hogar",0)));
        UmbralHogar.setText(getIntent().getStringExtra("Umbral"));
        Aldea.setText(getIntent().getStringExtra("Aldea"));
        Direccion.setText(getIntent().getStringExtra("Direccion"));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        nucleo_hogar_validar_Nucleo_adapter nucleo_hogar_validar_adapter = new nucleo_hogar_validar_Nucleo_adapter(
                                                        getApplicationContext()                                  ,
                                                        (Activity) this                                          ,
                                                        getIntent().getIntExtra("per_persona",0) ,
                                                        getIntent().getIntExtra("hogar", 0)  );

        Personas_Validar.setAdapter(nucleo_hogar_validar_adapter);
        Personas_Validar.setLayoutManager(linearLayoutManager);
        nucleo_hogar_validar_adapter.BuscarDatos(getIntent().getStringExtra("identidad"));

        getSupportActionBar().setTitle("Validar Nucleo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
