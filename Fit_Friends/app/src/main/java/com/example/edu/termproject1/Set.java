package com.example.edu.termproject1;

import java.io.Serializable;

/**
 * Created by EDU on 4/1/2017.
 */

public class Set implements Serializable {
    private String weight;
    private String reps;

    public Set() {
    }
    public void setSet(String wl,String rl) {
        weight = wl;
        reps = rl;
    }
    public String getWeight() {
        return weight;
    }

    public String getReps() {
        return reps;
    }
    public void setWeight(String i) {
        weight=i;
    }
    public void setReps(String i) {
        reps =i;
    }
}