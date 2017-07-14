package com.harlertechnologies.mkopopopote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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


    public class MyFirebaseMessagingService extends FirebaseMessagingService {
        private static final String TAG = "FCM Service";
        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            // TODO: Handle FCM messages here.
            // If the application is in the foreground handle both data and notification messages here.
            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated.
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        }
    }

    public class FirebaseIDService extends FirebaseInstanceIdService {
        private static final String TAG = "FirebaseIDService";

        @Override
        public void onTokenRefresh() {
            // Get updated InstanceID token.
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + refreshedToken);

            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(refreshedToken);
        }

        /**
         * Persist token to third-party servers.
         *
         * Modify this method to associate the user's FCM InstanceID token with any server-side account
         * maintained by your application.
         *
         * @param token The new token.
         */
        private void sendRegistrationToServer(String token) {
            // Add custom implementation, as needed.
        }
    }
}