package com.example.callforhelp.service;

import android.annotation.SuppressLint;
import android.content.Context;


import androidx.room.Room;

import com.example.callforhelp.db.Phone;
import com.example.callforhelp.db.PhoneDataBase;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class DataManager {

    private Context context;
    private PhoneDataBase phoneDataBase;

    public DataManager(Context context) {
        this.context = context;

        phoneDataBase = Room.databaseBuilder(context, PhoneDataBase.class, "phone").build();
    }

    public void addData(final DataCallback callback, final Phone phone) {

        Completable.fromAction(new Action() {
            @Override
            public void run() {
                Phone _phone = new Phone(phone.getNumber1(), phone.getNumber2(), phone.getNumber3(), phone.getNumber4());
                phoneDataBase.phoneDao().Insert(_phone);

            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

                callback.addData();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                callback.error();
            }
        });
    }

    public void updateNumber1(final DataCallback callback, final String number1) {

        Completable.fromAction(() -> {

            phoneDataBase.phoneDao().updateNumber1(number1);
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                callback.updateData();
            }

            @Override
            public void onError(Throwable e) {
                callback.updateError();
            }
        });

    }

    public void updateNumber2(final DataCallback callback, final String number1) {

        Completable.fromAction(() -> {

            phoneDataBase.phoneDao().updateNumber2(number1);
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                callback.updateData();
            }

            @Override
            public void onError(Throwable e) {
                callback.updateError();
            }
        });

    }

    public void updateNumber3(final DataCallback callback, final String number1) {

        Completable.fromAction(() -> {

            phoneDataBase.phoneDao().updateNumber3(number1);
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                callback.updateData();
            }

            @Override
            public void onError(Throwable e) {
                callback.updateError();
            }
        });

    }

    public void updateNumber4(final DataCallback callback, final String number1) {

        Completable.fromAction(() -> {

            phoneDataBase.phoneDao().updateNumber4(number1);
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                callback.updateData();
            }

            @Override
            public void onError(Throwable e) {
                callback.updateError();
            }
        });

    }

    @SuppressLint("CheckResult")
    public void getData(final DataCallback callback) {

        phoneDataBase.phoneDao().getAllPhones().subscribeOn(Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(phone -> {
            callback.getData(phone);
        });


    }
}
