package com.example.edu.termproject1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class FriendWorkouts extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    private DatabaseReference mUserId = FirebaseDatabase.getInstance().getReference().child("userNameIds");
    private DatabaseReference mFriend;
    private DatabaseReference mFriendWorkouts;
    List<AppUsers> appUser = new ArrayList<AppUsers>();
    private WorkoutListAdapter listAdapter;
    private ExpandableListView expandableListView;
    AppUsers chosenFriend;
    String uID;

    String dateCombo="0";
    HorizontalCalendar horizontalCalendar;
    List<Workout> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_workout);

        list = new ArrayList<Workout>();
        //receaves email of user to display workouts
        Intent i = getIntent();
        Bundle box= i.getExtras();
        final String ChoiseName = box.getString("userName");
        TextView fName2 = (TextView) findViewById(R.id.friendName);
        fName2.setText(ChoiseName+"'s Workouts ");


        mUserId.child(ChoiseName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uID=dataSnapshot.getValue(String.class);
                mFriend = FirebaseDatabase.getInstance().getReference().child("Users").child(uID);
                mFriendWorkouts=mFriend.child("Workouts");

                expandableListView = (ExpandableListView) findViewById(R.id.myList);

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                Calendar endDate = Calendar.getInstance();
                endDate.add(Calendar.MONTH, 50);

                Calendar startDate = Calendar.getInstance();
                if (dateCombo.equals("0")) {
                    startDate.add(Calendar.MONTH, -50);
                    Date dt1 = new Date();
                    Date dt = new Date(dt1.getTime());
                    int day1 = dt.getDate();
                    int month1 = dt.getMonth() + 1;
                    int year1 = dt.getYear() + 1900;
                    dateCombo = Integer.toString(day1) + "/" + Integer.toString(month1) + "/" + Integer.toString(year1);
                    mFriend.child("dateCombo").setValue(dateCombo);
                }
                horizontalCalendar = new HorizontalCalendar.Builder(FriendWorkouts.this, R.id.calendarView)
                        .startDate(startDate.getTime())
                        .endDate(endDate.getTime())
                        .datesNumberOnScreen(5)
                        .dayNameFormat("EEE")
                        .dayNumberFormat("dd")
                        .monthFormat("MMM")
                        .showDayName(true)
                        .showMonthName(true)
                        .textColor(Color.LTGRAY, Color.WHITE)
                        .selectedDateBackground(Color.TRANSPARENT)
                        .build();
                //horizontalCalendar scroller button listener sets date
                horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
                    @Override
                    public void onDateSelected(Date date, int position) {
                        Date nextDay = new Date(date.getTime());
                        int day = nextDay.getDate();
                        int month = nextDay.getMonth() + 1;
                        int year = nextDay.getYear() + 1900;
                        dateCombo = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
                        mFriend.child("dateCombo").setValue(dateCombo);
                    }
                });

                mFriend.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dateCombo= dataSnapshot.child("dateCombo").getValue(String.class);
                        mFriendWorkouts.orderByChild("date").equalTo(dateCombo).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Workout workout = new Workout();
                                list.clear();
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    list.add(child.getValue(Workout.class));
                                }
                                if (list.size() != 0) {
                                    listAdapter = new WorkoutListAdapter(FriendWorkouts.this, list);
                                    expandableListView.setAdapter(listAdapter);
                                    listAdapter.notifyDataSetChanged();
                                    expandAll();
                                }
                                else {
                                    if (listAdapter!=null){
                                        listAdapter.notifyDataSetChanged();
                                    }
                                    expandableListView.setEmptyView(findViewById(R.id.emptyElement));

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // ...
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




/*
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            String cardio =(String)m.get("cardio");
                            w.setCardio(cardio);
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
        */
    }

    protected void onStart(){
        super.onStart();
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableListView.expandGroup(i);
        }
    }
}
