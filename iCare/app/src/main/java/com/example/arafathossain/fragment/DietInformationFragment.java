package com.example.arafathossain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arafathossain.adapter.DietPagerAdapter;
import com.example.arafathossain.icare.R;
import com.example.arafathossain.interfacee.OnDietCreateListener;

/**
 * Created by Arafat Hossain on 6/11/2015.
 */
public class DietInformationFragment extends Fragment implements OnDietCreateListener {
    DietPagerAdapter dietPagerAdapter;
    ViewPager viewPager;

    public static DietInformationFragment getInstance(String profileTitle) {
        Bundle bundle = new Bundle();
        bundle.putString("profileTitle", profileTitle);
        DietInformationFragment dietFragment = new DietInformationFragment();
        dietFragment.setArguments(bundle);
        return dietFragment;
    }

    public static DietInformationFragment getInstance() {
        return new DietInformationFragment();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet_information, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.fragmentPager);
        dietPagerAdapter = new DietPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(dietPagerAdapter);
        return view;
    }

    @Override
    public void onCreateDiet() {
        dietPagerAdapter = new DietPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(dietPagerAdapter);
    }
}
