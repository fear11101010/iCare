package com.example.arafathossain.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.arafathossain.icare.R;
import com.example.arafathossain.icare.RequestCode;
import com.example.arafathossain.interfacee.OnImageSaveListener;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


public class DoctorNewAppointmentFragment extends DialogFragment implements View.OnClickListener,OnImageSaveListener{
    private String[] choice = {"Capture Image","Upload From Phone"};
    View attachment;
    TextView prescription;

    @Override
    public void onImageSave(String file) {
        prescription.setText(file);
    }

    @Override
    public void onImageCancel() {

    }

    public interface ImageChooserListener{
        void cameraIntent();
        void galleryIntent();
    }
    ImageChooserListener imageChooserListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.fragment_doctor_appointment_create_layout, null);
        attachment = view.findViewById(R.id.attachment);
        prescription = (TextView) view.findViewById(R.id.prescription);
        attachment.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.attachment:
                showChoiceDialog();
                break;

        }
    }
    private void showChoiceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        imageChooserListener.cameraIntent();
                        break;
                    case 1:
                        imageChooserListener.galleryIntent();
                        break;
                }
            }
        });
        builder.setTitle("Choose a action");
        builder.create().show();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        imageChooserListener = (ImageChooserListener)activity;
    }
}
