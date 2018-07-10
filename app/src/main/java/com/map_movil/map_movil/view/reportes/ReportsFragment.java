package com.map_movil.map_movil.view.reportes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.map_movil.map_movil.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReportsFragment extends Fragment {
    private View view;

    public ReportsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reports, container, false);
        final ListView lv = view.findViewById(R.id.lv);
        String[] graficos = new String[] {
                "Estado de solicitudes (según realización)",
                "Estado de solicitudes (según estado)",
        };
        final List<String> lista_graficos = new ArrayList<String>(Arrays.asList(graficos));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (view.getContext(), android.R.layout.simple_list_item_1, lista_graficos);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (position == 0){

                            Intent intent = new Intent(view.getContext(), GraficoActivity.class);
                            intent.putExtra("NombreGrafico", "Estado de solicitudes (según realización)");
                            intent.putExtra("NumGrafico", 0);

                            startActivity(intent);
                        }
                        if(position==1){
                            Intent intent = new Intent(view.getContext(), GraficoActivity.class);
                            intent.putExtra("NombreGrafico", "Estado de solicitudes (según estado)");
                            intent.putExtra("NumGrafico", 1);
                            startActivity(intent);
                        }

                    }
                }
        );

        return view;
    }

}
