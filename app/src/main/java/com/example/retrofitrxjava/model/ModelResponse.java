package com.example.retrofitrxjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class ModelResponse {
    @SerializedName("errorCode")
    @Expose
    private String errorCode;

}
