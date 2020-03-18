package com.example.callforhelp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment implements DataCallback {

    private FragmentEmergencyBinding emergencyBinding;
    private DataManager manager;

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

            }
        });
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
