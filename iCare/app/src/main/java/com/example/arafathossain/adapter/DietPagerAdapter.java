package com.example.arafathossain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.arafathossain.fragment.DietItemFragment;

/**
 * Created by Arafat Hossain on 6/11/2015.
 */
public class DietPagerAdapter extends FragmentPagerAdapter {
    private static final String[] WEEK_DAYS = {"SATURDAY", "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
    private static final int SIZE = 7;

    public DietPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DietItemFragment.getInstance(WEEK_DAYS[position]);
            case 1:
                return DietItemFragment.getInstance(WEEK_DAYS[position]);
            case 2:
                return DietItemFragment.getInstance(WEEK_DAYS[position]);
            case 3:
                return DietItemFragment.getInstance(WEEK_DAYS[position]);
            case 4:
                return DietItemFragment.getInstance(WEEK_DAYS[position]);
            case 5:
                return DietItemFragment.getInstance(WEEK_DAYS[position]);
            case 6:
                return DietItemFragment.getInstance(WEEK_DAYS[position]);
        }
        return null;
    }

    @Override
    public int getCount() {
        return SIZE;
    }
}
