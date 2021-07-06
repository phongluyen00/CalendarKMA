package com.example.retrofitrxjava.loginV3.model;

import com.example.retrofitrxjava.main.model.ResponseBDCT;
import com.example.retrofitrxjava.main.model.ResponseSchedule;
import com.example.retrofitrxjava.model.ModelResponse;
import com.example.retrofitrxjava.utils.AESHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse extends ModelResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("classRoom")
    @Expose
    private String classRoom;
    @SerializedName("faculty")
    @Expose
    private String faculty;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("permission")
    @Expose
    private int permission;
    @SerializedName("averageTranscript")
    @Expose
    private List<ResponseBDTB> responseBDTBS = null;
    @SerializedName("successFull")
    @Expose
    private Boolean successFull;
    private ResponseSchedule schedule;
    private ResponseBDCT responseBDCT;

    public String getPassword() {
        return AESHelper.decrypt(password,AESHelper.KEY);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseBDTB extends ModelResponse {
        @SerializedName("namHoc")
        @Expose
        private String namHoc;
        @SerializedName("hocKy")
        @Expose
        private String hocKy;
        @SerializedName("tbcHe10N1")
        @Expose
        private String tbcHe10N1;
        @SerializedName("tbcHe4N1")
        @Expose
        private String tbcHe4N1;
    }
}
