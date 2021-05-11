package com.example.retrofitrxjava.main.model;

import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class ResponseBDCT extends ModelResponse {
    @SerializedName("bangDiemCTS")
    @Expose
    private List<BangDiemCT> bangDiemCTS = null;
    @SerializedName("success")
    @Expose
    private boolean success;

    @Getter
    @AllArgsConstructor
    @Setter
    @NoArgsConstructor
    public static class BangDiemCT {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("creditNumber")
        @Expose
        private String creditNumber;
        @SerializedName("evaluate")
        @Expose
        private String evaluate;
        @SerializedName("point")
        @Expose
        private String point;
    }

}
