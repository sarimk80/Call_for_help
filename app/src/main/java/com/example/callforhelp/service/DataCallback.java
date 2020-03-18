package com.example.callforhelp.service;

import com.example.callforhelp.db.Phone;

public interface DataCallback {

    void addData();

    void error();

    void getData(Phone phone);
}
