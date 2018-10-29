package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class ResponseApi {

    @SerializedName("state")
    private int intState;
    @SerializedName("description")
    private String strDescription;


    public ResponseApi(int intState, String strDescription) {
        this.intState = intState;
        this.strDescription = strDescription;
    }

    public int getIntState() {
        return intState;
    }

    public void setIntState(int intState) {
        this.intState = intState;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }
}
