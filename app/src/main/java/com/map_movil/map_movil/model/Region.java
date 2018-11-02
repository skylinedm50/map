package com.map_movil.map_movil.model;

public class Region {
        private int intCodRegion;
        private String strRegion;

    public Region(int intCodRegion, String strRegion) {
        this.intCodRegion = intCodRegion;
        this.strRegion = strRegion;
    }

    public Region() {
    }

    public int getIntCodRegion() {
        return intCodRegion;
    }

    public void setIntCodRegion(int intCodRegion) {
        this.intCodRegion = intCodRegion;
    }

    public String getStrRegion() {
        return strRegion;
    }

    public void setStrRegion(String strRegion) {
        this.strRegion = strRegion;
    }
}
