package com.map_movil.map_movil.view.solicitudes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.hogar.ApiAdapterHogar;
import com.map_movil.map_movil.api.hogar.ApiServiceHogar;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.broadcasts.BroadCastDate;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.model.HogarByTitular;
import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.presenter.solicitud.ShowAddSolicitudAcitivityPresenter;
import com.map_movil.map_movil.presenter.solicitud.ShowAddSolicitudAcitivityPresenterImpl;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAddSolicitudActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, ShowAddSolicitudAcitivityView {
    private Intent intent;
    private ShowAddSolicitudAcitivityPresenter showAddSolicitudAcitivityPresenter;
    private SharedPreferences sharedPreferences;
    private int intCodSolicitud;
    private int intTipoOperacion;
    private ApiAdapterSolicitudes objApiAdapterSolicitudes;
    private ApiServiceSolicitudes objApiServiceSolicitudes;
    private ApiAdapterHogar adapterHogar;
    private ApiServiceHogar serviceHogar;

    private ArrayList<InfoSolicitud> infoSolicitud = new ArrayList<InfoSolicitud>();
    private ArrayList<HogarLigth> hogarLigthsList = new ArrayList<>();

    private TextInputEditText textInputEditTextObservacion;
    private Button buttonGuardar;
    private TextView textViewCodSolitud;
    private TextView textViewEstadoSolicitud;
    private TextView textViewMessageFind;
    private LinearLayout linearLayoutSolicitud;
    private LinearLayout linearLayoutObserEdit;
    private LinearLayout linearLayoutObserView;
    private LinearLayout linearLayoutNucleo;
    private LinearLayout linearLayoutMain;

    private LinearLayout linearLayoutTextoBusqueda;
    private RelativeLayout relativeLayoutProgressBar;

    private TextView textViewCodHogar;
    private TextView textViewEstadoHogar;
    private TextView textViewUmbral;
    private TextView textViewDepartamento;
    private TextView textViewMunicipio;
    private TextView textViewAldea;
    private TextView textViewCaserio;
    private TextView textViewObservacion;
    private CheckBox checkBoxActualizacionDatos;
    private CheckBox checkBoxCambioTitular;
    private CheckBox checkBoxNuevoIntegrante;
    private CheckBox checkBoxBajaIntegrante;
    private CheckBox checkBoxCambioDomicilio;
    private CheckBox checkBoxBajaPrograma;
    private CheckBox checkBoxCorrecionSancion;
    private CheckBox checkBoxReactivaPrograma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_add_solicitud);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewCompat.setElevation(findViewById(R.id.appBarVerSolicitud), 8);

        sharedPreferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        showAddSolicitudAcitivityPresenter = new ShowAddSolicitudAcitivityPresenterImpl(this, getApplicationContext());
        intent = getIntent();
        intCodSolicitud = intent.getIntExtra("intCodSolicitud", 0);
        intTipoOperacion = intent.getIntExtra("intTipoOperacion", 0);

        linearLayoutMain = findViewById(R.id.linearLayoutMain);
        linearLayoutTextoBusqueda = findViewById(R.id.linearLayoutTextoBusqueda);
        relativeLayoutProgressBar = findViewById(R.id.relativeLayoutProgressBar);
        textViewMessageFind = findViewById(R.id.textViewMessageFind);

        buttonGuardar = findViewById(R.id.buttonGuardar);
        linearLayoutNucleo = findViewById(R.id.conteinerLinearLayoutNucleo);
        linearLayoutObserEdit = findViewById(R.id.linearLayoutObserEdit);
        linearLayoutObserView = findViewById(R.id.linearLayoutObserView);
        textInputEditTextObservacion = findViewById(R.id.textEditObservacion);

        checkBoxActualizacionDatos = findViewById(R.id.checkboxActualizacionDatos);
        checkBoxCambioTitular = findViewById(R.id.checkBoxCambioTitular);
        checkBoxNuevoIntegrante = findViewById(R.id.checkBoxNuevoIntegrante);
        checkBoxBajaIntegrante = findViewById(R.id.checkBoxBajaIntegrante);
        checkBoxCambioDomicilio = findViewById(R.id.checkboxCambioDomicilio);
        checkBoxBajaPrograma = findViewById(R.id.checkboxBajaPrograma);
        checkBoxCorrecionSancion = findViewById(R.id.checkboxCorreccionSancion);
        checkBoxReactivaPrograma = findViewById(R.id.checkboxReactivaPrograma);

        objApiAdapterSolicitudes = new ApiAdapterSolicitudes();
        objApiServiceSolicitudes = objApiAdapterSolicitudes.getClientService();

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSolicitud();
            }
        });

        if(intTipoOperacion == 2) {//Buscar una solicitud
            findSolicitudSave(intCodSolicitud);
        }else{//Cuando se va a ingresar un a nueva solicitud.
            linearLayoutMain.setVisibility(View.GONE);
            linearLayoutObserEdit.setVisibility(View.VISIBLE);
            linearLayoutObserView.setVisibility(View.GONE);
        }
        BroadCastInternet.subscribeForMessageInternet(getApplicationContext(), findViewById(R.id.linearLayoutMother));
    }

    @Override
    public void onResume(){
        super.onResume();
        BroadCastInternet.subscribeForMessageInternet(getApplicationContext(), findViewById(R.id.linearLayoutMother));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(intTipoOperacion == 1){
            getMenuInflater().inflate(R.menu.menu_multiple_option, menu);
            MenuItem downloadItem = menu.findItem(R.id.download);
            MenuItem searchItem = menu.findItem(R.id.searchViewFind);
            MenuItem saveServerItem = menu.findItem(R.id.saveServer);
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(this);
            searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
            searchView.setQueryHint("Buscar...");

            downloadItem.setVisible(false);
            saveServerItem.setVisible(false);
            return  super.onCreateOptionsMenu(menu);
        }else{
            return false;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        findDataCreateSolicitud(query.toString());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }


    private void showProgressBar(Boolean show){
        if(show == true){
            relativeLayoutProgressBar.setVisibility(View.VISIBLE);
            linearLayoutTextoBusqueda.setVisibility(View.GONE);
            linearLayoutMain.setVisibility(View.GONE);
        }else{
            relativeLayoutProgressBar.setVisibility(View.GONE);
        }
    }

    private void findDataShowMessage(Boolean find){
        String strTextMessage = "Para agregar una solicitud favor buscar el hogar por la identidad del titular.";

        if(find == true){//Cunado se encontraron datos.
            linearLayoutTextoBusqueda.setVisibility(View.GONE);
            linearLayoutMain.setVisibility(View.VISIBLE);
        }else{
            strTextMessage = "No se encontraron datos";
            linearLayoutTextoBusqueda.setVisibility(View.VISIBLE);
        }
        textViewMessageFind.setText(strTextMessage);
        textViewMessageFind.setGravity(Gravity.CENTER);
    }

    @Override
    public void findDataCreateSolicitud(String strIdentidadTitular) {
        showProgressBar(true);
        showAddSolicitudAcitivityPresenter.findDataForCreateSolicitud(strIdentidadTitular);
    }

    @Override
    public void findSolicitudSave(int intCodSolicitud) {
        showProgressBar(true);
        showAddSolicitudAcitivityPresenter.findSolicitudSave(intCodSolicitud);
    }

    @Override
    public void saveSolicitud() {
        int intCodUser =  sharedPreferences.getInt("codigo", 0);
        SolicitudesDownload solicitudesDownload = new SolicitudesDownload();
        solicitudesDownload.setActualizacion_datos(checkBoxActualizacionDatos.isChecked());
        solicitudesDownload.setCambio_titular(checkBoxCambioTitular.isChecked());
        solicitudesDownload.setNuevo_integrante(checkBoxNuevoIntegrante.isChecked());
        solicitudesDownload.setBaja_integrante(checkBoxBajaIntegrante.isChecked());
        solicitudesDownload.setCambio_domicilio(checkBoxCambioDomicilio.isChecked());
        solicitudesDownload.setBaja_programa(checkBoxBajaPrograma.isChecked());
        solicitudesDownload.setCorreccion_sancion(checkBoxCorrecionSancion.isChecked());
        solicitudesDownload.setReactiva_programa(checkBoxReactivaPrograma.isChecked());
        solicitudesDownload.setObservacion((textInputEditTextObservacion.getText().toString()) == null? "SIN OBSERVACION": textInputEditTextObservacion.getText().toString());

        if(solicitudesDownload.isActualizacion_datos() && solicitudesDownload.isCambio_titular() &&
                solicitudesDownload.isNuevo_integrante() && solicitudesDownload.isBaja_integrante() &&
                solicitudesDownload.isCambio_domicilio() && solicitudesDownload.isBaja_programa() &&
                solicitudesDownload.isReactiva_programa() && solicitudesDownload.isCorreccion_sancion()) {
            Toast.makeText(getApplicationContext(), "ERROR: Favor seleccionar uno o más tipos de solicitudes.", Toast.LENGTH_SHORT).show();
        }else{
            for(HogarLigth item: hogarLigthsList){
                if(item.isBolTitular() == true){
                    showAddSolicitudAcitivityPresenter.saveSolicitud(solicitudesDownload, item, intCodUser);
                }
            }
        }
    }

    @Override
    public void showOnlyInforHogForCreateSolicitud(HogarByTitular hogarByTitular, ArrayList<HogarLigth> hogarLigthArrayList) {
        textViewCodHogar = findViewById(R.id.textViewCodigohogar);
        textViewEstadoHogar = findViewById(R.id.textViewEstadoHogar);
        textViewUmbral = findViewById(R.id.textViewUmbral);
        textViewDepartamento = findViewById(R.id.textViewDepartamento);
        textViewMunicipio = findViewById(R.id.textViewMunicipio);
        textViewAldea = findViewById(R.id.textViewAldea);
        textViewCaserio = findViewById(R.id.textViewCaserio);
        textViewCodHogar.setText(String.valueOf(hogarByTitular.getIntCodHogar()));

        textViewEstadoHogar.setText(String.valueOf(hogarByTitular.getStrEstadoHogar()));
        textViewUmbral.setText(String.valueOf(hogarByTitular.getStrUmbralHogar()));
        textViewDepartamento.setText(String.valueOf(hogarByTitular.getStrDepartamento()));
        textViewMunicipio.setText(String.valueOf(hogarByTitular.getStrMunicipio()));
        textViewAldea.setText(String.valueOf(hogarByTitular.getStrAldea()));
        textViewCaserio.setText(String.valueOf(hogarByTitular.getStrCaserio()));

        buttonGuardar.setVisibility(View.VISIBLE);
        createStructureHogar(hogarLigthArrayList);
    }

    @Override
    public void showSolicitud(InfoSolicitud infoSolicitud, ArrayList<HogarLigth> hogarLigthArrayList) {
        linearLayoutSolicitud = findViewById(R.id.linearLayoutSolicitud);
        textViewCodSolitud = findViewById(R.id.textViewCodigoSolicitud);
        textViewEstadoSolicitud = findViewById(R.id.textViewEstadoSolicitud);
        textViewCodHogar = findViewById(R.id.textViewCodigohogar);
        textViewEstadoHogar = findViewById(R.id.textViewEstadoHogar);
        textViewUmbral = findViewById(R.id.textViewUmbral);
        textViewDepartamento = findViewById(R.id.textViewDepartamento);
        textViewMunicipio = findViewById(R.id.textViewMunicipio);
        textViewAldea = findViewById(R.id.textViewAldea);
        textViewCaserio = findViewById(R.id.textViewCaserio);
        textViewObservacion = findViewById(R.id.textViewObservacion);

        checkBoxActualizacionDatos.setChecked(infoSolicitud.isBolActualizacionDatos());
        checkBoxCambioTitular.setChecked(infoSolicitud.isBolCambioTitular());
        checkBoxNuevoIntegrante.setChecked(infoSolicitud.isBolNuevoIntegrante());
        checkBoxBajaIntegrante.setChecked(infoSolicitud.isBolBajaIntegrante());
        checkBoxCambioDomicilio.setChecked(infoSolicitud.isBolCambioDomicilio());
        checkBoxBajaPrograma.setChecked(infoSolicitud.isBolBajaPrograma());
        checkBoxReactivaPrograma.setChecked(infoSolicitud.isBolReactivaPrograma());
        checkBoxCorrecionSancion.setChecked(infoSolicitud.isBolCorreccionSancion());

        checkBoxActualizacionDatos.setEnabled(false);
        checkBoxCambioTitular.setEnabled(false);
        checkBoxNuevoIntegrante.setEnabled(false);
        checkBoxBajaIntegrante.setEnabled(false);
        checkBoxCambioDomicilio.setEnabled(false);
        checkBoxBajaPrograma.setEnabled(false);
        checkBoxReactivaPrograma.setEnabled(false);
        checkBoxCorrecionSancion.setEnabled(false);

        linearLayoutSolicitud.setVisibility(View.VISIBLE);
        textViewCodSolitud.setText(String.valueOf(infoSolicitud.getIntCodSolicitud()));
        textViewEstadoSolicitud.setText(infoSolicitud.getStrEstadoSolicitud());
        textViewCodHogar.setText(String.valueOf(infoSolicitud.getIntCodHogar()));
        textViewEstadoHogar.setText(String.valueOf(infoSolicitud.getStrEstadoHogar()));
        textViewUmbral.setText(String.valueOf(infoSolicitud.getStrUmbral()));
        textViewDepartamento.setText(String.valueOf(infoSolicitud.getStrDepartamento()));
        textViewMunicipio.setText(String.valueOf(infoSolicitud.getStrMunicipio()));
        textViewAldea.setText(String.valueOf(infoSolicitud.getStrAldea()));
        textViewCaserio.setText(String.valueOf(infoSolicitud.getStrCaserio()));
        textViewObservacion.setText(String.valueOf(infoSolicitud.getStrObservacion()));
        createStructureHogar(hogarLigthArrayList);
    }

    @Override
    public void showMessage(String strMessage) {
        /*//String strTextMessage = "Para agregar una solicitud favor buscar el hogar por la identidad del titular.";;
        linearLayoutTextoBusqueda.setVisibility(View.VISIBLE);
        textViewMessageFind.setText(strMessage);
        textViewMessageFind.setGravity(Gravity.CENTER);*/
        showProgressBar(false);
        findDataShowMessage(false);
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void createStructureHogar(ArrayList<HogarLigth> hogarLigthArrayList) {
        hogarLigthsList = hogarLigthArrayList;
        Float intSizeFond = 11f;
        for(HogarLigth item: hogarLigthArrayList) {
            TextView textViewNombre = new TextView(getApplicationContext());
            TextView textViewEdad = new TextView(getApplicationContext());
            TextView textViewSexo = new TextView(getApplicationContext());
            TextView textViewIdentidad = new TextView(getApplicationContext());

            LinearLayout linearLayoutRow = new LinearLayout(getApplicationContext());
            LinearLayout linearLayoutNombre = new LinearLayout(getApplicationContext());
            LinearLayout linearLayoutEdad = new LinearLayout(getApplicationContext());
            LinearLayout linearLayoutSexo = new LinearLayout(getApplicationContext());
            LinearLayout linearLayoutIdentidad = new LinearLayout(getApplicationContext());

            textViewNombre.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textViewEdad.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textViewSexo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textViewIdentidad.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            linearLayoutRow.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            if(item.isBolTitular() == true) {
                linearLayoutRow.setBackgroundColor(Color.GREEN);
            }

            linearLayoutRow.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutNombre.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3f));
            linearLayoutSexo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));
            linearLayoutEdad.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3f));
            linearLayoutIdentidad.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

            textViewNombre.setText(item.getStrNombreBeneficiario());
            textViewSexo.setText(item.getStrSexo());
            textViewEdad.setText(String.valueOf(item.getStrEdad()));
            textViewIdentidad.setText(item.getStrIdentidad());

            textViewNombre.setTextSize(intSizeFond);
            textViewSexo.setTextSize(intSizeFond);
            textViewEdad.setTextSize(intSizeFond);
            textViewIdentidad.setTextSize(intSizeFond);

            linearLayoutNombre.addView(textViewNombre);
            linearLayoutSexo.addView(textViewSexo);
            linearLayoutEdad.addView(textViewEdad);
            linearLayoutIdentidad.addView(textViewIdentidad);

            linearLayoutRow.addView(linearLayoutNombre);
            linearLayoutRow.addView(linearLayoutSexo);
            linearLayoutRow.addView(linearLayoutEdad);
            linearLayoutRow.addView(linearLayoutIdentidad);

            linearLayoutNucleo.addView(linearLayoutRow);
        }
        showProgressBar(false);
        findDataShowMessage(true);
    }

    @Override
    public void finishCreationSolicitud() {
        finish();
    }

}
