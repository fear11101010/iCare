package com.example.arafathossain.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.arafathossain.adapter.VaccinePagerAdapter;
import com.example.arafathossain.icare.R;

public class VaccinationInformationFragment extends Fragment {
    private PagerSlidingTabStrip tabs;
    private ViewPager vaccinePager;
    private VaccinePagerAdapter vaccinePagerAdapter;
    public static VaccinationInformationFragment getInstance(String profileId) {
        Bundle bundle = new Bundle();
        bundle.putString("profileId", profileId);
        VaccinationInformationFragment vaccineFragment = new VaccinationInformationFragment();
        vaccineFragment.setArguments(bundle);
        return vaccineFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccination_layout, container, false);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        vaccinePager = (ViewPager)  view.findViewById(R.id.vaccinPager);
        vaccinePagerAdapter = new VaccinePagerAdapter(getChildFragmentManager());
        vaccinePager.setAdapter(vaccinePagerAdapter);
        tabs.setViewPager(vaccinePager);
        return view;
    }
}
