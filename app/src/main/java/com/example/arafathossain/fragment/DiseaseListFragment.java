package com.example.arafathossain.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.arafathossain.icare.R;


public class DiseaseListFragment extends Fragment implements AdapterView.OnItemClickListener {
    public interface OnVaccineScheduleCreateListener {
        void onCreateSchedule(String dn, String vn, String dsy, String dcom, String dcause, String doses);
    }

    OnVaccineScheduleCreateListener listener;
    ListView diseaseList;

    private String[] diseaseNames;
    private String[] diseaseSymptoms;
    private String[] diseaseComplication;
    private String[] diseaseCauses;
    private String[] doseMonths;
    private String[] vaccineList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        diseaseNames = getResources().getStringArray(R.array.diseaseNames);
        diseaseSymptoms = getResources().getStringArray(R.array.diseaseSymptoms);
        diseaseComplication = getResources().getStringArray(R.array.diseaseComplications);
        diseaseCauses = getResources().getStringArray(R.array.causeOfDisease);
        doseMonths = getResources().getStringArray(R.array.doseMonths);
        vaccineList = getResources().getStringArray(R.array.vaccineList);
        View view = inflater.inflate(R.layout.fragment_disease_list, container, false);
        diseaseList = (ListView) view.findViewById(R.id.diseaseList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, diseaseNames);
        diseaseList.setAdapter(adapter);
        diseaseList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        listener.onCreateSchedule(diseaseNames[position], vaccineList[position], diseaseSymptoms[position], diseaseComplication[position], diseaseCauses[position], doseMonths[position]);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnVaccineScheduleCreateListener)activity;
    }
}
