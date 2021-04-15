package com.northerly.myfragmentsapp.Model.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);
}
