package com.map_movil.map_movil.view.solicitudes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.hogar.ApiAdapterHogar;
import com.map_movil.map_movil.api.hogar.ApiServiceHogar;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.model.HogarByTitular;
import com.map_movil.map_movil.model.HogarLigth;
import com.map_movil.map_movil.model.InfoSolicitud;
import com.map_movil.map_movil.model.ResponseApi;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerSolicitudActivity extends AppCompatActivity {
    private Intent intent;
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
    private EditText editTextBuscar;
    private TextView textViewCodSolitud;
    private TextView textViewEstadoSolicitud;
    private LinearLayout linearLayoutSolicitud;
    private LinearLayout linearLayoutObserEdit;
    private LinearLayout linearLayoutObserView;
    private LinearLayout linearLayoutNucleo;
    private LinearLayout linearLayoutMain;
    private LinearLayout linearLayoutBuscar;
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
        setContentView(R.layout.activity_ver_solicitud);
        intent = getIntent();
        intCodSolicitud = intent.getIntExtra("intCodSolicitud", 0);
        intTipoOperacion = intent.getIntExtra("intTipoOperacion", 0);

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

        editTextBuscar = findViewById(R.id.textEditBuscar);
        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //findHogByTitular(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        linearLayoutMain = findViewById(R.id.linearLayoutMain);
        linearLayoutBuscar = findViewById(R.id.linearLayoutBuscar);

        Button buttonBuscar = findViewById(R.id.buttonBuscar);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findHogByTitular(editTextBuscar.getText().toString());
            }
        });

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSolicitud();
            }
        });


        if(intTipoOperacion == 2) {
            Call<ArrayList<InfoSolicitud>> call = objApiServiceSolicitudes.getSolicitudInfo(intCodSolicitud);
            call.enqueue(new Callback<ArrayList<InfoSolicitud>>() {
                @Override
                public void onResponse(Call<ArrayList<InfoSolicitud>> call, Response<ArrayList<InfoSolicitud>> response) {
                    if (response.body().size() > 0) {
                        infoSolicitud = response.body();
                        findHogar(infoSolicitud.get(0).getIntCodHogar()); //Buscar el hogar;
                        linearLayoutBuscar.setVisibility(View.GONE);

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


                        checkBoxActualizacionDatos.setChecked(infoSolicitud.get(0).isBolActualizacionDatos());
                        checkBoxCambioTitular.setChecked(infoSolicitud.get(0).isBolCambioTitular());
                        checkBoxNuevoIntegrante.setChecked(infoSolicitud.get(0).isBolNuevoIntegrante());
                        checkBoxBajaIntegrante.setChecked(infoSolicitud.get(0).isBolBajaIntegrante());
                        checkBoxCambioDomicilio.setChecked(infoSolicitud.get(0).isBolCambioDomicilio());
                        checkBoxBajaPrograma.setChecked(infoSolicitud.get(0).isBolBajaPrograma());
                        checkBoxCorrecionSancion.setChecked(infoSolicitud.get(0).isBolCorreccionSancion());


                        checkBoxActualizacionDatos.setEnabled(false);
                        checkBoxCambioTitular.setEnabled(false);
                        checkBoxNuevoIntegrante.setEnabled(false);
                        checkBoxBajaIntegrante.setEnabled(false);
                        checkBoxCambioDomicilio.setEnabled(false);
                        checkBoxBajaPrograma.setEnabled(false);
                        checkBoxCorrecionSancion.setEnabled(false);


                        linearLayoutSolicitud.setVisibility(View.VISIBLE);
                        textViewCodSolitud.setText(String.valueOf(infoSolicitud.get(0).getIntCodSolicitud()));
                        textViewEstadoSolicitud.setText(infoSolicitud.get(0).getStrEstadoSolicitud());
                        textViewCodHogar.setText(String.valueOf(infoSolicitud.get(0).getIntCodHogar()));
                        textViewEstadoHogar.setText(String.valueOf(infoSolicitud.get(0).getStrEstadoHogar()));
                        textViewUmbral.setText(String.valueOf(infoSolicitud.get(0).getStrUmbral()));
                        textViewDepartamento.setText(String.valueOf(infoSolicitud.get(0).getStrDepartamento()));
                        textViewMunicipio.setText(String.valueOf(infoSolicitud.get(0).getStrMunicipio()));
                        textViewAldea.setText(String.valueOf(infoSolicitud.get(0).getStrAldea()));
                        textViewCaserio.setText(String.valueOf(infoSolicitud.get(0).getStrCaserio()));
                        textViewObservacion.setText(String.valueOf(infoSolicitud.get(0).getStrObservacion()));

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<InfoSolicitud>> call, Throwable t) {

                }
            });
        }else{
            linearLayoutMain.setVisibility(View.GONE);
            linearLayoutObserEdit.setVisibility(View.VISIBLE);
            linearLayoutObserView.setVisibility(View.GONE);
        }
    }


    private void setView(int proceso){

    }

    private void findHogar(int intCodHogar){
        adapterHogar = new ApiAdapterHogar();
        serviceHogar = adapterHogar.getClientService();
        Call<ArrayList<HogarLigth>> call = serviceHogar.getLigthInfoHogar(intCodHogar);
        call.enqueue(new Callback<ArrayList<HogarLigth>>() {
            @Override
            public void onResponse(Call<ArrayList<HogarLigth>> call, Response<ArrayList<HogarLigth>> response) {
                if(response.body() != null && response.body().size() > 0){
                    hogarLigthsList = response.body();
                    Float intSizeFond = 11f;

                    for(HogarLigth item: hogarLigthsList) {
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

                        linearLayoutEdad.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3f));
                        linearLayoutSexo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));
                        linearLayoutIdentidad.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                        textViewNombre.setText(item.getStrNombreBeneficiario());
                        textViewSexo.setText(item.getStrSexo());
                        textViewEdad.setText(String.valueOf(item.getIntEdad()));
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
                }else{
                    Toast.makeText(getApplicationContext(), "No se puedieron obtener los datos del hogar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HogarLigth>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findHogByTitular(String strCodIdentidad){
        adapterHogar = new ApiAdapterHogar();
        serviceHogar = adapterHogar.getClientService();
        Call<ArrayList<HogarByTitular>> call = serviceHogar.getHogarByTitular(strCodIdentidad);
        call.enqueue(new Callback<ArrayList<HogarByTitular>>() {
            @Override
            public void onResponse(Call<ArrayList<HogarByTitular>> call, Response<ArrayList<HogarByTitular>> response) {
                if(response.body() != null && response.body().size() > 0){
                    ArrayList<HogarByTitular> hogarByTitulars = response.body();

                    findHogar(hogarByTitulars.get(0).getIntCodHogar());

                    linearLayoutMain.setVisibility(View.VISIBLE);

                    textViewCodHogar = findViewById(R.id.textViewCodigohogar);
                    textViewEstadoHogar = findViewById(R.id.textViewEstadoHogar);
                    textViewUmbral = findViewById(R.id.textViewUmbral);
                    textViewDepartamento = findViewById(R.id.textViewDepartamento);
                    textViewMunicipio = findViewById(R.id.textViewMunicipio);
                    textViewAldea = findViewById(R.id.textViewAldea);
                    textViewCaserio = findViewById(R.id.textViewCaserio);
                    textViewCodHogar.setText(String.valueOf(hogarByTitulars.get(0).getIntCodHogar()));

                    textViewEstadoHogar.setText(String.valueOf(hogarByTitulars.get(0).getStrEstadoHogar()));
                    textViewUmbral.setText(String.valueOf(hogarByTitulars.get(0).getStrUmbralHogar()));
                    textViewDepartamento.setText(String.valueOf(hogarByTitulars.get(0).getStrDepartamento()));
                    textViewMunicipio.setText(String.valueOf(hogarByTitulars.get(0).getStrMunicipio()));
                    textViewAldea.setText(String.valueOf(hogarByTitulars.get(0).getStrAldea()));
                    textViewCaserio.setText(String.valueOf(hogarByTitulars.get(0).getStrCaserio()));

                    buttonGuardar.setVisibility(View.VISIBLE);

                }else{
                    Toast.makeText(getApplicationContext(), "No se detectaron datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HogarByTitular>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveSolicitud(){
        objApiAdapterSolicitudes = new ApiAdapterSolicitudes();
        objApiServiceSolicitudes = objApiAdapterSolicitudes.getClientService();
        sharedPreferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);

        int actualizacionDatos = (checkBoxActualizacionDatos.isChecked() == true)? 1: 0;
        int cambioTitular = (checkBoxCambioTitular.isChecked() == true)? 1: 0;
        int nuevoMiembro = (checkBoxNuevoIntegrante.isChecked() == true)? 1: 0;
        int bajaMiembro = (checkBoxBajaIntegrante.isChecked() == true)? 1: 0;
        int cambioDomicilio = (checkBoxCambioDomicilio.isChecked() == true)? 1: 0;
        int bajaPrograma = (checkBoxBajaPrograma.isChecked() == true)? 1: 0;
        int correccionSancion = (checkBoxCorrecionSancion.isChecked() == true)? 1: 0;
        int reactivaPrograma = (checkBoxReactivaPrograma.isChecked() == true)? 1: 0;

        int intCodUser =  sharedPreferences.getInt("codigo", 0);

        String strObservacion = (textInputEditTextObservacion.getText().toString()) == null? "SIN OBSERVACION": textInputEditTextObservacion.getText().toString();


        if(actualizacionDatos == 0 && cambioTitular == 0 && nuevoMiembro == 0 && bajaMiembro == 0 && cambioDomicilio == 0 && bajaPrograma == 0 && correccionSancion == 0) {
            Toast.makeText(getApplicationContext(), "ERROR: Favor seleccionar uno o más tipos de solicitudes.", Toast.LENGTH_SHORT).show();
        }else{
            Call<ResponseApi> call = objApiServiceSolicitudes.createSolicitud(
                    hogarLigthsList.get(0).getStrIdentidad(),
                    intCodUser,
                    strObservacion,
                    actualizacionDatos,
                    cambioTitular,
                    nuevoMiembro,
                    bajaMiembro,
                    cambioDomicilio,
                    bajaPrograma,
                    reactivaPrograma,
                    correccionSancion);

            call.enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                    if (response.body() != null) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "No se logro ingresar el dato.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseApi> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
