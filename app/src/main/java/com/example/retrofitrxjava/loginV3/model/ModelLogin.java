package com.example.retrofitrxjava.loginV3.model;

import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by luyenphong on 08/01/2021
 * 0358844343
 */
@Getter
@Setter
public class ModelLogin extends ModelResponse {
    @SerializedName("data")
    @Expose
    private String data;
}
