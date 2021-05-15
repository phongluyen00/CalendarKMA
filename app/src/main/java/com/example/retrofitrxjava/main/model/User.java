package com.example.retrofitrxjava.main.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "User")
public class User {

    @PrimaryKey(autoGenerate = true)
    private long userId;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "point")
    private String point;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "classRoom")
    private String classRoom;

    @ColumnInfo(name = "faculty")
    private String faculty;

    @ColumnInfo(name = "message")
    private String message;

    public User(String password, String point, String name, String classRoom, String faculty) {
        this.password = password;
        this.point = point;
        this.name = name;
        this.classRoom = classRoom;
        this.faculty = faculty;
        this.message = message;
    }
}
