package com.example.arafathossain.fragment;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
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
    ViewPager doctorPager;
    PagerSlidingTabStrip tabs;
    DoctorPagerAdapter doctorPagerAdapter;
    Parcelable state;
    int currentPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_layout, container, false);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        doctorPager = (ViewPager) view.findViewById(R.id.doctorPager);
        doctorPagerAdapter = new DoctorPagerAdapter(getChildFragmentManager());
        doctorPager.setAdapter(doctorPagerAdapter);
        tabs.setViewPager(doctorPager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return view;
    }

}
