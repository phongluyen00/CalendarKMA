package com.example.retrofitrxjava.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Created by luyenphong on 9/29/2020.
 * https://www.facebook.com/phong.luyen.96/
 * luyenphong00@gmail.com
 */
@Getter
public class TuitionResponse {
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

        @SerializedName("soTaiKhoanNgannHang")
        @Expose
        private String soTaiKhoanNgannHang;
        @SerializedName("phaiDong")
        @Expose
        private String phaiDong;
        @SerializedName("daDong")
        @Expose
        private String daDong;
        @SerializedName("no")
        @Expose
        private String no;
    }
}
