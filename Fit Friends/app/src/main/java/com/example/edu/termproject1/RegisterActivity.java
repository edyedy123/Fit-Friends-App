package com.example.edu.termproject1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends Activity {
    private EditText NEmailField;
    private EditText NPassField;
    private EditText NFirstNameField;
    private EditText NLastNameField;
    private EditText NUserNameFiled;
    private Button createAccount;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseTaken;
    private DatabaseReference mDatabaseUserNames;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    List emails = new ArrayList();
    List userNames = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseTaken = FirebaseDatabase.getInstance().getReference().child("taken");
        mDatabaseUserNames = FirebaseDatabase.getInstance().getReference().child("userNames");
        mAuth= FirebaseAuth.getInstance();
        mProgress= new ProgressDialog(this);

        createAccount = (Button) findViewById(R.id.button3);
        NEmailField = (EditText) findViewById(R.id.email);
        NPassField = (EditText) findViewById(R.id.password);
        NFirstNameField = (EditText) findViewById(R.id.firstName);
        NLastNameField = (EditText) findViewById(R.id.lastName);
        NUserNameFiled = (EditText) findViewById(R.id.userName);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
        //loads all emails
        mDatabaseTaken.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emails.clear();
                Map temp = (Map) dataSnapshot.getValue();
                if(temp!= null) {
                    List list = new ArrayList(temp.values());
                    for(int i= 0; i< list.size();i++) {
                        Map m = (Map) list.get(i);
                        List list2 = new ArrayList(m.values());
                        emails.add(list2.get(0));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //loads all usernames
        mDatabaseUserNames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userNames.clear();
                Map temp = (Map) dataSnapshot.getValue();
                if(temp!= null) {
                    List list = new ArrayList(temp.values());
                    for(int i= 0; i< list.size();i++) {
                        Map m = (Map) list.get(i);
                        List list2 = new ArrayList(m.values());
                        userNames.add(list2.get(0));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void startRegister() {
        final String fName = NFirstNameField.getText().toString().trim();
        final String lName = NLastNameField.getText().toString().trim();
        final String email = NEmailField.getText().toString().trim();
        final String uName = NUserNameFiled.getText().toString().trim();
        String pass = NPassField.getText().toString().trim();
        int valid = 0;
        //field cannot be empty
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)|| TextUtils.isEmpty(fName)|| TextUtils.isEmpty(lName)){
            Toast.makeText(RegisterActivity.this,"A Field is Empty ...", Toast.LENGTH_LONG).show();
            valid=1;
        }
        //checks if email is in use
        if(emails.size()>0){
            for(int i=0; i< emails.size(); i++) {
                if (emails.get(i).equals(email)) {
                    Toast.makeText(getApplicationContext(),"Email Already In Use", Toast.LENGTH_LONG).show();
                    valid=1;
                }
            }
        }
        //checks if username is in use
        if(userNames.size()>0){
            for(int i=0; i< userNames.size(); i++) {
                if (userNames.get(i).equals(uName)) {
                    Toast.makeText(getApplicationContext(),"User Name Already In Use", Toast.LENGTH_LONG).show();
                    valid=1;
                }
            }
        }
        //registers using a firebase function
        if(valid == 0){
            mProgress.setMessage("Signing Up");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //I did not simply load a AppUsers object here to send becasue for some reason when I did the database receaved duplicate data...
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserId = mDatabase.child(user_id);
                        currentUserId.child("userName").setValue(uName);
                        currentUserId.child("email").setValue(email);
                        currentUserId.child("firstName").setValue(fName);
                        currentUserId.child("lastName").setValue(lName);
                        currentUserId.child("image").setValue("deafult");
                        currentUserId.child("workoutNumber").setValue(0);
                        currentUserId.child("friends").push().setValue("admin");
                        mDatabaseTaken.child("taken").push().setValue(email);
                        mDatabaseUserNames.child("userNames").push().setValue(uName);
                        mProgress.dismiss();
                    }
                    else{
                        mProgress.dismiss();
                        String registerError = getResources().getString(R.string.registerError);
                        Toast.makeText(RegisterActivity.this, registerError, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}

