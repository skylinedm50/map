package com.map_movil.map_movil.view.Quejas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.quejas_denuncias_adapter;
import com.map_movil.map_movil.model.QuejasDenuncias;
import java.util.ArrayList;

public class QuejasFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    public  Integer codigo_accion;
    private quejas_denuncias_adapter denuncias_adapter;
    private RecyclerView rv_quejas;
    private  LinearLayoutManager linearLayoutManager;

    public QuejasFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_quejas , container , false);

        rv_quejas = (RecyclerView)view.findViewById(R.id.rvQuejas);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        denuncias_adapter = new quejas_denuncias_adapter(getContext() , codigo_accion );
        rv_quejas.setAdapter(denuncias_adapter);
        rv_quejas.setLayoutManager(linearLayoutManager);

        denuncias_adapter.SolicitarQuejas();
        setHasOptionsMenu(true);
        return  view;
    }


    @Override
    public void onResume() {
        super.onResume();
        this.denuncias_adapter.SolicitarQuejas();

        this.rv_quejas.post(new Runnable() {
            @Override
            public void run() {
                rv_quejas.smoothScrollToPosition(denuncias_adapter.getItemCount());
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        MenuItem nextItem = menu.findItem(R.id.next);
        nextItem.setVisible(false);
        nextItem.setEnabled(false);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar...");

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void filtro(String Texto){
        ArrayList<QuejasDenuncias> quejas_denuncias = this.denuncias_adapter.ArrayAdapterQuejas( this.codigo_accion);
        ArrayList<QuejasDenuncias> filtro_adapter   = new ArrayList<>();

        Texto = (Texto.trim().equals(""))?"0-9":Texto;

        for(int x = 0; x < quejas_denuncias.size(); x++){
           if(Texto.matches("^[0-9]*$") || Texto =="0-9" ){
               if(String.valueOf( quejas_denuncias.get(x).getCodigo_solicitud() ).matches("^"+Texto+".*") || Texto =="0-9"){
                   filtro_adapter.add(quejas_denuncias.get(x));
               }
           }
           else{
               if(quejas_denuncias.get(x).getNombre_solicitante().matches("^"+Texto.toUpperCase()+".*")){
                   filtro_adapter.add(quejas_denuncias.get(x));
               }
           }
        }

        this.denuncias_adapter.ActualizarFiltro(filtro_adapter , filtro_adapter.size() , this.codigo_accion);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filtro(s);
        return false;
    }


}
