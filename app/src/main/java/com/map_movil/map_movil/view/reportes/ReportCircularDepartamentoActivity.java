package com.map_movil.map_movil.view.reportes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.reportes.ApiAdapterReportes;
import com.map_movil.map_movil.api.reportes.ApiServiceReportes;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.EstadosCantidadSolicitudes;
import com.map_movil.map_movil.model.Region;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportCircularDepartamentoActivity extends AppCompatActivity implements UbicacionView {
    private PieChart pieChart;
    private RadioButton rbDepartamento;
    private RadioButton rbRegion;
    private Spinner sp_departamento;
    private Spinner sp_region;
    private UbicacionesPresenter ubicacionesPresenter;
    private ArrayList<PieEntry> yVals;
    private ArrayList<Integer> colores;
    private PieDataSet dataset;
    private ApiServiceReportes service;
    private PieData data;
    private ApiAdapterReportes da;
    private static String Grafico1 = "Gestiones resueltas Departamento/Región Realizadas/No realizadas.";
    private static String Grafico2 = "Gestiones por estado Departamento/Region Por cada estado.";
    private static String Nombre_Grafico;
    private ArrayList<String> departamentos;
    private TextView tvTituloGrafico;
    private LinearLayout lytgrafico;
    private ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_circular_departamento);

        tvTituloGrafico = findViewById(R.id.tvTituloGrafico);
        rbDepartamento = findViewById(R.id.rbDepartamento);
        rbRegion = findViewById(R.id.rbRegion);
        sp_departamento = findViewById(R.id.sp_departamento);
        sp_region = findViewById(R.id.sp_region);
        pieChart = findViewById(R.id.pieChart);
        lytgrafico = findViewById(R.id.lytgrafico);
        progressBar1 = findViewById(R.id.progressBar1);
        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, getApplicationContext());
        getDepartamentos();

        pieChart.getDescription().setText("");
        pieChart.getDescription().setTextSize(14f);
        pieChart.setHoleRadius(40f);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(100);
        pieChart.setUsePercentValues(false);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setVerticalScrollBarEnabled(true);
        pieChart.setScrollbarFadingEnabled(true);
        pieChart.setCenterText("SOLICITUDES");
        pieChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.getLegend().setForm(Legend.LegendForm.CIRCLE);
        pieChart.setExtraTopOffset(-40);//piechart Y axis
        pieChart.getLegend().setYOffset(30);
        pieChart.setDrawSliceText(false);  //Eliminacion labels del piechart
    //    pieChart.getDescription().setYOffset(40);//descripcion Y axis
        da= new ApiAdapterReportes();
        service = da.getClientService();

        Nombre_Grafico = getIntent().getExtras().getString("Nombre_Grafico");

    //    Func_Generar_Grafico();



        service.getRegiones().enqueue(new Callback <ArrayList <Region>>() {
            @Override
            public void onResponse(Call <ArrayList <Region>> call, Response <ArrayList <Region>> response) {
                ArrayList<Region> arreglo = response.body(); //result
                cargarRegiones(arreglo);

            }

            @Override
            public void onFailure(Call <ArrayList <Region>> call, Throwable t) {

            }
        });

      // cargarRegiones(arreglo);


        this.sp_departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                try {
                    OcultarGrafico();
                    i++;
                    tvTituloGrafico.setText("Solicitudes Departamento: " + sp_departamento.getSelectedItem().toString());
                    if (Nombre_Grafico.equals(Grafico1)) {
                        if (rbDepartamento.isChecked()) {

                            Generar_grafico1_por_Departamento(int_to_String(i));

                        }
                    } else if (Nombre_Grafico.equals(Grafico2)) {

                        Generar_grafico2_por_departamento(int_to_String(i));
                    }
                    MostrarGrafico();
                }catch (Exception e){  Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });

        this.sp_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                try{
                OcultarGrafico();
                i++;
                tvTituloGrafico.setText("Solicitudes Región: "+sp_region.getSelectedItem().toString());
                if(Nombre_Grafico.equals(Grafico1)) {

                    Generar_grafico1_por_region(i);

                }else if(Nombre_Grafico.equals(Grafico2)){

                    Generar_grafico2_por_region(i);
                }
                MostrarGrafico();
                }catch (Exception e){  Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });

        rbDepartamento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try{
                    OcultarGrafico();

                if(b){
                    rbRegion.setChecked(false);
                    sp_region.setVisibility(View.GONE);
                    sp_departamento.setVisibility(View.VISIBLE);
                    sp_region.setSelection(0);

                    if(Nombre_Grafico.equals(Grafico1)) {
                        if(rbDepartamento.isChecked()){
                          Generar_grafico1_por_Departamento("01");
                        }
                    }else if(Nombre_Grafico.equals(Grafico2)){
                        Generar_grafico2_por_departamento("01");
                    }
                    tvTituloGrafico.setText("Solicitudes Departamento: "+sp_departamento.getSelectedItem().toString());
                }
                    MostrarGrafico();
                }catch (Exception e){  Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();}

            }
        });

        rbRegion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    OcultarGrafico();
                    if (b) {
                        rbDepartamento.setChecked(false);
                        sp_departamento.setVisibility(View.GONE);
                        sp_region.setVisibility(View.VISIBLE);

                        sp_departamento.setSelection(0);

                        if (Nombre_Grafico.equals(Grafico1)) {
                            if (rbRegion.isChecked()) {
                                Generar_grafico1_por_region(1);
                            }
                        } else if (Nombre_Grafico.equals(Grafico2)) {
                            Generar_grafico2_por_region(1);
                        }
                        tvTituloGrafico.setText("Solicitudes Región: " + sp_region.getSelectedItem().toString());
                    }
                    MostrarGrafico();
                 }catch (Exception e){  Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();}
            }
        });

    }



    private int Func_Cantidad_Segun_Estado(String Estado, ArrayList<EstadosCantidadSolicitudes> ES){

        for(int i = 0; i < ES.size() ; i++){

            if(ES.get(i).getEstado().equals(Estado)){
                return ES.get(i).getCantidad();
            }

        }
        return 0;
    }
    private int Func_Total_Cantidad_No_Realizada(ArrayList<EstadosCantidadSolicitudes> ES){

        int Total=0;

        for(int i = 0 ; i < ES.size() ; i++){
            if(!ES.get(i).getEstado().equals("No Aplicable") && !ES.get(i).getEstado().equals("Resuelta Conforme") && !ES.get(i).getEstado().equals("Resuelta No Conforme")) {
                Total = ES.get(i).getCantidad() + Total;
            }
        }
        return Total;

    }
    private int Func_Total_Cantidad(ArrayList<EstadosCantidadSolicitudes> arreglo){
        int Total=0;

        for (int i = 0 ; i < arreglo.size() ; i++){
            Total=Total+arreglo.get(i).getCantidad();
        }
        return Total;
    }

    public void Evt_Filtrar(View button){

    }

    @Override
    public void getDepartamentos() {
        this.ubicacionesPresenter.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(ArrayList<String> departamentos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line, departamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.sp_departamento.setAdapter(adapter);
        this.departamentos = departamentos;
    }
    @Override
    public void getMunicipios(String depto) {}

    @Override
    public void cargarMunicipios(ArrayList <String> municipios) {}

    @Override
    public void getAldeas(String muni) {}

    @Override
    public void cargarAldeas(List<Aldeas> aldeas) {}

    @Override
    public void getCaserios(String aldea) {}

    @Override
    public void cargarCaserios(List <Caserios> caserios) {}

    public void cargarRegiones(ArrayList<Region> regiones){
        ArrayList nom_region = new ArrayList();

            for(int i = 0; i< regiones.size(); i++){
                nom_region.add(regiones.get(i).getStrRegion());
            }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line, nom_region);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.sp_region.setAdapter(adapter);

    }

