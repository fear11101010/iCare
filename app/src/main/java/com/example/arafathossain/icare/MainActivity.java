package com.example.arafathossain.icare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView profileList;
    private View noProfileView;

    private Button addProfile;
    private Profile currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.mipmap.icare_icon);
        setSupportActionBar(toolbar);
        profileList = (ListView) findViewById(R.id.profileList);
        noProfileView = findViewById(R.id.noProfile);
        addProfile = (Button) findViewById(R.id.add);
        addProfile.setOnClickListener(this);
        setProfileAdapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.newProfile) {
            Intent newProfileActivity = new Intent(this, CreateProfileActivity.class);
            startActivityForResult(newProfileActivity, 1);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            setProfileAdapter();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            ArrayAdapter<Profile> adapter = (ArrayAdapter<Profile>) profileList.getAdapter();
            adapter.remove(currentProfile);
            adapter.notifyDataSetChanged();
            if (adapter.getCount() == 0) {
                profileList.setVisibility(View.GONE);
                noProfileView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setProfileAdapter() {
        ArrayList<Profile> profiles = ApplicationMain.getDatabase().getAllProfile();
        if (profiles != null) {
            ArrayAdapter<Profile> profileAdapter = new ArrayAdapter<Profile>(this, R.layout.listview_item_layout, R.id.textView, profiles) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final View view = super.getView(position, convertView, parent);
                    final Profile profile = getItem(position);
                    ((TextView) view.findViewById(R.id.textView)).setText(profile.getProfileName());
                    ImageButton goDetail = (ImageButton) view.findViewById(R.id.goDetail);
                    goDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent detailIntent = new Intent(MainActivity.this, ProfileDetailActivity.class);
                            currentProfile = profile;
                            detailIntent.putExtra("profileId", String.valueOf(profile.getId()));
                            detailIntent.putExtra("profileName", profile.getProfileName());
                            startActivityForResult(detailIntent, 2);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        }
                    });
                    return view;
                }
            };
            profileList.setAdapter(profileAdapter);
            profileList.setVisibility(View.VISIBLE);
            noProfileView.setVisibility(View.GONE);
        } else {
            profileList.setVisibility(View.GONE);
            noProfileView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent newProfileActivity = new Intent(this, CreateProfileActivity.class);
        startActivityForResult(newProfileActivity, 1);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
