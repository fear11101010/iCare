package com.example.arafathossain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.arafathossain.fragment.DoctorAppointmentFragment;
import com.example.arafathossain.fragment.DoctorProfileFragment;


public class DoctorPagerAdapter extends FragmentStatePagerAdapter {
    private static final String[] TITLE = {"Appointment Schedule", "Doctor Profile"};
    private static final int SIZE = 2;

    public DoctorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new DoctorAppointmentFragment();
                break;
            case 1:
                fragment = new DoctorProfileFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return SIZE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position];
    }


}
