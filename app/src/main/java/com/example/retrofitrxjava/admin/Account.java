package com.example.retrofitrxjava.admin;

import com.example.retrofitrxjava.model.ModelResponse;
import com.example.retrofitrxjava.utils.AESHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends ModelResponse {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("classRoom")
    @Expose
    private String classRoom;
    @SerializedName("faculty")
    @Expose
    private String faculty;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("permission")
    @Expose
    private int permission;

    public String getPassword() {
        return AESHelper.decrypt(password,AESHelper.KEY);
    }
}
