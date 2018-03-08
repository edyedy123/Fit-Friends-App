package com.example.edu.termproject1;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddFriendActivity extends AppCompatActivity {
    AutoCompleteTextView search;
    ArrayAdapterFreinds myCustomAdapter;
    ArrayAdapterPending pendingAdapter;
    ListView listView;
    ListView listViewPending;
    String selection;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String user_id = mAuth.getCurrentUser().getUid();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    private DatabaseReference mUserId = FirebaseDatabase.getInstance().getReference().child("userNameIds");
    private DatabaseReference mFriend;
    DatabaseReference currentUserId = mDatabase.child(user_id);
    List<AppUsers> appUser;
    AppUsers currentUser;
    List <String> pendingList;
    String uID;
    List <String> pendingFriendList = new ArrayList<>();
    int valid2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        currentUserId.keepSynced(true);
        mDatabase.keepSynced(true);
        List <String> userNameIds;
        appUser = new ArrayList<AppUsers>();
        listView = (ListView)findViewById(R.id.AutoCompleateFriends);
        search = (AutoCompleteTextView)findViewById(R.id.findUser);
        search.setThreshold(1);


        myCustomAdapter = new ArrayAdapterFreinds(AddFriendActivity.this, R.id.AutoCompleateFriends,appUser);
        search.setAdapter(myCustomAdapter);

        listViewPending = (ListView)findViewById(R.id.pendingList);

        //Firebase date retriving listneners
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appUser.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userName = (String) child.child("userName").getValue();
                    String fName = (String) child.child("firstName").getValue();
                    Map temp = (Map) child.child("friends").getValue();
                    String lName = (String) child.child("lastName").getValue();
                    String email = (String) child.child("email").getValue();
                    String image = (String) child.child("image").getValue();
                    Long workoutNumber = (Long) child.child("workoutNumber").getValue();
                    Collection f = temp.values();
                    List <String> list = new ArrayList(f);
                    AppUsers loader = new AppUsers();
                    Integer i = (int) (long) workoutNumber;
                    loader.setAppUser(userName,email,fName,lName,image,i,list);
                    appUser.add(loader);
                }

                myCustomAdapter = new ArrayAdapterFreinds(AddFriendActivity.this, R.id.AutoCompleateFriends, appUser);
                search.setAdapter(myCustomAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        currentUserId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String fName = (String) dataSnapshot.child("firstName").getValue();
                Map temp = (Map) dataSnapshot.child("friends").getValue();
                String userName = (String) dataSnapshot.child("userName").getValue();
                String lName = (String) dataSnapshot.child("lastName").getValue();
                String email = (String) dataSnapshot.child("email").getValue();
                String image = (String) dataSnapshot.child("image").getValue();
                Long workoutNumber = (Long) dataSnapshot.child("workoutNumber").getValue();
                Collection f = temp.values();
                List <String> list = new ArrayList(f);
                AppUsers loader = new AppUsers();
                Integer i = (int) (long) workoutNumber;
                loader.setAppUser(userName,email,fName,lName,image,i,list);

                currentUser = loader;

                if(dataSnapshot.child("pending").getValue()!= null) {
                    pendingList = (List) dataSnapshot.child("pending").getValue();
                }


                pendingAdapter = new ArrayAdapterPending(AddFriendActivity.this,pendingList);
                listViewPending.setAdapter(pendingAdapter);
                pendingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });



        Button addFriend = (Button)findViewById(R.id.add_friend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            CharSequence msg1= "Friend Request Sent";
            CharSequence msg2= "You Are Already Friends!";
            CharSequence msg3= "That's You!";
            //Validation
            @Override
            public void onClick(View v) {
                int valid =0;
                valid2 =0;
                selection = search.getText().toString().trim();
                //if you try to add yourself
                if (currentUser.getUserName().equals(selection)) {
                    Toast.makeText(getApplicationContext(), msg3, Toast.LENGTH_LONG).show();
                    valid=1;
                }
                else{
                    String t;
                    //if you add a friend you are already friends with
                    for (int i = 0; i < currentUser.getFriends().size(); i++) {
                        if (currentUser.getFriends().get(i).equals(selection)) {
                            Toast.makeText(getApplicationContext(), msg2, Toast.LENGTH_LONG).show();
                            valid=1;
                        }
                    }
                }
                if(TextUtils.isEmpty(selection)){
                    Toast.makeText(getApplicationContext(),"A Field is Empty ...", Toast.LENGTH_LONG).show();
                    valid=1;
                }
                //add friend if valid is 0
              if(valid==0){

                  mUserId.child(selection).addListenerForSingleValueEvent(new ValueEventListener () {

                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          uID = dataSnapshot.getValue(String.class);
                          mFriend = FirebaseDatabase.getInstance().getReference().child("Users").child(uID);
                          mFriend.child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {
                                  if (dataSnapshot.getValue() != null) {
                                      pendingFriendList = (List) dataSnapshot.getValue();
                                      for (int i = 0; i < pendingFriendList.size(); i++) {
                                          if (pendingFriendList.get(i).equals(currentUser.getUserName())) {
                                              Toast.makeText(getApplicationContext(), "Friend Request Already Sent", Toast.LENGTH_LONG).show();
                                              valid2 = 1;
                                          }
                                      }
                                  }
                                  if (valid2 == 0) {
                                      Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_LONG).show();
                                      pendingFriendList.add(currentUser.getUserName());
                                      mFriend.child("pending").setValue(pendingFriendList);
                                  }
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


                }
            }
        });
    }

}

