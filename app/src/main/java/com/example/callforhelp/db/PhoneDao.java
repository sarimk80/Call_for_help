package com.example.callforhelp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface PhoneDao {

    @Insert
    void Insert(Phone phone);

    @Query("SELECT * FROM phonenumbers")
    Flowable<Phone> getAllPhones();

    @Query("UPDATE PhoneNumbers SET number1= :number1")
    void updateNumber1(String number1);

    @Query("UPDATE PhoneNumbers SET number2= :number2")
    void updateNumber2(String number2);

    @Query("UPDATE PhoneNumbers SET number3= :number3")
    void updateNumber3(String number3);

    @Query("UPDATE PhoneNumbers SET number4= :number4")
    void updateNumber4(String number4);

    @Query("DELETE FROM PhoneNumbers")
    void delete();

}
