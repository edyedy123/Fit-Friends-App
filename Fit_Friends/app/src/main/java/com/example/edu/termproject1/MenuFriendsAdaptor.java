package com.example.edu.termproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;

/**
 * Created by EDU on 4/4/2017.
 */
public class MenuFriendsAdaptor extends BaseAdapter {
    Context context;
    List<String> friends;
    List<AppUsers> appUsers;

    public MenuFriendsAdaptor(Context c, List<String> w, List<AppUsers> t) {
        context = c;
        friends = w;
        appUsers = t;
    }

    @Override
    public int getCount() {
        return friends.size()-1;
    }

    @Override
    public String getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View row;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.array_friends, null);


        List <String> fName = new ArrayList<String>();
        List <String> lName = new ArrayList<String>();
        List <String> userNames = new ArrayList<String>();
        //loops through all users and loads only firends into arrays
        for (AppUsers person2 : appUsers) {
            // Loop arrayList1 items
            boolean found = false;
            for (String uName : friends) {
                if (person2.getUserName().equals(uName)) {
                    String firstName = person2.getFName();
                    fName.add(firstName.substring(0,1).toUpperCase() + firstName.substring(1));

                    String lastName = person2.getFName();
                    lName.add(lastName.substring(0,1).toUpperCase() + lastName.substring(1));
                    userNames.add(person2.getUserName());
                }
            }
        }



        TextView userNameView1 = (TextView) row.findViewById(R.id.userName1);

        String uName =userNames.get(position);
        if(!isDigit(uName.charAt(0))) {
            uName = uName.substring(0, 1).toUpperCase() + uName.substring(1);
        }
        userNameView1.setText("    "+uName);

        TextView fNameEntry2 = (TextView) row.findViewById(R.id.fName);
        fNameEntry2.setText(fName.get(position)+" "+lName.get(position)+"    ");

        return row;
    }

}