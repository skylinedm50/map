package com.map_movil.map_movil.model;

public class IndicadorBID {
    private String departamento;
    private int ingresada;
    private int resuelta;
    private int noIniciada;
    private int incompleta;
    private int noConforme;
    private int procesando;
    private int resueltaParcialmente;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getIngresada() {
        return ingresada;
    }

    public void setIngresada(int ingresada) {
        this.ingresada = ingresada;
    }

    public int getResuelta() {
        return resuelta;
    }

    public void setResuelta(int resuelta) {
        this.resuelta = resuelta;
    }

    public int getNoIniciada() {
        return noIniciada;
    }

    public void setNoIniciada(int noIniciada) {
        this.noIniciada = noIniciada;
    }

    public int getIncompleta() {
        return incompleta;
    }

    public void setIncompleta(int incompleta) {
        this.incompleta = incompleta;
    }

    public int getNoConforme() {
        return noConforme;
    }

    public void setNoConforme(int noConforme) {
        this.noConforme = noConforme;
    }

    public int getProcesando() {
        return procesando;
    }

    public void setProcesando(int procesando) {
        this.procesando = procesando;
    }

    public int getResueltaParcialmente() {
        return resueltaParcialmente;
    }

    public void setResueltaParcialmente(int resueltaParcialmente) {
        this.resueltaParcialmente = resueltaParcialmente;
    }
}
