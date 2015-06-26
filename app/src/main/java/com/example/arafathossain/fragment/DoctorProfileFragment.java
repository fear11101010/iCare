package com.example.arafathossain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arafathossain.icare.ApplicationMain;
import com.example.arafathossain.icare.DoctorProfile;
import com.example.arafathossain.icare.R;

import java.util.ArrayList;


public class DoctorProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private ListView doctorProfileList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_profile_layout, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        doctorProfileList = (ListView) view.findViewById(R.id.doctorProfileList);
        setAdapter();
        refreshLayout.setOnRefreshListener(this);
        return view;
    }


    @Override
    public void onRefresh() {
        setAdapter();
        refreshLayout.setRefreshing(false);
    }

    public void setAdapter() {
        final ArrayList<DoctorProfile> profiles = ApplicationMain.getDatabase().getAllDoctorProfile();
        if (profiles != null) {
            ArrayAdapter<DoctorProfile> doctorProfiles = new ArrayAdapter<DoctorProfile>(getActivity(), R.layout.doctor_profile_adapter_layout,R.id.textView, profiles) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    final DoctorProfile profile = profiles.get(position);
                    ((TextView) v.findViewById(R.id.textView)).setText(profile.getName());
                    v.findViewById(R.id.removeProfile).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ApplicationMain.getDatabase().removeDoctorProfile(profile.getId())>0) {
                                remove(profile);
                                notifyDataSetChanged();
                            }
                            else Toast.makeText(getActivity(),"Unable to remove",Toast.LENGTH_LONG).show();
                        }
                    });
                    return v;
                }
            };
            doctorProfileList.setAdapter(doctorProfiles);
        }
    }
}
