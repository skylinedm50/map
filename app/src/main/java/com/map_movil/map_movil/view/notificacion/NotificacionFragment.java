package com.map_movil.map_movil.view.notificacion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.AdapterItemUsuarioRecyclerView;
import com.map_movil.map_movil.presenter.notificacion.NotificacionFragmentPresenter;
import com.map_movil.map_movil.presenter.notificacion.NotificacionFragmentPresenterImpl;

import java.util.ArrayList;

public class NotificacionFragment extends Fragment implements NotificacionFragmentView {
    private View view;
    private Spinner spinnerRegiones;
    private Spinner spinnerOficinas;
    private CheckBox checkboxSelectAll;
    private RecyclerView recyclerViewUsuarios;
    private TextInputEditText textInputEditTextNotificacion;
    private NotificacionFragmentPresenter notificacionFragmentPresenter;
    private AdapterItemUsuarioRecyclerView adapterItemUsuarioRecyclerView;
    private LinearLayout linearLayoutData;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private MenuItem sendDataItem;
    private CheckBox checkboxSendAll;
    private TextView textViewCountLetters;

    public NotificacionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        notificacionFragmentPresenter = new NotificacionFragmentPresenterImpl(this);
        spinnerRegiones = view.findViewById(R.id.spinnerRegiones);
        spinnerOficinas = view.findViewById(R.id.spinnerOficinas);
        checkboxSendAll = view.findViewById(R.id.checkboxSendAll);
        checkboxSelectAll = view.findViewById(R.id.checkboxSelectAll);
        recyclerViewUsuarios = view.findViewById(R.id.recyclerViewUsuarios);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapterItemUsuarioRecyclerView = new AdapterItemUsuarioRecyclerView(new ArrayList<String>());
        recyclerViewUsuarios.setAdapter(adapterItemUsuarioRecyclerView);
        textInputEditTextNotificacion = view.findViewById(R.id.textInputEditTextNotificacion);
        linearLayoutData = view.findViewById(R.id.linearLayoutData);
        progressBar =  view.findViewById(R.id.progressBar);
        sharedPreferences = view.getContext().getSharedPreferences("USER", view.getContext().MODE_PRIVATE);

        spinnerRegiones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getOficionas(Integer.parseInt(parent.getItemAtPosition(position).toString().split("-")[0]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerOficinas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getUsuarios(Integer.parseInt(parent.getItemAtPosition(position).toString().split("-")[0]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        checkboxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapterItemUsuarioRecyclerView.selectAll(isChecked);
            }
        });
        checkboxSendAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setDisabledAllFilter(isChecked);
            }
        });

        textInputEditTextNotificacion.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //textViewCountLetters.setText("/150");
                return false;
            }
        });
        getRegiones();

        return  view;
    }

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.menu_multiple_option, menu);
       MenuItem searchItem = menu.findItem(R.id.searchViewFind);
       sendDataItem = menu.findItem(R.id.sendData);
       searchItem.setEnabled(false);
       searchItem.setVisible(false);
       sendDataItem.setEnabled(true);
       sendDataItem.setVisible(true);
       sendDataItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               showProgressBar(true);
               notificacionFragmentPresenter.validDataSend(adapterItemUsuarioRecyclerView.getArrayListSelected(),
                       textInputEditTextNotificacion.getText().toString(),
                       sharedPreferences.getInt("codigo", 0),
                       ((spinnerOficinas.getSelectedItem().toString().isEmpty())? 0: Integer.parseInt(spinnerOficinas.getSelectedItem().toString().split("-")[0])),
                       checkboxSendAll.isChecked());
               return false;
           }
       });
    }

    @Override
    public void getRegiones() {
        notificacionFragmentPresenter.getRegiones();
    }

    @Override
    public void showRegiones(ArrayList<String> arrayListRegiones) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, arrayListRegiones);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegiones.setAdapter(arrayAdapter);
    }

    @Override
    public void getOficionas(int intCodRegion) {
        notificacionFragmentPresenter.getOficionas(intCodRegion);
    }

    @Override
    public void showOficinas(ArrayList<String> arrayListOficinas) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, arrayListOficinas);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOficinas.setAdapter(arrayAdapter);
    }

    @Override
    public void getUsuarios(int intCodOficina) {
        notificacionFragmentPresenter.getUsuarios(intCodOficina);
    }

    @Override
    public void showUsuarios(ArrayList<String> arrayListUsuarios) {
        adapterItemUsuarioRecyclerView.adapterDataChange(arrayListUsuarios);
    }

    @Override
    public void showMessage(String strMessage) {
        Toast.makeText(view.getContext(), strMessage, Toast.LENGTH_SHORT).show();
        showProgressBar(false);
    }

    @Override
    public void showProgressBar(boolean bolValue) {
        sendDataItem.setEnabled((bolValue)? false: true);
        linearLayoutData.setVisibility((bolValue)? View.GONE : View.VISIBLE);
        progressBar.setVisibility((bolValue)? View.VISIBLE : View.GONE);
    }

    @Override
    public void closeActivity() {
        getActivity().finish();
    }

    @Override
    public void setDisabledAllFilter(boolean bolValue) {
        spinnerRegiones.setEnabled((bolValue)? false: true);
        spinnerOficinas.setEnabled((bolValue)? false: true);
        checkboxSelectAll.setEnabled((bolValue)? false: true);
        recyclerViewUsuarios.setEnabled((bolValue)? false: true);
    }
}
