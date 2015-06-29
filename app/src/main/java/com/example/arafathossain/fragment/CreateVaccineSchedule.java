package com.example.arafathossain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.arafathossain.icare.R;

/**
 * Created by Arafat Hossain on 6/29/2015.
 */
public class CreateVaccineSchedule extends Fragment {
    TextView diseaseName,vaccineName,diseaseSymptoms,diseaseComplication,diseaseCauses;
    LinearLayout vaccineDoses;
    public static CreateVaccineSchedule getInstance(String dn,String vn, String dsy,String dcom,String dcause,String doses) {
        Bundle bundle = new Bundle();
        bundle.putString("dn", dn);
        bundle.putString("dsy", dsy);
        bundle.putString("vn", vn);
        bundle.putString("dcom", dcom);
        bundle.putString("dcause", dcause);
        bundle.putString("doses", doses);
        CreateVaccineSchedule createVaccineSchedule = new CreateVaccineSchedule();
        createVaccineSchedule.setArguments(bundle);
        return createVaccineSchedule;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_vaccine_schedule, container, false);
        diseaseName = (TextView)view.findViewById(R.id.diseaseName);
        vaccineName = (TextView)view.findViewById(R.id.vaccineName);
        diseaseSymptoms = (TextView)view.findViewById(R.id.symptoms);
        diseaseComplication = (TextView)view.findViewById(R.id.complications);
        diseaseCauses = (TextView)view.findViewById(R.id.causes);
        vaccineDoses = (LinearLayout)view.findViewById(R.id.dosesList);
        setValue();
        return view;
    }
    private void setValue(){
        diseaseName.setText(getArguments().getString("dn"));
        vaccineName.setText(getArguments().getString("vn"));
        diseaseSymptoms.setText(getArguments().getString("dsy"));
        diseaseComplication.setText(getArguments().getString("dcom"));
        diseaseCauses.setText(getArguments().getString("dcause"));
        String[] doseList = getArguments().getString("doses").split(",");
        RadioGroup radioGroup = new RadioGroup(getActivity());
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        vaccineDoses.addView(radioGroup);
        CheckBox[] doses = new CheckBox[doseList.length];
        for (int i = 0;i < doseList.length;i++){
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(doseList[i]);
            radioButton.setId(i);
            radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            radioGroup.addView(radioButton);
            radioGroup.invalidate();
        }
    }
}
