package com.example.arafathossain.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arafathossain.icare.R;

/**
 * Created by Arafat Hossain on 6/10/2015.
 */
public class NavListAdaper extends BaseAdapter {

    private Context context;
    private String[] navItemTitles;
    private TypedArray navItemIcons;

    public NavListAdaper(Context context) {
        this.context = context;
        navItemTitles = context.getResources().getStringArray(R.array.navList);
        navItemIcons = context.getResources().obtainTypedArray(R.array.iconList);
    }

    @Override
    public int getCount() {
        return navItemTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return navItemTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.nav_list_item_layout,parent,false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        imageView.setImageDrawable(navItemIcons.getDrawable(position));
        textView.setText(navItemTitles[position]);
        return convertView;
    }
}
