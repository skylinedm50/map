package com.map_movil.map_movil.view.descargar_validacion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Departamentos;
import com.map_movil.map_movil.model.HogaresValidar;
import com.map_movil.map_movil.model.Municipios;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.presenter.descargarvalidacion.DescargarValidacionPresenter;
import com.map_movil.map_movil.presenter.descargarvalidacion.DescargarValidacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;
import com.pitt.library.fresh.FreshDownloadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DescargarValidacionFragment extends Fragment implements UbicacionView , DescargaView {


    private UbicacionesPresenter ubicacionesPresenter;
    private DescargarValidacionPresenter descargarValidacionPresenter;

    private AppCompatSpinner DepartamentoSpiner;
    private AppCompatSpinner MunicipioSpiner;
    private AppCompatSpinner AldeaSpiner;

    private HashMap<Integer , String > SpinnerMapDepto;
    private HashMap<Integer , String > SpinnerMapMuni;
    private HashMap<Integer , String > SpinnerMapAldea;
    private FreshDownloadView DescargarBtn;
    private int Sleep = 1;
    private Realm realm;

    private Handler handler_descarga = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = (int) msg.obj;
            DescargarBtn.upDateProgress(progress);
            switch (msg.what) {
                case 10:
                    break;
                case 11:
                    DescargarBtn.showDownloadError();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_descargar_validacion , container , false);
        Realm.init(getContext());


        this.realm = Realm.getDefaultInstance();
        this.descargarValidacionPresenter = new DescargarValidacionPresenterImpl(this);
        this.ubicacionesPresenter = new UbicacionPresenterImpl(this);

        this.SpinnerMapDepto   = new HashMap<Integer , String>();
        this.SpinnerMapMuni    = new HashMap<Integer , String>();
        this.SpinnerMapAldea   = new HashMap<Integer , String>();
        this.DescargarBtn      = (FreshDownloadView) view.findViewById(R.id.descargar);

        RealmQuery<Hogar_Validar> query = this.realm.where(Hogar_Validar.class);
        RealmResults<Hogar_Validar> result = query.findAll();

        this.getDepartamentos();
        DepartamentoSpiner = (AppCompatSpinner) view.findViewById(R.id.departamento);
        MunicipioSpiner = (AppCompatSpinner) view.findViewById(R.id.municipio);
        AldeaSpiner = (AppCompatSpinner) view.findViewById(R.id.aldea);

        DepartamentoSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getMunicipios(SpinnerMapDepto.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        MunicipioSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAldeas(SpinnerMapMuni.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DescargarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
                int UsuarioCod = sharedPreferences.getInt("codigo",0);
                SolicitarDatos( SpinnerMapAldea.get( AldeaSpiner.getSelectedItemPosition() ) , UsuarioCod );
            }
        });

        return view;
    }


    @Override
    public void getDepartamentos() {
        this.ubicacionesPresenter.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(List<Departamentos> departamentos) {

        List<String> spinner =  new ArrayList<String>();
        this.SpinnerMapDepto.clear();

        for(int x = 0; x < departamentos.size(); x++){
            spinner.add(departamentos.get(x).getDesc_departamento());
            this.SpinnerMapDepto.put(x ,departamentos.get(x).getCod_departamento());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.DepartamentoSpiner.setAdapter(adapter);
    }

    @Override
    public void getMunicipios(String depto) {
        this.ubicacionesPresenter.getMunicipios(depto);
    }

    @Override
    public void cargarMunicipios(List<Municipios> municipios) {

        List<String> spinner =  new ArrayList<String>();
        this.SpinnerMapMuni.clear();

        for(int x = 0; x < municipios.size(); x++){
            spinner.add(municipios.get(x).getDesc_municipio());
            this.SpinnerMapMuni.put(x ,municipios.get(x).getCod_municipio());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.MunicipioSpiner.setAdapter(adapter);
    }

    @Override
    public void getAldeas(String muni) {
        this.ubicacionesPresenter.getAldeas(muni);
    }

    @Override
    public void cargarAldeas(List<Aldeas> aldeas) {
        List<String> spinner =  new ArrayList<String>();
        this.SpinnerMapAldea.clear();

        for(int x = 0; x < aldeas.size(); x++){
            spinner.add(aldeas.get(x).getDesc_aldea());
            this.SpinnerMapAldea.put(x ,aldeas.get(x).getCod_aldea());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.AldeaSpiner.setAdapter(adapter);
    }

    @Override
    public void getCaserios(String aldea) {

    }

    @Override
    public void cargarCaserios(List<Caserios> caserios) {

    }

    @Override
    public void SolicitarDatos(String aldea , int Usuario) {
        this.descargarValidacionPresenter.SolicitarDatos(aldea , Usuario);
    }

    @Override
    public void DescargarDatos(ArrayList<HogaresValidar> hogaresValidars) {
        this.Sleep = (int) Math.ceil( (hogaresValidars.size()/ 90) );
        this.MostarProgress(100,0);

        for(int x = 0; x < hogaresValidars.size() ; x++){
            this.realm.beginTransaction();
            Hogar_Validar hogar_validar = new Hogar_Validar(
                    hogaresValidars.get(x).getPer_persona()             ,
                    hogaresValidars.get(x).getNombre()                  ,
                    hogaresValidars.get(x).getPer_estado_descripcion()  ,
                    hogaresValidars.get(x).getHog_umbral()              ,
                    hogaresValidars.get(x).getSexo()                    ,
                    hogaresValidars.get(x).getEdad()                    ,
                    hogaresValidars.get(x).getPer_ciclo()               ,
                    hogaresValidars.get(x).getPer_titular()             ,
                    hogaresValidars.get(x).getHog_hogar()               ,
                    hogaresValidars.get(x).getCod_departamento()        ,
                    hogaresValidars.get(x).getDesc_departamento()       ,
                    hogaresValidars.get(x).getCod_municipio()           ,
                    hogaresValidars.get(x).getDesc_municipio()          ,
                    hogaresValidars.get(x).getCod_aldea()               ,
                    hogaresValidars.get(x).getDesc_aldea()              ,
                    hogaresValidars.get(x).getCod_caserio()             ,
                    hogaresValidars.get(x).getDesc_caserio()            ,
                    hogaresValidars.get(x).getHogar_direccion()         ,
                    hogaresValidars.get(x).getHog_telefono()            );
            this.realm.copyToRealm(hogar_validar);
            this.realm.commitTransaction();
        }
      //  Toast.makeText(this.getContext(),"Descargado",Toast.LENGTH_LONG).show();
    }

    private void MostarProgress(final int score , final int start){

      //  if (DescargarBtn.using()) return;
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = start; i <= score; i++) {
                    Log.d("tag","mensaje_"+String.valueOf(i));
                    try {
                        Thread.sleep(Sleep*100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = Message.obtain();
                    message.obj = i;
                    handler_descarga.sendMessage(message);
                }
             //  DescargarBtn.reset();
            }
        }).start();
    }


}
