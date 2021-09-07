package com.example.appointment.model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("status")
    public boolean success;

    @SerializedName("message")
    public String foundMessage;
    @SerializedName("Message")
    public String foundMessageCapital;

    public boolean logout;

    public Object data;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFoundMessage() {
        return foundMessage;
    }

    public void setFoundMessage(String foundMessage) {
        this.foundMessage = foundMessage;
    }

    public String getFoundMessageCapital() {
        return foundMessageCapital;
    }

    public void setFoundMessageCapital(String foundMessageCapital) {
        this.foundMessageCapital = foundMessageCapital;
    }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
