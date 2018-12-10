package com.map_movil.map_movil.view.notificacion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.AdapterItemEmisionRecyclerView;
import com.map_movil.map_movil.model.Emision;
import com.map_movil.map_movil.presenter.notificacion.EmisionesFragmentPresenter;
import com.map_movil.map_movil.presenter.notificacion.EmisionesFragmentPresenterImpl;

import java.util.ArrayList;

public class EmisionesFragment extends Fragment implements EmisionesFragmentView {
    public int intTypeFind;
    private View view;
    private EmisionesFragmentPresenter emisionesFragmentPresenter;
    private RecyclerView recyclerViewEmisiones;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Emision> emisionArrayList = new ArrayList<>();
    private AdapterItemEmisionRecyclerView adapterItemEmisionRecyclerView;
    private TextView textViewMessageEmpty;

    public EmisionesFragment(){}

    @Override
    public void  onResume() {
        super.onResume();
        getEmisiones(intTypeFind);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_emisiones, container, false);
        emisionesFragmentPresenter = new EmisionesFragmentPresenterImpl(this, view.getContext());
        recyclerViewEmisiones = view.findViewById(R.id.recyclerViewEmisiones);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        adapterItemEmisionRecyclerView = new AdapterItemEmisionRecyclerView(emisionArrayList);
        recyclerViewEmisiones.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewEmisiones.setAdapter(adapterItemEmisionRecyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEmisiones(intTypeFind);
            }
        });
        textViewMessageEmpty = view.findViewById(R.id.textViewMessageEmpty);

        return view;
    }

    @Override
    public void getEmisiones(int intType) {
        showProgressBar(true);
        adapterItemEmisionRecyclerView.adapterDataChange(new ArrayList<Emision>());
        emisionesFragmentPresenter.getEmisiones(intType);
    }

    @Override
    public void showEmisiones(ArrayList<Emision> emisiones) {
        adapterItemEmisionRecyclerView.adapterDataChange(emisiones);
        showProgressBar(false);
    }

    @Override
    public void showMessage(String strMessage) {
        Toast.makeText(view.getContext(), strMessage, Toast.LENGTH_SHORT).show();
        showProgressBar(false);
    }

    @Override
    public void showProgressBar(boolean bolValue) {
        recyclerViewEmisiones.setVisibility((bolValue)? View.GONE: View.VISIBLE);
        swipeRefreshLayout.setRefreshing(bolValue);
        textViewMessageEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showMessageForEmptyData(boolean bolValue) {
        textViewMessageEmpty.setVisibility((bolValue)? View.VISIBLE: View.GONE);
    }
}
