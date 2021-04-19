package com.northerly.myfragmentsapp.Model.SQliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table User(firstname TEXT, lastname TEXT, email TEXT, phone TEXT primary key, brand TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists User");
    }

    public boolean insertuserdata(String fname, String lname, String email, String phone, String brand)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname",fname);
        contentValues.put("lastname",lname);
        contentValues.put("email", email);
        contentValues.put("phone",phone);
        contentValues.put("brand",brand);
        long result = db.insert("User", null, contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean updateuserdata(String fname, String lname, String email, String phone, String brand)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname",fname);
        contentValues.put("lastname",lname);
        contentValues.put("email", email);
        contentValues.put("brand",brand);
        Cursor cursor = db.rawQuery("Select * from User where phone = ?",new String[]{phone});
        if (cursor.getCount()>0) {
            long result = db.update("User", contentValues, "phone=?", new String[]{phone});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else {return false;}
    }

    public Cursor getuser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from User", null);
        return cursor;
    }
}
