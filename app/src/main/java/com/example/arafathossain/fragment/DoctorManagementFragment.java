package com.example.arafathossain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.arafathossain.adapter.DoctorPagerAdapter;
import com.example.arafathossain.icare.R;


public class DoctorManagementFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_layout, container, false);
        final PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        ViewPager doctorPager = (ViewPager) view.findViewById(R.id.doctorPager);
        doctorPager.setAdapter(new DoctorPagerAdapter(getActivity().getSupportFragmentManager()));
        tabs.setViewPager(doctorPager);

        return view;
    }
}
