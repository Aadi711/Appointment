package com.example.appointment.model;

import com.google.gson.annotations.SerializedName;

public class Time {
    @SerializedName("time")
    String time;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
