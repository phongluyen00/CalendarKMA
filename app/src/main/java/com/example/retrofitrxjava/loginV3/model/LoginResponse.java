package com.example.retrofitrxjava.loginV3.model;

import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends ModelResponse {
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
}
