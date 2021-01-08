package com.example.retrofitrxjava.loginV3.model;


import com.example.retrofitrxjava.common.model.ScheduleModelResponse;
import com.example.retrofitrxjava.main.model.Notification;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.model.Article;
import com.example.retrofitrxjava.model.DetailScoreModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginResponse{

    @SerializedName("data")
    @Expose
    private Data data;

    public LoginResponse() {
    }

    @Getter
    @Setter
    public class Data {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("hoDem")
        @Expose
        private Object hoDem;
        @SerializedName("ten")
        @Expose
        private String ten;

        @SerializedName("cmtnd")
        @Expose
        private String cmtnd;
        @SerializedName("ngaySinh")
        @Expose
        private String ngaySinh;
        @SerializedName("noiSinh")
        @Expose
        private String noiSinh;
        @SerializedName("sdt")
        @Expose
        private String sdt;
        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("passWord")
        @Expose
        private String passWord;
        @SerializedName("khoa")
        @Expose

        private String token;
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
