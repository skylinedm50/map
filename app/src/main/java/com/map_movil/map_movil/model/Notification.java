package com.map_movil.map_movil.model;

public class Notification {
    private String strId;
    private String strTitle;
    private String strDescription;
    private String strSomeValues;

    public Notification(String strId, String strTitle, String strDescription, String strSomeValues) {
        this.strId = strId;
        this.strTitle = strTitle;
        this.strDescription = strDescription;
        this.strSomeValues = strSomeValues;
    }

    public Notification() {
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrSomeValues() {
        return strSomeValues;
    }

    public void setStrSomeValues(String strSomeValues) {
        this.strSomeValues = strSomeValues;
    }
}
