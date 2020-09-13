package com.example.retrofitrxjava.main.model;

import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ScoreMediumResponse extends ModelResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;

    @Getter
    @NoArgsConstructor
    public class Datum extends ModelResponse {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("mssv")
        @Expose
        private String mssv;
        @SerializedName("namHoc")
        @Expose
        private String namHoc;
        @SerializedName("hocKy")
        @Expose
        private String hocKy;
        @SerializedName("value_1")
        @Expose
        private String tbTlH10N1;
        @SerializedName("value_3")
        @Expose
        private String h4N1;
        @SerializedName("value_5")
        @Expose
        private String tcTlN1;
        @SerializedName("value_7")
        @Expose
        private String tbcH10N1;
        @SerializedName("value_9")
        @Expose
        private String tbcH4N1;
        @SerializedName("value_11")
        @Expose
        private String countTC;
    }
}
