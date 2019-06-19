package com.map_movil.map_movil.model;

public class Rendimiento {
    private String codigo;
    private String departamento;
    private int resueltas;
    private int ingresadas;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getResueltas() {
        return resueltas;
    }

    public void setResueltas(int resueltas) {
        this.resueltas = resueltas;
    }

    public int getIngresadas() {
        return ingresadas;
    }

    public void setIngresadas(int ingresadas) {
        this.ingresadas = ingresadas;
    }
}
