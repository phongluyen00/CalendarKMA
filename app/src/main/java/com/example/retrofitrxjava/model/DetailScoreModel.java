package com.example.retrofitrxjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class DetailScoreModel extends ModelResponse{
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data extends ModelResponse{

        @SerializedName("maHocPhan")
        @Expose
        private String maHocPhan;
        @SerializedName("tenHocPhan")
        @Expose
        private String tenHocPhan;

        @SerializedName("danhGia")
        @Expose
        private String danhGia;

        @SerializedName("tkhp2")
        @Expose
        private String tkhp2;

    }
}
