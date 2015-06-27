package com.example.arafathossain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arafathossain.icare.ApplicationMain;
import com.example.arafathossain.icare.DoctorProfile;
import com.example.arafathossain.icare.R;


public class CreateDoctorProfileFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener, DoctorChamberAddressFragment.OnAddressCreateListener {

    private EditText doctorName;
    private EditText degreeAchieved;
    private EditText specialization;
    private EditText designation;
    private EditText workplace;
    private EditText chamberAddress;
    private EditText email;
    private EditText contactNo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_doctor_profile, container, false);

        doctorName = (EditText) view.findViewById(R.id.doctorName);
        degreeAchieved = (EditText) view.findViewById(R.id.degreeAcheived);
        specialization = (EditText) view.findViewById(R.id.specialization);
        designation = (EditText) view.findViewById(R.id.designation);
        workplace = (EditText) view.findViewById(R.id.workPlace);
        chamberAddress = (EditText) view.findViewById(R.id.chamberAddress);
        email = (EditText) view.findViewById(R.id.email);
        contactNo = (EditText) view.findViewById(R.id.contactNo);
        View save = view.findViewById(R.id.saveProfile);
        save.setOnClickListener(this);
        chamberAddress.setOnClickListener(this);
        chamberAddress.setOnFocusChangeListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveProfile:
                saveProfile();
                break;
            case R.id.chamberAddress:
                new DoctorChamberAddressFragment().show(getActivity().getSupportFragmentManager(), "ppp");
                break;
        }
    }

    private void saveProfile() {
        DoctorProfile profile = new DoctorProfile();
        profile.setName(doctorName.getText().toString());
        profile.setEmail(email.getText().toString());
        profile.setContactNo(contactNo.getText().toString());
        profile.setWorkPlace(workplace.getText().toString());
        profile.setSpecialist(specialization.getText().toString());
        profile.setDegree(degreeAchieved.getText().toString());
        profile.setDesignation(designation.getText().toString());
        profile.setChamber(chamberAddress.getText().toString());
        if (ApplicationMain.getDatabase().addDoctorProfile(profile) > 0) {
            Toast.makeText(getActivity(), "InsertComplete", Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getActivity(), "Unable to create profile", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAddressCreate(String address) {
        chamberAddress.setText(address);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus)
            new DoctorChamberAddressFragment().show(getActivity().getSupportFragmentManager(), "ppp");
    }
}
