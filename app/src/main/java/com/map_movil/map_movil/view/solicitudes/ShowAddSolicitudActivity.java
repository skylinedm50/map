package com.map_movil.map_movil.view.solicitudes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.AdapterItemMiembroNucleoRecyclerView;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.model.HogarByTitular;
import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.SolicitudesDownload;
import com.map_movil.map_movil.presenter.solicitud.ShowAddSolicitudAcitivityPresenter;
import com.map_movil.map_movil.presenter.solicitud.ShowAddSolicitudAcitivityPresenterImpl;

import java.util.ArrayList;

public class ShowAddSolicitudActivity extends AppCompatActivity implements MenuItem.OnActionExpandListener, ShowAddSolicitudAcitivityView {
    private Intent intent;
    private ShowAddSolicitudAcitivityPresenter showAddSolicitudAcitivityPresenter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    private int intCodSolicitud;
    private int intTipoOperacion;
    private ApiAdapterSolicitudes objApiAdapterSolicitudes;
    private ApiServiceSolicitudes objApiServiceSolicitudes;

    private ArrayList<HogarLigth> hogarLigthsList = new ArrayList<>();

    private TextInputEditText textInputObservacion;
    private TextInputEditText textInputPhoneNumber;
    private TextView textViewCodSolitud;
    private TextView textViewEstadoSolicitud;
    private TextView textViewMessageFind;
    private CardView cardWiewSolicitud;
    private LinearLayout linearLayoutMain;

    private LinearLayout linearLayoutTextoBusqueda;
    private RelativeLayout relativeLayoutProgressBar;

    private TextView textViewAlertChangeTitular;
    private TextView textViewCodHogar;
    private TextView textViewEstadoHogar;
    private TextView textViewUmbral;
    private TextView textViewDepartamento;
    private TextView textViewMunicipio;
    private TextView textViewAldea;
    private TextView textViewCaserio;
    private CheckBox checkBoxActualizacionDatos;
    private CheckBox checkBoxCambioTitular;
    private CheckBox checkBoxNuevoIntegrante;
    private CheckBox checkBoxBajaIntegrante;
    private CheckBox checkBoxCambioDomicilio;
    private CheckBox checkBoxBajaPrograma;
    private CheckBox checkBoxCorrecionSancion;
    private CheckBox checkBoxReactivaPrograma;

    private MenuItem saveDataItem;
    private RecyclerView recyclerViewMiembros;
    private AdapterItemMiembroNucleoRecyclerView adapterItemMiembroNucleoRecyclerView;

    private AlertDialog alertDialog;

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
        sharedPreferencesEditor = sharedPreferences.edit();

        showAddSolicitudAcitivityPresenter = new ShowAddSolicitudAcitivityPresenterImpl(this, getApplicationContext());
        intent = getIntent();
        intCodSolicitud = intent.getIntExtra("intCodSolicitud", 0);
        intTipoOperacion = intent.getIntExtra("intTipoOperacion", 0);

        linearLayoutMain = findViewById(R.id.linearLayoutMain);
        linearLayoutTextoBusqueda = findViewById(R.id.linearLayoutTextoBusqueda);
        relativeLayoutProgressBar = findViewById(R.id.relativeLayoutProgressBar);
        textViewMessageFind = findViewById(R.id.textViewMessageFind);
        textInputObservacion = findViewById(R.id.textInputObservacion);
        textInputPhoneNumber = findViewById(R.id.textInputPhoneNumber);

        checkBoxActualizacionDatos = findViewById(R.id.checkboxActualizacionDatos);
        checkBoxCambioTitular = findViewById(R.id.checkBoxCambioTitular);
        checkBoxNuevoIntegrante = findViewById(R.id.checkBoxNuevoIntegrante);
        checkBoxBajaIntegrante = findViewById(R.id.checkBoxBajaIntegrante);
        checkBoxCambioDomicilio = findViewById(R.id.checkboxCambioDomicilio);
        checkBoxBajaPrograma = findViewById(R.id.checkboxBajaPrograma);
        checkBoxCorrecionSancion = findViewById(R.id.checkboxCorreccionSancion);
        checkBoxReactivaPrograma = findViewById(R.id.checkboxReactivaPrograma);

        recyclerViewMiembros = findViewById(R.id.recyclerViewMiembros);
        recyclerViewMiembros.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterItemMiembroNucleoRecyclerView = new AdapterItemMiembroNucleoRecyclerView(hogarLigthsList);
        recyclerViewMiembros.setAdapter(adapterItemMiembroNucleoRecyclerView);

