package com.example.callforhelp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface PhoneDao {

    @Insert
    void Insert(Phone phone);

    @Query("SELECT * FROM phonenumbers")
    Flowable<Phone> getAllPhones();

    @Query("DELETE FROM PhoneNumbers")
    void delete();

}
