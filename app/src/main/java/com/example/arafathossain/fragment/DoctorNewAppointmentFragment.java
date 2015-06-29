package com.example.arafathossain.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.arafathossain.icare.R;


public class DoctorNewAppointmentFragment extends DialogFragment implements View.OnClickListener{
    private String[] choice = {"Capture Image","Upload From Phone"};
    View attachment;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.fragment_doctor_appointment_create_layout, null);
        attachment = view.findViewById(R.id.attachment);
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
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent,1);
                        break;
                    case 1:
                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent,2);
                        break;
                }
            }
        });
        builder.setTitle("Choose a action");
        builder.create().show();
    }
}
