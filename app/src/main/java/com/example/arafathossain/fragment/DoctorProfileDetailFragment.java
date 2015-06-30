package com.example.arafathossain.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arafathossain.icare.ApplicationMain;
import com.example.arafathossain.icare.DatabaseHelper;
import com.example.arafathossain.icare.DoctorProfile;
import com.example.arafathossain.icare.ProfileValidation;
import com.example.arafathossain.icare.R;


public class DoctorProfileDetailFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener, DoctorChamberAddressFragment.OnAddressCreateListener {

    private EditText doctorName;
    private EditText degreeAchieved;
    private EditText specialization;
    private EditText designation;
    private EditText workplace;
    private EditText chamberAddress;
    private EditText email;
    private EditText contactNo;
    private View save;
    private View edit;
    private DoctorProfile profile;

    public static DoctorProfileDetailFragment getInstance(DoctorProfile profile) {
        DoctorProfileDetailFragment detailFragment = new DoctorProfileDetailFragment();
        detailFragment.profile = profile;
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_profile_detail, container, false);

        doctorName = (EditText) view.findViewById(R.id.doctorName);
        degreeAchieved = (EditText) view.findViewById(R.id.degreeAcheived);
        specialization = (EditText) view.findViewById(R.id.specialization);
        designation = (EditText) view.findViewById(R.id.designation);
        workplace = (EditText) view.findViewById(R.id.workPlace);
        chamberAddress = (EditText) view.findViewById(R.id.chamberAddress);
        email = (EditText) view.findViewById(R.id.email);
        contactNo = (EditText) view.findViewById(R.id.contactNo);
        save = view.findViewById(R.id.saveProfile);
        edit = view.findViewById(R.id.editProfile);
        save.setOnClickListener(this);
        edit.setOnClickListener(this);
        chamberAddress.setOnClickListener(this);
        chamberAddress.setOnFocusChangeListener(this);
        disableAll();
        setValue();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveProfile:
                updateProfile();
                break;
            case R.id.chamberAddress:
                new DoctorChamberAddressFragment().show(getActivity().getSupportFragmentManager(), "ppp");
                break;
            case R.id.editProfile:
                save.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
                enableAll();
                break;

        }
    }

    public void updateProfile() {
        ContentValues values = new ContentValues();


        if (!profile.getName().equalsIgnoreCase(doctorName.getText().toString())) {
            values.put(DatabaseHelper.ProfileTable.COLUMN_PROFILE_NAME, doctorName.getText().toString());
        }
        if (!profile.getEmail().equalsIgnoreCase(email.getText().toString())) {
            if (!ProfileValidation.validateEmail(email.getText().toString())) {
                Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.ProfileTable.COLUMN_EMAIL, email.getText().toString());

        }
        if (!profile.getDesignation().equalsIgnoreCase(designation.getText().toString())) {
            values.put(DatabaseHelper.ProfileTable.COLUMN_WEIGHT, designation.getText().toString());

        }
        if (!profile.getDegree().equalsIgnoreCase(degreeAchieved.getText().toString())) {

            values.put(DatabaseHelper.ProfileTable.COLUMN_HEIGHT, degreeAchieved.getText().toString());

        }
        if (!profile.getWorkPlace().equalsIgnoreCase(workplace.getText().toString())) {

            values.put(DatabaseHelper.ProfileTable.COLUMN_USER_NAME, workplace.getText().toString());

        }
        if (!profile.getChamber().equalsIgnoreCase(chamberAddress.getText().toString())) {

            values.put(DatabaseHelper.ProfileTable.COLUMN_DATE_OF_BIRTH, chamberAddress.getText().toString());

        }
        if (!profile.getContactNo().equalsIgnoreCase(contactNo.getText().toString())) {

            values.put(DatabaseHelper.ProfileTable.COLUMN_CONTACT_NO, contactNo.getText().toString());
        }
        if (values.size() <= 0) {
            Toast.makeText(getActivity(), "Update complete", Toast.LENGTH_LONG).show();
            disableAll();
        } else if (ApplicationMain.getDatabase().updateDotorProfile(values, profile.getId()) > 0) {
            Toast.makeText(getActivity(), "Update complete", Toast.LENGTH_LONG).show();
            disableAll();
            save.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);

        } else Toast.makeText(getActivity(), "Unable to update", Toast.LENGTH_LONG).show();
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

    public void disableAll() {

        doctorName.setEnabled(false);
        degreeAchieved.setEnabled(false);
        specialization.setEnabled(false);
        designation.setEnabled(false);
        email.setEnabled(false);
        contactNo.setEnabled(false);
        workplace.setEnabled(false);
        chamberAddress.setEnabled(false);
    }

    public void setValue() {
        doctorName.setText(profile.getName());
        designation.setText(profile.getDesignation());
        contactNo.setText(profile.getContactNo());
        email.setText(profile.getEmail());
        degreeAchieved.setText(profile.getDegree());
        workplace.setText(profile.getWorkPlace());
        chamberAddress.setText(profile.getChamber());
    }

    public void enableAll() {

        doctorName.setEnabled(true);
        degreeAchieved.setEnabled(true);
        specialization.setEnabled(true);
        designation.setEnabled(true);
        email.setEnabled(true);
        contactNo.setEnabled(true);
        workplace.setEnabled(true);
        chamberAddress.setEnabled(true);
    }
}
