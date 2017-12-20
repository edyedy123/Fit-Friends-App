package com.example.edu.termproject1;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

public class WorkoutListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<Workout> list;
    private SparseBooleanArray mSelectedItemsIds;

    public WorkoutListAdapter(Context c, List<Workout> w) {
        context = c;
        list = w;
        mSelectedItemsIds = new SparseBooleanArray();
    }
    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
            return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Set> setList =  list.get(groupPosition).getSetArray();
        return setList.get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        Workout workout = (Workout) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.main_heading, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(workout.getName());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        Set set = (Set) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.activity_workout_list_adapter, null);
        }

        TextView weight = (TextView) view.findViewById(R.id.weight2);
        weight.setText(set.getWeight());
        TextView rep = (TextView) view.findViewById(R.id.reps2);
        rep.setText(set.getReps());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
