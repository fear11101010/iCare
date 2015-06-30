package com.example.arafathossain.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arafathossain.icare.ApplicationMain;
import com.example.arafathossain.icare.DoctorProfile;
import com.example.arafathossain.icare.R;

import java.util.ArrayList;


public class DoctorProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener{
    private SwipeRefreshLayout refreshLayout;
    private ListView doctorProfileList;
    private String[] actionList={"Call Doctor","SMS Doctor","Email Doctor","View Detail","Remove Profile"};
    public interface ShowDoctorProfileListener{
        void showProfile(DoctorProfile profile);
    }
    ShowDoctorProfileListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_profile_layout, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setRefreshing(false);
        doctorProfileList = (ListView) view.findViewById(R.id.doctorProfileList);
        doctorProfileList.setOnItemClickListener(this);
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
            ArrayAdapter<DoctorProfile> doctorProfiles = new ArrayAdapter<DoctorProfile>(getActivity(), android.R.layout.simple_list_item_1, profiles) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    final DoctorProfile profile = profiles.get(position);
                    ((TextView) v).setText(profile.getName());
                    return v;
                }
            };
            doctorProfileList.setAdapter(doctorProfiles);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DoctorProfile profile = ((ArrayAdapter<DoctorProfile>)parent.getAdapter()).getItem(position);
        showActionChooser(profile);
    }
    private void showActionChooser(final DoctorProfile profile){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(actionList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent call = new Intent(Intent.ACTION_CALL);
                        call.setData(Uri.parse("tel:"+profile.getContactNo()));
                        startActivity(call);
                        break;
                    case 1:
                        Intent sms = new Intent(Intent.ACTION_VIEW);
                        sms.setData(Uri.parse("smsto:"+profile.getContactNo()));
                        startActivity(sms);
                        break;
                    case 2:
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{profile.getEmail()});
                        email.setType("message/rfc822");
                        startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        break;
                    case 3:
                        listener.showProfile(profile);
                        break;
                    case 4:
                        if (ApplicationMain.getDatabase().removeDoctorProfile(String.valueOf(profile.getId()))>0) {
                            ((ArrayAdapter<DoctorProfile>) doctorProfileList.getAdapter()).remove(profile);
                            ((ArrayAdapter<DoctorProfile>) doctorProfileList.getAdapter()).notifyDataSetChanged();
                        }
                        break;
                }
            }
        });
        builder.setTitle("Choose an action");
        builder.create().show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ShowDoctorProfileListener)activity;
    }
}
