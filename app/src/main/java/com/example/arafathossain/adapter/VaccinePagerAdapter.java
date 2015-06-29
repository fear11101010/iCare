package com.example.arafathossain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.arafathossain.fragment.DietItemFragment;
import com.example.arafathossain.fragment.DiseaseListFragment;
import com.example.arafathossain.fragment.VaccineScheduleListFragment;


public class VaccinePagerAdapter extends FragmentStatePagerAdapter {
    private static final String[] TITLES = {"Vaccine Schedule", "Vaccine List"};
    private static final int SIZE = 2;

    public VaccinePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new VaccineScheduleListFragment();
                break;
            case 1:
                fragment = new DiseaseListFragment();
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
        return TITLES[position];
    }

}
