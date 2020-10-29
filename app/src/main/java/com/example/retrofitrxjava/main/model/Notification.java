package com.example.retrofitrxjava.main.model;

import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by luyenphong on 10/17/2020.
 * 0358844343
 * luyenphong00@gmail.com
 */
@Getter
@AllArgsConstructor
public class Notification extends ModelResponse {
    @SerializedName("data")
    private List<Data> data;

    @Getter
    public class Data extends ModelResponse {
        @SerializedName("tieuDe")
        @Expose
        private String tieuDe;
        @SerializedName("noiDung")
        @Expose
        private String noiDung;
        @SerializedName("link")
        @Expose
        private String link;
    }
}
