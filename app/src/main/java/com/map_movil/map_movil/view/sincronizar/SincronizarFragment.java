package com.map_movil.map_movil.view.sincronizar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.presenter.sincronizar.SincronizarPresenter;
import com.map_movil.map_movil.presenter.sincronizar.SincronizarPresenterImpl;

public class SincronizarFragment extends Fragment implements SincronizarView {

    private SincronizarPresenter sincronizarPresenter;
    private LinearLayout progressBar;
    private CheckedTextView checkedTextViewSolicitud;
    private CheckedTextView checkedTextViewQueja;
    private TextView txtMensajeSincro;

    private NavigationView navigationView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sincronizar_datos, container, false);
        Button btnSincronizar = (Button) view.findViewById(R.id.btnSincronizar);
        this.sincronizarPresenter = new SincronizarPresenterImpl(this , getContext());
        this.progressBar = (LinearLayout) view.findViewById(R.id.progressbar_sincro);
        this.checkedTextViewQueja = (CheckedTextView) view.findViewById(R.id.chk_queja);
        this.checkedTextViewSolicitud = (CheckedTextView) view.findViewById(R.id.chk_solicitud);
        this.txtMensajeSincro = (TextView) view.findViewById(R.id.txtMensajeSincro);

        sharedPreferences = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sincronizar();
            }
        });
        return view;
    }


    @Override
    public void Sincronizar() {

        LimpiarControles();
        this.progressBar.setVisibility(View.VISIBLE);
        this.checkedTextViewSolicitud.setText("1. Sincronizando solicitudes");
        SharedPreferences sharedPreferences = getContext()
                .getSharedPreferences("USER", Context.MODE_PRIVATE);
        sincronizarPresenter.Sincronizar(sharedPreferences.getInt("codigo",0));
    }

    @Override
    public void EventoCompletado(int evento) {
        if(evento==1){
            this.checkedTextViewSolicitud.setText("1. Solicitudes sincronizadas");
            this.checkedTextViewQueja.setText("2. Sincronizando quejas y denuncias");
            this.checkedTextViewSolicitud.setCheckMarkDrawable(R.drawable.ic_check);
            this.checkedTextViewSolicitud.setTextColor(Color.parseColor("#055102"));
        }else{
            this.checkedTextViewQueja.setText("2. Quejas y denuncias sincronizadas");
            this.checkedTextViewQueja.setCheckMarkDrawable(R.drawable.ic_check);
            this.checkedTextViewQueja.setTextColor(Color.parseColor("#055102"));
            this.progressBar.setVisibility(View.GONE);
        }

          if(evento == 11){
            sharedPreferencesEditor.putInt("Sincronizar",0);
            sharedPreferencesEditor.commit();
            navigationView = (NavigationView)this.getActivity().findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(0).getSubMenu().getItem(2).setActionView(null);
        }
    }

    @Override
    public void MensajeSincronizar(String Mensaje) {
        String l = String.format(this.txtMensajeSincro.getText().toString(),"\n");
        this.txtMensajeSincro.setText( l+Mensaje);
    }

    private void LimpiarControles(){
        this.txtMensajeSincro.setText("");
        this.checkedTextViewSolicitud.setText("1. Solicitudes por sincronizar.  ");
        this.checkedTextViewQueja.setText("2. Quejas y denuncias por sincronizar. ");
        this.checkedTextViewSolicitud.setCheckMarkDrawable(null);
        this.checkedTextViewQueja.setCheckMarkDrawable(null);
        this.checkedTextViewSolicitud.setTextColor(Color.parseColor("#700f0f"));
        this.checkedTextViewQueja.setTextColor(Color.parseColor("#700f0f"));
    }

}
