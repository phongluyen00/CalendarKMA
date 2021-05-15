package com.example.retrofitrxjava.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DAO {
    @Query("Select * from User")
    Single<List<User>> findStudent();

    @Insert(onConflict = REPLACE)
    void insert(User... items);

    @Query("SELECT * FROM User WHERE userId = :userId")
    Flowable<User> getUserByUserId(int userId);
}
