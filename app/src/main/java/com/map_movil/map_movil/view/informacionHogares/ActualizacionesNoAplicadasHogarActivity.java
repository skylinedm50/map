package com.map_movil.map_movil.view.informacionHogares;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.HogarActualizaciones;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ActualizacionesNoAplicadasHogarActivity extends AppCompatActivity {

    private TextView tvMensajeAM;
    private TextView tvMensajeCT;
    private RecyclerView rvAgregacion_Menor;
    private RecyclerView rvCambio_Titular;
    private ArrayList<HogarActualizaciones> Agregacion_Menores;
    private ArrayList<HogarActualizaciones> Cambio_Titulares;
    private AdapterActualizacionesMiembroHogar Agregacion_Menor_Adapter;
    private AdapterActualizacionesMiembroHogar Cambio_Titular_Adapter;
    private Gson gson = new Gson();
    private String jsonAgregacion_Menores;
    private String jsonCambio_Titulares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizaciones_no_aplicadas_hogar);

        tvMensajeAM = findViewById(R.id.tvMensajeAM);
        tvMensajeCT = findViewById(R.id.tvMensajeCT);

        rvAgregacion_Menor = findViewById(R.id.rvAgregacion_Menor);
        rvCambio_Titular = findViewById(R.id.rvCambio_Titular);
        //this.setTitle("Ir Atrás");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jsonAgregacion_Menores = getIntent().getStringExtra("jsonAgregacion_Menores");
        jsonCambio_Titulares = getIntent().getStringExtra("jsonCambio_Titulares");

        Type type = new TypeToken<ArrayList<HogarActualizaciones>>(){}.getType();
        Type type2 = new TypeToken<ArrayList<HogarActualizaciones>>(){}.getType();

        Agregacion_Menores = gson.fromJson(jsonAgregacion_Menores, type);
        Cambio_Titulares = gson.fromJson(jsonCambio_Titulares,type2);

        if(Agregacion_Menores==null || Agregacion_Menores.isEmpty()){
            tvMensajeAM.setVisibility(View.VISIBLE);


        }else{

            rvAgregacion_Menor.setLayoutManager(new LinearLayoutManager(this));
            Agregacion_Menor_Adapter = new AdapterActualizacionesMiembroHogar(this,Agregacion_Menores);
            rvAgregacion_Menor.setAdapter(Agregacion_Menor_Adapter);
        }
        if(Cambio_Titulares==null || Cambio_Titulares.isEmpty()){
            tvMensajeCT.setVisibility(View.VISIBLE);

        }else{
            rvCambio_Titular.setLayoutManager(new LinearLayoutManager(this));
            Cambio_Titular_Adapter = new AdapterActualizacionesMiembroHogar(this, Cambio_Titulares);
            rvCambio_Titular.setAdapter(Cambio_Titular_Adapter);
        }

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    public class AdapterActualizacionesMiembroHogar extends RecyclerView.Adapter<AdapterActualizacionesMiembroHogar.ViewHolder> {


        private ArrayList<HogarActualizaciones> A_Menores;
        private LayoutInflater mInflater;
        private TextView tv_Nombre;
        private TextView tv_Estado;
        private TextView tv_Fecha;


        // data is passed into the constructor
        public AdapterActualizacionesMiembroHogar(Context context, ArrayList <HogarActualizaciones> A_Menores) {
            this.mInflater = LayoutInflater.from(context);
            this.A_Menores = A_Menores;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_info_persona_actualizacion_hogar, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {



            tv_Nombre.setText(A_Menores.get(position).getPER_NOMBRE());
            tv_Estado.setText(A_Menores.get(position).getESTADO());
            tv_Fecha.setText(A_Menores.get(position).getFCH_ACTUALIZACION());


        }

        // total number of rows
        @Override
        public int getItemCount() {
            return A_Menores.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            ViewHolder(View item) {
                super(item);
                tv_Nombre =  item.findViewById(R.id.tv_Nombre);
                tv_Estado = item.findViewById(R.id.tv_Estado);
                tv_Fecha = item.findViewById(R.id.tv_Fecha);


                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View view) {

            }
        }
    }
}
