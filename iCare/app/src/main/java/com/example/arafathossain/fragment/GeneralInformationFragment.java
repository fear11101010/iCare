package com.example.arafathossain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arafathossain.icare.DatabaseApplication;
import com.example.arafathossain.icare.Profile;
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
        switch (item.getItemId()){
            case R.id.edit:
                editProfile();
                break;
            case R.id.save:
                updateProfile();
                break;
        }
    }

    public void setValue() {
        Profile profile = DatabaseApplication.getDatabase().getProfileByName(getArguments().getString("profileTitle"));
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
    public void editProfile(){
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
    public void updateProfile(){

    }
}
