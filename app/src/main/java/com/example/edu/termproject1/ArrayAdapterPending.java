package com.example.edu.termproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Character.isDigit;


/**
 * Created by EDU on 4/3/2017.
 */

public class ArrayAdapterPending extends BaseAdapter {
    Context context;
    List<String> pending;//pending user names
    List<String> pendingIds;//pending Ids
    List<String>pendingOriginal;//Original list
    String currentUserName;
    int remove;

    //List<String> w passed in has a user name and a user Id in the next position(ex.0->edyedy123, 1->edyedy's_user_ Id, 2->john23, 1->john23's_user_Id)
    //this list is separated into three lists, a user names list, a userId list and an original list.
    public ArrayAdapterPending(Context c, List<String> w, String curr) {
        context = c;
        pending = w;
        pendingIds= new ArrayList<String>();
        pendingOriginal= w;
        currentUserName = curr;

        for(int i=0; i < getCount();i++){
            if(i%2 != 0) {
                pendingIds.add(pending.get(i));
            }
        }
        List<String> temp = new ArrayList<String>();
        for(int i=0; i < getCount();i++){
            if(i%2 == 0) {
                temp.add(pending.get(i));
            }
        }
        pending=temp;
    }

    @Override
    public int getCount() {
        if(pending!=null) {
            return pending.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public String getItem(int position) {
        return pending.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View row;
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference currentUserId = mDatabase.child(user_id);
        remove=0;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.array_adapter_pending, null);

        String uName =pending.get(position);
        if(!isDigit(uName.charAt(0))) {
            uName = uName.substring(0, 1).toUpperCase() + uName.substring(1);
        }

        TextView pendingName = (TextView) row.findViewById(R.id.pendingName);
        pendingName.setText(uName);

        Button accept=(Button)row.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference friendUserId = mDatabase.child(pendingIds.get(position));

                Toast.makeText(v.getContext(), "Friend Added", Toast.LENGTH_SHORT).show();
                currentUserId.child("friends").push().setValue(pending.get(position));

                pending.remove(position);
                pendingIds.remove(position);
                pendingOriginal.remove(position*2);
                pendingOriginal.remove(position*2);
                currentUserId.child("pending").setValue(pendingOriginal);

                friendUserId.child("friends").push().setValue(currentUserName);
                notifyDataSetChanged();
            }
        });

        Button ignore=(Button)row.findViewById(R.id.ignore);
        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Ignored", Toast.LENGTH_SHORT).show();
                pending.remove(position);
                pendingIds.remove(position);
                pendingOriginal.remove(position*2);
                pendingOriginal.remove(position*2);
                currentUserId.child("pending").setValue(pendingOriginal);
                notifyDataSetChanged();
            }
        });


        return row;
    }
}