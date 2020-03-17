package com.example.callforhelp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.callforhelp.databinding.FragmentHomeBinding;
import com.example.callforhelp.events.FragmentHomeEvent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private LocationManager locationManager;
    FragmentHomeBinding fragmentHomeBinding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false);

        // Inflate the layout for this fragment
        final RxPermissions permissions = new RxPermissions(HomeFragment.this);


        permissions.request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(granted -> {
            if (granted) {
                Log.d("Main", granted.toString());
//
                locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    try {
                        Geocoder geocoder = new Geocoder(getContext());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        fragmentHomeBinding.address.setText(addresses.get(0).getAddressLine(0));
                        //Log.d("Main", addresses.get(0).getAddressLine(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
//
            } else {
                Toast.makeText(getContext(), "Permission is Required for this app", Toast.LENGTH_LONG).show();
            }
        });

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        fragmentHomeBinding.setHomeEvent(new FragmentHomeEvent() {
            @Override
            public void Police() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10"));
                startActivity(intent);

            }

            @Override
            public void Ambulance() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10"));
                startActivity(intent);
            }

            @Override
            public void Hospital() {
                String uri = "http://maps.google.co.in/maps?q=" + fragmentHomeBinding.address.getText();

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                startActivity(intent);
            }

            @Override
            public void FireTruck() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10"));
                startActivity(intent);
            }

            @Override
            public void Contacts() {

            }

            @Override
            public void Records() {

            }

            @Override
            public void Share() {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, fragmentHomeBinding.address.getText());
                intent.setType("text/plain");
                startActivity(intent);

            }
        });
    }
}
