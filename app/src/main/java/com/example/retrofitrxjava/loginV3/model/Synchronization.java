package com.example.retrofitrxjava.loginV3.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Synchronization {
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
}
