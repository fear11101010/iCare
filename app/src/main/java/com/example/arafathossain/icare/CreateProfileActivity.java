package com.example.arafathossain.icare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CreateProfileActivity extends AppCompatActivity {
    private Spinner bloodGroup;

    private EditText profileName;
    private EditText userName;
    private EditText email;
    private EditText contactNo;
    private EditText height;
    private EditText weight;
    private EditText dateOfBirth;
    private RadioGroup genderGroup;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionBar);
        toolbar.setTitle(getTitle());
        toolbar.setLogo(R.mipmap.icare_icon);
        setSupportActionBar(toolbar);
        bloodGroup = (Spinner) findViewById(R.id.bloodGroup);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.bloodGroupList, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        bloodGroup.setAdapter(spinnerAdapter);
        profileName = (EditText) findViewById(R.id.profileName);
        userName = (EditText) findViewById(R.id.userName);

        email = (EditText) findViewById(R.id.email);
        contactNo = (EditText) findViewById(R.id.contactNo);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            createProfile();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createProfile() {


        if (!ProfileValidation.validateProfileName(profileName.getText().toString())) {
            Toast.makeText(this, "Profile name invalid or already exists", Toast.LENGTH_LONG).show();
            return;
        }
        if (!ProfileValidation.validateUserNAme(userName.getText().toString())) {
            Toast.makeText(this, "User name can not be empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (!ProfileValidation.validateEmail(email.getText().toString())) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_LONG).show();
            return;
        }
        if (genderGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select your gender", Toast.LENGTH_LONG).show();
            return;
        }

        if (!ProfileValidation.validateDateOfBirth(dateOfBirth.getText().toString())) {
            Toast.makeText(this, "Date of birth can not be empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (!ProfileValidation.validateWeight(weight.getText().toString())) {
            Toast.makeText(this, "Weight Invalid", Toast.LENGTH_LONG).show();
            return;
        }
        if (!ProfileValidation.validateHeight(height.getText().toString())) {
            Toast.makeText(this, "Height Invalid", Toast.LENGTH_LONG).show();
            return;
        }
        Profile profile = new Profile();
        profile.setBloodGroup(bloodGroup.getSelectedItem().toString());
        profile.setProfileName(profileName.getText().toString());
        profile.setUserName(userName.getText().toString());
        profile.setEmail(email.getText().toString());
        profile.setContactNo(contactNo.getText().toString());
        profile.setHeight(height.getText().toString());
        profile.setWeight(weight.getText().toString());
        profile.setDateOfBirth(dateOfBirth.getText().toString());
        profile.setGender((genderGroup.getCheckedRadioButtonId() == R.id.male ? "Male" : "Female"));
        int status = ApplicationMain.getDatabase().addProfile(profile);
        if (status == 0)
            Toast.makeText(CreateProfileActivity.this, "Unable to create profile", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(CreateProfileActivity.this, "Profile created successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("profileName", profileName.getText());
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    private void showDatePicker() {
        final DatePicker datePicker = new DatePicker(this);
        datePicker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (dateOfBirth.getText() != null || !dateOfBirth.getText().toString().isEmpty()) {
            try {
                datePicker.getCalendarView().setDate(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth.getText().toString()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(datePicker);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.YEAR, datePicker.getYear());

                dateOfBirth.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
