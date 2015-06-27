package com.example.arafathossain.fragment;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arafathossain.adapter.DietListAdapter;
import com.example.arafathossain.icare.ApplicationMain;
import com.example.arafathossain.icare.DietInformation;
import com.example.arafathossain.icare.R;

import java.util.ArrayList;


public class DietItemFragment extends Fragment {
    ListView dietList;
    TextView notFound;
    DietListAdapter dietListAdapter;
    ArrayList<DietInformation> dietInformations;

    public static DietItemFragment getInstance(String weekDay) {
        Bundle bundle = new Bundle();
        bundle.putString("weekDay", weekDay);
        DietItemFragment dietFragment = new DietItemFragment();
        dietFragment.setArguments(bundle);
        return dietFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diet_item_fragment_layout, container, false);
        TextView weekDay = (TextView) view.findViewById(R.id.weekDay);
        notFound = (TextView) view.findViewById(R.id.notFound);
        dietList = (ListView) view.findViewById(R.id.dietList);
        weekDay.setText(getArguments().getString("weekDay"));
        setAdapter();
        return view;
    }

    public void setAdapter() {
        dietInformations = ApplicationMain.getDatabase().getDietList(getArguments().getString("weekDay"), getActivity().getIntent().getStringExtra("profileId"));
        if (dietInformations == null) {
            notFound.setVisibility(View.VISIBLE);
            dietList.setVisibility(View.GONE);
        } else {
            notFound.setVisibility(View.GONE);
            dietList.setVisibility(View.VISIBLE);
            dietListAdapter = new DietListAdapter(getActivity(), dietInformations);
            dietList.setAdapter(dietListAdapter);
            dietListAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    setAdapter();
                }
            });
        }
    }
}
