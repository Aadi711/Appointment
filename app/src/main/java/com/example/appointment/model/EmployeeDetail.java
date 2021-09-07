package com.example.appointment.model;

import com.google.gson.annotations.SerializedName;

public class EmployeeDetail {

    @SerializedName("status")
    private String status;

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;

    @SerializedName("image")
    private String image;
    @SerializedName("phone")
    private String phone;
    @SerializedName("day")
    private String day;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("time_gap")
    private String timeGap;
    @SerializedName("booked_time_slots")
    private BookedTimeSlots bookedTimeSlots;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public String getTimeGap() {
        return timeGap;
    }

    public void setTimeGap(String timeGap) {
        this.timeGap = timeGap;
    }

    public BookedTimeSlots getBookedTimeSlots() {
        return bookedTimeSlots;
    }

    public void setBookedTimeSlots(BookedTimeSlots bookedTimeSlots) {
        this.bookedTimeSlots = bookedTimeSlots;
    }
}
