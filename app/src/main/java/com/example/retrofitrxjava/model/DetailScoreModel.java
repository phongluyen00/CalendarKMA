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

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("mssv")
        @Expose
        private String mssv;
        @SerializedName("STT")
        @Expose
        private String sTT;
        @SerializedName("maHocPhan")
        @Expose
        private String maHocPhan;
        @SerializedName("tenHocPhan")
        @Expose
        private String tenHocPhan;
        @SerializedName("soTC")
        @Expose
        private String soTC;
        @SerializedName("lanHoc")
        @Expose
        private String lanHoc;
        @SerializedName("lanThi")
        @Expose
        private String lanThi;
        @SerializedName("diemThu")
        @Expose
        private String diemThu;
        @SerializedName("laDiemTongKetMon")
        @Expose
        private String laDiemTongKetMon;
        @SerializedName("danhGia")
        @Expose
        private String danhGia;
        @SerializedName("tp1")
        @Expose
        private String tp1;
        @SerializedName("tp2")
        @Expose
        private String tp2;
        @SerializedName("thi")
        @Expose
        private String thi;
        @SerializedName("tkhp")
        @Expose
        private String tkhp;
        @SerializedName("tkhp2")
        @Expose
        private String tkhp2;

        public Data(String maHocPhan, String tenHocPhan, String tkhp2) {
            this.maHocPhan = maHocPhan;
            this.tenHocPhan = tenHocPhan;
            this.tkhp2 = tkhp2;
        }
    }
}
