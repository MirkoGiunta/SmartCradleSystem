package com.example.smartcradlesystem;

public class SoundsData {
    private String id;
    private String date;
    private String value;

    public SoundsData(String id, String date, String value) {
        this.id = id;
        this.date = date;
        this.value = value;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() { return value; }

    public void setValue(String value) {
        this.value = value;
    }

}
