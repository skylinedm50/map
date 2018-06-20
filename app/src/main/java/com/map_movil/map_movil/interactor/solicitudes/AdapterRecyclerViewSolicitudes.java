package com.map_movil.map_movil.interactor.solicitudes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.SolicitudesUsuario;

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
        private TextView objTextViewCodigoSolicitud;
        private TextView objTextViewNombreSolicitante;
        private TextView objTextViewObservacion;

        public ViewHolder(View itemView,final OnItemClickListener listener){
            super(itemView);

            objTextViewCodigoSolicitud = itemView.findViewById(R.id.textViewCodSolicitud);
            objTextViewNombreSolicitante = itemView.findViewById(R.id.textViewNombreSolicitante);
            objTextViewObservacion = itemView.findViewById(R.id.textViewObservacion);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_preview_solicitudes, parent, false);
        //view.setOnClickListener(this);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SolicitudesUsuario objSolicitudes = this.listSolicitudesUsuario.get(position);
        holder.objTextViewCodigoSolicitud.setText(objSolicitudes.getIntCodSolicitud().toString());
        holder.objTextViewNombreSolicitante.setText(objSolicitudes.getStrNombreSolicitante());
        holder.objTextViewObservacion.setText(objSolicitudes.getStrObservacion());
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
