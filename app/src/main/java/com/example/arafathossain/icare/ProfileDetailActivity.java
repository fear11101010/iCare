package com.example.arafathossain.icare;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arafathossain.adapter.NavListAdaper;
import com.example.arafathossain.fragment.CreateDietFragment;
import com.example.arafathossain.fragment.CreateDoctorProfileFragment;
import com.example.arafathossain.fragment.DietInformationFragment;
import com.example.arafathossain.fragment.DiseaseListFragment;
import com.example.arafathossain.fragment.DoctorChamberAddressFragment;
import com.example.arafathossain.fragment.DoctorManagementFragment;
import com.example.arafathossain.fragment.DoctorNewAppointmentFragment;
import com.example.arafathossain.fragment.DoctorProfileDetailFragment;
import com.example.arafathossain.fragment.DoctorProfileFragment;
import com.example.arafathossain.fragment.GeneralInformationFragment;
import com.example.arafathossain.fragment.HomeProfileDetailFragment;
import com.example.arafathossain.fragment.VaccinationInformationFragment;
import com.example.arafathossain.fragment.VaccineDetailFragment;
import com.example.arafathossain.interfacee.OnDietCreateListener;
import com.example.arafathossain.interfacee.OnImageSaveListener;
import com.example.arafathossain.interfacee.OnMenuItemClickListener;
import com.example.arafathossain.interfacee.OnUpdateListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfileDetailActivity extends AppCompatActivity implements DoctorProfileFragment.ShowDoctorProfileListener, DoctorNewAppointmentFragment.ImageChooserListener, DiseaseListFragment.OnVaccineScheduleCreateListener, DoctorChamberAddressFragment.OnAddressCreateListener, OnUpdateListener, HomeProfileDetailFragment.OnLayoutButtonClickListener, AdapterView.OnItemClickListener, OnDietCreateListener {
    private static final int HOME_FRAGMENT = 1;
    private static final int DIET_FRAGMENT = 2;
    private static final int CREATE_DIET_FRAGMENT = 4;
    private static final int GENERAL_FRAGMENT = 3;
    private static final int DOCTOR_FRAGMENT = 3;
    private static final int EDIT_MODE = 4;
    private static final int SAVE_MODE = 5;
    private static final String HOME_FRAGMENT_TAG = "homeFragment";
    private static final String DIET_FRAGMENT_TAG = "dietFragment";
    private static final String CREATE_DIET_FRAGMENT_TAG = "createDietFragment";
    private static final String GENERAL_FRAGMENT_TAG = "generalFragment";
    private static final String VACCINATION_FRAGMENT_TAG = "vaccinationFragment";
    private static final String HISTORY_FRAGMENT_TAG = "historyFragment";
    private static final String DOCTOR_FRAGMENT_TAG = "doctorFragment";

    DrawerLayout drawerLayout;
    private int which;
    private int mode;
    private OnMenuItemClickListener menuItemClickListener;
    private OnDietCreateListener onDietCreateListener;
    private DoctorChamberAddressFragment.OnAddressCreateListener addressCreateListener;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.mipmap.icare_icon);
        toolbar.setNavigationIcon(R.drawable.nevigation_icon);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        ListView listView = (ListView) findViewById(R.id.navList);
        NavListAdaper navListAdaper = new NavListAdaper(this);
        listView.setAdapter(navListAdaper);
        listView.setOnItemClickListener(this);
        initializeHomeFragment(getIntent().getStringExtra("profileName"));
        which = HOME_FRAGMENT;
        mode = EDIT_MODE;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        int resMenu = R.menu.menu_profile_detail;
        switch (which) {
            case HOME_FRAGMENT:
                resMenu = R.menu.menu_profile_detail;
                break;
            case DIET_FRAGMENT:
                resMenu = R.menu.diet_fragment_menu;
                break;
            case GENERAL_FRAGMENT:
                resMenu = R.menu.menu_general_fragment;
                break;
        }
        getMenuInflater().inflate(resMenu, menu);
        if (which == GENERAL_FRAGMENT) {
            MenuItem editItem = menu.getItem(0);
            MenuItem saveItem = menu.getItem(1);
            if (mode == SAVE_MODE) {
                editItem.setVisible(false);
                saveItem.setVisible(true);


            } else if (mode == EDIT_MODE) {
                editItem.setVisible(true);
                saveItem.setVisible(false);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.save:
                menuItemClickListener.onMenuItemClick(item);
                break;
            case R.id.edit:
                mode = SAVE_MODE;
                menuItemClickListener.onMenuItemClick(item);
                invalidateOptionsMenu();
                break;
            case R.id.addDiet:
                showCreateDietFragment(getIntent().getStringExtra("profileName"));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawers();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(count - 1);
            if (entry.getName().equalsIgnoreCase(HOME_FRAGMENT_TAG)) {

                which = HOME_FRAGMENT;
                invalidateOptionsMenu();
            } else if (entry.getName().equalsIgnoreCase(DIET_FRAGMENT_TAG)) {
                which = HOME_FRAGMENT;
                invalidateOptionsMenu();
            } else if (entry.getName().equalsIgnoreCase(GENERAL_FRAGMENT_TAG)) {
                which = HOME_FRAGMENT;
                invalidateOptionsMenu();
            } else if (entry.getName().equalsIgnoreCase(CREATE_DIET_FRAGMENT_TAG)) {
                which = DIET_FRAGMENT;
                invalidateOptionsMenu();
            }
        }
        mode = EDIT_MODE;
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void initializeHomeFragment(String profileName) {
        Fragment homeFragment = HomeProfileDetailFragment.getInstance(profileName);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, homeFragment, HOME_FRAGMENT_TAG);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    public void onLayoutButtonClick(int id) {
        switch (id) {
            case R.id.generalInformation:
                showGeneralFragment(getIntent().getStringExtra("profileId"));
                break;
            case R.id.dietInformation:
                showDietFragment(getIntent().getStringExtra("profileName"));

                break;
            case R.id.vaccinationInformation:
                showVaccinFragment(getIntent().getStringExtra("profileId"));
                break;
            case R.id.doctorInformation:
                showDoctorFragment();
                break;
            case R.id.healthHistory:
                showHistoryFragment();
                break;
            case R.id.growthInformation:
                showGrowthFragment();
                break;
        }
    }

    public void showDietFragment(String profileName) {
        Fragment dietFragment = DietInformationFragment.getInstance(profileName);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, dietFragment, DIET_FRAGMENT_TAG);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(DIET_FRAGMENT_TAG);
        fragmentTransaction.commit();
        which = DIET_FRAGMENT;
        invalidateOptionsMenu();
        onDietCreateListener = (OnDietCreateListener) dietFragment;
    }

    public void showCreateDietFragment(String profileName) {
        DialogFragment dietFragment = CreateDietFragment.getInstance(profileName);
        dietFragment.show(getSupportFragmentManager(), CREATE_DIET_FRAGMENT_TAG);

    }

    public void showDoctorFragment() {
        Fragment generalFragment = new DoctorManagementFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, generalFragment, DOCTOR_FRAGMENT_TAG);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(DOCTOR_FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    public void showVaccinFragment(String id) {
        Fragment generalFragment = VaccinationInformationFragment.getInstance(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, generalFragment, HOME_FRAGMENT_TAG);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(HOME_FRAGMENT_TAG);
        fragmentTransaction.commit();
        which = HOME_FRAGMENT;
        invalidateOptionsMenu();
    }

    public void showGeneralFragment(String id) {
        Fragment generalFragment = GeneralInformationFragment.getInstance(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, generalFragment, GENERAL_FRAGMENT_TAG);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(GENERAL_FRAGMENT_TAG);
        fragmentTransaction.commit();
        which = GENERAL_FRAGMENT;
        invalidateOptionsMenu();
        menuItemClickListener = (OnMenuItemClickListener) generalFragment;
    }

    public void showGrowthFragment() {

    }

    public void showHistoryFragment() {

    }

    public void removeProfile() {
        ArrayList<Integer> idList = ApplicationMain.getDatabase().getAllAlarmByProfileId(getIntent().getStringExtra("profileId"));
        int row = ApplicationMain.getDatabase().removeProfile(getIntent().getStringExtra("profileId"));
        if (row > 0) {
            if (idList != null)
                for (int i : idList) {
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(this, i, new Intent(this, AlarmReceiver.class), 0);
                    ApplicationMain.getAlarmManager().cancel(alarmIntent);
                }
            Toast.makeText(this, "Profile Delete Complete", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("profileId", getIntent().getStringExtra("profileId"));
            intent.putExtra("profileName", getIntent().getStringExtra("profileName"));
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Unable to Delete Profile Complete", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                showGeneralFragment(getIntent().getStringExtra("profileId"));
                drawerLayout.closeDrawers();
                break;
            case 1:
                showDietFragment(getIntent().getStringExtra("profileId"));
                drawerLayout.closeDrawers();
                break;
            case 2:
                showVaccinFragment(getIntent().getStringExtra("profileId"));
                drawerLayout.closeDrawers();
                break;
            case 3:
                showDoctorFragment();
                drawerLayout.closeDrawers();
                break;
            case 4:
                showGrowthFragment();
                drawerLayout.closeDrawers();
                break;
            case 5:
                showHistoryFragment();
                drawerLayout.closeDrawers();
                break;

            case 6:
                removeProfile();
                drawerLayout.closeDrawers();
                break;
        }
    }

    @Override
    public void onCreateDiet() {
        onDietCreateListener.onCreateDiet();
    }

    @Override
    public void onUpdateDiet() {
        onDietCreateListener.onUpdateDiet();
    }

    @Override
    public void onUpdate() {
        mode = EDIT_MODE;
        invalidateOptionsMenu();
    }

    public void createProfile(View v) {
        Fragment fragment = new CreateDoctorProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack("doctorCreateProfile");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
        addressCreateListener = (DoctorChamberAddressFragment.OnAddressCreateListener) fragment;
    }

    public void showDoctorCreateProfile(View v) {

    }

    public void showAppointmentCreate(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment fragment = new DoctorNewAppointmentFragment();
        imageSaveListener = (OnImageSaveListener)fragment;
        fragment.show(fragmentManager,"newAppointment");
    }
private OnImageSaveListener imageSaveListener;
    @Override
    public void onAddressCreate(String address) {
        addressCreateListener.onAddressCreate(address);
    }

    @Override
    public void onCreateSchedule(String dn, String vn, String dsy, String dcom, String dcause, String doses) {
        Fragment fragment = VaccineDetailFragment.getInstance(dn, vn, dsy, dcom, dcause, doses);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(HOME_FRAGMENT_TAG);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ddddd", requestCode + "");
        if (requestCode == RequestCode.CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, ImageEditorActivity.class);
            intent.putExtra("profileId", getIntent().getStringExtra("profileId"));
            intent.setData(uri);
            startActivityForResult(intent, RequestCode.SAVE_IMAGE);

        } else if (requestCode == RequestCode.GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, ImageEditorActivity.class);
            intent.setData(data.getData());
            intent.putExtra("profileId", getIntent().getStringExtra("profileId"));
            startActivityForResult(intent, RequestCode.SAVE_IMAGE);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        } else if (requestCode == RequestCode.SAVE_IMAGE && resultCode == RESULT_OK) {
            Toast.makeText(this, data.getStringExtra("fileName"), Toast.LENGTH_LONG).show();
            imageSaveListener.onImageSave(data.getStringExtra("fileName"));
        }
    }

    @Override
    public void cameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            uri = generateImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, RequestCode.CAMERA_REQUEST_CODE);
    }

    @Override
    public void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, RequestCode.GALLERY_REQUEST_CODE);
    }

    private Uri generateImageFile() throws IOException {
        String fileName = "profile_" + getIntent().getStringExtra("profileId") + "_ct_" + Calendar.getInstance().getTimeInMillis();
        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = File.createTempFile(fileName, ".jpg", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        } else {
            file = File.createTempFile(fileName, ".jpg", getFilesDir());
        }

        return Uri.fromFile(file);
    }

    @Override
    public void showProfile(DoctorProfile profile) {
        Fragment fragment = DoctorProfileDetailFragment.getInstance(profile);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(HOME_FRAGMENT_TAG);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
