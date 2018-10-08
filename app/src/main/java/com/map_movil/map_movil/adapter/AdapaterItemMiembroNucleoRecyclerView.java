package com.map_movil.map_movil.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.HogarLigth;

import java.util.ArrayList;

public class AdapaterItemMiembroNucleoRecyclerView extends RecyclerView.Adapter<AdapaterItemMiembroNucleoRecyclerView.ViewHolder> {
    private ArrayList<HogarLigth> hogarLigthArrayList;

    public AdapaterItemMiembroNucleoRecyclerView(ArrayList<HogarLigth> hogarLigthArrayList){
        this.hogarLigthArrayList = hogarLigthArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNombre;
        private TextView textViewSexo;
        private TextView textViewEdad;
        private TextView textViewIdentidad;
        private LinearLayout linearLayoutMainData;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewSexo = itemView.findViewById(R.id.textViewSexo);
            textViewEdad = itemView.findViewById(R.id.textViewEdad);
            textViewIdentidad = itemView.findViewById(R.id.textViewIdentidad);
            linearLayoutMainData = itemView.findViewById(R.id.linearLayoutMainData);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_miembro_nucleo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HogarLigth hogarLigth = hogarLigthArrayList.get(position);
        int intBackgroundColor = Color.WHITE;

        holder.textViewNombre.setText(hogarLigth.getStrNombreBeneficiario());
        holder.textViewSexo.setText(hogarLigth.getStrSexo());
        holder.textViewEdad.setText(hogarLigth.getStrEdad());
        holder.textViewIdentidad.setText(hogarLigth.getStrIdentidad());
        if(hogarLigth.isBolTitular()){
            intBackgroundColor = Color.argb(255, 185, 255, 85);
        }
        holder.linearLayoutMainData.setBackgroundColor(intBackgroundColor);
    }

    @Override
    public int getItemCount() {
        return hogarLigthArrayList.size();
    }

    public void adapterDataChange(ArrayList<HogarLigth> hogarLigthArrayList){
        this.hogarLigthArrayList = hogarLigthArrayList;
        notifyDataSetChanged();
    }
}
