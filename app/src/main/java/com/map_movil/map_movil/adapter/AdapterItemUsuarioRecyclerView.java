package com.map_movil.map_movil.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.map_movil.map_movil.R;

import java.util.ArrayList;

public class AdapterItemUsuarioRecyclerView extends RecyclerView.Adapter<AdapterItemUsuarioRecyclerView.ViewHolder>{
    private ArrayList<String> arrayListUsuario;
    private ArrayList<String> arrayListUsuarioSelect = new ArrayList<>();

    public AdapterItemUsuarioRecyclerView(ArrayList<String> arrayList) {
        arrayListUsuario = arrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBoxItem;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBoxItem = itemView.findViewById(R.id.checkBoxItem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_with_checkbox_only, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        boolean isChecked = false;
        String strUsuario = arrayListUsuario.get(position).split("-")[1];
        final String strCodigoUser = arrayListUsuario.get(position).split("-")[0];

        for(int i = 0; i < arrayListUsuarioSelect.size(); i++){
            if(strCodigoUser.equals(arrayListUsuarioSelect.get(i))){
                isChecked = true;
                break;
            }
        }
        holder.checkBoxItem.setText(strUsuario);
        holder.checkBoxItem.setChecked(isChecked);
        holder.checkBoxItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    boolean isAdd = true;
                    for (String strElement: arrayListUsuarioSelect) {
                        if(strElement.equals(strCodigoUser)) {
                            isAdd = false;
                            break;
                        }
                    }
                    if(isAdd){
                        arrayListUsuarioSelect.add(strCodigoUser);
                    }
                }else{
                    for(int i = 0; i < arrayListUsuarioSelect.size(); i++){
                        if(strCodigoUser.equals(arrayListUsuarioSelect.get(i))){
                            arrayListUsuarioSelect.remove(i);
                            break;
                        }
                    }
                }
            }
        });
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

    public void selectAll(boolean bolValue){
        arrayListUsuarioSelect = new ArrayList<>();

        if(bolValue){
            for (String strElement: arrayListUsuario) {
                arrayListUsuarioSelect.add(strElement.split("-")[0]);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<String> getArrayListSelected(){
        return arrayListUsuarioSelect;
    }
}
