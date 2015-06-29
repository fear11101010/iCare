package com.example.arafathossain.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arafathossain.icare.R;

public class CreateVaccineSchedule extends DialogFragment implements View.OnClickListener {
    TextView dn, vn;
    LinearLayout dl;
    CheckedTextView reminder;
    RadioGroup group;

    public static CreateVaccineSchedule getInstance(String dn, String vn, String[] dl) {
        Bundle bundle = new Bundle();
        bundle.putString("dn", dn);
        bundle.putString("vn", vn);
        bundle.putStringArray("dl", dl);
        CreateVaccineSchedule createVaccineSchedule = new CreateVaccineSchedule();
        createVaccineSchedule.setArguments(bundle);
        return createVaccineSchedule;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_vaccine_schedule_create_layout, null);
        View dnView = view.findViewById(R.id.dn);
        View vnView = view.findViewById(R.id.vn);
        dnView.setBackgroundColor(Color.GRAY);
        vnView.setBackgroundColor(Color.GRAY);
        dn = (TextView) view.findViewById(R.id.diseaseName);
        vn = (TextView) view.findViewById(R.id.vaccineName);
        dl = (LinearLayout) view.findViewById(R.id.vaccineDoses);
        reminder = (CheckedTextView) view.findViewById(R.id.reminder);
        View svs = view.findViewById(R.id.saveVaccineSchedule);
        reminder.setOnClickListener(this);
        svs.setOnClickListener(this);
        setValue();

        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }

    public void setValue() {
        dn.setText(getArguments().getString("dn"));
        vn.setText(getArguments().getString("vn"));
        String[] doses = getArguments().getStringArray("dl");
        group = new RadioGroup(getActivity());
        group.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        group.setOrientation(LinearLayout.VERTICAL);
        dl.addView(group);
        dl.invalidate();
        for (int i = 0; i < doses.length; i++) {
            RadioButton button = new RadioButton(getActivity());
            button.setId(i);
            button.setText(doses[i]);
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setPadding(5, 5, 5, 5);
            group.addView(button);
            group.invalidate();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reminder:
                if (reminder.isChecked()) {
                    reminder.setChecked(false);
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                } else {
                    reminder.setChecked(true);
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                }
                break;
            case R.id.saveVaccineSchedule:
                Toast.makeText(getActivity(),group.getCheckedRadioButtonId()+"",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
