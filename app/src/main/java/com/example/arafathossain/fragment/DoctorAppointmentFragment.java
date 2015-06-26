package com.example.arafathossain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.arafathossain.icare.R;


public class DoctorAppointmentFragment extends DialogFragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_appointment_layout, container, false);
        ListView appointmentList = (ListView) view.findViewById(R.id.appointmentList);
        View newAppointment = view.findViewById(R.id.newAppointment);
        newAppointment.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        new DoctorNewAppointmentFragment().show(fragmentManager,"newAppointment");
    }
}