        objApiAdapterSolicitudes = new ApiAdapterSolicitudes();
        objApiServiceSolicitudes = objApiAdapterSolicitudes.getClientService();

        textInputObservacion.setEnabled(false);
        textInputPhoneNumber.setVisibility(View.GONE);

        if(intTipoOperacion == 2) {//Buscar una solicitud
            findSolicitudSave(intCodSolicitud);
        }else{//Cuando se va a ingresar una nueva solicitud.
            linearLayoutMain.setVisibility(View.GONE);
            textInputObservacion.setEnabled(true);
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
            MenuItem searchItem = menu.findItem(R.id.searchViewFind);
            SearchView searchView = (SearchView) searchItem.getActionView();

            saveDataItem = menu.findItem(R.id.saveData);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    findDataCreateSolicitud(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
            searchView.setQueryHint("Buscar...");
            searchView.setMaxWidth(Integer.MAX_VALUE);
            saveDataItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {


                    saveSolicitud();
                    return true;
                }
            });
            saveDataItem.setEnabled(true);
            return super.onCreateOptionsMenu(menu);
        }else{
            return false;
        }
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
        solicitudesDownload.setObservacion(textInputObservacion.getText().toString());

        if(!solicitudesDownload.isActualizacion_datos() && !solicitudesDownload.isCambio_titular() &&
                !solicitudesDownload.isNuevo_integrante() && !solicitudesDownload.isBaja_integrante() &&
                !solicitudesDownload.isCambio_domicilio() && !solicitudesDownload.isBaja_programa() &&
                !solicitudesDownload.isReactiva_programa() && !solicitudesDownload.isCorreccion_sancion()) {
            Toast.makeText(getApplicationContext(), "ERROR: Favor seleccionar uno o más tipos de solicitudes.", Toast.LENGTH_SHORT).show();
        } else if (solicitudesDownload.getObservacion().equals(null) || solicitudesDownload.getObservacion().isEmpty()
                || solicitudesDownload.getObservacion().length() < 15) {
            Toast.makeText(getApplicationContext(), "ERROR: Favor ingresar una observación, recuerde al menos debe contener 15 caracteres.", Toast.LENGTH_SHORT).show();
        } else {
            for (HogarLigth item : hogarLigthsList) {
                if (item.isBolTitular() == true) {
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

        checkBoxActualizacionDatos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)disableEnableCheckBox(false);
                if(isChecked)showPoppup(1);
            }
        });
        checkBoxCambioTitular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)disableEnableCheckBox(false);
                if(isChecked)showPoppup(2);
            }
        });
        checkBoxNuevoIntegrante.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)disableEnableCheckBox(false);
                if(isChecked)showPoppup(3);
            }
        });
        checkBoxBajaIntegrante.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)disableEnableCheckBox(false);
                if(isChecked)showPoppup(4);
            }
        });
        checkBoxCambioDomicilio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)disableEnableCheckBox(false);
                if(isChecked)showPoppup(5);
            }
        });
        checkBoxBajaPrograma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)disableEnableCheckBox(false);
                if(isChecked)showPoppup(6);
            }
        });
        checkBoxCorrecionSancion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)disableEnableCheckBox(false);
                if(isChecked)showPoppup(7);
            }
        });
        checkBoxReactivaPrograma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)disableEnableCheckBox(false);
                if(isChecked)showPoppup(8);
            }
        });

        saveDataItem.setVisible(true);
        hogarLigthsList = hogarLigthArrayList;
        adapterItemMiembroNucleoRecyclerView.adapterDataChange(hogarLigthsList);
        showProgressBar(false);
        findDataShowMessage(true);
    }

    @Override
    public void showSolicitud(InfoSolicitud infoSolicitud, ArrayList<HogarLigth> hogarLigthArrayList) {
        cardWiewSolicitud = findViewById(R.id.cardWiewSolicitud);
        textViewCodSolitud = findViewById(R.id.textViewCodigoSolicitud);
        textViewEstadoSolicitud = findViewById(R.id.textViewEstadoSolicitud);
        textViewCodHogar = findViewById(R.id.textViewCodigohogar);
        textViewEstadoHogar = findViewById(R.id.textViewEstadoHogar);
        textViewUmbral = findViewById(R.id.textViewUmbral);
        textViewDepartamento = findViewById(R.id.textViewDepartamento);
        textViewMunicipio = findViewById(R.id.textViewMunicipio);
        textViewAldea = findViewById(R.id.textViewAldea);
        textViewCaserio = findViewById(R.id.textViewCaserio);
        textViewAlertChangeTitular = findViewById(R.id.textViewAlertChangeTitular);
        textViewAlertChangeTitular.setVisibility(infoSolicitud.isBolTitularCambioHogar()? View.VISIBLE: View.GONE);

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

        cardWiewSolicitud.setVisibility(View.VISIBLE);
        textViewCodSolitud.setText(String.valueOf(infoSolicitud.getIntCodSolicitud()));
        textViewEstadoSolicitud.setText(infoSolicitud.getStrEstadoSolicitud());
        textViewCodHogar.setText(String.valueOf(infoSolicitud.getIntCodHogar()));
        textViewEstadoHogar.setText(String.valueOf(infoSolicitud.getStrEstadoHogar()));
        textViewUmbral.setText(String.valueOf(infoSolicitud.getStrUmbral()));
        textViewDepartamento.setText(String.valueOf(infoSolicitud.getStrDepartamento()));
        textViewMunicipio.setText(String.valueOf(infoSolicitud.getStrMunicipio()));
        textViewAldea.setText(String.valueOf(infoSolicitud.getStrAldea()));
        textViewCaserio.setText(String.valueOf(infoSolicitud.getStrCaserio()));
        textInputObservacion.setText(String.valueOf(infoSolicitud.getStrObservacion()));
        hogarLigthsList = hogarLigthArrayList;
        adapterItemMiembroNucleoRecyclerView.adapterDataChange(hogarLigthsList);
        showProgressBar(false);
        findDataShowMessage(true);
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
    public void finishCreationSolicitud(int offline) {
        if(offline == 1){
            sharedPreferencesEditor.putInt("Sincronizar",1);
            sharedPreferencesEditor.commit();
        }
        finish();
    }

    @Override
    public void showPoppup(int intPopuToShow) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.dialog_document_support_solicitudes, null);
        builder.setCancelable(false);
        builder.setView(viewInflate);

        alertDialog = builder.create();
        TextView ngButtonContinue = viewInflate.findViewById(R.id.ngButtonContinue);
        LinearLayout linearLayoutCambioTitular = viewInflate.findViewById(R.id.linearLayoutCambioTitular);
        LinearLayout linearLayoutActualizacionDatos = viewInflate.findViewById(R.id.linearLayoutActualizacionDatos);
        LinearLayout linearLayoutBajaIntegrante = viewInflate.findViewById(R.id.linearLayoutBajaIntegrante);
        LinearLayout linearLayoutNuevoIntegrante = viewInflate.findViewById(R.id.linearLayoutNuevoIntegrante);
        LinearLayout linearLayoutBajaPrograma = viewInflate.findViewById(R.id.linearLayoutBajaPrograma);
        LinearLayout linearLayoutReactivaPrograma = viewInflate.findViewById(R.id.linearLayoutReactivaPrograma);
        LinearLayout linearLayoutCorrecionSancion = viewInflate.findViewById(R.id.linearLayoutCorrecionSancion);

        switch (intPopuToShow) {
            case 1:
            case 5:
                linearLayoutActualizacionDatos.setVisibility(View.VISIBLE);
                break;
            case 2:
                linearLayoutCambioTitular.setVisibility(View.VISIBLE);
                break;
            case 3:
                linearLayoutNuevoIntegrante.setVisibility(View.VISIBLE);
                break;
            case 4:
                linearLayoutBajaIntegrante.setVisibility(View.VISIBLE);
                break;
            case 6:
                linearLayoutBajaPrograma.setVisibility(View.VISIBLE);
                break;
            case 7:
                linearLayoutCorrecionSancion.setVisibility(View.VISIBLE);
                break;
            case 8:
                linearLayoutReactivaPrograma.setVisibility(View.VISIBLE);
                break;
        }

        ngButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableEnableCheckBox(true);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void disableEnableCheckBox(boolean bolValue) {
        checkBoxActualizacionDatos.setEnabled(bolValue);
        checkBoxCambioTitular.setEnabled(bolValue);
        checkBoxNuevoIntegrante.setEnabled(bolValue);
        checkBoxBajaIntegrante.setEnabled(bolValue);
        checkBoxCambioDomicilio.setEnabled(bolValue);
        checkBoxBajaPrograma.setEnabled(bolValue);
        checkBoxReactivaPrograma.setEnabled(bolValue);
        checkBoxCorrecionSancion.setEnabled(bolValue);
    }
}
