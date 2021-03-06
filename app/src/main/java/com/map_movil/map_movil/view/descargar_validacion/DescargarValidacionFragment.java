package com.map_movil.map_movil.view.descargar_validacion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.Hogar_Validar;
import com.map_movil.map_movil.presenter.descargarvalidacion.DescargarValidacionPresenter;
import com.map_movil.map_movil.presenter.descargarvalidacion.DescargarValidacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;
import com.pitt.library.fresh.FreshDownloadView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
    private int Score = 25;
    private int UsuarioCod;
    private Thread thread_descarga;
    private Thread thread_contador;
    private RealmConfig realmConfig;
    private Handler handler_descarga = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = (int) msg.obj;
            DescargarBtn.upDateProgress(progress);
        }
    };

    private Handler Descargar_Datos = new Handler(){
        @Override
        public void handleMessage(Message msg){
            int descargar = (int) msg.obj;
            switch (descargar){
                case 1: SolicitarDatos(SpinnerMapAldea.get( AldeaSpiner.getSelectedItemPosition() ) , UsuarioCod);
                        break;
                case 2: SolicitarHistorialPago(SpinnerMapAldea.get( AldeaSpiner.getSelectedItemPosition() ));
                        break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_descargar_validacion , container , false);
        this.realmConfig = new RealmConfig(getContext());

        this.descargarValidacionPresenter = new DescargarValidacionPresenterImpl(this);
        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, view.getContext());

        this.SpinnerMapDepto   = new HashMap<Integer , String>();
        this.SpinnerMapMuni    = new HashMap<Integer , String>();
        this.SpinnerMapAldea   = new HashMap<Integer , String>();
        this.DescargarBtn      = (FreshDownloadView) view.findViewById(R.id.descargar);

        DepartamentoSpiner = (AppCompatSpinner) view.findViewById(R.id.departamento);
        MunicipioSpiner = (AppCompatSpinner) view.findViewById(R.id.municipio);
        AldeaSpiner = (AppCompatSpinner) view.findViewById(R.id.aldea);

        DepartamentoSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getMunicipios(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        MunicipioSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAldeas(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DescargarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
                UsuarioCod = sharedPreferences.getInt("codigo",0);
                Score = 100;
                MostarProgress(0);
                Descargar(1);
                Descargar(2);
            }
        });
        setHasOptionsMenu(true);
        getTargetFragment();
        this.getDepartamentos();

        return view;
    }

    @Override
    public void getDepartamentos() {
        this.ubicacionesPresenter.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(ArrayList<String> departamentos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, departamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.DepartamentoSpiner.setAdapter(adapter);
    }

    @Override
    public void getMunicipios(String depto) {
        this.ubicacionesPresenter.getMunicipios(depto);
    }

    @Override
    public void cargarMunicipios(ArrayList<String> municipios) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, municipios);
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
    public void getCaserios(String aldea) { }

    @Override
    public void cargarCaserios(List<Caserios> caserios) { }

    @Override
    public void SolicitarDatos(String aldea , int Usuario) {
        this.descargarValidacionPresenter.SolicitarDatos(aldea , Usuario);
    }

    @Override
    public void DescargarDatos(ArrayList<Hogar_Validar> hogaresValidars) {
        this.Sleep = (int) Math.ceil( (hogaresValidars.size()/ 5) );
        this.realmConfig.getRealm().beginTransaction();
        this.realmConfig.getRealm().insert(hogaresValidars);
        this.realmConfig.getRealm().commitTransaction();
        this.realmConfig.getRealm().close();
    }

    @Override
    public void SolicitarHistorialPago(String aldea) {
        this.descargarValidacionPresenter.SolicitarHistorialPago(aldea);
    }

    @Override
    public void DescargarHistorial(ArrayList<HistorialPago> historialPagos) {
        this.realmConfig.getRealm().beginTransaction();
        this.realmConfig.getRealm().insert(historialPagos);
        this.realmConfig.getRealm().commitTransaction();
        this.realmConfig.getRealm().close();
    }

    private void MostarProgress( final int start){

        this.thread_contador = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = start; i <= Score; i++) {
                    try {
                        Thread.sleep(Sleep*15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = Message.obtain();
                    message.obj = i;
                    handler_descarga.sendMessage(message);
                }
            }
        });

        this.thread_contador.start();
    }

    private void Descargar(final int proceso){
            this.thread_descarga = new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = Message.obtain();
                    message.obj = proceso;
                    Descargar_Datos.sendMessage(message);
                }
            });
            this.thread_descarga.start();
    }


}
