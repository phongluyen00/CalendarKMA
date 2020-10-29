package com.example.retrofitrxjava.loginV3.model;


import com.example.retrofitrxjava.common.model.ScheduleModelResponse;
import com.example.retrofitrxjava.main.model.Notification;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.model.Article;
import com.example.retrofitrxjava.model.DetailScoreModel;
import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginResponse extends ModelResponse {
    @SerializedName("data")
    @Expose
    private Data data;

    public LoginResponse() {
    }

    @Getter
    @Setter
    public static class Data  {

        @SerializedName("MSSV")
        @Expose
        private String id;
        @SerializedName("hoTen")
        @Expose
        private String name;
        @SerializedName("khoa")
        @Expose
        private String khoa;
        @SerializedName("lop")
        @Expose
        private String classRoom;
        @SerializedName("trangThaiHocTap")
        @Expose
        private String status;
        private String token;
        private String password;
        private String mediumScore;
        private ScheduleModelResponse modelResponse;
        private ScheduleModelResponse.Data modelDataResponse;
        private DetailScoreModel detailScoreModel;
        private ScoreMediumResponse scoreMediumResponse;
        private File avata;
        private Notification notification;
        private ArrayList<Article> articleListTD;

    }

}
