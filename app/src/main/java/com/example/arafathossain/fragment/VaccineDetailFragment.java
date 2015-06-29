package com.example.arafathossain.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arafathossain.icare.R;


public class VaccineDetailFragment extends Fragment implements View.OnClickListener {
    TextView diseaseName, vaccineName, diseaseSymptoms, diseaseComplication, diseaseCauses;
    LinearLayout vaccineDoses;
    String[] doseList;

    public interface OnCreateScheduleButtonClickListener {
        void onClick();
    }

    OnCreateScheduleButtonClickListener buttonClickListener;

    public static VaccineDetailFragment getInstance(String dn, String vn, String dsy, String dcom, String dcause, String doses) {
        Bundle bundle = new Bundle();
        bundle.putString("dn", dn);
        bundle.putString("dsy", dsy);
        bundle.putString("vn", vn);
        bundle.putString("dcom", dcom);
        bundle.putString("dcause", dcause);
        bundle.putString("doses", doses);
        VaccineDetailFragment vaccineDetailFragment = new VaccineDetailFragment();
        vaccineDetailFragment.setArguments(bundle);
        return vaccineDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease_detail, container, false);
        diseaseName = (TextView) view.findViewById(R.id.diseaseName);
        vaccineName = (TextView) view.findViewById(R.id.vaccineName);
        diseaseSymptoms = (TextView) view.findViewById(R.id.symptoms);
        diseaseComplication = (TextView) view.findViewById(R.id.complications);
        diseaseCauses = (TextView) view.findViewById(R.id.causes);
        vaccineDoses = (LinearLayout) view.findViewById(R.id.dosesList);
        view.findViewById(R.id.createVaccineSchedule).setOnClickListener(this);
        setValue();
        return view;
    }

    private void setValue() {
        diseaseName.setText(getArguments().getString("dn"));
        vaccineName.setText(getArguments().getString("vn"));
        diseaseSymptoms.setText(getArguments().getString("dsy"));
        diseaseComplication.setText(getArguments().getString("dcom"));
        diseaseCauses.setText(getArguments().getString("dcause"));
        doseList = getArguments().getString("doses").split(",");
        for (int i = 0; i < doseList.length; i++) {
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setPadding(5, 3, 5, 3);
            textView.setTextColor(Color.BLUE);
            textView.setTextSize(20);
            textView.setText("Dose No. " + (i + 1) + ": " + doseList[i]);
            vaccineDoses.addView(textView);
            vaccineDoses.invalidate();
        }
    }

    @Override
    public void onClick(View v) {
        DialogFragment fragment = CreateVaccineSchedule.getInstance(getArguments().getString("dn"), getArguments().getString("vn"), doseList);
        fragment.show(getChildFragmentManager(), "createVaccineSchedule");
    }
}
