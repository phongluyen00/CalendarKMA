package com.example.retrofitrxjava.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Update {
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;

}
