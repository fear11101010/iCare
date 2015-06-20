package com.example.arafathossain.adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.arafathossain.fragment.CreateDietFragment;
import com.example.arafathossain.icare.AlarmReceiver;
import com.example.arafathossain.icare.ApplicationMain;
import com.example.arafathossain.icare.DietInformation;
import com.example.arafathossain.icare.R;

import java.util.ArrayList;

/**
 * Created by Arafat Hossain on 6/18/2015.
 */
public class DietListAdapter extends ArrayAdapter {
    ArrayList<DietInformation> dietInformations;
    Context context;

    public DietListAdapter(Context context, ArrayList<DietInformation> dietInformations) {
        super(context, R.layout.diet_adapter_layout, dietInformations);
        this.dietInformations = dietInformations;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.diet_adapter_layout, parent, false);
            holder.toolbar = (Toolbar) convertView.findViewById(R.id.toolBar);
            holder.timeView = (TextView) convertView.findViewById(R.id.time);
            holder.titleView = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DietInformation dietInformation = dietInformations.get(position);
        holder.toolbar.inflateMenu(R.menu.menu_diet_adapter);
        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.editDiet:
                        DialogFragment fragment = CreateDietFragment.getInstance(dietInformation);
                        fragment.show(((AppCompatActivity)context).getSupportFragmentManager(),"createDiet");
                        break;
                    case R.id.removeDiet:
                        if (ApplicationMain.getDatabase().removeDiet(dietInformation.getId())>0) {
                            remove(dietInformation);
                            notifyDataSetChanged();
                            Intent alarmReceiver = new Intent(context, AlarmReceiver.class);
                            PendingIntent dietIntent = PendingIntent.getBroadcast(context,dietInformation.getId(),alarmReceiver,0);
                            ApplicationMain.getAlarmManager().cancel(dietIntent);
                        }
                        break;
                }
                return false;
            }
        });
        holder.titleView.setText(dietInformation.getTitle());
        holder.timeView.setText("at " + dietInformation.getTime());
        return convertView;
    }

    public class ViewHolder {
        public TextView titleView;
        public TextView timeView;
        public Toolbar toolbar;
    }
}
