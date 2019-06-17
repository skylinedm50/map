package com.map_movil.map_movil.view.desempeno;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.map_movil.map_movil.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DesempenoFragment extends Fragment {

    private View view;
    private HorizontalBarChart horizontalBarChart;
    private ArrayList<BarEntry> yVals;

    public DesempenoFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view = inflater.inflate(R.layout.fragment_desempeno,container,false);
        horizontalBarChart = view.findViewById(R.id.horizontalBarChart);

        horizontalBarChart.animateXY(2000,2000);


        yVals = new ArrayList();


        yVals.add(new BarEntry(0,new float[]{ 5, 15}));
        yVals.add(new BarEntry(1,new float[]{ 10, 10}));
        yVals.add(new BarEntry(2,new float[]{ 20, 10}));
        yVals.add(new BarEntry(3,new float[]{ 40, 10 }));
        yVals.add(new BarEntry(4,new float[]{ 50, 10 }));
        yVals.add(new BarEntry(5,new float[]{ 60, 10 }));
        yVals.add(new BarEntry(6,new float[]{ 35, 35 }));
        yVals.add(new BarEntry(7,new float[]{ 40, 40 }));
        yVals.add(new BarEntry(8,new float[]{ 50, 40 }));
        yVals.add(new BarEntry(9,new float[]{ 60, 40 }));
        yVals.add(new BarEntry(10,new float[]{ 70, 40 }));
        yVals.add(new BarEntry(11,new float[]{ 80, 40 }));
        yVals.add(new BarEntry(12,new float[]{ 90, 40 }));
        yVals.add(new BarEntry(13,new float[]{ 100, 40 }));
        yVals.add(new BarEntry(14,new float[]{ 110, 40 }));
        yVals.add(new BarEntry(15,new float[]{ 120, 40 }));
        yVals.add(new BarEntry(16,new float[]{ 130, 40}));
        BarDataSet dataSet = new BarDataSet(yVals, "");

        BarData data = new BarData(dataSet);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ColorTemplate.MATERIAL_COLORS[3]);
        colors.add(ColorTemplate.MATERIAL_COLORS[0]);



        String[] StackLabels = {"Resueltas","Ingresadas"};

        dataSet.setStackLabels(StackLabels);
        dataSet.setDrawValues(true);
        dataSet.setHighlightEnabled(true);
     //   data.setValueFormatter(new StackedValueFormatter(true,"",0));
        dataSet.setValueFormatter(new MyBarValueFormatter(getContext()));


        dataSet.setColors(colors);

        horizontalBarChart.setData(data);

        String[] values = {"Francisco Morazán", "Yoro", "Intibucá","La Paz","El paraíso","Choluteca","Colón","Valle","Gracias a Dios","Olancho","Comayagua","Atlántida","Santa Bárbara","Copán","Cortés","Ocotepeque","Lempira"};

        horizontalBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(values));
        horizontalBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        horizontalBarChart.getXAxis().setEnabled(true);
        horizontalBarChart.setDrawValueAboveBar(false); //mostrar sobre stackedbar
        horizontalBarChart.getXAxis().setLabelCount(17);
        horizontalBarChart.getXAxis().setTextSize(15f);
        horizontalBarChart.setTouchEnabled(false);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.getLegend().setEnabled(true);
        horizontalBarChart.getLegend().setTextSize(15f);
        horizontalBarChart.getLegend().setFormSize(15f);

        horizontalBarChart.invalidate();

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}

class MyBarValueFormatter implements IValueFormatter {

    private DecimalFormat mFormat;
    private Context context;

    int toggle;
    float totalVal;

    public MyBarValueFormatter(Context context){
        toggle = 0;
        totalVal = 0;
        this.context = context;
        mFormat = new DecimalFormat("###,###,###,##0");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        if(toggle % 2 == 0){
            toggle++;
            totalVal =  value;
            return " "+mFormat.format(totalVal);
        }else{
            toggle++;
            totalVal = totalVal + value;
            return " "+mFormat.format(totalVal);
        }
    }
}


