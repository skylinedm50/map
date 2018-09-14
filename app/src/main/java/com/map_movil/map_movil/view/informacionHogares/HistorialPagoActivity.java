package com.map_movil.map_movil.view.informacionHogares;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.model.HistorialPago;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HistorialPagoActivity extends AppCompatActivity {
    private TextView tv_Mensaje;
    private ListView lv_Historial_Pago;
    private TextView tv_Identidad;
    private TextView tv_Titular;
    private LV_Historial_Adapter Historial_Adapter;
    private ArrayList<HistorialPago> Historiales;
    private String jsonHistorial;
    private String Identidad;
    private String Titular;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pago);

        tv_Mensaje = findViewById(R.id.tv_Mensaje);
        tv_Identidad = findViewById(R.id.tv_Identidad);
        tv_Titular = findViewById(R.id.tv_Titular);
        lv_Historial_Pago = findViewById(R.id.lv_Historial_Pago);
        //this.setTitle("Ir Atrás");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        jsonHistorial = getIntent().getStringExtra("jsonHistorial");
        Identidad = getIntent().getStringExtra("Identidad_Titular");
        Titular = getIntent().getStringExtra("Titular");


        Type type = new TypeToken<ArrayList<HistorialPago>>(){}.getType();
        Historiales = gson.fromJson(jsonHistorial, type);
        Historial_Adapter = new LV_Historial_Adapter(this, Historiales);
        lv_Historial_Pago.setAdapter(Historial_Adapter);

        tv_Titular.setText(Titular);
        tv_Identidad.setText(Identidad);
        BroadCastInternet.subscribeForMessageInternet(getApplicationContext(), findViewById(R.id.relativeLayoutMain));
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    public class LV_Historial_Adapter extends ArrayAdapter<HistorialPago> {
        private Context context;
        private ArrayList<HistorialPago> historiales;

        public LV_Historial_Adapter(Context context, ArrayList<HistorialPago> historiales) {
            super(context, R.layout.item_historial_pago, historiales);

            this.context = context;
            this.historiales = historiales;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View item = inflater.inflate(R.layout.item_historial_pago, null);

            TextView tv_Anio_Pago =  item.findViewById(R.id.tv_Anio_Pago);
            TextView tv_Pago = item.findViewById(R.id.tv_Pago);
            TextView tv_Codigo_Hogar = item.findViewById(R.id.tv_Codigo_Hogar);
            TextView tv_Estado_Pago = item.findViewById(R.id.tv_Estado_Pago);
            TextView tv_Fecha_Cobro = item.findViewById(R.id.tv_Fecha_Cobro);
            TextView tv_Descripcion = item.findViewById(R.id.tv_Descripcion);

            tv_Anio_Pago.setText(String.valueOf(historiales.get(position).getPag_anyo()));
            tv_Pago.setText(historiales.get(position).getPag_nombre());
            tv_Codigo_Hogar.setText(String.valueOf(historiales.get(position).getTit_hogar()));
            tv_Fecha_Cobro.setText(historiales.get(position).getTit_fecha_cobro());
            tv_Estado_Pago.setText(historiales.get(position).getEstado_Pago());
            tv_Descripcion.setText(historiales.get(position).getTit_proy_corta());

            return item;
        }
    }
}
