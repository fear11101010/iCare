package com.example.arafathossain.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arafathossain.icare.R;

/**
 * Created by Arafat Hossain on 6/11/2015.
 */
public class DietItemFragment extends Fragment {
    public static DietItemFragment getInstance(String weekDay){
        Bundle bundle = new Bundle();
        bundle.putString("weekDay",weekDay);
        DietItemFragment dietFragment = new DietItemFragment();
        dietFragment.setArguments(bundle);
        return dietFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diet_item_fragment_layout,container,false);
        TextView weekDay = (TextView) view.findViewById(R.id.weekDay);
        weekDay.setText(getArguments().getString("weekDay"));
        return view;
    }
}
