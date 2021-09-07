package com.example.appointment.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookedTimeSlots {

    @SerializedName("time")
    private ArrayList<Time> timeList;

    public ArrayList<Time> getTimeList() {
        return timeList;
    }

    public void setTimeList(ArrayList<Time> timeList) {
        this.timeList = timeList;
    }
}
