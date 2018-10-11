package com.map_movil.map_movil.interactor.solicitudes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.SolicitudesUsuario;
import com.map_movil.map_movil.view.solicitudes.ShowAddSolicitudActivity;

import java.util.ArrayList;

public class AdapterRecyclerViewSolicitudes extends RecyclerView.Adapter<AdapterRecyclerViewSolicitudes.ViewHolder>{
    private ArrayList<SolicitudesUsuario> listSolicitudesUsuario;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewCodigoSolicitud;
        private TextView textViewNombreSolicitante;
        private TextView textViewObservacion;
        private TextView textViewEstado;
        private LinearLayout linearLayoutDivider;
        private LinearLayout linearLayoutItem;

        public ViewHolder(View itemView){
            super(itemView);
            textViewCodigoSolicitud = itemView.findViewById(R.id.textViewPrincipal);
            textViewNombreSolicitante = itemView.findViewById(R.id.textViewDescripcion);
            textViewObservacion = itemView.findViewById(R.id.textViewReferencia);
            textViewEstado = itemView.findViewById(R.id.textViewEstado);
            linearLayoutDivider = itemView.findViewById(R.id.linearLayoutDivider);
            linearLayoutItem = itemView.findViewById(R.id.linearLayoutItem);
        }
    }

    public AdapterRecyclerViewSolicitudes(ArrayList<SolicitudesUsuario> listSolicitudesUsuario, Context context){
        this.listSolicitudesUsuario = listSolicitudesUsuario;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT ,1);
        int intButtom = (position == (listSolicitudesUsuario.size() - 1)) ? 400 : 0;
        SolicitudesUsuario objSolicitudes = this.listSolicitudesUsuario.get(position);
        holder.textViewCodigoSolicitud.setText(objSolicitudes.getIntCodSolicitud().toString());
        holder.textViewNombreSolicitante.setText(objSolicitudes.getStrNombreSolicitante());
        holder.textViewObservacion.setText(objSolicitudes.getStrObservacion());
        holder.textViewEstado.setText(objSolicitudes.getStrEstado());
        layoutParams.setMargins(0,0,0,intButtom);
        holder.linearLayoutDivider.setLayoutParams(layoutParams);
        holder.linearLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textViewCodSolicitud = v.findViewById(R.id.textViewPrincipal);
                Intent intent = new Intent(context.getApplicationContext(), ShowAddSolicitudActivity.class);
                intent.putExtra("intTipoOperacion", 2);
                intent.putExtra("intCodSolicitud", Integer.parseInt(textViewCodSolicitud.getText().toString()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSolicitudesUsuario.size();
    }

    public void dataAdapterChange(ArrayList<SolicitudesUsuario> solicitudesUsuarios){
        listSolicitudesUsuario = solicitudesUsuarios;
        notifyDataSetChanged();
    }
}
