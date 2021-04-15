package com.northerly.myfragmentsapp.Model.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();
    
}
