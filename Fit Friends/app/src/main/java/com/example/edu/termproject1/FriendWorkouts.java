package com.example.edu.termproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FriendWorkouts extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    List<AppUsers> appUser = new ArrayList<AppUsers>();
    private WorkoutListAdapter listAdapter;
    private ExpandableListView expandableListView;
    AppUsers chosenFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_workouts);
        //receaves email of user to display workouts
        Intent i = getIntent();
        Bundle box= i.getExtras();
        final String ChoiseName = box.getString("userName");

        mDatabase.orderByChild("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appUser.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userName = (String) child.child("userName").getValue();
                    String fName = (String) child.child("firstName").getValue();
                    String lName = (String) child.child("lastName").getValue();
                    String email = (String) child.child("email").getValue();
                    String image = (String) child.child("image").getValue();
                    Long workoutNumber = (Long) child.child("workoutNumber").getValue();
                    List<Workout> workoutList = new ArrayList<Workout>();
                    List<String> list =new ArrayList<String>();
                    Map workoutsTemp = (Map) child.child("Workouts").getValue();
                    if(workoutsTemp != null) {
                        List workoutHashList = new ArrayList(workoutsTemp.values());
                        for(int i= 0; i< workoutHashList.size();i++) {
                            Workout w = new Workout();
                            Map m =(Map)workoutHashList.get(i);
                            String name =(String)m.get("name");
                            String date =(String)m.get("date");
                            w.setName(name);
                            w.setDate(date);
                            List setArrayHash= (List)m.get("setArray");
                            for(int z= 0; z< setArrayHash.size();z++) {
                                Map m1 = (Map)setArrayHash.get(z);
                                String weight =(String)m1.get("weight");
                                String rep =(String)m1.get("reps");
                                w.setSet(weight,rep);
                            }
                            workoutList.add(w);
                        }
                    }
                    Map temp = (Map) child.child("friends").getValue();
                    if(temp != null) {
                        Collection f = temp.values();
                        list = new ArrayList(f);
                    }
                    AppUsers loader = new AppUsers();
                    Integer i = (int) (long) workoutNumber;
                    loader.setAppUserW(userName, email,fName,lName,image,i,list, workoutList);
                    appUser.add(loader);
                }

                expandableListView = (ExpandableListView)findViewById(R.id.friendWorkout);

                for(int i =0; i<appUser.size();i++) {
                    if (ChoiseName.equals(appUser.get(i).getUserName())) {
                        chosenFriend=appUser.get(i);
                    }
                }
                TextView fName = (TextView) findViewById(R.id.friendName);
                fName.setText(chosenFriend.getFName()+" "+chosenFriend.getLName());

                if (chosenFriend.getWorkoutSize()!= 0) {
                    listAdapter = new WorkoutListAdapter(FriendWorkouts.this, chosenFriend.getWorkouts());
                    expandableListView.setAdapter(listAdapter);
                    expandAll();
                }
                else {
                    //if list view is empty, empty list message shows
                    expandableListView.setEmptyView(findViewById(R.id.emptyElement));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableListView.expandGroup(i);
        }
    }
}
