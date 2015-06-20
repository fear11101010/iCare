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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.mipmap.icare_icon);
        setSupportActionBar(toolbar);
        profileList = (ListView) findViewById(R.id.profileList);
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

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            setProfileAdapter();
        }
    }

    public void setProfileAdapter() {
        ArrayList<String> strings = ApplicationMain.getDatabase().getAllProfileName();
        if (strings != null) {
            ArrayAdapter<String> profileAdapter = new ArrayAdapter<String>(this, R.layout.listview_item_layout, R.id.textView, strings) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final View view = super.getView(position, convertView, parent);
                    ImageButton goDetail = (ImageButton) view.findViewById(R.id.goDetail);
                    goDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent detailIntent = new Intent(MainActivity.this, ProfileDetailActivity.class);
                            detailIntent.putExtra("profileName", ((TextView) view.findViewById(R.id.textView)).getText());
                            startActivity(detailIntent);
                        }
                    });
                    return view;
                }
            };
            profileList.setAdapter(profileAdapter);
        }
    }
}
