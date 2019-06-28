package com.map_movil.map_movil.view.validar_hogares;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.hogares_validar_adapter;
import com.map_movil.map_movil.decorator.StickyHeaderRv;
import java.util.ArrayList;

public class ListarValidacionesFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

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
        Personas_Validar.addItemDecoration(new StickyHeaderRv(hogares_validar_adapter));
        hogares_validar_adapter.BuscarValidaciones();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        hogares_validar_adapter.BuscarValidaciones();
    }

    public void Filtro(String texto){
        ArrayList<String[]> validaciones = hogares_validar_adapter.ListarHogaresValidar();
        ArrayList<String[]> Filtros      = new ArrayList<String[]>();

        for(int i = 0; i < validaciones.size(); i++){
            if(validaciones.get(i)[0].matches("^"+texto.toUpperCase()+".*") || validaciones.get(i)[9].equals("1")){
                Filtros.add(validaciones.get(i));
            }
        }
        hogares_validar_adapter.ActualizarFiltro(Filtros);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        MenuItem nextItem = menu.findItem(R.id.next);

        searchItem.setVisible(true);
        searchItem.setEnabled(true);

        nextItem.setVisible(false);
        nextItem.setEnabled(false);

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar...");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Filtro(newText);
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return false;
    }
}
