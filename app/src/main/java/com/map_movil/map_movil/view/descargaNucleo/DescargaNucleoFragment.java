package com.map_movil.map_movil.view.descargaNucleo;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.MuniDescAdapter;
import com.map_movil.map_movil.api.ApiConfig;
import com.map_movil.map_movil.api.descargaNucleo.ApiServiceDescargaNucleo;
import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.HogaresPersonas;
import com.map_movil.map_movil.model.Municipios;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DescargaNucleoFragment extends Fragment {
    private Spinner spinnerDepto;
    private Spinner spinnerMuni;
    private TextView textViewLastDepto;
    private TextView textViewHI;
    private TextView textViewHF;
    private TextView textViewCR;
    private ProgressBar progressBar;
    private String[] deptos = new String[]
            {"01-ATLANTIDA",
                    "02-COLON",
                    "03-COMAYAGUA",
                    "04-COPAN",
                    "05-CORTES",
                    "06-CHOLUTECA",
                    "07-EL PARAISO",
                    "08-FRANCISCO MORAZAN",
                    "09-GRACIAS A DIOS",
                    "10-INTIBUCA",
                    "11-ISLAS DE LA BAHIA",
                    "12-LA PAZ",
                    "13-LEMPIRA",
                    "14-OCOTEPEQUE",
                    "15-OLANCHO",
                    "16-SANTA  BARBARA",
                    "17-VALLE",
                    "18-YORO"};
    private ArrayAdapter<String> adapter;

    private Button buttonDescargar;
    private Realm realm;
    private ApiServiceDescargaNucleo service;
    private ListView listViewMunisDescargado;
    private MuniDescAdapter munisDescAdapter;

    private DescargaNucleoFragment.CheckInternetAsyncTask checkInternetAsyncTask;
    private  View view;

    String itemSeleccionado;
    String codigoItemSeleccionado;


    public DescargaNucleoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_descarga_nucleo, container, false);
        spinnerDepto = (Spinner) view.findViewById(R.id.spinnerDeptos);
        spinnerMuni = (Spinner) view.findViewById(R.id.spinnerMuni);
        textViewHI = (TextView) view.findViewById(R.id.textViewHI);
        textViewHF = (TextView) view.findViewById(R.id.textViewHF);
        textViewCR = (TextView) view.findViewById(R.id.textViewCR);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        checkInternetAsyncTask = (CheckInternetAsyncTask) new CheckInternetAsyncTask(getContext());

        if (checkInternetAsyncTask.isConectado())
        {
            Toast.makeText(getContext(),"Conectado",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getContext(),"No Conectado",Toast.LENGTH_LONG).show();
        }



        ///***INICIO DE LA CONFIGURACION DE LA BASE DE DATOS DB_REALM*/////
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();

        //SET DEL SERVICIO//
        service = new ServiceProvider().getService();

        ///****QUERY PARA TRAER LOS MUNICIPIOS YA DESCARGADOS EN LA BASE DE DATOS LOCAL*///////
        RealmQuery<HogaresPersonas> query = realm.where(HogaresPersonas.class).distinct("desc_municipio").sort("cod_municipio");
        RealmResults<HogaresPersonas> result1 = query.findAll();



        listViewMunisDescargado = (ListView) view.findViewById(R.id.ListViewMunisDescargados);
        munisDescAdapter = new MuniDescAdapter(getContext(),result1,R.layout.item_municipio_desc);
        listViewMunisDescargado.setAdapter(munisDescAdapter);



        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,deptos);
        spinnerDepto.setAdapter(adapter);

        // textViewLastDepto = (TextView) findViewById(R.id.textViewLineasDesc);


        if (checkInternetAsyncTask.isConectado())
        {
            spinnerDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    itemSeleccionado=spinnerDepto.getSelectedItem().toString();
                    codigoItemSeleccionado=itemSeleccionado.substring(0,2);
                    service.getMunicipios(codigoItemSeleccionado.toString()).enqueue(new Callback<List<Municipios>>() {
                        @Override
                        public void onResponse(Call<List<Municipios>> call, Response<List<Municipios>> response) {
                            int i=0;
                            int cantidadRegistros=response.body().size();
                            if (response.isSuccessful())
                            {
                                String[] arrayMunicipios;
                                arrayMunicipios=new String[cantidadRegistros];
                                while (i<cantidadRegistros)
                                {
                                    arrayMunicipios[i]=response.body().get(i).getCod_municipio()+"-"+response.body().get(i).getDesc_municipio();
                                    i++;
                                }
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,arrayMunicipios);
                                spinnerMuni.setAdapter(adapter);
                            }


                        }

                        @Override
                        public void onFailure(Call<List<Municipios>> call, Throwable t) {
                            Toast.makeText(getContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else
        {
            Toast.makeText(getContext(),"Se requiere conexión a internet para descargar información.",Toast.LENGTH_LONG).show();
        }


        buttonDescargar = (Button) view.findViewById(R.id.buttonDescargar);

        buttonDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkInternetAsyncTask = (CheckInternetAsyncTask) new CheckInternetAsyncTask(getContext());

                if (checkInternetAsyncTask.isConectado())
                {
                    itemSeleccionado=spinnerMuni.getSelectedItem().toString();
                    if(itemSeleccionado!="" && itemSeleccionado!=null)
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        buttonDescargar.setEnabled(false);
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(Calendar.getInstance().getTime());
                        textViewHI.setText(timeStamp);


                        codigoItemSeleccionado=itemSeleccionado.substring(0,4);



                        //Toast.makeText(getApplicationContext(),codigoItemSeleccionado,Toast.LENGTH_SHORT).show();

                        service.getDatos(codigoItemSeleccionado.toString()).enqueue(new Callback<List<HogaresPersonas>>() {
                            @Override
                            public void onResponse(Call<List<HogaresPersonas>> call, Response<List<HogaresPersonas>> response) {
                                if (response.isSuccessful())
                                {
                                    //QUERY PARA BORRAR TODOS LOS REGISTROS INGRESADOS ANTERIORMENTE EN LA BD LOCAL QUE SON DEL MUNICIPIO ACTUALMENTE SELECCIONADOS//
                                    RealmQuery<HogaresPersonas> query = realm.where(HogaresPersonas.class).equalTo("cod_municipio",codigoItemSeleccionado.toString());
                                    RealmResults<HogaresPersonas> result1 = query.findAll();
                                    realm.beginTransaction();
                                    result1.deleteAllFromRealm();
                                    realm.commitTransaction();


                                    int per_persona;//
                                    String per_identidad;//
                                    String nombre;//
                                    String per_estado_descripcion;//
                                    String sexo;//
                                    String edad;//
                                    int per_ciclo;//
                                    int per_titular;//
                                    int hog_hogar;//
                                    String hog_umbral;//
                                    String cod_departamento;//
                                    String desc_departamento;//
                                    String cod_municipio;//
                                    String desc_municipio;//
                                    String cod_aldea;//
                                    String desc_aldea;//
                                    String cod_caserio;//
                                    String desc_caserio;//
                                    String hogar_direccion;//
                                    String hog_telefono;//
                                    String hog_estado_descripcion;//

                                    int data=0;
                                    int i=0;
                                    String Per_Personas="";
                                    data = response.body().size();
                                    while (i<data)
                                    {
                                        cod_departamento=response.body().get(i).getCod_departamento().toString();
                                        desc_departamento=response.body().get(i).getDesc_departamento().toString();
                                        cod_municipio=response.body().get(i).getCod_municipio().toString();
                                        desc_municipio=response.body().get(i).getDesc_municipio().toString();
                                        cod_aldea=response.body().get(i).getCod_aldea().toString();
                                        desc_aldea=response.body().get(i).getDesc_aldea().toString();
                                        cod_caserio=response.body().get(i).getCod_caserio().toString();
                                        desc_caserio=response.body().get(i).getDesc_caserio().toString();
                                        hog_hogar=response.body().get(i).getHog_hogar();//
                                        hog_umbral=response.body().get(i).getHog_umbral().toString();
                                        hog_estado_descripcion=response.body().get(i).getHog_estado_descripcion();
                                        per_persona=response.body().get(i).getPer_persona();//
                                        per_identidad=response.body().get(i).getPer_identidad().toString();
                                        nombre=response.body().get(i).getNombre();
                                        edad=response.body().get(i).getEdad();
                                        sexo=response.body().get(i).getSexo().toString();
                                        per_estado_descripcion=response.body().get(i).getPer_estado_descripcion();
                                        per_titular=response.body().get(i).getPer_titular();
                                        per_ciclo=response.body().get(i).getPer_ciclo();
                                        hog_telefono=response.body().get(i).getHog_telefono();
                                        hogar_direccion=response.body().get(i).getHogar_direccion();




                                        createHogarPersona(
                                                per_persona
                                                ,  nombre
                                                ,  per_estado_descripcion
                                                ,  hog_umbral
                                                ,  sexo
                                                ,  edad
                                                ,  per_ciclo
                                                ,  per_titular
                                                ,  hog_hogar
                                                ,  cod_departamento
                                                ,  desc_departamento
                                                ,  cod_municipio
                                                ,  desc_municipio
                                                ,  cod_aldea
                                                ,  desc_aldea
                                                ,  cod_caserio
                                                ,  desc_caserio
                                                ,  hogar_direccion
                                                ,  hog_telefono
                                                ,  per_identidad
                                                ,  hog_estado_descripcion);
                                        i++;


                                    }

                                    //******************************HISTORIAL DE PAGO ALMACENAMIENTO*********************************


                                    service.getHistorialPago(codigoItemSeleccionado.toString()).enqueue(new Callback<List<HistorialPago>>() {

                                        @Override
                                        public void onResponse(Call<List<HistorialPago>> call, Response<List<HistorialPago>> response) {

                                            if (response.isSuccessful())
                                            {

                                                int pag_anyo;
                                                String pag_nombre;
                                                int tit_hogar;
                                                String nombre_Titular;
                                                String estado_Pago;
                                                String tit_fecha_cobro;
                                                String tit_proy_corta;

                                                int data=0;
                                                int i=0;
                                                String Per_Personas="";
                                                data = response.body().size();
                                                while (i<data)
                                                {
                                                    pag_anyo=response.body().get(i).getPag_anyo();
                                                    pag_nombre=response.body().get(i).getPag_nombre().toString();
                                                    tit_hogar=response.body().get(i).getTit_hogar();
                                                    nombre_Titular=response.body().get(i).getNombre_Titular().toString();
                                                    estado_Pago=response.body().get(i).getEstado_Pago().toString();
                                                    tit_fecha_cobro=response.body().get(i).getTit_fecha_cobro().toString();
                                                    tit_proy_corta=response.body().get(i).getTit_proy_corta().toString();

                                                    createHistorialPago( pag_anyo,  pag_nombre,  tit_hogar,  nombre_Titular,  estado_Pago,  tit_fecha_cobro,  tit_proy_corta);

                                                    i++;

                                                }

                                            }
                                            else
                                            {
                                                Toast.makeText(getContext(),response.message()+" -Error Historial de pago-",Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }

                                            //***************************************************************************************************************
                                            String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(Calendar.getInstance().getTime());
                                            textViewHF.setText(timeStamp2);



                                            RealmQuery<HogaresPersonas> query = realm.where(HogaresPersonas.class).distinct("desc_municipio").sort("cod_municipio");
                                            RealmResults<HogaresPersonas> result1 = query.findAll();


                                            listViewMunisDescargado = (ListView) view.findViewById(R.id.ListViewMunisDescargados);
                                            munisDescAdapter = new MuniDescAdapter(getContext(),result1,R.layout.item_municipio_desc);
                                            listViewMunisDescargado.setAdapter(munisDescAdapter);
                                            progressBar.setVisibility(View.INVISIBLE);
                                            buttonDescargar.setEnabled(true);


                                        }

                                        @Override
                                        public void onFailure(Call<List<HistorialPago>> call, Throwable t) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            buttonDescargar.setEnabled(true);
                                        }

                                    });

                                }
                                else
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    buttonDescargar.setEnabled(true);
                                    Toast.makeText(getContext(),response.message()+" -Error Información de hogares-",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<HogaresPersonas>> call, Throwable t) {
                                progressBar.setVisibility(View.INVISIBLE);
                                buttonDescargar.setEnabled(true);
                                Toast.makeText(getContext(),t.getMessage()+" -Error-1-",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Es necesario seleccionar un municipio",Toast.LENGTH_SHORT).show();
                        buttonDescargar.setEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Se requiere conexión a internet para descargar información.",Toast.LENGTH_LONG).show();
                    buttonDescargar.setEnabled(true);
                }
            }


        });


        return  view;
    }



    // CRUD //
    private void createHogarPersona(int per_persona
            , String nombre
            , String per_estado_descripcion
            , String hog_umbral
            , String sexo
            , String edad
            , int per_ciclo
            , int per_titular
            , int hog_hogar
            , String cod_departamento
            , String desc_departamento
            , String cod_municipio
            , String desc_municipio
            , String cod_aldea
            , String desc_aldea
            , String cod_caserio
            , String desc_caserio
            , String hogar_direccion
            , String hog_telefono
            , String per_identidad
            , String hog_estado_descripcion) {

        realm.beginTransaction();
        //realm.deleteAll();
        HogaresPersonas hogar = new HogaresPersonas(per_persona
                ,  nombre
                ,  per_estado_descripcion
                ,  hog_umbral
                ,  sexo
                ,  edad
                ,  per_ciclo
                ,  per_titular
                ,  hog_hogar
                ,  cod_departamento
                ,  desc_departamento
                ,  cod_municipio
                ,  desc_municipio
                ,  cod_aldea
                ,  desc_aldea
                ,  cod_caserio
                ,  desc_caserio
                ,  hogar_direccion
                ,  hog_telefono
                ,  per_identidad
                ,  hog_estado_descripcion);
        realm.copyToRealm(hogar);
        realm.commitTransaction();

    }



    private void createHistorialPago(int pag_anyo
            ,String pag_nombre
            ,int tit_hogar
            ,String nombre_Titular
            ,String estado_Pago
            ,String tit_fecha_cobro
            ,String tit_proy_corta)
    {
        realm.beginTransaction();
        //realm.deleteAll();
        HistorialPago historial_pago = new HistorialPago(pag_anyo,pag_nombre,tit_hogar,nombre_Titular,estado_Pago,tit_fecha_cobro,tit_proy_corta);
        realm.copyToRealm(historial_pago);
        realm.commitTransaction();

    }

    /*******************************************************************************************************************
     * CLASES INNER
     */
    public class CheckInternetAsyncTask {

        private Context context;
        private boolean conectado;

        public CheckInternetAsyncTask(Context context) {
            this.context = context;
        }

        public CheckInternetAsyncTask() {

        }


        public boolean isConectado() {
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            assert cm != null;
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();


            if (isConnected) {
                conectado=true;

            } else {

                conectado=false;
            }

            return conectado;
        }
    }

    public class ServiceProvider {
        private  ApiServiceDescargaNucleo service;

        public ApiServiceDescargaNucleo getService()
        {
            if (service==null)
            {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.MINUTES)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.MINUTES)
                        .build();
                service = new Retrofit.Builder()
                        .baseUrl(ApiConfig.strURL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(ApiServiceDescargaNucleo.class);
            }
            return service;
        }
    }
}
