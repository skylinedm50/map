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
import com.map_movil.map_movil.view.solicitudes.VerSolicitudActivity;

import java.util.ArrayList;

public class AdapterRecyclerViewSolicitudes extends RecyclerView.Adapter<AdapterRecyclerViewSolicitudes.ViewHolder>{
    private ArrayList<SolicitudesUsuario> listSolicitudesUsuario;
    private Context context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onitemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewCodigoSolicitud;
        private TextView textViewNombreSolicitante;
        private TextView textViewObservacion;
        private TextView textViewEstado;

        public ViewHolder(View itemView,final OnItemClickListener listener){
            super(itemView);

            textViewCodigoSolicitud = itemView.findViewById(R.id.textViewPrincipal);
            textViewNombreSolicitante = itemView.findViewById(R.id.textViewDescripcion);
            textViewObservacion = itemView.findViewById(R.id.textViewReferencia);
            textViewEstado = itemView.findViewById(R.id.textViewEstado);

           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onitemClick(position);
                        }
                    }
                }
            });
        }
    }

    public AdapterRecyclerViewSolicitudes(ArrayList<SolicitudesUsuario> listSolicitudesUsuario, Context context){
        //Constructor....
        this.listSolicitudesUsuario = listSolicitudesUsuario;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        //mListener = view.setOnClickListener(this);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SolicitudesUsuario objSolicitudes = this.listSolicitudesUsuario.get(position);
        holder.textViewCodigoSolicitud.setText(objSolicitudes.getIntCodSolicitud().toString());
        holder.textViewNombreSolicitante.setText(objSolicitudes.getStrNombreSolicitante());
        holder.textViewObservacion.setText(objSolicitudes.getStrObservacion());
        holder.textViewEstado.setText(objSolicitudes.getStrEstado());

    }

    @Override
    public int getItemCount() {
        return listSolicitudesUsuario.size();
    }



   /* public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }*/

   /* @Override
    public void onClick(View v) {


        if(listener != null){
            listener.onClick(v);
        }
    }*/

   public void dataAdapterChange(ArrayList<SolicitudesUsuario> solicitudesUsuarios){
       listSolicitudesUsuario = solicitudesUsuarios;
       notifyDataSetChanged();
   }

}
