package com.example.arafathossain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.arafathossain.icare.ApplicationMain;
import com.example.arafathossain.icare.R;
import com.example.arafathossain.icare.VaccineDetail;

import java.util.ArrayList;


public class VaccineScheduleListFragment extends Fragment {
    View noSchedule;
    ListView vaccineSchedule;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccine_schedule, container, false);
        vaccineSchedule = (ListView) view.findViewById(R.id.vaccineSchedule);
        noSchedule = view.findViewById(R.id.noSchedule);
        ArrayList<VaccineDetail> details = ApplicationMain.getDatabase().getVaccineList(getActivity().getIntent().getStringExtra("profileId"));
        if (details==null){
            noSchedule.setVisibility(View.VISIBLE);
            vaccineSchedule.setVisibility(View.GONE);
        }
        else {
            noSchedule.setVisibility(View.GONE);
            vaccineSchedule.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
