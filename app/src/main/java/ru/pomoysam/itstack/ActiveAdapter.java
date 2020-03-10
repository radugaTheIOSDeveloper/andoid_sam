package ru.pomoysam.itstack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ActiveAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ActiveItem> objects;


    ActiveAdapter(Context context, ArrayList<ActiveItem> products) {
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
            view = lInflater.inflate(R.layout.active_item, parent, false);
        }


        ActiveItem p = getActiveItem(position);



        ((TextView) view.findViewById(R.id.sizeMinut)).setText(p.sizeMinuts);
        ((TextView) view.findViewById(R.id.date)).setText(p.date);
        ((TextView) view.findViewById(R.id.textinfo)).setText(p.sizeCoins);

       // ((ImageView) view.findViewById(R.id.backgroundImageMap)).setImageResource(p.image);

        return view;
    }

    ActiveItem getActiveItem(int position) {
        return ((ActiveItem) getItem(position));
    }
}
