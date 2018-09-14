package com.map_movil.map_movil.view.reportes;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.map_movil.map_movil.api.reportes.ApiAdapterReportes;
import com.map_movil.map_movil.api.reportes.ApiServiceReportes;
import com.map_movil.map_movil.model.EstadosCantidadSolicitudes;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class ReportCircularActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener{
    private PieChart pieChart;
    private TextView tvTituloGrafico;
    private ArrayList<PieEntry> yVals;
    private ArrayList<Integer> colores;
    private PieDataSet dataset;
    private PieData data;
    private TextView txtMensaje;
    private LinearLayout lytFiltro_Fecha;
    private Button btnMostrar_Filtro;
    private String Nombre_Grafico;
    private View btnPickDate_Inicio;
    private View btnPickDate_Final;
    private Calendar now;
    private EditText tvFecha_Inicio;
    private EditText tvFecha_Final;
    private DatePickerDialog dpd;
    private String Formato_Fecha = "Todos";
    public String Fecha_Inicial = "2000-01-01";
    private String tmpFecha_Inicio;
    private String tmpFecha_Final;
    private int Codigo_Usuario;
    private String Nombre_Usuario;
    private static String Grafico1 = "Estado de solicitudes Por realizacion";
    private static String Grafico2 = "Estado de solicitudes Por estado";
    private static String Grafico3 = "Mis estados de solicitud Por realizacion";
    private static String Grafico4 = "Mis estados de solicitud Por estado";
    private SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
    private ApiServiceReportes service;
    private ApiAdapterReportes da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_circular);

        lytFiltro_Fecha = findViewById(R.id.lytFiltro_Fecha);
        btnMostrar_Filtro = findViewById(R.id.btnMostrar_Filtro);
        txtMensaje = findViewById(R.id.txtMensaje);
        btnPickDate_Inicio = findViewById(R.id.tvFecha_Inicio);
        btnPickDate_Final = findViewById(R.id.tvFecha_Final);
        tvFecha_Inicio = findViewById(R.id.tvFecha_Inicio);
        tvFecha_Final = findViewById(R.id.tvFecha_Final);
        pieChart = findViewById(R.id.pieChart);
        tvTituloGrafico = findViewById(R.id.tvTituloGrafico);

        tvFecha_Inicio.setText(Formato_Fecha);
        tvFecha_Final.setText(Formato_Fecha);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewCompat.setElevation(findViewById(R.id.appBarVerSolicitud), 8);*/


        Nombre_Grafico = getIntent().getExtras().getString("Nombre_Grafico");
        Codigo_Usuario = getIntent().getExtras().getInt("Codigo_Usuario");
        Nombre_Usuario = getIntent().getExtras().getString("Nombre_Usuario");

        //toolbar.setTitle(Nombre_Grafico);


        //Generación del gráfico

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
        pieChart.getLegend().setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);

        pieChart.setDrawSliceText(false);  //Eliminacion labels del piechart

        da= new ApiAdapterReportes();
        service = da.getClientService();
        now = Calendar.getInstance();

        Func_Generar_Grafico();
        Func_Inicializar_Calendario();


    }




