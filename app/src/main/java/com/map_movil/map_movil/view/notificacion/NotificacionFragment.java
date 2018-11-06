package com.map_movil.map_movil.view.notificacion;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.map_movil.map_movil.R;

import java.util.ArrayList;

public class NotificacionFragment extends Fragment implements NotificacionFragmentView {
    private View view;
    private Spinner spinnerRegiones;
    private Spinner spinnerOficinas;
    private CheckBox checkboxSelectAll;
    private RecyclerView recyclerViewUsuarios;
    private TextInputEditText textInputEditTextNotificacion;

    public NotificacionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        spinnerRegiones = view.findViewById(R.id.spinnerRegiones);
        spinnerOficinas = view.findViewById(R.id.spinnerOficinas);
        checkboxSelectAll = view.findViewById(R.id.checkboxSelectAll);
        recyclerViewUsuarios = view.findViewById(R.id.recyclerViewUsuarios);
        textInputEditTextNotificacion = view.findViewById(R.id.textInputEditTextNotificacion);

        return  view;
    }

    @Override
    public void getRegiones() {

    }

    @Override
    public void showRegiones(ArrayList<String> arrayListRegiones) {

    }

    @Override
    public void getOficionas(int intCodRegion) {

    }

    @Override
    public void showOficinas(ArrayList<String> arrayListOficinas) {

    }

    @Override
    public void getUsuarios(int intCodOficina) {

    }

    @Override
    public void showUsuarios(ArrayList<String> arrayListUsuarios) {

    }

    @Override
    public void sendNotificacion(ArrayList<String> arrayListUsuarios, String strNotificacion) {

    }

    @Override
    public void showMessage(String strMessage) {

    }
}
