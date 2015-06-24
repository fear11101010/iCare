package com.example.arafathossain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.arafathossain.icare.R;

import java.util.ArrayList;


public class FoodAdapter extends ArrayAdapter {
    private Context context;
    public FoodAdapter(Context context) {
        super(context, R.layout.food_adapter_item_layout);
        this.context = context;
    }
    public FoodAdapter(Context context,String[] strings) {
        super(context, R.layout.food_adapter_item_layout,strings);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.food_adapter_item_layout,parent,false);
            holder.remove = (ImageButton) convertView.findViewById(R.id.ppp);
            holder.textView =(TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String s = getItem(position).toString();
        holder.textView.setText(s);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(s);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    public class ViewHolder {
        public TextView textView;
        public ImageButton remove;
    }
}
