package com.example.edu.termproject1;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.addAll;

/**
 * Created by EDU on 4/3/2017.
 */
public class ArrayAdapterFreinds extends ArrayAdapter<AppUsers> {
    private LayoutInflater layoutInflater;
    List<AppUsers> friends;

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((AppUsers)resultValue).getUserName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<AppUsers> suggestions = new ArrayList<AppUsers>();
                for (AppUsers appUser : friends) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (appUser.getUserName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(appUser);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<AppUsers>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(friends);
            }
            notifyDataSetChanged();
        }
    };

    public ArrayAdapterFreinds(Context context, int textViewResourceId, List<AppUsers> customers) {
        super(context, textViewResourceId, customers);

        friends = new ArrayList<AppUsers>(customers.size());
        friends.addAll(customers);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.array_friends, null);
        }

        AppUsers appUser = getItem(position);



        TextView name = (TextView) view.findViewById(R.id.fName);
        name.setText(appUser.getFName()+" "+appUser.getLName()+"   ");

        TextView userName = (TextView) view.findViewById(R.id.userName1);
        userName.setText("    "+appUser.getUserName());

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}