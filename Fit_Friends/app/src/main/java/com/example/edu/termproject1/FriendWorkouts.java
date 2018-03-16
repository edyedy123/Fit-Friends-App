package com.example.edu.termproject1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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

import static java.lang.Character.isDigit;

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
    int pos=0;
    int stop=0;
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

        String uName=ChoiseName;
        if(!isDigit(ChoiseName.charAt(0))) {
            uName =ChoiseName.substring(0, 1).toUpperCase() + ChoiseName.substring(1);
        }

        fName2.setText(uName+"'s Workouts ");

        expandableListView = (ExpandableListView) findViewById(R.id.myList);

        mUserId.child(ChoiseName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uID=dataSnapshot.getValue(String.class);
                mFriend = FirebaseDatabase.getInstance().getReference().child("Users").child(uID);
                mFriendWorkouts=mFriend.child("Workouts");



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
        //////
        expandableListView.setChoiceMode(expandableListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Capture ListView item click- multi click delete
        expandableListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = expandableListView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                pos=position;
                listAdapter.toggleSelection(position);

            }
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                stop=0;
                while(stop==0) {
                    switch (item.getItemId()) {
                        case R.id.copy:
                            // Calls getSelectedIds method from ListViewAdapter Class
                            SparseBooleanArray selected1 = listAdapter.getSelectedIds();
                            // Captures all selected ids with a loop
                            if (selected1.valueAt(0)) {
                                Workout selecteditem = listAdapter.getGroupMain(selected1.keyAt(0));

                                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                                intent.putExtra("copy", selecteditem);
                                startActivity(intent);
                            }
                            // Close CAB
                            mode.finish();
                            return true;
                        default:
                            return false;
                    }
                }
                return false;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.copy_menue, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                listAdapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                stop=1;
                expandableListView.setItemChecked(pos,false);
                return true;
            }
        });


     //////
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
