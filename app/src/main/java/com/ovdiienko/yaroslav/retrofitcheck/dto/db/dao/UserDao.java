package com.ovdiienko.yaroslav.retrofitcheck.dto.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ovdiienko.yaroslav.retrofitcheck.dto.db.models.User;

import java.util.List;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("select * from user where login = :loginId")
    LiveData<User> loadUser(String loginId);

    @Query("select * from user where login = :loginId")
    User loadUserSync(String loginId);

    @Query("SELECT * FROM user")
    LiveData<List<User>> loadAllUsers();
}
