package com.harlertechnologies.mkopopopote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    Button buttonStart;

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //locate the button in activity_welcome.xml
        buttonStart = (Button) findViewById(R.id.buttonStart);

        //Capture the button click

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Start next activity
                Intent myIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(myIntent);
            }
        });
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if (firebaseAuth.getCurrentUser() != null) {
                               //start next activity
                Intent intent = new Intent(WelcomeActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}