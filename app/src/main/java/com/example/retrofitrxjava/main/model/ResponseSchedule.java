package com.example.retrofitrxjava.main.model;

import androidx.room.Entity;

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
@Entity(tableName = "responseSchedule")
public class ResponseSchedule {
    @SerializedName("schedules")
    @Expose
    private List<Schedule> schedules = null;
    @SerializedName("success")
    @Expose
    private boolean success;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Schedule {
        @SerializedName("id")
        @Expose
        private Object id;
        @SerializedName("termCode")
        @Expose
        private String courseCode;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("subjectName")
        @Expose
        private String subjectName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("schoolShift")
        @Expose
        private String schoolShift;
        @SerializedName("calendarDays")
        @Expose
        private String calendarDays;
        @SerializedName("teacher")
        @Expose
        private String teacher;
    }
}
