package com.example.retrofitrxjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class PaymentModel extends ModelResponse {
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    @Getter
    public class Data extends ModelResponse {
        @SerializedName("hocKy")
        @Expose
        private String hocKy;
        @SerializedName("soTC")
        @Expose
        private String soTC;
        @SerializedName("soTien")
        @Expose
        private String soTien;
        @SerializedName("mienGiam")
        @Expose
        private String mienGiam;
        @SerializedName("hocLai")
        @Expose
        private String hocLai;
        @SerializedName("phaiDong")
        @Expose
        private String phaiDong;
    }
}
