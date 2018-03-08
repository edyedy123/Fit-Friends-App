package com.example.edu.termproject1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by EDU on 4/1/2017.
 */

public class AppUsers {
    String userName;
    String email;
    String firstName;
    String image;
    String lastName;
    int workoutNumber;
    List friends= new ArrayList();
    List<Workout> workouts= new ArrayList<Workout>();

    public AppUsers() {
    }
    public AppUsers(String un,String u,String f, String l, String i, int wn, List o) {
        userName= un;
         email = u;
         firstName = f;
         image = i;
         lastName = l;
         workoutNumber = wn;
         friends=o;
    }

    public String getEmail() {
        return email;
    }
    public List getFriends() {
        return friends;
    }
    public int getFriendsSize() {
        return friends.size();
    }
    public String getFName() {
        return firstName;
    }
    public String getLName() {
        return lastName;
    }
    public String getImage() { return image; }

    public int getWorkoutNumber() { return workoutNumber;}

    public void setAppUser(String un,String u,String f, String l, String i, int wn, List friend) {
        userName= un;
        email = u;
        firstName = f;
        image = i;
        lastName = l;
        workoutNumber = wn;
        friends=friend;

    }
    public void setAppUserW(String un,String u,String f, String l, String i, int wn, List friend, List w) {
        userName= un;
        email = u;
        firstName = f;
        image = i;
        lastName = l;
        workoutNumber = wn;
        friends=friend;
        workouts = w;
    }
    public void setFriends(List f) {
        friends = f;
    }
    public void setFName(String f) {
        firstName = f;
    }
    public void setLName(String f) {
        lastName = f;
    }
    public void setImage(String f) { image = f;      }
    public void setEmail(String f) { email = f; }
    public void setWorkoutNumber(int f) { workoutNumber = f; }

    public void addFriends(String f) {
        friends.add(f);
    }
    public void removeFriends(int f) {
        friends.remove(f);
    }

    public List getWorkouts() {
        return workouts;
    }
    public void setWorkouts(List<Workout> f) {
        workouts=f;
    }
    public Workout getWorkout(int i) {
        return workouts.get(i);
    }
    public int getWorkoutSize() {
        return workouts.size();
    }
    public String getUserName() { return userName; }
    public void setUserName(String i) {
         userName = i;
    }
}
