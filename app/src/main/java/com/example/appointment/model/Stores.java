package com.example.appointment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Stores implements Serializable {

    @SerializedName("stores")
    private ArrayList<StoresDetail> storesDetails;
    @SerializedName("store_and_their_employees")
    private StoresDetail storesDetailsWithEmployees;

    public ArrayList<StoresDetail> getStoresDetails() {
        return storesDetails;
    }

    public void setStoresDetails(ArrayList<StoresDetail> storesDetails) {
        this.storesDetails = storesDetails;
    }

    public StoresDetail getStoresDetailsWithEmployees() {
        return storesDetailsWithEmployees;
    }

    public void setStoresDetailsWithEmployees(StoresDetail storesDetailsWithEmployees) {
        this.storesDetailsWithEmployees = storesDetailsWithEmployees;
    }
}
