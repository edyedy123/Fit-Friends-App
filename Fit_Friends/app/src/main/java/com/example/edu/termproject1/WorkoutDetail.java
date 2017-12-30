package com.example.edu.termproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkoutDetail extends AppCompatActivity {
    EditText weightForm;
    EditText repsForm;
    ArrayAdapterSets myCustomAdapter;
    ListView listView;

    Calendar c = Calendar.getInstance();
    Workout workout= new Workout();

    String cardio="0";
    Double weight;
    int reps;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String user_id = mAuth.getCurrentUser().getUid();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    int workoutNumber;
    String dateCombo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        final DatabaseReference currentUserId = mDatabase.child(user_id);
        //KeepSynced methods allow firebase to update data when internet conection returns
        mDatabase.keepSynced(true);
        currentUserId.keepSynced(true);
        //Retreave date from database-date is sent to databse is main activity to be used here
        //also sets workout number
        currentUserId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dateCombo = dataSnapshot.child("dateCombo").getValue(String.class);
                Long Number = (Long) dataSnapshot.child("workoutNumber").getValue();
                Integer i = (int)(long)Number;
                i++;
                workoutNumber=i;
                currentUserId.child("workoutNumber").setValue(workoutNumber);
                workout.setDate(dateCombo);
                workout.setCardio(cardio);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
        //gets exersise name
        Intent i = getIntent();
        Bundle box= i.getExtras();
        String ChoiseName = box.getString("Name");
        cardio = box.getString("Cardio");

        listView = (ListView) findViewById(R.id.savedList);
        TextView wName = (TextView) findViewById(R.id.workoutName);
        wName.setText(ChoiseName);
        workout.setName(ChoiseName);

        weightForm = (EditText) findViewById(R.id.weight);
        repsForm = (EditText) findViewById(R.id.reps);


        myCustomAdapter = new ArrayAdapterSets(this, workout);
        listView.setAdapter(myCustomAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Capture ListView item click- multi click delete
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                myCustomAdapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = myCustomAdapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Set selecteditem = myCustomAdapter.getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                myCustomAdapter.remove(selecteditem);
                                workout.removeSet(selecteditem);
                                if(workout.size()==0) {
                                    currentUserId.child("Workouts").child("Workout" + workoutNumber).removeValue();
                                }else
                                {
                                    currentUserId.child("Workouts").child("Workout" + workoutNumber).setValue(workout);
                                }
                            }
                        }
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.delete_sets, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                myCustomAdapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
        });

        Button moreWeight=(Button) findViewById(R.id.more);
        moreWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = weightForm.getText().toString();
                if (val.equals("")) {
                    weight=0.0;
                }
                else {
                    weight = Double.parseDouble(weightForm.getText().toString());
                }
                if(cardio.equals("1")){
                    weight += 1;
                }
                else {
                    weight += 2.5;
                }
                weightForm.setText("" + weight);
            }
        });

        Button lessWeight=(Button) findViewById(R.id.less);
        lessWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = weightForm.getText().toString();
                if (val.equals("")) {
                    weight=0.0;
                }
                else {
                    weight = Double.parseDouble(weightForm.getText().toString());
                }
                if(weight>0.0) {
                    if(cardio.equals("1")){
                        weight -= 1;
                    }
                    else {
                        weight -= 2.5;
                    }
                    weightForm.setText("" + weight);
                }
                else{
                    weight=0.0;
                    weightForm.setText("" + weight);
                }
            }
        });

        Button moreReps=(Button) findViewById(R.id.more2);
        moreReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = repsForm.getText().toString();
                if (val.equals("")) {
                    reps = 0;
                }
                else {
                    reps = Integer.parseInt(repsForm.getText().toString());
                }
                    reps += 1;
                    repsForm.setText("" + reps);

            }
        });

        Button lessReps=(Button) findViewById(R.id.less2);
        lessReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = repsForm.getText().toString();
                if (val.equals("")) {
                    reps = 0;
                }
                else {
                    reps = Integer.parseInt(repsForm.getText().toString());
                }
                if(reps>0) {
                    reps -= 1;
                    repsForm.setText("" + reps);
                }
            }
        });
        Button save=(Button) findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if string is null, set it to 0
                String val = weightForm.getText().toString();
                if (val.equals("")) {
                    weight = 0.0;
                }
                else {
                    weight = Double.parseDouble(weightForm.getText().toString());
                }
                String val2 = repsForm.getText().toString();
                if (val2.equals("")) {
                    reps = 0;
                }
                else {
                    reps = Integer.parseInt(repsForm.getText().toString());
                }
                workout.setSet(Double.toString(weight), Integer.toString(reps));
                workout.setWorkoutNumber(Integer.toString(workoutNumber));
                myCustomAdapter.notifyDataSetChanged();

                currentUserId.child("Workouts").child("Workout"+workoutNumber).setValue(workout);
            }
        });
        Button clear=(Button) findViewById(R.id.Clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    weight=0.0;
                    reps = 0;
                    weightForm.setText("" + weight);
                    repsForm.setText("" + reps);
            }
        });
    }



}
