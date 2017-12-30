package com.example.edu.termproject1;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

public class WorkoutListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<Workout> list;
    String cardio;
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
    public Workout getGroup(int groupPosition) {
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
        cardio=workout.getCardio();
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
        if(cardio.equals("1")){
            weight.setText(set.getWeight() + " Kms");
        }
        else {
            weight.setText(set.getWeight() +" lbs");
        }
        TextView rep = (TextView) view.findViewById(R.id.reps2);
        if(set.getReps().equals("1")) {
            if(cardio.equals("1")){
                rep.setText(set.getReps() + " Min");
            }
            else {
                rep.setText(set.getReps() + " Rep");
            }
        }
        else {
            if(cardio.equals("1")){
                rep.setText(set.getReps() + " Mins");
            }
            else {
                rep.setText(set.getReps() + " Reps");
            }
        }
        return view;
    }

    public void remove(Object object) {
        list.remove(object);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }


    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



}


