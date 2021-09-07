package com.example.appointment.model;

import com.google.gson.annotations.SerializedName;

public class Appointment {

    @SerializedName("name")
    private String name;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("time")
    private String time;
    @SerializedName("date")
    private String date;
    @SerializedName("status")
    private String status;
    @SerializedName("description")
    private String description;
    @SerializedName("user_image")
    private int userImage;
    @SerializedName("user_name")
    private String userName;
    private boolean isSelected = false;

    public Appointment(String time,String status) {
    this.time = time;
    this.status = status;
    }

    public Appointment(String name, String startTime, String endTime, String date, String status,
                       String description, int userImage, String userName) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.status = status;
        this.description = description;
        this.userImage = userImage;
        this.userName = userName;
    }

    public Appointment(String name, String startTime, String endTime, String status,
                       String description, int userImage, String userName) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.description = description;
        this.userImage = userImage;
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
