package com.map_movil.map_movil.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.R;

import java.util.ArrayList;

public class AdapterItemMunicipioRecyclerView extends RecyclerView.Adapter<AdapterItemMunicipioRecyclerView.ViewHolder> {
    private ArrayList<String> arrayListMunicipio;
    private ArrayList<String> arrayListMunicipiosSelect = new ArrayList<>();

    public AdapterItemMunicipioRecyclerView(ArrayList<String> arrayListMunicipio){
        this.arrayListMunicipio = arrayListMunicipio;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBoxMunicipio;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBoxMunicipio = itemView.findViewById(R.id.checkBoxItem);
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
        String stringMunicipio = arrayListMunicipio.get(position);
        boolean isChecked = false;
        holder.checkBoxMunicipio.setText(stringMunicipio);

        for(int i = 0; i < arrayListMunicipiosSelect.size(); i++){
            if(stringMunicipio.equals(arrayListMunicipiosSelect.get(i))){
                isChecked = true;
                break;
            }
        }

        holder.checkBoxMunicipio.setChecked(isChecked);
        holder.checkBoxMunicipio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String strCodMunicipio = buttonView.getText().toString();
                if(isChecked){
                    boolean isAdd = true;
                    for (String strElement: arrayListMunicipiosSelect) {
                        if(strElement.equals(strCodMunicipio)) {
                            isAdd = false;
                            break;
                        }
                    }
                    if(isAdd){
                        arrayListMunicipiosSelect.add(strCodMunicipio);
                    }
                }else{
                    for(int i = 0; i < arrayListMunicipiosSelect.size(); i++){
                        if(strCodMunicipio.equals(arrayListMunicipiosSelect.get(i))){
                            arrayListMunicipiosSelect.remove(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListMunicipio.size();
    }

    public void adapterDataChange(ArrayList<String> arrayListMunicipio){
        this.arrayListMunicipio = arrayListMunicipio;
        arrayListMunicipiosSelect = new ArrayList<>();
        notifyDataSetChanged();
    }

    public ArrayList<String> getArrayListSelected(){
        return arrayListMunicipiosSelect;
    }

    public JsonArray getJsonArraySelected(){
        //JsonArray jsonArrayFinal = new JsonArray();
        JsonArray jsonArrayMunicipios = new JsonArray();
        //JsonObject jsonObjectData = new JsonObject();
        //String strMuniSelectForSharePreferen = new String();
        int intCounter = 0;

        for(String strElement: arrayListMunicipiosSelect){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("municipio", strElement.split("-")[0]);
            jsonArrayMunicipios.add(jsonObject);
            //strMuniSelectForSharePreferen += (arrayListMunicipiosSelect.size() == intCounter + 1)? strElement : strElement + ",";
            intCounter ++;
        }

        //jsonObjectData.add("municipios", jsonArrayMunicipios);
        //jsonArrayFinal.add(jsonObjectData);

        return jsonArrayMunicipios;
    }
}
