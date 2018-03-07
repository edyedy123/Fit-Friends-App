package com.example.edu.termproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.example.edu.termproject1.R.id.parent;
import static java.lang.Character.isDigit;
import static java.security.AccessController.getContext;

public class FriendsActivity extends AppCompatActivity{
    private MenuFriendsAdaptor mAdapter;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String user_id = mAuth.getCurrentUser().getUid();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference currentUserId = mDatabase.child(user_id);
    List<AppUsers> appUser = new ArrayList<AppUsers>();
    AppUsers currentUser = new AppUsers();
    ListView fView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        fView = (ListView)findViewById(R.id.friendsView);
        mDatabase.keepSynced(true);
        currentUserId.keepSynced(true);
        //load current user object
        currentUserId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appUser.clear();
                String userName = (String) dataSnapshot.child("userName").getValue();
                String fName = (String) dataSnapshot.child("firstName").getValue();
                Map temp = (Map) dataSnapshot.child("friends").getValue();
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        //load all users
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
                        List<String> list = new ArrayList(f);
                        AppUsers loader = new AppUsers();
                        Integer i = (int) (long) workoutNumber;
                        loader.setAppUser(userName, email, fName, lName, image, i, list);
                        appUser.add(loader);
                    }
                    if (appUser.size() != 0) {
                        int x = currentUser.getFriends().size();
                        mAdapter = new MenuFriendsAdaptor(FriendsActivity.this, currentUser.getFriends(), appUser);
                        fView.setAdapter(mAdapter);
                    } else {
                        fView.setEmptyView(findViewById(R.id.emptyElement));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        //pass selected friend's username to the FriendWorkouts Activity
        fView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getApplicationContext(), FriendWorkouts.class);
                Bundle box= new Bundle();

                TextView c = (TextView) arg1.findViewById(R.id.userName1);
                String uName = c.getText().toString().trim();

                if(!isDigit(uName.charAt(0))) {
                    uName = uName.substring(0, 1).toLowerCase() + uName.substring(1);
                }

                box.putString("userName", uName);
                intent.putExtras(box);
                startActivity(intent);
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_friends) {
            startActivity(new Intent(FriendsActivity.this, AddFriendActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
