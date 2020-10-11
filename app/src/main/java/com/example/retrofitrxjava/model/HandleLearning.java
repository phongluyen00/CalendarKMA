package com.example.retrofitrxjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class HandleLearning extends ModelResponse{
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    @Getter
    public class Data extends ModelResponse{

        @SerializedName("stt")
        @Expose
        private String stt;
        @SerializedName("mssv")
        @Expose
        private String mssv;
        @SerializedName("hocKy")
        @Expose
        private String hocKy;
        @SerializedName("mucXuLy")
        @Expose
        private String mucXuLy;

    }
}
