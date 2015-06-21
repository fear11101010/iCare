package com.example.arafathossain.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arafathossain.icare.ApplicationMain;
import com.example.arafathossain.icare.DatabaseHelper;
import com.example.arafathossain.icare.Profile;
import com.example.arafathossain.icare.ProfileValidation;
import com.example.arafathossain.icare.R;
import com.example.arafathossain.interfacee.OnMenuItemClickListener;

import java.util.Arrays;


public class GeneralInformationFragment extends Fragment implements OnMenuItemClickListener {
    private Spinner bloodGroup;
    private EditText profileName;
    private EditText userName;
    private EditText email;
    private EditText contactNo;
    private EditText height;
    private EditText weight;
    private EditText dateOfBirth;
    private RadioGroup genderGroup;
    private Profile profile;

    public static GeneralInformationFragment getInstance(String profileTitle) {
        Bundle bundle = new Bundle();
        bundle.putString("profileTitle", profileTitle);
        GeneralInformationFragment generalFragment = new GeneralInformationFragment();
        generalFragment.setArguments(bundle);
        return generalFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_information, container, false);
        bloodGroup = (Spinner) view.findViewById(R.id.bloodGroup);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bloodGroupList, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        bloodGroup.setAdapter(spinnerAdapter);
        profileName = (EditText) view.findViewById(R.id.profileName);
        userName = (EditText) view.findViewById(R.id.userName);
        email = (EditText) view.findViewById(R.id.email);
        contactNo = (EditText) view.findViewById(R.id.contactNo);
        weight = (EditText) view.findViewById(R.id.weight);
        height = (EditText) view.findViewById(R.id.height);
        dateOfBirth = (EditText) view.findViewById(R.id.dateOfBirth);
        genderGroup = (RadioGroup) view.findViewById(R.id.genderGroup);
        setValue();
        return view;
    }

    @Override
    public void onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                editProfile();
                break;
            case R.id.save:
                updateProfile();
                break;
        }
    }

    public void setValue() {
        profile = ApplicationMain.getDatabase().getProfileByName(getArguments().getString("profileTitle"));
        int position = Arrays.binarySearch(getActivity().getResources().getStringArray(R.array.bloodGroupList), profile.getBloodGroup());
        bloodGroup.setSelection(position);
        profileName.setText(profile.getProfileName());
        userName.setText(profile.getUserName());
        contactNo.setText(profile.getContactNo());
        email.setText(profile.getEmail());
        weight.setText(profile.getWeight());
        height.setText(profile.getHeight());
        dateOfBirth.setText(profile.getDateOfBirth());
        if (profile.getGender().equalsIgnoreCase("male")) genderGroup.check(R.id.male);
        else genderGroup.check(R.id.female);
    }

    public void editProfile() {
        profileName.setEnabled(true);
        userName.setEnabled(true);
        weight.setEnabled(true);
        height.setEnabled(true);
        email.setEnabled(true);
        contactNo.setEnabled(true);
        dateOfBirth.setEnabled(true);
        bloodGroup.setClickable(true);
        genderGroup.getChildAt(0).setEnabled(true);
        genderGroup.getChildAt(1).setEnabled(true);

    }

    public void updateProfile() {
        ContentValues values = new ContentValues();
        if (!profile.getProfileName().equalsIgnoreCase(profileName.getText().toString())) {
            if (ApplicationMain.getDatabase().checkProfileName(profileName.getText().toString())) {
                Toast.makeText(getActivity(), "Profile name already exists", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.ProfileTable.COLUMN_PROFILE_NAME, profileName.getText().toString());
        }
        if (!profile.getBloodGroup().equalsIgnoreCase(bloodGroup.getSelectedItem().toString())) {
            values.put(DatabaseHelper.ProfileTable.COLUMN_BLOOD_GROUP, bloodGroup.getSelectedItem().toString());
        }
        if (!profile.getGender().equalsIgnoreCase(genderGroup.getCheckedRadioButtonId() == R.id.male ? "Male" : "Female")) {

            values.put(DatabaseHelper.ProfileTable.COLUMN_GENDER, genderGroup.getCheckedRadioButtonId() == R.id.male ? "Male" : "Female");
        }
        if (!profile.getEmail().equalsIgnoreCase(email.getText().toString())) {
            if (!ProfileValidation.validateEmail(email.getText().toString())) {
                Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.ProfileTable.COLUMN_EMAIL, email.getText().toString());

        }
        if (!profile.getWeight().equalsIgnoreCase(weight.getText().toString())) {
            if (!ProfileValidation.validateWeight(weight.getText().toString())) {
                Toast.makeText(getActivity(), "Invalid weight", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.ProfileTable.COLUMN_WEIGHT, weight.getText().toString());

        }
        if (!profile.getHeight().equalsIgnoreCase(height.getText().toString())) {
            if (!ProfileValidation.validateHeight(height.getText().toString())) {
                Toast.makeText(getActivity(), "Invalid height", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.ProfileTable.COLUMN_HEIGHT, height.getText().toString());

        }
        if (!profile.getUserName().equalsIgnoreCase(userName.getText().toString())) {
            if (!ProfileValidation.validateUserNAme(userName.getText().toString())) {
                Toast.makeText(getActivity(), "User name cannot be empty", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.ProfileTable.COLUMN_USER_NAME, userName.getText().toString());

        }
        if (!profile.getDateOfBirth().equalsIgnoreCase(dateOfBirth.getText().toString())) {
            if (!ProfileValidation.validateDateOfBirth(dateOfBirth.getText().toString())) {
                Toast.makeText(getActivity(), "Date of birth can not be empty", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.ProfileTable.COLUMN_DATE_OF_BIRTH, dateOfBirth.getText().toString());

        }
        if (!profile.getContactNo().equalsIgnoreCase(contactNo.getText().toString())) {

            values.put(DatabaseHelper.ProfileTable.COLUMN_CONTACT_NO, contactNo.getText().toString());
        }
        if (values.size() <= 0) {
            Toast.makeText(getActivity(), "Update complete", Toast.LENGTH_LONG).show();
            disableAll();
        } else if (ApplicationMain.getDatabase().updateProfile(values, profile.getId()) > 0) {
            Toast.makeText(getActivity(), "Update complete", Toast.LENGTH_LONG).show();
            disableAll();
        } else Toast.makeText(getActivity(), "Unable to update", Toast.LENGTH_LONG).show();
    }

    public void disableAll() {
        profileName.setEnabled(false);
        userName.setEnabled(false);
        weight.setEnabled(false);
        height.setEnabled(false);
        email.setEnabled(false);
        contactNo.setEnabled(false);
        dateOfBirth.setEnabled(false);
        bloodGroup.setClickable(false);
        genderGroup.getChildAt(0).setEnabled(false);
        genderGroup.getChildAt(1).setEnabled(false);
    }

}
