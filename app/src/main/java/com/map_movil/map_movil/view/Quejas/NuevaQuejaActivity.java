package com.map_movil.map_movil.view.Quejas;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.QuejasDenuncias;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenter;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NuevaQuejaActivity extends AppCompatActivity implements UbicacionView , QuejasView {

    private CoordinatorLayout          LinearRoot;
    private UbicacionesPresenter       ubicacionesPresenter;
    private QuejasPresenter            quejasPresenter;

    private AppCompatSpinner           DepartamentoSpiner;
    private AppCompatSpinner           MunicipioSpiner;
    private AppCompatSpinner           TipoSolicitudSpiner;
    private CheckBox                   ChkAnonimo;
    private HashMap<Integer , String > SpinnerMapDepto;
    private HashMap<Integer , String > SpinnerMapMuni;

    private TextInputEditText          TxtIdentidad         , TxtNombre1   ,
                                       TxtNombre2           , TxtApellido1 ,
                                       TxtApellido2         , TxtTelefono  ,
                                       TxtDetalleSolicitud  ;

    private TextInputLayout LayoutSolicitud  ;
    private TextInputLayout LayoutIdentidad  ;
    private TextInputLayout LayoutNombre1    ;
    private TextInputLayout LayoutApellido1  ;
    private TextInputLayout LayoutDescripcion;

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

        this.LinearRoot        = (CoordinatorLayout) findViewById(R.id.LyRoot);
        this.LayoutSolicitud   = (TextInputLayout)   findViewById(R.id.InputLayoutSolicitudes);
        this.LayoutIdentidad   = (TextInputLayout)   findViewById(R.id.InputLayoutIdentidad);
        this.LayoutNombre1     = (TextInputLayout)   findViewById(R.id.InputLayoutNombre1);
        this.LayoutApellido1   = (TextInputLayout)   findViewById(R.id.InputLayoutApellido);
        this.LayoutDescripcion = (TextInputLayout)   findViewById(R.id.InputLayoutDescripcion);

        this.SpinnerMapDepto   = new HashMap<Integer , String>();
        this.SpinnerMapMuni    = new HashMap<Integer , String>();
        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, getApplicationContext());
        this.quejasPresenter      = new QuejasPresenterImpl(this );

        this.DepartamentoSpiner  = (AppCompatSpinner)  findViewById(R.id.departamento_spinner);
        this.MunicipioSpiner     = (AppCompatSpinner)  findViewById(R.id.municipio_spinner);

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

       getSupportActionBar().setTitle("Registrar Quejas y Denuncias");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       //BroadCastInternet.subscribeForMessageInternet(this.getApplicationContext(), LinearRoot);
       getDepartamentos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchView = menu.findItem(R.id.searchViewFind);
        MenuItem continuar = menu.findItem(R.id.next);
        searchView.setEnabled(false);
        searchView.setVisible(false);
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
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public void getDepartamentos() {
        this.ubicacionesPresenter.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(ArrayList<String> departamentos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line, departamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.DepartamentoSpiner.setAdapter(adapter);

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line, municipios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.MunicipioSpiner.setAdapter(adapter);
    }

    @Override
    public void getAldeas(String muni) {

    }

    @Override
    public void cargarAldeas(List<Aldeas> aldeas) {

    }

    @Override
    public void getCaserios(String aldea) {

    }

    @Override
    public void cargarCaserios(List<Caserios> caserios) {

    }

    @Override
    public void MostarQuejas(ArrayList<QuejasDenuncias> respuesta, int RealizadosLenght, int noRealizadosLenght) {

    }

    @Override
    public void SolicitarQuejas() {
        onBackPressed();
    }

    @Override
    public void RegistrarQueja() {

        SharedPreferences sharedPreferences = getApplicationContext()
                                                  .getSharedPreferences("USER", Context.MODE_PRIVATE);

          this.quejasPresenter.RegistrarQueja(sharedPreferences.getInt("codigo",0)      ,
                     this.TxtDetalleSolicitud.getText().toString()      ,
                     (int) this.TipoSolicitudSpiner.getSelectedItemId() ,
                     this.SpinnerMapMuni.get(this.MunicipioSpiner.getSelectedItemPosition()) ,
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
    public void ActualizarDatos() {
        finish();
    }

}