//------------------FUNCIONES DE CALCULOS DE ESTADOS-----------------------


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
            if(!ES.get(i).getEstado().equals("Realizada")) {
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
    private void Func_Inicializar_Calendario(){
        //Inicialización de las vistas del calendario

        btnPickDate_Inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dpd = DatePickerDialog.newInstance(
                        ReportCircularActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.dismissOnPause(true);
                dpd.showYearPickerFirst(false);
                dpd.setTitle("Seleccione la fecha inicial");
                dpd.show(getFragmentManager(), "FechaInicio");
            }


        });

        btnPickDate_Final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datepickerdialog = DatePickerDialog.newInstance(
                        ReportCircularActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                datepickerdialog.setVersion(DatePickerDialog.Version.VERSION_2);

                datepickerdialog.dismissOnPause(true);
                datepickerdialog.showYearPickerFirst(false);
                datepickerdialog.setTitle("Seleccione la fecha final");
                datepickerdialog.show(getFragmentManager(), "FechaFinal");
            }
        });
    }
    private void Func_Generar_Grafico(){

        if(Nombre_Grafico.equals(Grafico1)) {


            service.getCifrasCantidadDatos().enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                @Override
                public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                    tvTituloGrafico.setText("Estados de todas las Solicitudes");
                    txtMensaje.setText("");
                    ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result


                    yVals = new ArrayList<>();

                    int CantidadRealizada= Func_Cantidad_Segun_Estado("Realizada",arreglo);
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
                public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable tr) {

                    txtMensaje.setText("ERROR DE CONEXIÓN");
                }
            });
        }

        if(Nombre_Grafico.equals(Grafico2)) {
            service.getCifrasCantidadDatos().enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                @Override
                public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                    txtMensaje.setText("");
                    tvTituloGrafico.setText("Estados de todas las Solicitudes");
                    ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result

                    yVals = new ArrayList<>();


                    if(Func_Cantidad_Segun_Estado("Denegada", arreglo)!=0) {
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Denegada", arreglo), "DENEGADAS"));
                    }
                    if(Func_Cantidad_Segun_Estado("En proceso", arreglo)!=0){
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("En proceso", arreglo), "EN PROCESO"));
                    }
                    if(Func_Cantidad_Segun_Estado("Incompleta", arreglo)!=0){
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Incompleta", arreglo), "INCOMPLETAS"));
                    }
                    if(Func_Cantidad_Segun_Estado("Ingresada", arreglo)!=0){
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Ingresada", arreglo), "INGRESADAS"));
                    }
                    if(Func_Cantidad_Segun_Estado("Realizada",arreglo)!=0){
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Realizada",arreglo), "REALIZADAS"));
                    }

                    pieChart.getDescription().setText("TOTAL SOLICITUDES: " + Func_Total_Cantidad(arreglo));
                    colores = new ArrayList<>();

                    colores.add(ColorTemplate.VORDIPLOM_COLORS[0]);
                    colores.add(ColorTemplate.VORDIPLOM_COLORS[1]);
                    colores.add(ColorTemplate.VORDIPLOM_COLORS[2]);
                    colores.add(ColorTemplate.VORDIPLOM_COLORS[4]);
                    colores.add(ColorTemplate.VORDIPLOM_COLORS[3]);

                    dataset = new PieDataSet(yVals, null);
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
                public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable XXX) {
                    txtMensaje = findViewById(R.id.txtMensaje);
                    txtMensaje.setText("ERROR DE CONEXIÓN");
                }

            });
        }
        if(Nombre_Grafico.equals(Grafico3)) {


            service.getCantidadEstadosPorUsuario(Codigo_Usuario,Fecha_Inicial,Func_Obtener_Fecha_Actual()).enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                @Override
                public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                    txtMensaje.setText("");
                    tvTituloGrafico.setText("Estados de Solicitud de "+Nombre_Usuario);
                    ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result


                    yVals = new ArrayList<>();

                    int CantidadRealizada= Func_Cantidad_Segun_Estado("Realizada",arreglo);
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
                public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable XXX) {

                    txtMensaje.setText("ERROR DE CONEXIÓN");
                }
            });
        }

        if(Nombre_Grafico.equals(Grafico4)) {

            service.getCantidadEstadosPorUsuario(Codigo_Usuario,Fecha_Inicial,Func_Obtener_Fecha_Actual()).enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                @Override
                public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                    txtMensaje.setText("");
                    tvTituloGrafico.setText("Estados de Solicitud de "+Nombre_Usuario);
                    ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result

                    yVals = new ArrayList<>();


                    if(Func_Cantidad_Segun_Estado("Denegada", arreglo)!=0) {
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Denegada", arreglo), "DENEGADAS"));
                    }
                    if(Func_Cantidad_Segun_Estado("En proceso", arreglo)!=0){
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("En proceso", arreglo), "EN PROCESO"));
                    }
                    if(Func_Cantidad_Segun_Estado("Incompleta", arreglo)!=0){
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Incompleta", arreglo), "INCOMPLETAS"));
                    }
                    if(Func_Cantidad_Segun_Estado("Ingresada", arreglo)!=0){
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Ingresada", arreglo), "INGRESADAS"));
                    }
                    if(Func_Cantidad_Segun_Estado("Realizada",arreglo)!=0){
                        yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Realizada",arreglo), "REALIZADAS"));
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
                    txtMensaje = findViewById(R.id.txtMensaje);
                    txtMensaje.setText("ERROR DE CONEXIÓN");
                }

            });
        }
        tmpFecha_Inicio=Formato_Fecha;
        tmpFecha_Final=Formato_Fecha;
    }
    private String Func_Obtener_Fecha_Actual(){
        int anio = now.get(Calendar.YEAR);
        int mes = now.get(Calendar.MONTH);
        int dia = now.get(Calendar.DAY_OF_MONTH);
        String Fecha_Actual;
        String mm;
        String dd;
        mes++;


        if(mes < 10){mm = "0" + mes;}else{mm = mes+"";}
        if(dia < 10){dd  = "0" + dia;} else{dd = dia+"";}

        Fecha_Actual=anio+"-"+mm+"-"+dd;

        return Fecha_Actual;

    }



    //------------------EVENTOS DE ANIMACIÓN------------------------------------




    public void Evt_Mostrar(View button){
        if (lytFiltro_Fecha.getVisibility() == View.GONE)
        {
            Func_Animar(true);
            lytFiltro_Fecha.setVisibility(View.VISIBLE);
            btnMostrar_Filtro.setText("OCULTAR FILTRO");
            btnMostrar_Filtro.setBackgroundColor(Color.WHITE);
            btnMostrar_Filtro.setTextColor(ColorTemplate.rgb("#3f51b5"));
        }else if (lytFiltro_Fecha.getVisibility() == View.VISIBLE)
        {
            Func_Animar(false);
            lytFiltro_Fecha.setVisibility(View.GONE);
            btnMostrar_Filtro.setText("MOSTRAR FILTRO");
            btnMostrar_Filtro.setBackgroundColor(ColorTemplate.rgb("#3f51b5"));
            btnMostrar_Filtro.setTextColor(Color.WHITE);
        }

    }
    public void Evt_Filtrar(View button) throws ParseException {

        final String Inicio = tvFecha_Inicio.getText().toString();
        final String Final = tvFecha_Final.getText().toString();

        if(!((Inicio.equals(Formato_Fecha))||(Final.equals(Formato_Fecha)))){


            final Date dtInicio = Formato.parse(Inicio);
            final Date dtFinal = Formato.parse(Final);

            pieChart.clear();
            pieChart.setHoleRadius(40f);
            pieChart.setRotationEnabled(true);
            pieChart.animateY(1400);
            pieChart.setTransparentCircleAlpha(110);
            pieChart.setTransparentCircleRadius(50f);



            if(Nombre_Grafico.equals(Grafico1)) {


                service.getCifrasCantidadDatosPorFechas(Inicio,Final).enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                    @Override
                    public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                        txtMensaje.setText("");
                        int AnioInicio = 1900;
                        int AnioFinal = 1900;

                        AnioInicio = AnioInicio + dtInicio.getYear();
                        AnioFinal = AnioFinal + dtFinal.getYear();
                        String msj = "Estados de todas las Solicitudes desde "+dtInicio.getDate() +" de "+Func_Obtener_Mes(dtInicio.getMonth()+1)+
                                " del "+AnioInicio+" Hasta "+dtFinal.getDate() +" de "+Func_Obtener_Mes(dtFinal.getMonth()+1)+" del "+AnioFinal;
                        tvTituloGrafico.setText(msj);
                        ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result


                        yVals = new ArrayList<>();

                        int CantidadRealizada= Func_Cantidad_Segun_Estado("Realizada",arreglo);
                        int CantidadNoRealizada= Func_Total_Cantidad_No_Realizada(arreglo);


                        if(CantidadNoRealizada != 0){yVals.add(new PieEntry(CantidadNoRealizada, "NO REALIZADAS"));}
                        if(CantidadRealizada != 0){yVals.add(new PieEntry(CantidadRealizada, "REALIZADAS"));}

                        pieChart.getDescription().setText("TOTAL SOLICITUDES: "+ Func_Total_Cantidad(arreglo));
                        colores = new ArrayList<>();
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[3]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[4]);

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
                    public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable tr) {

                        txtMensaje.setText("ERROR DE CONEXIÓN");
                    }
                });
            }

            if(Nombre_Grafico.equals(Grafico2)) {

                service.getCifrasCantidadDatosPorFechas(Inicio,Final).enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                    @Override
                    public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                        txtMensaje.setText("");
                        int AnioInicio = 1900;
                        int AnioFinal = 1900;

                        AnioInicio = AnioInicio + dtInicio.getYear();
                        AnioFinal = AnioFinal + dtFinal.getYear();

                        String msj = "Estados de todas las Solicitudes desde "+dtInicio.getDate() +" de "+Func_Obtener_Mes(dtInicio.getMonth()+1)+
                                " del "+AnioInicio+" Hasta "+dtFinal.getDate() +" de "+Func_Obtener_Mes(dtFinal.getMonth()+1)+" del "+AnioFinal;
                        tvTituloGrafico.setText(msj);
                        ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result

                        yVals = new ArrayList<>();

                        if(Func_Cantidad_Segun_Estado("Denegada", arreglo)!= 0) {
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Denegada", arreglo), "DENEGADAS"));
                        }
                        if(Func_Cantidad_Segun_Estado("En proceso", arreglo)!= 0){
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("En proceso", arreglo), "EN PROCESO"));
                        }
                        if(Func_Cantidad_Segun_Estado("Incompleta", arreglo)!= 0){
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Incompleta", arreglo), "INCOMPLETAS"));
                        }
                        if(Func_Cantidad_Segun_Estado("Ingresada", arreglo)!= 0){
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Ingresada", arreglo), "INGRESADAS"));
                        }
                        if(Func_Cantidad_Segun_Estado("Realizada",arreglo)!= 0){
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Realizada",arreglo), "REALIZADAS"));
                        }


                        pieChart.getDescription().setText("TOTAL SOLICITUDES: "+ Func_Total_Cantidad(arreglo));

                        colores = new ArrayList<>();

                        colores.add(ColorTemplate.VORDIPLOM_COLORS[0]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[1]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[2]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[3]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[4]);


                        dataset = new PieDataSet(yVals, null);
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
                    public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable tr) {

                        txtMensaje = findViewById(R.id.txtMensaje);
                        txtMensaje.setText("ERROR DE CONEXIÓN");
                    }

                });
            }
            if(Nombre_Grafico.equals(Grafico3)) {


                service.getCantidadEstadosPorUsuario(Codigo_Usuario,Inicio,Final).enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                    @Override
                    public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                        txtMensaje.setText("");
                        int AnioInicio = 1900;
                        int AnioFinal = 1900;
                        AnioInicio = AnioInicio + dtInicio.getYear();
                        AnioFinal = AnioFinal + dtFinal.getYear();
                        String msj = "Estados de las Solicitudes de "+Nombre_Usuario+" desde "+dtInicio.getDate() +" de "+Func_Obtener_Mes(dtInicio.getMonth()+1)+
                                " del "+AnioInicio+" Hasta "+dtFinal.getDate() +" de "+Func_Obtener_Mes(dtFinal.getMonth()+1)+" del "+AnioFinal;

                        tvTituloGrafico.setText(msj);
                        ArrayList<EstadosCantidadSolicitudes> arreglo = response.body();


                        yVals = new ArrayList<>();

                        int CantidadRealizada= Func_Cantidad_Segun_Estado("Realizada",arreglo);
                        int CantidadNoRealizada= Func_Total_Cantidad_No_Realizada(arreglo);

                        if(CantidadNoRealizada != 0){yVals.add(new PieEntry(CantidadNoRealizada, "NO REALIZADAS"));}
                        if(CantidadRealizada != 0){yVals.add(new PieEntry(CantidadRealizada, "REALIZADAS"));}

                        pieChart.getDescription().setText("TOTAL SOLICITUDES: "+ Func_Total_Cantidad(arreglo));
                        colores = new ArrayList<>();
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[3]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[4]);

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
                    public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable tr) {

                        txtMensaje.setText("ERROR DE CONEXIÓN");
                    }
                });
            }

            if(Nombre_Grafico.equals(Grafico4)) {

                service.getCantidadEstadosPorUsuario(Codigo_Usuario,Inicio,Final).enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                    @Override
                    public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                        txtMensaje.setText("");
                        int AnioInicio = 1900;
                        int AnioFinal = 1900;
                        AnioInicio = AnioInicio + dtInicio.getYear();
                        AnioFinal = AnioFinal + dtFinal.getYear();

                        String msj = "Estados de las Solicitudes de "+Nombre_Usuario+" desde "+dtInicio.getDate() +" de "+Func_Obtener_Mes(dtInicio.getMonth()+1)+
                                " del "+AnioInicio+" Hasta "+dtFinal.getDate() +" de "+Func_Obtener_Mes(dtFinal.getMonth()+1)+" del "+AnioFinal;

                        tvTituloGrafico.setText(msj);
                        ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result

                        yVals = new ArrayList<>();

                        if(Func_Cantidad_Segun_Estado("Denegada", arreglo)!= 0) {
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Denegada", arreglo), "DENEGADAS"));
                        }
                        if(Func_Cantidad_Segun_Estado("En proceso", arreglo)!= 0){
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("En proceso", arreglo), "EN PROCESO"));
                        }
                        if(Func_Cantidad_Segun_Estado("Incompleta", arreglo)!= 0){
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Incompleta", arreglo), "INCOMPLETAS"));
                        }
                        if(Func_Cantidad_Segun_Estado("Ingresada", arreglo)!= 0){
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Ingresada", arreglo), "INGRESADAS"));
                        }
                        if(Func_Cantidad_Segun_Estado("Realizada",arreglo)!= 0){
                            yVals.add(new PieEntry(Func_Cantidad_Segun_Estado("Realizada",arreglo), "REALIZADAS"));
                        }


                        pieChart.getDescription().setText("TOTAL SOLICITUDES: "+ Func_Total_Cantidad(arreglo));

                        colores = new ArrayList<>();

                        colores.add(ColorTemplate.VORDIPLOM_COLORS[0]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[1]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[2]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[3]);
                        colores.add(ColorTemplate.VORDIPLOM_COLORS[4]);


                        dataset = new PieDataSet(yVals, null);
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
                    public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable tr) {

                        txtMensaje = findViewById(R.id.txtMensaje);
                        txtMensaje.setText("ERROR DE CONEXIÓN");
                    }

                });
            }

            tmpFecha_Inicio=Inicio;
            tmpFecha_Final=Final;

        }
    }
    public void Evt_Borrar_Filtro(View button){

        tvFecha_Inicio.setText(Formato_Fecha);
        tvFecha_Final.setText(Formato_Fecha);

        pieChart.clear();
        pieChart.setHoleRadius(40f);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500,1500);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setTransparentCircleRadius(50f);



        Func_Generar_Grafico();


    }
    public void Evt_ImprimirPDF(View button){


        try {
            Toast.makeText(this, "Abriendo PDF...", Toast.LENGTH_LONG).show();

            Document documento = new Document();
            File archivo = new File(getExternalFilesDir(""), tvTituloGrafico.getText()+".pdf");
            FileOutputStream ficheroPdf = new FileOutputStream(archivo);
            documento.setPageSize(PageSize.LETTER);

            PdfWriter.getInstance(documento, ficheroPdf);
            Font boldFont = new Font(Font.FontFamily.UNDEFINED, 17, Font.BOLD);
            documento.open();
            documento.add(new Paragraph("GRÁFICO: "+ tvTituloGrafico.getText(),boldFont));
            documento.add(new Paragraph("FECHA INICIO: "+ tmpFecha_Inicio));
            documento.add(new Paragraph("FECHA FINAL: "+ tmpFecha_Final));
            documento.add(new Paragraph("GENERADO POR: "+ Nombre_Usuario));

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
    private void Func_Animar(boolean mostrar){

        AnimationSet set = new AnimationSet(true);
        Animation animation;
        if (mostrar)
        {
            //Desde la esquina inferior derecha a la superior izquierda
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        }
        else
        {    //Desde la esquina superior izquierda a la esquina inferior derecha
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        }
        //Duración en milisegundos
        animation.setDuration(250);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);

        lytFiltro_Fecha.setLayoutAnimation(controller);
        lytFiltro_Fecha.startAnimation(animation);
    }

    private String Func_Obtener_Mes(int mes){
        if (mes==1){
            return "Enero";
        }
        if (mes==2){
            return "Febrero";
        }
        if (mes==3){
            return "Marzo";
        }
        if (mes==4){
            return "Abril";
        }
        if (mes==5){
            return "Mayo";
        }
        if (mes==6){
            return "Junio";
        }
        if (mes==7){
            return "Julio";
        }
        if (mes==8){
            return "Agosto";
        }
        if (mes==9){
            return "Septiembre";
        }
        if (mes==10){
            return "Octubre";
        }
        if (mes==11){
            return "Noviembre";
        }
        if (mes==12){
            return "Diciembre";
        }
        return "";
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String mm;
        String dd;

        if(monthOfYear < 10){mm = "0" + monthOfYear;}else{mm = monthOfYear + "";}
        if(dayOfMonth < 10){dd  = "0" + dayOfMonth;} else{dd = dayOfMonth + "";}


        if(view.getTag().equals("FechaInicio")) {

            tvFecha_Inicio.setText(year + "-" + mm + "-" + dd);
        }
        if(view.getTag().equals("FechaFinal")){
            tvFecha_Final.setText(year + "-" + mm + "-" + dd);
        }
    }

    public class IntValueFormatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return ((int) value) + " " ;
        }
    }
}