class IntValueFormatter implements IValueFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return ((int) value) + " " ;
    }
}

    public void Generar_grafico1_por_Departamento(String cod_departamento){
        pieChart.animateXY(1500, 1500);
        service.getSolicitudesporDepartamento(cod_departamento).enqueue(new Callback <ArrayList <EstadosCantidadSolicitudes>>() {
            @Override
            public void onResponse(Call <ArrayList <EstadosCantidadSolicitudes>> call, Response <ArrayList <EstadosCantidadSolicitudes>> response) {
                ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result


                yVals = new ArrayList<>();

                int CantidadRealizada= Func_Cantidad_Segun_Estado("Resuelta Conforme",arreglo);
                int CantidadNoRealizada= Func_Total_Cantidad_No_Realizada(arreglo);
                if(CantidadNoRealizada != 0){yVals.add(new PieEntry(CantidadNoRealizada, "NO REALIZADAS"));}
                if(CantidadRealizada != 0){yVals.add(new PieEntry(CantidadRealizada, "REALIZADAS"));}

                pieChart.getDescription().setText("TOTAL SOLICITUDES: "+ Func_Total_Cantidad(arreglo));

                colores = new ArrayList<>();
                colores.add(ColorTemplate.VORDIPLOM_COLORS[4]);
                colores.add(ColorTemplate.VORDIPLOM_COLORS[3]);

                dataset = new PieDataSet(yVals,"");
                dataset.setSliceSpace(3f);
                dataset.setColors(colores);
                dataset.setValueTextSize(15f);

                dataset.setValueFormatter(new IntValueFormatter());

                data = new PieData(dataset);

                pieChart.setData(data);
                pieChart.highlightValues(null);
                pieChart.invalidate();
            }

            @Override
            public void onFailure(Call <ArrayList <EstadosCantidadSolicitudes>> call, Throwable t) {

            }
        });
    }

    public void Generar_grafico1_por_region(int cod_region){
        pieChart.animateXY(1500, 1500);

        service.getSolicitudesporRegion(cod_region).enqueue(new Callback <ArrayList <EstadosCantidadSolicitudes>>() {
            @Override
            public void onResponse(Call <ArrayList <EstadosCantidadSolicitudes>> call, Response <ArrayList <EstadosCantidadSolicitudes>> response) {

                ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result


                yVals = new ArrayList<>();

                int CantidadRealizada= Func_Cantidad_Segun_Estado("Resuelta Conforme",arreglo);
                int CantidadNoRealizada= Func_Total_Cantidad_No_Realizada(arreglo);
                if(CantidadNoRealizada != 0){yVals.add(new PieEntry(CantidadNoRealizada, "NO REALIZADAS"));}
                if(CantidadRealizada != 0){yVals.add(new PieEntry(CantidadRealizada, "REALIZADAS"));}

                pieChart.getDescription().setText("TOTAL SOLICITUDES: "+ Func_Total_Cantidad(arreglo));

                colores = new ArrayList<>();
                colores.add(ColorTemplate.VORDIPLOM_COLORS[4]);
                colores.add(ColorTemplate.VORDIPLOM_COLORS[3]);

                dataset = new PieDataSet(yVals,"");
                dataset.setSliceSpace(3f);
                dataset.setColors(colores);
                dataset.setValueTextSize(15f);

                dataset.setValueFormatter(new IntValueFormatter());

                data = new PieData(dataset);

                pieChart.setData(data);
                pieChart.highlightValues(null);
                pieChart.invalidate();
            }

            @Override
            public void onFailure(Call <ArrayList <EstadosCantidadSolicitudes>> call, Throwable t) {

            }
        });

    }

    public void Generar_grafico2_por_departamento(String cod_departamento){
        pieChart.animateXY(1500, 1500);

        service.getSolicitudesporDepartamento(cod_departamento).enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
            @Override
            public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                tvTituloGrafico.setText("Solicitudes Departamento: "+sp_departamento.getSelectedItem().toString());
                ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result

                yVals = new ArrayList<>();


                if(Func_Cantidad_Segun_Estado("Ingresada", arreglo)!=0) {
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Ingresada", arreglo), "INGRESADA"));
                }
                if(Func_Cantidad_Segun_Estado("Incompleta", arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Incompleta", arreglo), "INCOMPLETA"));
                }
                if(Func_Cantidad_Segun_Estado("No Conforme", arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("No Conforme", arreglo), "NO COMFORME"));
                }
                if(Func_Cantidad_Segun_Estado("No Aplicable", arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("No Aplicable", arreglo), "NO APLICABLE"));
                }
                if(Func_Cantidad_Segun_Estado("Procesando",arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Procesando",arreglo), "PROCESANDO"));
                }
                if(Func_Cantidad_Segun_Estado("Resuelta Conforme",arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Resuelta Conforme",arreglo), "RESUELTA CONFORME"));
                }
                if(Func_Cantidad_Segun_Estado("Resuelta No Conforme",arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Resuelta No Conforme",arreglo), "RESUELTA NO CONFORME"));
                }
                if(Func_Cantidad_Segun_Estado("Resuelta Parcialmente",arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Resuelta Parcialmente",arreglo), "RESUELTA PARCIALMENTE"));
                }

                pieChart.getDescription().setText("TOTAL SOLICITUDES: "+ Func_Total_Cantidad(arreglo));
                colores = new ArrayList<>();

                dataset = new PieDataSet(yVals, null);
                dataset.setSliceSpace(3f);

                dataset.setColors(new int[] {Color.rgb(102, 255, 204)
                        ,Color.rgb(255, 255, 153)
                        ,Color.rgb(128, 179, 255)
                        ,Color.rgb(92, 214, 92) //Verde
                        ,Color.rgb(255, 153, 51)});

                dataset.setValueTextSize(15f);
                dataset.setValueFormatter(new IntValueFormatter());

                data = new PieData(dataset);
                pieChart.setData(data);
                pieChart.highlightValues(null);
                pieChart.invalidate();
            }

            @Override
            public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable XXX) {

            }

        });
    }

    public void Generar_grafico2_por_region(int cod_region){
        pieChart.animateXY(1500, 1500);

        service.getSolicitudesporRegion(cod_region).enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
            @Override
            public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {

                ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result

                yVals = new ArrayList<>();


                if(Func_Cantidad_Segun_Estado("Ingresada", arreglo)!=0) {
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Ingresada", arreglo), "INGRESADA"));
                }
                if(Func_Cantidad_Segun_Estado("Incompleta", arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Incompleta", arreglo), "INCOMPLETA"));
                }
                if(Func_Cantidad_Segun_Estado("No Conforme", arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("No Conforme", arreglo), "NO COMFORME"));
                }
                if(Func_Cantidad_Segun_Estado("No Aplicable", arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("No Aplicable", arreglo), "NO APLICABLE"));
                }
                if(Func_Cantidad_Segun_Estado("Procesando",arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Procesando",arreglo), "PROCESANDO"));
                }
                if(Func_Cantidad_Segun_Estado("Resuelta Conforme",arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Resuelta Conforme",arreglo), "RESUELTA CONFORME"));
                }
                if(Func_Cantidad_Segun_Estado("Resuelta No Conforme",arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Resuelta No Conforme",arreglo), "RESUELTA NO CONFORME"));
                }
                if(Func_Cantidad_Segun_Estado("Resuelta Parcialmente",arreglo)!=0){
                    yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Resuelta Parcialmente",arreglo), "RESUELTA PARCIALMENTE"));
                }

                pieChart.getDescription().setText("TOTAL SOLICITUDES: "+ Func_Total_Cantidad(arreglo));
                colores = new ArrayList<>();

                dataset = new PieDataSet(yVals, null);
                dataset.setSliceSpace(3f);

                dataset.setColors(new int[] {Color.rgb(102, 255, 204)
                        ,Color.rgb(255, 255, 153)
                        ,Color.rgb(128, 179, 255)
                        ,Color.rgb(92, 214, 92) //Verde
                        ,Color.rgb(255, 153, 51),ColorTemplate.MATERIAL_COLORS[2],ColorTemplate.VORDIPLOM_COLORS[2],ColorTemplate.VORDIPLOM_COLORS[0]});

                dataset.setValueTextSize(15f);
                dataset.setValueFormatter(new IntValueFormatter());

                data = new PieData(dataset);
                pieChart.setData(data);
                pieChart.highlightValues(null);
                pieChart.invalidate();
            }

            @Override
            public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable XXX) {

            }

        });
    }

    private String int_to_String(int i){
        if (i==1){
            return "01";
        }else if (i==2){
            return "02";
        }else if (i==3){
            return "03";
        }else if (i==4){
            return "04";
        }else if (i==5){
            return "05";
        }else if (i==6){
            return "06";
        }else if (i==7){
            return "07";
        }else if (i==8){
            return "08";
        }else if (i==9){
            return "09";
        }else{return i+"";}
    }

    public void MostrarGrafico(){
        lytgrafico.setVisibility(View.VISIBLE);
        progressBar1.setVisibility(View.GONE);
    }
    public void OcultarGrafico(){
        lytgrafico.setVisibility(View.GONE);
        progressBar1.setVisibility(View.VISIBLE);
    }

    public void ExportarPDF(View button){
        try {
       //     Toast.makeText(this, "Abriendo PDF...", Toast.LENGTH_LONG).show();

            Document documento = new Document();

            Calendar cal = new GregorianCalendar();
            Date date = cal.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            File archivo = new File(getExternalFilesDir(""), tvTituloGrafico.getText()+".pdf");
            FileOutputStream ficheroPdf = new FileOutputStream(archivo);
            documento.setPageSize(PageSize.LETTER);

            PdfWriter.getInstance(documento, ficheroPdf);
            Font boldFont = new Font(Font.FontFamily.UNDEFINED, 17, Font.BOLD);
            documento.open();
            documento.add(new Paragraph("GRÁFICO: "+ tvTituloGrafico.getText(),boldFont));
            documento.add(new Paragraph("FECHA: "+ df.format(date),boldFont));

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            Bitmap bitmap = Bitmap.createScaledBitmap(pieChart.getChartBitmap(), 500, 500, true);


            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image myImg = Image.getInstance(stream.toByteArray());

            myImg.setAlignment(Image.ALIGN_CENTER);
            documento.add(myImg);
            documento.close();


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(archivo), "application/pdf");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }catch (Exception e) {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}