package com.map_movil.map_movil.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.Emision;

import java.util.ArrayList;

public class AdapterItemEmisionRecyclerView extends RecyclerView.Adapter<AdapterItemEmisionRecyclerView.ViewHolder> {
    private ArrayList<Emision> emisionArrayList;

    public AdapterItemEmisionRecyclerView(ArrayList<Emision> emisionArrayList) {
        this.emisionArrayList = emisionArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewFechaEmision;
        private TextView textViewDescripcionEmision;
        private TextView textViewEmisor;
        private TextView textViewTipoEmision;
        private LinearLayout linearLayoutDivider;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewFechaEmision = itemView.findViewById(R.id.textViewPrincipal);
            textViewDescripcionEmision = itemView.findViewById(R.id.textViewDescripcion);
            textViewEmisor = itemView.findViewById(R.id.textViewReferencia);
            textViewTipoEmision = itemView.findViewById(R.id.textViewEstado);
            linearLayoutDivider = itemView.findViewById(R.id.linearLayoutDivider);
        }
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
        int intButtom = (position == (emisionArrayList.size() - 1)) ? 400 : 0;
        layoutParams.setMargins(0,0,0,intButtom);
        holder.linearLayoutDivider.setLayoutParams(layoutParams);
        holder.textViewEmisor.setVisibility(View.GONE);
        holder.textViewDescripcionEmision.setText(emisionArrayList.get(position).getStrMensaje());
        holder.textViewFechaEmision.setText(String.valueOf(emisionArrayList.get(position).getStrFecha()));
        holder.textViewTipoEmision.setText(emisionArrayList.get(position).getStrTipoEmision());
    }

    @Override
    public int getItemCount() {
        return emisionArrayList.size();
    }

    public void adapterDataChange(ArrayList<Emision> emisionArrayList){
        this.emisionArrayList = emisionArrayList;
        notifyDataSetChanged();
    }
}
