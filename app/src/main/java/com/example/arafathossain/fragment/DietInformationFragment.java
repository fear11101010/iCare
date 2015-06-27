package com.example.arafathossain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.arafathossain.adapter.DietPagerAdapter;
import com.example.arafathossain.icare.R;
import com.example.arafathossain.interfacee.OnDietCreateListener;


public class DietInformationFragment extends Fragment implements OnDietCreateListener, ViewPager.OnPageChangeListener {
    DietPagerAdapter dietPagerAdapter;
    ViewPager viewPager;
    PagerSlidingTabStrip tabs;
    int currentPage;

    public static DietInformationFragment getInstance(String profileId) {
        Bundle bundle = new Bundle();
        bundle.putString("profileId", profileId);
        DietInformationFragment dietFragment = new DietInformationFragment();
        dietFragment.setArguments(bundle);
        return dietFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet_information, container, false);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.fragmentPager);
        dietPagerAdapter = new DietPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(dietPagerAdapter);
        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(this);
        currentPage = 0;
        return view;
    }

    @Override
    public void onCreateDiet() {
        dietPagerAdapter = new DietPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(dietPagerAdapter);
        viewPager.setCurrentItem(currentPage);
    }

    @Override
    public void onUpdateDiet() {
        dietPagerAdapter = new DietPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(dietPagerAdapter);
        viewPager.setCurrentItem(currentPage);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
