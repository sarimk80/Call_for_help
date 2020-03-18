package com.example.callforhelp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Phone.class, version = 1,exportSchema = false)
public abstract class PhoneDataBase extends RoomDatabase {


    public abstract PhoneDao phoneDao();


}
