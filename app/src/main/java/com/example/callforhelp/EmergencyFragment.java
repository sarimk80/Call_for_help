package com.example.callforhelp;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.callforhelp.databinding.FragmentEmergencyBinding;
import com.example.callforhelp.db.Phone;
import com.example.callforhelp.service.DataCallback;
import com.example.callforhelp.service.DataManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment implements DataCallback {

    private FragmentEmergencyBinding emergencyBinding;
    private DataManager manager;

    public EmergencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        emergencyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_emergency, null, false);

        manager = new DataManager(getContext());

        Phone phone = new Phone("10", "32", "56", "78");

        manager.addData(EmergencyFragment.this, phone);
        manager.getData(EmergencyFragment.this);

        return emergencyBinding.getRoot();
    }

    @Override
    public void addData() {

    }

    @Override
    public void error() {

    }

    @Override
    public void getData(Phone phone) {

        emergencyBinding.setPhone(phone);
    }
}
