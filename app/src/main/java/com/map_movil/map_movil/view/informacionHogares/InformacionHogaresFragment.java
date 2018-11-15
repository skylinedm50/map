package com.map_movil.map_movil.view.informacionHogares;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.api.informacionHogares.ApiAdapterInformacionHogares;
import com.map_movil.map_movil.api.informacionHogares.ApiServiceInformacionHogares;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.HogarActualizaciones;
import com.map_movil.map_movil.model.HogarInformacion;
import com.map_movil.map_movil.model.NucleoHogar;
import com.map_movil.map_movil.model.Hogar_Validar;

import java.util.ArrayList;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformacionHogaresFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private TextView tv_Mensaje;
    private TextView tv_Caserio;
    private TextView tv_Aldea;
    private TextView tv_Municipio;
    private TextView tv_Departamento;
    private TextView tv_Umbral;
    private TextView tv_Estado_Hogar;
    private TextView tv_Telefono;
    private ProgressBar pBar;
    public Context context;
    private LinearLayout layout;
    private ApiServiceInformacionHogares Service;
    private Gson gson = new Gson();
    public ApiAdapterInformacionHogares da;
    private String Identidad_Titular;
    private String jsonCambio_Titulares;
    private String jsonAgregacion_Menores;
    private String id_Hogar;
    private RV_Nucleo_Hogar_Adapter Nucleo_Hogar_Adapter;
    private RecyclerView rv_Nucleo_Hogar;
    private Button buttonVerHistrorialPago;
    private Button buttonVerActualizacionesNoRealizadas;
    private RealmConfig realmConfig;

    private View view;

    public InformacionHogaresFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_informacion_hogares, container, false);

        pBar = view.findViewById(R.id.pBar);
        tv_Mensaje = view.findViewById(R.id.tv_Mensaje);
        layout = view.findViewById(R.id.layout);
        tv_Caserio = view.findViewById(R.id.tv_Caserio);
        tv_Aldea = view.findViewById(R.id.tv_Aldea);
        tv_Municipio = view.findViewById(R.id.tv_Municipio);
        tv_Departamento = view.findViewById(R.id.tv_Departamento);
        tv_Umbral = view.findViewById(R.id.tv_Umbral);
        tv_Estado_Hogar = view.findViewById(R.id.tv_Estado_Hogar);
        tv_Telefono = view.findViewById(R.id.tv_Telefono);

        rv_Nucleo_Hogar = view.findViewById(R.id.rvNucleo_Hogar);
        tv_Mensaje.setTextColor(Color.GRAY);
        //this.setTitle("Buscar por Identidad del Titular");
        da= new ApiAdapterInformacionHogares();
        Service = da.getClientService();
        context = view.getContext();

        buttonVerHistrorialPago = view.findViewById(R.id.button2);
        buttonVerActualizacionesNoRealizadas = view.findViewById(R.id.button4);
        buttonVerHistrorialPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Evt_Ver_Historial_Pago();
            }
        });
        buttonVerActualizacionesNoRealizadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Evt_Ver_Actualizacion();
            }
        });

        setHasOptionsMenu(true);
        BroadCastInternet.subscribeForMessageInternet(view.getContext(), view);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        BroadCastInternet.subscribeForMessageInternet(view.getContext(), view);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setQueryHint("Identidad del Titular...");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Identidad_Titular = query;
        tv_Aldea.setText("");
        tv_Caserio.setText("");
        tv_Departamento.setText("");
        tv_Estado_Hogar.setText("");
        tv_Municipio.setText("");
        tv_Umbral.setText("");
        tv_Telefono.setText("");
        rv_Nucleo_Hogar.setAdapter(null);
        layout.setVisibility(View.GONE);
        tv_Mensaje.setVisibility(View.VISIBLE);
        tv_Mensaje.setTextColor(Color.GRAY);
        tv_Mensaje.setText("Buscando información del titular "+Identidad_Titular+"...");
        pBar.setVisibility(View.VISIBLE);

        if(BroadCastInternet.isConnected){
            Service.getDatos_Hogar(Identidad_Titular).enqueue(new Callback<ArrayList<HogarInformacion>>() {
                @Override
                public void onResponse(Call<ArrayList<HogarInformacion>> call, Response<ArrayList<HogarInformacion>> response) {
                    ArrayList<HogarInformacion> Datos_Hogar;

                    Datos_Hogar = response.body();
                    tv_Mensaje.setText("Cargando Nucleo Familiar...");

                    if (!Datos_Hogar.isEmpty()) {

                        tv_Aldea.setText(Datos_Hogar.get(0).getDesc_aldea());
                        tv_Caserio.setText(Datos_Hogar.get(0).getDesc_caserio());
                        tv_Departamento.setText(Datos_Hogar.get(0).getDesc_departamento());
                        tv_Estado_Hogar.setText(Datos_Hogar.get(0).getHog_estado_descripcion());
                        tv_Municipio.setText(Datos_Hogar.get(0).getDesc_municipio());
                        tv_Umbral.setText(Datos_Hogar.get(0).getHog_umbral());
                        tv_Telefono.setText(Datos_Hogar.get(0).getHog_telefono());

                        id_Hogar = String.valueOf(Datos_Hogar.get(0).getHog_hogar());


                        Service.getDatos_Personas(id_Hogar).enqueue(new Callback<ArrayList<NucleoHogar>>() {
                            @Override
                            public void onResponse(Call<ArrayList<NucleoHogar>> call, Response<ArrayList<NucleoHogar>> response) {

                                ArrayList<NucleoHogar> Datos_Personas;
                                Datos_Personas = response.body();

                                layout.setVisibility(View.VISIBLE);
                                tv_Mensaje.setVisibility(View.GONE);
                                pBar.setVisibility(View.GONE);
                                if (!Datos_Personas.isEmpty()) {
                                    rv_Nucleo_Hogar.setLayoutManager(new LinearLayoutManager(context));
                                    Nucleo_Hogar_Adapter = new RV_Nucleo_Hogar_Adapter(context, Datos_Personas);

                                    rv_Nucleo_Hogar.setAdapter(Nucleo_Hogar_Adapter);

                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<NucleoHogar>> call, Throwable t) {
                                //SetTitleLayout("Buscar por Identidad del Titular");
                                layout.setVisibility(View.GONE);
                                pBar.setVisibility(View.GONE);
                                tv_Mensaje.setVisibility(View.VISIBLE);
                                tv_Mensaje.setTextColor(Color.RED);
                                tv_Mensaje.setText("Hay problemas con la conexión, verifique e intente de nuevo.");
                            }
                        });

                    } else {
                        //SetTitleLayout("Buscar por Identidad del Titular");
                        layout.setVisibility(View.GONE);
                        pBar.setVisibility(View.GONE);
                        tv_Mensaje.setVisibility(View.VISIBLE);
                        tv_Mensaje.setTextColor(Color.RED);
                        tv_Mensaje.setText("No se ha encontrado información del titular con identidad: " + Identidad_Titular);
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<HogarInformacion>> call, Throwable t) {

                    //SetTitleLayout("Buscar por Identidad del Titular");
                    layout.setVisibility(View.GONE);
                    pBar.setVisibility(View.GONE);
                    tv_Mensaje.setVisibility(View.VISIBLE);
                    tv_Mensaje.setTextColor(Color.RED);
                    tv_Mensaje.setText("Hay problemas con la conexión, verifique e intente de nuevo.");
                }
            });
        }else{
            realmConfig = new RealmConfig(context);
            realmConfig.getRealm().beginTransaction();
            RealmResults<Hogar_Validar> getTitularHogar;
            getTitularHogar = realmConfig.getRealm().where(Hogar_Validar.class).equalTo("per_identidad", Identidad_Titular).and().equalTo("per_titular", 1).findAll();

            RealmResults<Hogar_Validar> getNucleoHogar;
            getNucleoHogar = realmConfig.getRealm().where(Hogar_Validar.class).equalTo("hog_hogar", getTitularHogar.get(0).getHog_hogar()).findAll();
            realmConfig.getRealm().commitTransaction();


            if(getTitularHogar.size() > 0) {
                tv_Aldea.setText(getTitularHogar.get(0).getDesc_aldea());
                tv_Caserio.setText(getTitularHogar.get(0).getDesc_caserio());
                tv_Departamento.setText(getTitularHogar.get(0).getDesc_departamento());
                tv_Estado_Hogar.setText(getTitularHogar.get(0).getHog_estado_descripcion());
                tv_Municipio.setText(getTitularHogar.get(0).getDesc_municipio());
                tv_Umbral.setText(getTitularHogar.get(0).getHog_umbral());
                tv_Telefono.setText(getTitularHogar.get(0).getHog_telefono());

                id_Hogar = String.valueOf(getTitularHogar.get(0).getHog_hogar());

                ArrayList<NucleoHogar> Datos_Personas = new ArrayList<>();

                for(Hogar_Validar item: getNucleoHogar){
                    NucleoHogar nucleoHogar = new NucleoHogar();
                    nucleoHogar.setPer_identidad(item.getPer_identidad());
                    nucleoHogar.setNombre_completo(item.getNombre());
                    nucleoHogar.setPer_fch_nacimiento(null);
                    nucleoHogar.setSexoD(item.getSexo());
                    nucleoHogar.setPer_estado_descripcion(item.getPer_estado_descripcion());
                    nucleoHogar.setPer_titular(item.getPer_titular());
                    Datos_Personas.add(nucleoHogar);
                }

                layout.setVisibility(View.VISIBLE);
                tv_Mensaje.setVisibility(View.GONE);
                pBar.setVisibility(View.GONE);
                if (!Datos_Personas.isEmpty()) {
                    rv_Nucleo_Hogar.setLayoutManager(new LinearLayoutManager(context));
                    Nucleo_Hogar_Adapter = new RV_Nucleo_Hogar_Adapter(context, Datos_Personas);

                    rv_Nucleo_Hogar.setAdapter(Nucleo_Hogar_Adapter);

                }
            }else{
                layout.setVisibility(View.GONE);
                pBar.setVisibility(View.GONE);
                tv_Mensaje.setVisibility(View.VISIBLE);
                tv_Mensaje.setTextColor(Color.RED);
                tv_Mensaje.setText("No se ha encontrado información del titular con identidad: " + Identidad_Titular);
            }
            realmConfig.getRealm().close();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    public void Evt_Ver_Historial_Pago(){
        layout.setVisibility(View.GONE);
        tv_Mensaje.setVisibility(View.VISIBLE);
        tv_Mensaje.setTextColor(Color.GRAY);
        pBar.setVisibility(View.VISIBLE);
        tv_Mensaje.setText("Cargando Historial del Titular...");


        if(BroadCastInternet.isConnected) {
            Service.getHistorial_Pagos(Identidad_Titular).enqueue(new Callback<ArrayList<HistorialPago>>() {
                @Override
                public void onResponse(Call<ArrayList<HistorialPago>> call, Response<ArrayList<HistorialPago>> response) {
                    ArrayList<HistorialPago> Datos_Historial;
                    Datos_Historial = response.body();

                    if (!Datos_Historial.isEmpty()) {
                        String jsonHistorial = gson.toJson(Datos_Historial);
                        Intent intent = new Intent(context, HistorialPagoActivity.class);
                        intent.putExtra("Identidad_Titular", Identidad_Titular);
                        intent.putExtra("Titular", Datos_Historial.get(0).getNombre_Titular());
                        intent.putExtra("jsonHistorial", jsonHistorial);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "No tiene Historial de Pago", Toast.LENGTH_LONG).show();
                    }
                    layout.setVisibility(View.VISIBLE);
                    tv_Mensaje.setVisibility(View.GONE);
                    pBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ArrayList<HistorialPago>> call, Throwable t) {
                    layout.setVisibility(View.VISIBLE);
                    tv_Mensaje.setVisibility(View.GONE);
                    pBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Hay problemas con la conexión..", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            realmConfig = new RealmConfig(context);
            realmConfig.getRealm().beginTransaction();
            RealmResults<Hogar_Validar> getTitularHogar;
            RealmResults<HistorialPago> historialPagoRealmResults;
            ArrayList<HistorialPago> Datos_Historial = new ArrayList<>();

            getTitularHogar = realmConfig.getRealm().where(Hogar_Validar.class).equalTo("per_identidad", Identidad_Titular).and().equalTo("per_titular", 1).findAll();

            if(getTitularHogar.size() > 0) {
                historialPagoRealmResults = realmConfig.getRealm().where(HistorialPago.class).equalTo("tit_hogar", getTitularHogar.get(0).getHog_hogar()).findAll();
                for(HistorialPago item: historialPagoRealmResults){
                    HistorialPago historialPago = new HistorialPago();
                    historialPago.setPag_anyo(item.getPag_anyo());
                    historialPago.setPag_nombre(item.getPag_nombre());
                    historialPago.setTit_hogar(item.getTit_hogar());
                    historialPago.setNombre_Titular(item.getNombre_Titular());
                    historialPago.setEstado_Pago(item.getEstado_Pago());
                    historialPago.setTit_fecha_cobro(item.getTit_fecha_cobro());
                    historialPago.setTit_proy_corta(item.getTit_proy_corta());
                    Datos_Historial.add(historialPago);
                }
                if(historialPagoRealmResults.size() > 0){
                    String jsonHistorial = gson.toJson(Datos_Historial);
                    Intent intent = new Intent(context, HistorialPagoActivity.class);
                    intent.putExtra("Identidad_Titular", Identidad_Titular);
                    intent.putExtra("Titular", Datos_Historial.get(0).getNombre_Titular());
                    intent.putExtra("jsonHistorial", jsonHistorial);
                    startActivity(intent);
                }else{
                    Toast.makeText(context, "No tiene historial de pago localmente", Toast.LENGTH_LONG).show();
                }

                layout.setVisibility(View.VISIBLE);
                tv_Mensaje.setVisibility(View.GONE);
                pBar.setVisibility(View.GONE);


            }else{
                Toast.makeText(context, "Localmente no posee información de este hogar", Toast.LENGTH_LONG).show();
                layout.setVisibility(View.VISIBLE);
                tv_Mensaje.setVisibility(View.GONE);
                pBar.setVisibility(View.GONE);
            }

            realmConfig.getRealm().commitTransaction();
            realmConfig.getRealm().close();
        }
    }

    public void Evt_Ver_Actualizacion(){
        if(BroadCastInternet.isConnected) {
            jsonAgregacion_Menores = "";
            jsonCambio_Titulares = "";

            layout.setVisibility(View.GONE);
            tv_Mensaje.setVisibility(View.VISIBLE);
            pBar.setVisibility(View.VISIBLE);
            tv_Mensaje.setTextColor(Color.GRAY);
            tv_Mensaje.setText("Cargando Actualizaciones No Aplicadas...");
            Service.getCambio_Titulares(Identidad_Titular).enqueue(new Callback<ArrayList<HogarActualizaciones>>() {
                @Override
                public void onResponse(Call<ArrayList<HogarActualizaciones>> call, Response<ArrayList<HogarActualizaciones>> response) {

                    final ArrayList<HogarActualizaciones> Datos_Cambio_Titular;
                    Datos_Cambio_Titular = response.body();

                    jsonCambio_Titulares = gson.toJson(Datos_Cambio_Titular);
                    Service.getAgregacionMenores(Identidad_Titular).enqueue(new Callback<ArrayList<HogarActualizaciones>>() {
                        @Override
                        public void onResponse(Call<ArrayList<HogarActualizaciones>> call, Response<ArrayList<HogarActualizaciones>> response) {
                            ArrayList<HogarActualizaciones> Datos_Agregacion_Menor;
                            Datos_Agregacion_Menor = response.body();
                            Intent intent = new Intent(context, ActualizacionesNoAplicadasHogarActivity.class);

                            if (!Datos_Agregacion_Menor.isEmpty() || !Datos_Cambio_Titular.isEmpty()) {
                                jsonAgregacion_Menores = gson.toJson(Datos_Agregacion_Menor);
                                intent.putExtra("jsonAgregacion_Menores", jsonAgregacion_Menores);
                                intent.putExtra("jsonCambio_Titulares", jsonCambio_Titulares);
                                startActivity(intent);


                            } else if (!Datos_Agregacion_Menor.isEmpty() && !Datos_Cambio_Titular.isEmpty()) {
                                jsonAgregacion_Menores = gson.toJson(Datos_Agregacion_Menor);
                                intent.putExtra("jsonAgregacion_Menores", jsonAgregacion_Menores);
                                intent.putExtra("jsonCambio_Titulares", jsonCambio_Titulares);
                                startActivity(intent);
                            } else if (Datos_Agregacion_Menor.isEmpty() && Datos_Cambio_Titular.isEmpty()) {
                                Toast.makeText(context, "No tiene Actualizaciones No Aplicadas", Toast.LENGTH_LONG).show();
                            }

                            tv_Mensaje.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            pBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<HogarActualizaciones>> call, Throwable t) {
                            Toast.makeText(context, "Hay problemas con la conexión...", Toast.LENGTH_LONG).show();
                            layout.setVisibility(View.VISIBLE);
                            tv_Mensaje.setVisibility(View.GONE);
                            pBar.setVisibility(View.GONE);
                        }
                    });

                }

                @Override
                public void onFailure(Call<ArrayList<HogarActualizaciones>> call, Throwable t) {
                    Toast.makeText(context, "Hay problemas con la conexión...", Toast.LENGTH_LONG).show();
                    layout.setVisibility(View.VISIBLE);
                    tv_Mensaje.setVisibility(View.GONE);
                    pBar.setVisibility(View.GONE);
                }
            });
        }else{
            Toast.makeText(context, "Es necesario tener conexión para realizar la acción", Toast.LENGTH_LONG).show();
            layout.setVisibility(View.VISIBLE);
            tv_Mensaje.setVisibility(View.GONE);
            pBar.setVisibility(View.GONE);
        }
    }


    public class RV_Nucleo_Hogar_Adapter extends RecyclerView.Adapter<RV_Nucleo_Hogar_Adapter.ViewHolder> {
        private ArrayList<NucleoHogar> Personas;
        private LayoutInflater mInflater;
        private TextView tv_Nombre_Completo;
        private TextView tv_Identidad;
        private TextView tv_Fecha_Nac;
        private TextView tv_Sexo;
        private TextView tv_Estado;
        private TextView tv_Titular;


        // data is passed into the constructor
        public RV_Nucleo_Hogar_Adapter(Context context, ArrayList <NucleoHogar> Personas) {
            this.mInflater = LayoutInflater.from(context);
            this.Personas = Personas;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_info_hogar_persona, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            tv_Nombre_Completo.setText(Personas.get(position).getNombre_completo());
            tv_Identidad.setText(Personas.get(position).getPer_identidad());
            tv_Sexo.setText(Personas.get(position).getSexoD());
            tv_Fecha_Nac.setText(Personas.get(position).getPer_fch_nacimiento());
            tv_Titular.setText(Personas.get(position).getPer_titular());
            tv_Estado.setText(Personas.get(position).getPer_estado_descripcion());
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return Personas.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            ViewHolder(View item) {
                super(item);
                tv_Nombre_Completo =  item.findViewById(R.id.tv_Nombre_Completo);
                tv_Identidad = item.findViewById(R.id.tv_Identidad);
                tv_Fecha_Nac = item.findViewById(R.id.tv_Fecha_Nac);
                tv_Sexo = item.findViewById(R.id.tv_Sexo);
                tv_Estado = item.findViewById(R.id.tv_Estado);
                tv_Titular = item.findViewById(R.id.tv_Titular);
                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View view) {

            }
        }




    }
}
