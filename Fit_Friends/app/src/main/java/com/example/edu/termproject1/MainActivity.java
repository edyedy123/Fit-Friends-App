package com.example.edu.termproject1;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity  {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String user_id = mAuth.getCurrentUser().getUid();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference currentUserId1 = mDatabase.child(user_id);

    private WorkoutListAdapter listAdapter;
    private ExpandableListView expandableListView;
    String dateCombo="0";
    HorizontalCalendar horizontalCalendar;
    List<Workout> list;
    int pos=0;
    int stop=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<Workout>();
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference currentUserIdWorkouts = mDatabase.child(user_id).child("Workouts");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase.keepSynced(true);
        currentUserIdWorkouts.keepSynced(true);
        currentUserId1.keepSynced(true);

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
            currentUserId1.child("dateCombo").setValue(dateCombo);
        }
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
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
                currentUserId1.child("dateCombo").setValue(dateCombo);
            }
        });

        currentUserId1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dateCombo= dataSnapshot.child("dateCombo").getValue(String.class);
                currentUserIdWorkouts.orderByChild("date").equalTo(dateCombo).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Workout workout = new Workout();
                        list.clear();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            list.add(child.getValue(Workout.class));
                        }
                        if (list.size() != 0) {
                            listAdapter = new WorkoutListAdapter(MainActivity.this, list);
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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalCalendar.goToday(false);
            }
        });
        //if sign out, go to login
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                     Intent loginIntent= new Intent(MainActivity.this, LoginActivity.class);
                     loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(loginIntent);
                }
            }
        };
       // /*
        //############################################################################################################

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
                        case R.id.delete:
                            // Calls getSelectedIds method from ListViewAdapter Class
                            SparseBooleanArray selected = listAdapter.getSelectedIds();
                            // Captures all selected ids with a loop
                            if (selected.valueAt(0)) {
                                Workout selecteditem = listAdapter.getGroupMain(selected.keyAt(0));
                                // Remove selected items following the ids
                                listAdapter.remove(selecteditem);
                                currentUserId1.child("Workouts").child("Workout" + selecteditem.getWorkoutNumber()).removeValue();
                                mode.finish();
                            }
                            return true;
                        case R.id.edit:
                            // Calls getSelectedIds method from ListViewAdapter Class
                            SparseBooleanArray selected1 = listAdapter.getSelectedIds();
                            // Captures all selected ids with a loop
                            if (selected1.valueAt(0)) {
                                Workout selecteditem = listAdapter.getGroupMain(selected1.keyAt(0));

                                Intent intent = new Intent(getApplicationContext(), WorkoutDetailEdit.class);
                                Bundle box = new Bundle();
                                box.putString("wNumber", selecteditem.getWorkoutNumber());
                                box.putString("Name", selecteditem.getName());
                                box.putString("Cardio", selecteditem.getCardio());
                                intent.putExtras(box);
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
                mode.getMenuInflater().inflate(R.menu.delete_and_edit, menu);
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
            // */
    }


    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            mAuth.signOut();
        }
        if (id == R.id.add) {
            startActivity(new Intent(MainActivity.this, AddActivity.class));
        }
        if (id == R.id.friends) {
            startActivity(new Intent(MainActivity.this, FriendsActivity.class));
        }
        if (id == R.id.calendar) {
            startActivity(new Intent(MainActivity.this, CalendarActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableListView.expandGroup(i);
        }
    }

}

