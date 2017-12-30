package com.example.edu.termproject1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EDU on 4/1/2017.
 */

public class Workout {
    String name;
    String date;
    String cardio="0";
    String workoutNumber;
    List<Set> setArray= new ArrayList<Set>();

    public Workout() {}
    public void SetWorkout(String n, String d, List<Set> set) {
        name=n;
        date=d;
        setArray= set;
    }
    public List<Set> getSetArray() {
        return setArray;
    }
    public Set getSet(int i) {
        Set set=setArray.get(i);
        return set;
    }
    public void setSet(String w, String r) {
        Set set=new Set();
        set.setSet(w,r);
        setArray.add(set);
    }

    public void removeSet(Set i) {
        setArray.remove(i);
    }

    public void setDate(String d) {
        date=d;
    }

    public String getDate() {
        return date;
    }

    public void setCardio(String c) {
        cardio=c;
    }

    public String getCardio() {
        return cardio;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
       name=n;
    }

    public int size() {
        return setArray.size();
    }

    public void remove(Set i) {
         setArray.remove(i);
    }

    public void setWorkoutNumber(String n) {
        workoutNumber=n;
    }

    public String getWorkoutNumber() {
        return workoutNumber;
    }
}