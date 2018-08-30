package com.map_movil.map_movil.view.validar_hogares;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.hogares_validar_adapter;

public class ListarValidacionesFragment extends Fragment {

    private  hogares_validar_adapter hogares_validar_adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_validar_hogar,container, false);

        RecyclerView Personas_Validar =(RecyclerView) view.findViewById(R.id.rv_personas_validar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        hogares_validar_adapter = new hogares_validar_adapter(getContext());
        Personas_Validar.setAdapter(hogares_validar_adapter);
        Personas_Validar.setLayoutManager(linearLayoutManager);

        hogares_validar_adapter.BuscarValidaciones();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        hogares_validar_adapter.BuscarValidaciones();
    }

}
