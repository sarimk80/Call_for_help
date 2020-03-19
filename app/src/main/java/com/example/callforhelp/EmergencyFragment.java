package com.example.callforhelp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.callforhelp.databinding.FragmentEmergencyBinding;
import com.example.callforhelp.db.Phone;
import com.example.callforhelp.events.EmergencyFragmentEvent;
import com.example.callforhelp.service.DataCallback;
import com.example.callforhelp.service.DataManager;
import com.google.android.material.snackbar.Snackbar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment implements DataCallback {

    private FragmentEmergencyBinding emergencyBinding;
    private DataManager manager;
    private final static int RESULT_REQUES_1 = 1;

    public EmergencyFragment() {
        // Required empty public constructor
    }


    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        emergencyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_emergency, null, false);

        final RxPermissions permissions = new RxPermissions(EmergencyFragment.this);

        permissions.request(Manifest.permission.SEND_SMS).subscribe(granted -> {
            if (granted) {

            } else {
                Toast.makeText(getContext(), "Permission is Required for this app", Toast.LENGTH_LONG).show();
            }
        });

        manager = new DataManager(getContext());

        manager.getData(EmergencyFragment.this);

        return emergencyBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emergencyBinding.setEvent(new EmergencyFragmentEvent() {
            @Override
            public void UpdateNumber1() {
                manager.updateNumber1(EmergencyFragment.this, emergencyBinding.number1.getText().toString());
            }

            @Override
            public void UpdateNumber2() {
                manager.updateNumber2(EmergencyFragment.this, emergencyBinding.number2.getText().toString());
            }

            @Override
            public void UpdateNumber3() {
                manager.updateNumber3(EmergencyFragment.this, emergencyBinding.number3.getText().toString());
            }

            @Override
            public void UpdateNumber4() {
                manager.updateNumber4(EmergencyFragment.this, emergencyBinding.number4.getText().toString());
            }

            @Override
            public void help() {
                SmsManager smsManager = SmsManager.getDefault();
                String[] numbers = {emergencyBinding.number1.getText().toString(), emergencyBinding.number2.getText().toString(), emergencyBinding.number3.getText().toString(), emergencyBinding.number4.getText().toString()};
                Observable.fromArray(numbers).zipWith(Observable.interval(10, TimeUnit.SECONDS), (item, interval) -> item).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        smsManager.sendTextMessage(s, null, emergencyBinding.edtHelp.getText().toString(), null, null);
                        Snackbar.make(emergencyBinding.getRoot(), "Message Successfully Send", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Main", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }

            @Override
            public void selectContact1() {
                Log.d("Main", "Click");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, RESULT_REQUES_1);
            }

            @Override
            public void selectContact2() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 2);
            }

            @Override
            public void selectContact3() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 3);
            }

            @Override
            public void selectContact4() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 4);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_REQUES_1) {

            Uri contactData = data.getData();
            Cursor cursor = getActivity().getContentResolver().query(contactData, null, null, null, null);
            cursor.moveToFirst();
            int phoneNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            emergencyBinding.number1.setText(cursor.getString(phoneNumber));

        }

        if (requestCode == 2) {

            Uri contactData = data.getData();
            Cursor cursor = getActivity().getContentResolver().query(contactData, null, null, null, null);
            cursor.moveToFirst();
            emergencyBinding.number2.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

        }
        if (requestCode == 3) {

            Uri contactData = data.getData();
            Cursor cursor = getActivity().getContentResolver().query(contactData, null, null, null, null);
            cursor.moveToFirst();
            emergencyBinding.number3.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)));

        }
        if (requestCode == 4) {

            Uri contactData = data.getData();
            Cursor cursor = getActivity().getContentResolver().query(contactData, null, null, null, null);
            cursor.moveToFirst();
            emergencyBinding.number4.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)));

        }
    }

    @Override
    public void addData() {
        Snackbar.make(emergencyBinding.getRoot(), "Successfully Updated", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void error() {

    }

    @Override
    public void getData(Phone phone) {

        emergencyBinding.setPhone(phone);
    }

    @Override
    public void updateData() {
        Snackbar.make(emergencyBinding.getRoot(), "Successfully Updated", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void updateError() {

    }
}
