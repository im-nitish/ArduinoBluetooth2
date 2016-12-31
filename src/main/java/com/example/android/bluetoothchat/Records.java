package com.example.android.bluetoothchat;

/**
 * Created by Nitish on 21-Dec-16.
 */

public class Records {

    private int _id;
    public String value;
    public String DatenTime;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDatenTime() {
        return DatenTime;
    }

    public void setDatenTime(String datenTime) {
        DatenTime = datenTime;
    }
}
