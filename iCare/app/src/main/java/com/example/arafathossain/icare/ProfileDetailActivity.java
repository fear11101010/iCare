package com.example.arafathossain.icare;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.arafathossain.adapter.NavListAdaper;
import com.example.arafathossain.fragment.DietInformationFragment;
import com.example.arafathossain.fragment.GeneralInformationFragment;
import com.example.arafathossain.fragment.HomeProfileDetailFragment;
import com.example.arafathossain.interfacee.OnMenuItemClickListener;

public class ProfileDetailActivity extends AppCompatActivity implements HomeProfileDetailFragment.OnLayoutButtonClickListener, AdapterView.OnItemClickListener {
    DrawerLayout drawerLayout;
    private static final int HOME_FRAGMENT = 1;
    private static final int DIET_FRAGMENT = 2;
    private static final int GENERAL_FRAGMENT = 3;
    private static final int EDIT_MODE = 4;
    private static final int SAVE_MODE = 5;
    private static final String HOME_FRAGMENT_TAG = "homeFragment";
    private static final String DIET_FRAGMENT_TAG = "dietFragment";
    private static final String GENERAL_FRAGMENT_TAG = "generalFragment";
    private static final String VACCINATION_FRAGMENT_TAG = "vaccinationFragment";
    private static final String HISTORY_FRAGMENT_TAG = "historyFragment";
    private int which;
    private int mode;
    private OnMenuItemClickListener menuItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.mipmap.icare_icon);
        toolbar.setNavigationIcon(R.drawable.nevigation_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
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
        if (which==GENERAL_FRAGMENT) {
            MenuItem editItem = menu.getItem(0);
            MenuItem saveItem = menu.getItem(1);
            if (mode==SAVE_MODE){
                editItem.setVisible(false);
                saveItem.setVisible(true);


            }
            else if (mode==EDIT_MODE){
                editItem.setVisible(true);
                saveItem.setVisible(false);
            }
        }

        //Toast.makeText(this,which+"",Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.save:
                mode = EDIT_MODE;
                menuItemClickListener.onMenuItemClick(item);
                invalidateOptionsMenu();
                break;
            case R.id.edit:
                mode = SAVE_MODE;
                menuItemClickListener.onMenuItemClick(item);
                invalidateOptionsMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        //Toast.makeText(this,count+"",Toast.LENGTH_LONG).show();
        if (count > 0) {
            FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(count - 1);
            Toast.makeText(this,entry.getName(),Toast.LENGTH_LONG).show();
            if (entry.getName().equalsIgnoreCase(HOME_FRAGMENT_TAG)) {

                which = HOME_FRAGMENT;
                invalidateOptionsMenu();
            } else if (entry.getName().equalsIgnoreCase(DIET_FRAGMENT_TAG)) {
                which = HOME_FRAGMENT;
                invalidateOptionsMenu();
            }
            else if (entry.getName().equalsIgnoreCase(GENERAL_FRAGMENT_TAG)) {
                which = HOME_FRAGMENT;
                invalidateOptionsMenu();
            }
        }
        mode = EDIT_MODE;
        super.onBackPressed();
        //Toast.makeText(this,getSupportFragmentManager().findFragmentById(R.id.fragmentContainer).getClass().getSimpleName(),Toast.LENGTH_LONG).show();
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
                showGeneralFragment(getIntent().getStringExtra("profileName"));
                break;
            case R.id.dietInformation:
                showDietFragment(getIntent().getStringExtra("profileName"));

                break;
            case R.id.vaccinationInformation:
                showVaccinFragment();
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
    }

    public void showDoctorFragment() {

    }

    public void showVaccinFragment() {

    }

    public void showGeneralFragment(String profileName) {
        Fragment generalFragment = GeneralInformationFragment.getInstance(profileName);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, generalFragment, GENERAL_FRAGMENT_TAG);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(GENERAL_FRAGMENT_TAG);
        fragmentTransaction.commit();
        which = GENERAL_FRAGMENT;
        invalidateOptionsMenu();
        menuItemClickListener = (OnMenuItemClickListener)generalFragment;
    }

    public void showGrowthFragment() {

    }

    public void showHistoryFragment() {

    }

    public void removeProfile() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getAdapter().getItem(position) + " " + position, Toast.LENGTH_LONG).show();
        switch (position) {
            case 0:
                showGeneralFragment(getIntent().getStringExtra("profileName"));
                drawerLayout.closeDrawers();
                break;
            case 1:
                showDietFragment(getIntent().getStringExtra("profileName"));
                drawerLayout.closeDrawers();
                break;
            case 2:
                showVaccinFragment();
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
}
