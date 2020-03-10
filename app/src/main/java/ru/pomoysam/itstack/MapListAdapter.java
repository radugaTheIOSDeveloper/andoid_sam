package ru.pomoysam.itstack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class MapListAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<MapListItem> objects;


    MapListAdapter(Context context, ArrayList<MapListItem> products) {
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
            view = lInflater.inflate(R.layout.maprow, parent, false);
        }

        view = lInflater.inflate(R.layout.maprow, parent, false);

        MapListItem p = getMapListItem(position);


        ((TextView) view.findViewById(R.id.nameMap)).setText(p.nameMarker);

        ((ImageView) view.findViewById(R.id.logoMap)).setImageResource(p.logo);

        ((ImageView) view.findViewById(R.id.backgroundImageMap)).setImageResource(p.background);


        return view;
    }

    MapListItem getMapListItem(int position) {
        return ((MapListItem) getItem(position));

    }


}
