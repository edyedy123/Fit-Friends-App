package com.example.edu.termproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//Choose exersise Activity
public class AddActivityWorkout extends AppCompatActivity {
    String cardio="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        //choose body part(previous activity/AddActivity) passed choise in bundle
        Intent i = getIntent();
        Bundle box= i.getExtras();
        int choiceNum = box.getInt("Number");

        String choiceName;
        final String[] body_part = getResources().getStringArray(R.array.bodyPart_array);
        ListView listView = (ListView) findViewById(R.id.WorkoutList);
        String[] BodySelection = getResources().getStringArray(R.array.Chest);
        choiceName=body_part[choiceNum];

        if(choiceName.equals("Chest")){
            BodySelection = getResources().getStringArray(R.array.Chest);
        }
        if(choiceName.equals("Back")){
            BodySelection = getResources().getStringArray(R.array.Back);
        }
        if(choiceName.equals("Legs")){
            BodySelection = getResources().getStringArray(R.array.Legs);
        }
        if(choiceName.equals("Biceps")){
            BodySelection = getResources().getStringArray(R.array.Biceps);
        }
        if(choiceName.equals("Triceps")){
            BodySelection = getResources().getStringArray(R.array.Triceps);
        }
        if(choiceName.equals("Shoulders")){
            BodySelection = getResources().getStringArray(R.array.Shoulders);
        }
        if(choiceName.equals("Abs")){
            BodySelection = getResources().getStringArray(R.array.Abs);
        }
        if(choiceName.equals("Cardio")){
            BodySelection = getResources().getStringArray(R.array.Cardio);
            cardio="1";
        }
        final ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BodySelection);
        listView.setAdapter(sAdapter);

        final String[] Workouts = BodySelection;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(getApplicationContext(), WorkoutDetail.class);
                Bundle box= new Bundle();
                box.putInt("Number", arg2);
                box.putString("Name", Workouts[arg2]);
                box.putString("Cardio",cardio);
                intent.putExtras(box);
                startActivity(intent);
            }

        });
    }
}
