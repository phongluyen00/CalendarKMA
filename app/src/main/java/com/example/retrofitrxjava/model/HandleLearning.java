package com.example.retrofitrxjava.model;

import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class HandleLearning extends ModelResponse {
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    @Getter
    public class Data {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("mssv")
        @Expose
        private String mssv;
        @SerializedName("stt")
        @Expose
        private String stt;
        @SerializedName("hocKy")
        @Expose
        private String hocKy;
        @SerializedName("mucXuLy")
        @Expose
        private String mucXuLy;

    }
}
