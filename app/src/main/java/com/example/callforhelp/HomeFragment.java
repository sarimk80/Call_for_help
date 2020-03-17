package com.example.callforhelp;


import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final RxPermissions permissions = new RxPermissions(HomeFragment.this);


        permissions.request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(granted -> {
            if (granted) {
//Permission is granted
            } else {
                Toast.makeText(getContext(), "Permission is Required for this app", Toast.LENGTH_LONG).show();
            }
        });

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }
}
