package com.northerly.myfragmentsapp.Model.RoomDB;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @NonNull
    void insert(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Update
    void updateUser(User user);
}
