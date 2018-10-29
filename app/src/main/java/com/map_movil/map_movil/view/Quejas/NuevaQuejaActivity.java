package com.map_movil.map_movil.view.Quejas;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Realm.QuejasDenuncias;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenter;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;
import java.util.ArrayList;
import java.util.List;

public class NuevaQuejaActivity extends AppCompatActivity implements UbicacionView , QuejasView  {

    private CoordinatorLayout          LinearRoot;
    private UbicacionesPresenter       ubicacionesPresenter;
    private QuejasPresenter            quejasPresenter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    private AppCompatSpinner           DepartamentoSpiner;
    private AppCompatSpinner           MunicipioSpiner;
    private AppCompatSpinner           AldeaSpiner;
    private AppCompatSpinner           CaserioSpiner;
    private AppCompatSpinner           TipoSolicitudSpiner;

    private CheckBox                   ChkAnonimo;

    private TextInputEditText          TxtIdentidad         , TxtNombre1   ,
                                       TxtNombre2           , TxtApellido1 ,
                                       TxtApellido2         , TxtTelefono  ,
                                       TxtDetalleSolicitud  ;

    private TextInputLayout LayoutSolicitud  ;
    private TextInputLayout LayoutIdentidad  ;
    private TextInputLayout LayoutNombre1    ;
    private TextInputLayout LayoutApellido1  ;
    private TextInputLayout LayoutDescripcion;
    private IntentFilter interFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_queja);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sharedPreferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        this.LinearRoot        = (CoordinatorLayout) findViewById(R.id.LyRoot);
        this.LayoutSolicitud   = (TextInputLayout)   findViewById(R.id.InputLayoutSolicitudes);
        this.LayoutIdentidad   = (TextInputLayout)   findViewById(R.id.InputLayoutIdentidad);
        this.LayoutNombre1     = (TextInputLayout)   findViewById(R.id.InputLayoutNombre1);
        this.LayoutApellido1   = (TextInputLayout)   findViewById(R.id.InputLayoutApellido);
        this.LayoutDescripcion = (TextInputLayout)   findViewById(R.id.InputLayoutDescripcion);

        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, getApplicationContext());
        this.quejasPresenter      = new QuejasPresenterImpl(this , null, getApplicationContext() );

        this.DepartamentoSpiner  = (AppCompatSpinner)  findViewById(R.id.departamento_spinner);
        this.MunicipioSpiner     = (AppCompatSpinner)  findViewById(R.id.municipio_spinner);
        this.AldeaSpiner         = (AppCompatSpinner)  findViewById(R.id.aldea_spinner);
        this.CaserioSpiner       = (AppCompatSpinner)  findViewById(R.id.caserios_spinner);

        this.TipoSolicitudSpiner = (AppCompatSpinner)  findViewById(R.id.TipoSolicitud);
        this.TxtIdentidad        = (TextInputEditText) findViewById(R.id.Tinput_Identidad);
        this.TxtNombre1          = (TextInputEditText) findViewById(R.id.Tinput_Nombre1);
        this.TxtNombre2          = (TextInputEditText) findViewById(R.id.Tinput_Nombre2);
        this.TxtApellido1        = (TextInputEditText) findViewById(R.id.Tinput_Apellido1);
        this.TxtApellido2        = (TextInputEditText) findViewById(R.id.Tinput_Apellido2);
        this.TxtTelefono         = (TextInputEditText) findViewById(R.id.Tinput_Telefono);
        this.TxtDetalleSolicitud = (TextInputEditText) findViewById(R.id.Tinput_solicitud);
        this.ChkAnonimo          = (CheckBox)          findViewById(R.id.chk_anonimo);

        this.ChkAnonimo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean anonimo) {
                if(anonimo){
                    TxtIdentidad.setEnabled(false);
                    TxtNombre1.setEnabled(false);
                    TxtNombre2.setEnabled(false);
                    TxtApellido1.setEnabled(false);
                    TxtApellido2.setEnabled(false);
                    TxtTelefono.setEnabled(false);
                }else{
                    TxtIdentidad.setEnabled(true);
                    TxtNombre1.setEnabled(true);
                    TxtNombre2.setEnabled(true);
                    TxtApellido1.setEnabled(true);
                    TxtApellido2.setEnabled(true);
                    TxtTelefono.setEnabled(true);
                }
            }
        });
        this.DepartamentoSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getMunicipios(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        this.MunicipioSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAldeas(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        this.AldeaSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCaserios(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> TipoSolicitud = new ArrayList<>();
        TipoSolicitud.add(0,"- Seleccione un tipo de solicitud -");
        TipoSolicitud.add(1,"Quejas");
        TipoSolicitud.add(2,"Denuncias");
        TipoSolicitud.add(3,"Solicitud");

        ArrayAdapter<String> Adapter = new ArrayAdapter<>(
                this.getApplicationContext() , android.R.layout.simple_dropdown_item_1line, TipoSolicitud
        );
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.TipoSolicitudSpiner.setAdapter(Adapter);


        if( getIntent().getIntExtra("accion",0) == 1){

            String [] NombreSolicitante = getIntent().getStringExtra("Nombre").split(" ");
            TextView TxtMsjAnonimo = (TextView)findViewById(R.id.TxtMsjAnonimo);
            TextView txtRequerido = (TextView)findViewById(R.id.txtRequerido);

            this.ChkAnonimo.setEnabled(false);
            this.TxtIdentidad.setEnabled(false);
            this.TipoSolicitudSpiner.setEnabled(false);
            this.TxtNombre1.setEnabled(false);
            this.TxtNombre2.setEnabled(false);
            this.TxtApellido1.setEnabled(false);
            this.TxtApellido2.setEnabled(false);
            this.TxtTelefono.setEnabled(false);
            this.TxtDetalleSolicitud.setEnabled(false);
            txtRequerido.setVisibility(View.GONE);
            TxtMsjAnonimo.setVisibility(View.GONE);

            this.ChkAnonimo.setChecked((getIntent().getIntExtra("Anonimo",0) == 1)?true:false);
            this.TipoSolicitudSpiner.setSelection(getIntent().getIntExtra("Gestion",0));
            this.TxtIdentidad.setText(getIntent().getStringExtra("Identidad"));
            this.TxtIdentidad.setFocusable(false);
            this.TxtNombre1.setText( ( getIntent().getIntExtra("Anonimo",0) == 1 || NombreSolicitante.length < 1)?"":NombreSolicitante[0]);
            this.TxtNombre1.setFocusable(false);
            this.TxtNombre2.setText( ( getIntent().getIntExtra("Anonimo",0) == 1 || NombreSolicitante.length < 2)?"":NombreSolicitante[1]);
            this.TxtNombre2.setFocusable(false);
            this.TxtApellido1.setText( ( getIntent().getIntExtra("Anonimo",0) == 1 || NombreSolicitante.length < 3)?"":NombreSolicitante[2]);
            this.TxtApellido1.setFocusable(false);
            this.TxtApellido2.setText( (getIntent().getIntExtra("Anonimo",0) == 1 || NombreSolicitante.length < 4)?"":NombreSolicitante[3]);
            this.TxtApellido2.setFocusable(false);
            this.TxtTelefono.setText(getIntent().getStringExtra("Telefono"));
            this.TxtTelefono.setFocusable(false);
            this.TxtDetalleSolicitud.setText(getIntent().getStringExtra("Detalle"));
            this.TxtDetalleSolicitud.setFocusable(false);

        }


        getSupportActionBar().setTitle(
                (getIntent().getIntExtra("accion",0)==0)?"Registrar Quejas y Denuncias":"Quejas y Denuncias"
        );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BroadCastInternet.subscribeForMessageInternet(getApplicationContext(), findViewById(R.id.LyRoot));
        getDepartamentos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        String menuSD ="saveData";
        getMenuInflater().inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchView = menu.findItem(R.id.searchViewFind);
        MenuItem continuar = menu.findItem(R.id.saveData);
       // MenuItem continuar = menu.findItem( getResources().getIdentifier(menuSD ,"id",getPackageName()) );
        searchView.setEnabled(false);
        searchView.setVisible(false);

        if(getIntent().getIntExtra("accion",0) == 0){
            continuar.setEnabled(true);
            continuar.setVisible(true);
            continuar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(VerificarVacios()){
                        RegistrarQueja();
                    }
                    return false;
                }
            });
        }

        return  super.onCreateOptionsMenu(menu);
    }


    @Override
    public void getDepartamentos() {
        this.ubicacionesPresenter.getDepartamentos();
    }


    @Override
    public void cargarDepartamentos(ArrayList<String> departamentos) {

        int DepartamentoIndex = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line, departamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(getIntent().getIntExtra("accion",0) == 1 ){

            for(int x = 0; x < adapter.getCount(); x++){
                if(DepartamentoIndex == 0){
                    DepartamentoIndex = ( getIntent().getStringExtra("Caserio")
                            .substring(0,2).equals( adapter.getItem(x).substring(0,2) ) )?x:0;
                }else{
                    break;
                }
            }
        }

        this.DepartamentoSpiner.setAdapter(adapter);
        if(getIntent().getIntExtra("accion",0) == 1) {
            this.DepartamentoSpiner.setEnabled(false);
            this.DepartamentoSpiner.setSelection(DepartamentoIndex);
        }
    }


    public boolean VerificarVacios(){

        if(
                this.TipoSolicitudSpiner.getSelectedItemId() != 0 &&
                        ( ( !this.TxtIdentidad.getText().toString().equals("")   &&
                                !this.TxtNombre1.getText().toString().equals("")     &&
                                !this.TxtApellido1.getText().toString().equals("")   &&
                                !this.ChkAnonimo.isChecked()
                        ) || this.ChkAnonimo.isChecked()
                        ) &&
                        !this.TxtDetalleSolicitud.getText().toString().equals("") ){

            return  true;
        }

        if( this.TipoSolicitudSpiner.getSelectedItemId() == 0 ){
            this.LayoutSolicitud.setError("* Tiene que seleccionar un tipo de solicitud");
        }

        if(TxtIdentidad.getText().toString().equals("") && !this.ChkAnonimo.isChecked()){
            this.LayoutIdentidad.setError("* Tiene que ingresar una identidad");
        }

        if(TxtNombre1.getText().toString().equals("") && !this.ChkAnonimo.isChecked()){
            this.TxtNombre1.setError("* Tiene que ingresar el primer nombre");
        }

        if(TxtApellido1.getText().toString().equals("") && !this.ChkAnonimo.isChecked()){
            this.TxtApellido1.setError("* Tiene que ingresar el primer apellido");
        }

        if(TxtDetalleSolicitud.getText().toString().equals("") ){
            this.TxtDetalleSolicitud.setError("* Tiene que ingresar el detalle de la solicitud");
        }

        return  false;
    }

    @Override
    public void getMunicipios(String depto) {
        this.ubicacionesPresenter.getMunicipios(depto);
    }

    @Override
    public void cargarMunicipios(ArrayList<String> municipios) {
        int MunicipioIndex=0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line, municipios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if( getIntent().getIntExtra("accion",0) == 1 ){
            for(int x = 0; x < adapter.getCount(); x++){
                if(MunicipioIndex == 0){
                    MunicipioIndex = ( getIntent().getStringExtra("Caserio")
                            .substring(0,4).equals( adapter.getItem(x).substring(0,4) ))?x:0;
                }else{
                    break;
                }
            }
        }

        this.MunicipioSpiner.setAdapter(adapter);
        if(getIntent().getIntExtra("accion",0) == 1) {
            this.MunicipioSpiner.setEnabled(false);
            this.MunicipioSpiner.setSelection(MunicipioIndex);
        }
    }

    @Override
    public void getAldeas(String muni) {
        this.ubicacionesPresenter.getAldeas(muni);
    }

    @Override
    public void cargarAldeas(List<Aldeas> aldeas) {

        List<String> spinner =  new ArrayList<String>();
        int AldeaIndex = 0;

        for(int x = 0; x < aldeas.size(); x++){
            spinner.add(aldeas.get(x).getCod_aldea()+"-"+aldeas.get(x).getDesc_aldea());

            if(getIntent().getIntExtra("accion",0) == 1 && AldeaIndex==0) {
                AldeaIndex = (getIntent().getStringExtra("Caserio").substring(0,6).equals(aldeas.get(x).getCod_aldea()))?x:0;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.AldeaSpiner.setAdapter(adapter);

        if(getIntent().getIntExtra("accion",0) == 1) {
            this.AldeaSpiner.setEnabled(false);
            this.AldeaSpiner.setSelection(AldeaIndex);
        }
    }

    @Override
    public void getCaserios(String aldea) {
        this.ubicacionesPresenter.getCaserios(aldea);
    }

    @Override
    public void cargarCaserios(List<Caserios> caserios) {
        List<String> spinner =  new ArrayList<String>();
        int CaserioIndex = 0;
        for(int x = 0; x < caserios.size(); x++){
            spinner.add(caserios.get(x).getCod_caserio()+"-"+caserios.get(x).getDesc_caserio());
            if(getIntent().getIntExtra("accion",0) == 1 && CaserioIndex==0) {
                CaserioIndex = (getIntent().getStringExtra("Caserio").equals(caserios.get(x).getCod_caserio()))?x:0;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.CaserioSpiner.setAdapter(adapter);

        if(getIntent().getIntExtra("accion",0) == 1) {
            this.CaserioSpiner.setEnabled(false);
            this.CaserioSpiner.setSelection(CaserioIndex);
        }
    }

    @Override
    public void MostarQuejas(ArrayList<QuejasDenuncias> respuesta, int RealizadosLenght, int noRealizadosLenght) { }

    @Override
    public void SolicitarQuejas() {
        onBackPressed();
    }

    @Override
    public void RegistrarQueja() {

        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("USER", Context.MODE_PRIVATE);

        this.quejasPresenter.RegistrarQueja(sharedPreferences.getInt("codigo",0)      ,
                this.TxtDetalleSolicitud.getText().toString()                ,
                (int) this.TipoSolicitudSpiner.getSelectedItemId()           ,
                this.CaserioSpiner.getSelectedItem().toString().split("-")[0] ,
                this.TxtIdentidad.getText().toString() ,
                this.TxtNombre1.getText().toString()   ,
                this.TxtNombre2.getText().toString()   ,
                this.TxtApellido1.getText().toString() ,
                this.TxtApellido2.getText().toString() ,
                this.TxtTelefono.getText().toString()  ,
                (this.ChkAnonimo.isChecked())?1:0
        );
    }

    @Override
    public void ActualizarDatos(int offline) {
        if(offline == 1){
            sharedPreferencesEditor.putInt("Sincronizar",1);
            sharedPreferencesEditor.commit();
        }
        finish();
    }

}
