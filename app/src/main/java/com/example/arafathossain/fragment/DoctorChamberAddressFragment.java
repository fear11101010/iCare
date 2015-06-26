package com.example.arafathossain.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import com.example.arafathossain.icare.R;

public class DoctorChamberAddressFragment extends DialogFragment implements View.OnClickListener {
    TextView address;
    TextView visitingHour;
    TextView visitingDay;
    ViewSwitcher timeSwitcher;
    TimePicker from;
    TimePicker to;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.doctor_chamber_address_layout, null);
        view.findViewById(R.id.addressLayout).setOnClickListener(this);
        view.findViewById(R.id.visitingHour).setOnClickListener(this);
        view.findViewById(R.id.visitingDay).setOnClickListener(this);
        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.visitingHour:
                showVisitingHourPicker();
                break;
            case R.id.next:
                timeSwitcher.showNext();
                break;
            case R.id.previous:
                timeSwitcher.showPrevious();
                break;
        }
    }

    public void showVisitingHourPicker() {
        View view = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.visiting_hour_layout, null);
        timeSwitcher = (ViewSwitcher) view.findViewById(R.id.timeSwitcher);
        from = (TimePicker) view.findViewById(R.id.form);
        to = (TimePicker) view.findViewById(R.id.to);
        view.findViewById(R.id.next).setOnClickListener(this);
        view.findViewById(R.id.previous).setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}
