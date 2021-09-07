package com.example.appointment.model;

import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("image")
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
