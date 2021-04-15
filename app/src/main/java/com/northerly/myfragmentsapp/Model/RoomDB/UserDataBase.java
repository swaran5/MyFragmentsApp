package com.northerly.myfragmentsapp.Model.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = User.class, version = 1)
public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDao userDao();
    private static volatile UserDataBase userDataInstance;

    public static UserDataBase getDataBase(final Context context){
        if(userDataInstance == null){
            synchronized (UserDataBase.class){
                if(userDataInstance == null) {
                    userDataInstance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDataBase.class, "user_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return userDataInstance;
    }
}

