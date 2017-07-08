package com.harlertechnologies.mkopopopote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button buttonStart;

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
}
