package com.example.appointment.model;

import com.google.gson.annotations.SerializedName;

public class AppointmentType {

    @SerializedName("name")
    private String name;
    @SerializedName("time")
    private String time;
    @SerializedName("address")
    private String address;
    @SerializedName("image")
    private int image;


    public AppointmentType(String name, String time, String address, int image) {
        this.name = name;
        this.time = time;
        this.address = address;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
