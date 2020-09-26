package com.example.retrofitrxjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class CertificateResponse extends ModelResponse {
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Getter
    public class Data {

        @SerializedName("STT")
        @Expose
        private String sTT;
        @SerializedName("chungChi1")
        @Expose
        private String chungChi1;
        @SerializedName("diem")
        @Expose
        private String diem;
        @SerializedName("xepLoai")
        @Expose
        private String xepLoai;

    }

}
