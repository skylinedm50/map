package com.map_movil.map_movil.presenter.reportes;

import com.map_movil.map_movil.model.Rendimiento;

import java.util.ArrayList;

public interface ReportesPresenter {
    void getRendimiento();
    void showRendimiento(ArrayList<Rendimiento> arrayList);
    void showMessage(String strMessage);
}
