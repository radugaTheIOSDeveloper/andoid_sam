package ru.pomoysam.itstack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ActiveAdapterFalse extends BaseAdapter {


    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ItemActiveFalse> objects;


    ActiveAdapterFalse(Context context, ArrayList<ItemActiveFalse> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();

    }

    @Override
    public Object getItem(int position) {

        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.active_false, parent, false);
        }

        ItemActiveFalse p = itemActiveFalse(position);
        ((TextView) view.findViewById(R.id.twFalseActive)).setText(p.str);

        return view;
    }

       ItemActiveFalse itemActiveFalse(int position) {
        return ((ItemActiveFalse) getItem(position));
    }
}
