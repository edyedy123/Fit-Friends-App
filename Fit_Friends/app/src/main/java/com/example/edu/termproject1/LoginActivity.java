package com.example.edu.termproject1;

import android.app.Activity;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends Activity {
    private Button button1;
    private DatabaseReference mDatabase;
    private EditText NEmailField;
    private EditText NPassField;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        button1=(Button) findViewById(R.id.button1);
        NEmailField=(EditText) findViewById(R.id.email_field);
        NPassField=(EditText) findViewById(R.id.pass_field);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mAuth= FirebaseAuth.getInstance();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

        //if already signed in, go to main activuty
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent loginIntent= new Intent(LoginActivity.this, MainActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };
        Button registerButton = (Button)findViewById(R.id.button2);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
    protected void onStart(){
        super.onStart();
        //Firebase autentication object attached to a listener that checks if you are logged in
        mAuth.addAuthStateListener(mAuthListener);

    }
    protected void startSignIn(){
        String email= NEmailField.getText().toString().trim();
        String pass= NPassField.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(LoginActivity.this,"Fields are Empty ...", Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Sign In Failed ...", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
