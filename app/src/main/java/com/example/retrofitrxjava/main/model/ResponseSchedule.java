package com.example.retrofitrxjava.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResponseSchedule {
    @SerializedName("schedules")
    @Expose
    private List<Schedule> schedules = null;
    @SerializedName("success")
    @Expose
    private boolean success;

    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public class Schedule {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("term")
        @Expose
        private String term;
        @SerializedName("teacher")
        @Expose
        private String teacher;
        @SerializedName("lstDetailSchedule")
        @Expose
        private List<LstDetailSchedule> lstDetailSchedule = null;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public class LstDetailSchedule {
        @SerializedName("schoolShift")
        @Expose
        private String schoolShift;
        @SerializedName("day")
        @Expose
        private String day;
    }
}
