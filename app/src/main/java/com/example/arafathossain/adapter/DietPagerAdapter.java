package com.example.arafathossain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.arafathossain.fragment.DietItemFragment;


public class DietPagerAdapter extends FragmentStatePagerAdapter {
    private static final String[] WEEK_DAYS = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private static final int SIZE = 7;

    public DietPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = DietItemFragment.getInstance(WEEK_DAYS[position]);
                break;
            case 1:
                fragment = DietItemFragment.getInstance(WEEK_DAYS[position]);
                break;
            case 2:
                fragment = DietItemFragment.getInstance(WEEK_DAYS[position]);
                break;
            case 3:
                fragment = DietItemFragment.getInstance(WEEK_DAYS[position]);
                break;
            case 4:
                fragment = DietItemFragment.getInstance(WEEK_DAYS[position]);
                break;
            case 5:
                fragment = DietItemFragment.getInstance(WEEK_DAYS[position]);
                break;
            case 6:
                fragment = DietItemFragment.getInstance(WEEK_DAYS[position]);
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
        return WEEK_DAYS[position];
    }

}
