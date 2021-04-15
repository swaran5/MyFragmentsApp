package com.northerly.myfragmentsapp.Model.RoomDB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @NonNull
    private String phone;

    @NonNull
    private String brand;

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String phone, @NonNull String brand) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.brand = brand;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    @NonNull
    public String getBrand() {
        return brand;
    }
}
