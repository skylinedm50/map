package com.map_movil.map_movil.model;

public class Region {
    private int intCodRegion;
    private String strRegion;

    public Region(){}

    public Region(int intCodRegion, String strRegion) {
        this.intCodRegion = intCodRegion;
        this.strRegion = strRegion;
    }

    public int getIntCodRegion() {
        return intCodRegion;
    }

    public void setIntCodRegion(int intCodRegion) {
        this.intCodRegion = intCodRegion;
    }

    public void setStrRegion(String strRegion) {
        this.strRegion = strRegion;
    }

    public String getStrRegion() {
        return strRegion;
    }
}
