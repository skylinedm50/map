package com.map_movil.map_movil.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.map_movil.map_movil.R;

import java.util.ArrayList;

public class AdapterItemUsuarioRecyclerView extends RecyclerView.Adapter<AdapterItemUsuarioRecyclerView.ViewHolder>{
    private ArrayList<String> arrayListUsuario;
    private ArrayList<String> arrayListUsuarioSelect;

    public AdapterItemUsuarioRecyclerView(ArrayList<String> arrayList) {
        arrayListUsuario = arrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBoxUsuario;
        private TextView textViewCodigoUsuario;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBoxUsuario = itemView.findViewById(R.id.checkboxUsuario);
            textViewCodigoUsuario = itemView.findViewById(R.id.textViewCodigoUsuario);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario_checkbox, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       String strUsuario = arrayListUsuario.get(position).split("-")[1];
       String strCodigoUser = arrayListUsuario.get(position).split("-")[1];
       holder.checkBoxUsuario.setText(strUsuario);
       holder.textViewCodigoUsuario.setText(strCodigoUser);
    }

    @Override
    public int getItemCount() {
        return arrayListUsuario.size();
    }

    public void adapterDataChange(ArrayList<String> arrayList){
        arrayListUsuario = arrayList;
        arrayListUsuarioSelect = new ArrayList<>();
        notifyDataSetChanged();
    }

    public ArrayList<String> getArrayListSelected(){
        return arrayListUsuarioSelect;
    }
}
