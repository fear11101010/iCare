package com.example.arafathossain.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.arafathossain.icare.R;


public class HomeProfileDetailFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout dietButton;
    private RelativeLayout generalButton;
    private RelativeLayout vaccinationButton;
    private RelativeLayout doctorButton;
    private RelativeLayout historyButton;
    private RelativeLayout growthButton;

    @Override
    public void onClick(View v) {
        listener.onLayoutButtonClick(v.getId());
    }

    public interface OnLayoutButtonClickListener{
        void onLayoutButtonClick(int id);
    }
    OnLayoutButtonClickListener listener;
    public static HomeProfileDetailFragment getInstance(String profileTitle){
        Bundle bundle = new Bundle();
        bundle.putString("profileTitle",profileTitle);
        HomeProfileDetailFragment homeFragment = new HomeProfileDetailFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_profile_detail,container,false);
        dietButton = (RelativeLayout) view.findViewById(R.id.dietInformation);
        generalButton = (RelativeLayout) view.findViewById(R.id.generalInformation);
        vaccinationButton = (RelativeLayout) view.findViewById(R.id.vaccinationInformation);
        doctorButton = (RelativeLayout) view.findViewById(R.id.doctorInformation);
        historyButton = (RelativeLayout) view.findViewById(R.id.healthHistory);
        growthButton = (RelativeLayout) view.findViewById(R.id.growthInformation);
        doctorButton.setOnClickListener(this);
        dietButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        growthButton.setOnClickListener(this);
        generalButton.setOnClickListener(this);
        vaccinationButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnLayoutButtonClickListener)activity;
    }
}
