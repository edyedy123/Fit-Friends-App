package com.example.edu.termproject1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EDU on 3/31/2017.
 */

public class ArrayAdapterSets extends BaseAdapter {
    Context context;
    Workout workout;
    int count=0;
    private SparseBooleanArray mSelectedItemsIds;

    public ArrayAdapterSets(WorkoutDetail c, Workout w){
        context = c;
        workout = w;;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        return workout.size();
    }
    @Override
    public Set getItem(int position) {
        return workout.getSet(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View row;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.array_adapter_sets, null);

        TextView countEntry = (TextView) row.findViewById(R.id.count1);

        ArrayList<Integer> temp = new ArrayList<Integer>();
        for(int i=0;i<workout.size();i++){
            temp.add(i+1);
        }
        ArrayList<String> strings1 = new ArrayList<String>();
        for (Integer d : temp) {
            strings1.add(d.toString());
        }
        countEntry.setText(strings1.get(position));

         TextView weightEntry = (TextView) row.findViewById(R.id.weight1);
        weightEntry.setText(workout.getSet(position).getWeight()+" lbs");

       TextView repsEntry = (TextView) row.findViewById(R.id.reps1);
        repsEntry.setText(workout.getSet(position).getReps()+" reps");

        return row;
    }

    public void remove(Set object) {
       workout.remove(object);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
