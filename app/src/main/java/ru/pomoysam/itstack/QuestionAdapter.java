package ru.pomoysam.itstack;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class QuestionAdapter extends BaseExpandableListAdapter {



    private ArrayList<String> mGroups;
    ArrayList<ArrayList<String>> mChild = new ArrayList<ArrayList<String>>();

    private Context mContext;

    public QuestionAdapter(Context context,ArrayList<String> groups,   ArrayList<ArrayList<String>> child){
        mContext = context;
        mGroups = groups;
        mChild = child;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChild.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }



    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChild.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        if (isExpanded){
            Log.d("LOG TAG ", "isExpanded true");
            convertView.setBackgroundColor(Color.parseColor("#14E73336"));
        }
        else{
            Log.d("LOG TAG ", "isExpanded false");
            convertView.setBackgroundColor(Color.WHITE);


        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        textGroup.setText("В: " + mGroups.get(groupPosition));

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        textChild.setText("О: " +mChild.get(groupPosition).get(childPosition));


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
