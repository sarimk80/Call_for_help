package com.example.callforhelp;


import android.Manifest;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.callforhelp.databinding.FragmentRecordBinding;
import com.example.callforhelp.events.FragmentRecordEvent;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {

    private FragmentRecordBinding fragmentRecordBinding;

    public RecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentRecordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_record, null, false);

        final RxPermissions rxPermissions = new RxPermissions(RecordFragment.this);

        rxPermissions.request(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA).subscribe(granted -> {
            if (granted) {

            } else {
                Toast.makeText(getContext(), "Permission is required", Toast.LENGTH_LONG).show();
            }
        });

        return fragmentRecordBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentRecordBinding.setRecordEvent(new FragmentRecordEvent() {
            @Override
            public void RecordVideo() {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, 0);
            }

            @Override
            public void RecordAudio() {
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, 0);
            }
        });
    }
}
