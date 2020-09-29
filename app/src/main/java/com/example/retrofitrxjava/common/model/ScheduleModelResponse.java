package com.example.retrofitrxjava.common.model;

import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class ScheduleModelResponse extends ModelResponse {
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    @Getter
    public class Data extends ModelResponse{

        @SerializedName("mssv")
        @Expose
        private String token;
        @SerializedName("monHoc")
        @Expose
        private String monHoc;
        @SerializedName("lopHocPhan")
        @Expose
        private String title;
        @SerializedName("maHocPhan")
        @Expose
        private String maHocPhan;
        @SerializedName("caHoc")
        @Expose
        private String caHoc;
        @SerializedName("ngayHoc")
        @Expose
        private String datetime;
        @SerializedName("diaDiem")
        @Expose
        private String location;
        @SerializedName("giaoVien")
        @Expose
        private String teacher;

    }
}
