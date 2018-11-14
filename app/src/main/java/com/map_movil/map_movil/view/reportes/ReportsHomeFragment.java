package com.map_movil.map_movil.view.reportes;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.map_movil.map_movil.R;

import java.util.ArrayList;


public class ReportsHomeFragment extends Fragment {
    private View view;
    private SharedPreferences sharedPreferences;
    private int intCodUser;
    private String strNombreUser;
    private int intCodRolUser;
    private ArrayList<Report> arrayListReport = new ArrayList<>();

    public ReportsHomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reports, container, false);
        sharedPreferences = view.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        intCodUser = sharedPreferences.getInt("codigo", 0);
        strNombreUser = sharedPreferences.getString("nombre", "");
        intCodRolUser = sharedPreferences.getInt("rol", 0);
     //   arrayListReport.add(new Report("Estado de solicitudes", "Por realizacion"));
     //   arrayListReport.add(new Report("Estado de solicitudes", "Por estado"));
        switch (intCodRolUser){
            case 2://Gerenciales
            case 4:
            case 5:
            case 8:
                arrayListReport.add(new Report("Gestiones resueltas Departamento/Región", "Realizadas/No realizadas.",0));
                arrayListReport.add(new Report("Gestiones por estado Departamento/Region", "Por cada estado.",0));
                //arrayListReport.add(new Report("Tiempo de respuesta", "De las solicitudes"));
                break;
            case 1: //Admin
                arrayListReport.add(new Report("Gestiones resueltas Departamento/Región", "Realizadas/No realizadas.",0));
                arrayListReport.add(new Report("Gestiones por estado Departamento/Region", "Por cada estado.",0));
                arrayListReport.add(new Report("Gestiones resueltas", "Realizadas/No realizadas.",1));
                arrayListReport.add(new Report("Gestiones por estado", "Por cada estado.",1));
             //   arrayListReport.add(new Report("Tiempo de respuesta", "De las solicitudes",2));
                arrayListReport.add(new Report("Gestiones", "Por tipo de solicitud",2));
                break;
            default:
                arrayListReport.add(new Report("Gestiones resueltas", "Realizadas/No realizadas.",1));
                arrayListReport.add(new Report("Gestiones por estado", "Por cada estado.",1));
                break;
        }


       /* arrayListReport.add(new Report("Gestiones resueltas", "Realizadas/No realizadas."));
        arrayListReport.add(new Report("Gestiones por estado", "Por cada estado."));

        arrayListReport.add(new Report("Gestiones resueltas Departamento/Región", "Realizadas/No realizadas."));
        arrayListReport.add(new Report("Gestiones por estado Departamento/Region", "Por cada estado."));

        arrayListReport.add(new Report("Tiempo de respuesta", "De las solicitudes"));*/


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewReport);
        AdapterRecyclerReport adapterRecyclerReport = new AdapterRecyclerReport(arrayListReport);
        adapterRecyclerReport.setOnClickListener(new AdapterRecyclerReport.OnItemClickListener() {
            @Override
            public void onitemClick(int position) {
           //     if(intCodRolUser == 2 || intCodRolUser == 4 || intCodRolUser == 5 || intCodRolUser == 8){
                    //}else if(position == 2 || position == 3){
                String Nombre_Grafico = arrayListReport.get(position).getStrNombre()+ " " + arrayListReport.get(position).getStrDescripcion();
                if (arrayListReport.get(position).getTipoGrafico() == 0) {

                    Intent intent = new Intent(view.getContext(), ReportCircularDepartamentoActivity.class);
                    intent.putExtra("Nombre_Grafico", Nombre_Grafico);
                    startActivity(intent);
                    //      }else if(intCodRolUser != 2 || intCodRolUser != 4 || intCodRolUser != 5 || intCodRolUser != 8){
                    //if(position == 0 || position == 1){
                }else if(arrayListReport.get(position).getTipoGrafico() == 1) {

                    Intent intent = new Intent(view.getContext(), ReportCircularActivity.class);
                    intent.putExtra("Nombre_Grafico",Nombre_Grafico);
                    intent.putExtra("Codigo_Usuario", intCodUser);
                    intent.putExtra("Nombre_Usuario", strNombreUser);
                    startActivity(intent);
               // }else if((intCodRolUser == 2 || intCodRolUser == 4 || intCodRolUser == 5 || intCodRolUser == 8) && position == 3123){
               }else if(arrayListReport.get(position).getTipoGrafico() == 2){

                    Intent intent = new Intent(view.getContext(), ReportBarActivity.class);
                    intent.putExtra("Nombre_Grafico",Nombre_Grafico);
                    intent.putExtra("Codigo_Usuario", intCodUser);
                    intent.putExtra("Nombre_Usuario",strNombreUser);

                    startActivity(intent);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapterRecyclerReport);

        return this.view;
    }

    class Report {
        private String strNombre;
        private String strDescripcion;
        private int TipoGrafico;

        //TipoGrafico = 0 para Circular por Departamento
        //TipoGrafico = 1 para Circular
        //TipoGrafico = 2 para Barras

        public Report(String strNombre, String strDescripcion, int TipoGrafico) {
            this.strNombre = strNombre;
            this.strDescripcion = strDescripcion;
            this.TipoGrafico = TipoGrafico;
        }

        public String getStrNombre() {
            return strNombre;
        }

        public void setStrNombre(String strNombre) {
            this.strNombre = strNombre;
        }

        public String getStrDescripcion() {
            return strDescripcion;
        }

        public void setStrDescripcion(String strDescripcion) {
            this.strDescripcion = strDescripcion;
        }

        public int getTipoGrafico() {
            return TipoGrafico;
        }

        public void setTipoGrafico(int TipoGrafico) {
            this.TipoGrafico = TipoGrafico;
        }
    }

    static class AdapterRecyclerReport extends RecyclerView.Adapter<AdapterRecyclerReport.ViewHolder>{
        private ArrayList<Report> arrayListReport;
        private OnItemClickListener onClickListener;

       public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView textViewNombre;
            private TextView textViewDescripcion;

            public ViewHolder(View itemView, OnItemClickListener listener){
                super(itemView);

                this.textViewNombre = itemView.findViewById(R.id.textViewNombre);
                this.textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onClickListener != null){
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION){
                                onClickListener.onitemClick(position);
                            }
                        }
                    }
                });
            }
        }

        public interface OnItemClickListener{
            void onitemClick(int position);
        }

        public void setOnClickListener(OnItemClickListener onClickListener){
            this.onClickListener = onClickListener;
        }

        public AdapterRecyclerReport(ArrayList<Report> arrayListReport){
            this.arrayListReport = arrayListReport;
        }

        @NonNull
        @Override
        public AdapterRecyclerReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);

            return new ViewHolder(view, this.onClickListener);
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterRecyclerReport.ViewHolder holder, int position) {
            Report report = this.arrayListReport.get(position);
            holder.textViewNombre.setText(report.getStrNombre());
            holder.textViewDescripcion.setText(report.getStrDescripcion());
        }

        @Override
        public int getItemCount() {
            return arrayListReport.size();
        }
    }

}
