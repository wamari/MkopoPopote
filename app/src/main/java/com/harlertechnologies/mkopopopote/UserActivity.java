package com.harlertechnologies.mkopopopote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class UserActivity extends AppCompatActivity {

    //define view objects
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextIDNo;
    EditText editTextEmail;
    EditText editTextDOB;
    Spinner spinnerGender;
    Button buttonSubmit;


    //database reference object

    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        //get views
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextIDNo = (EditText) findViewById(R.id.editTextIDNo);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextDOB = (EditText) findViewById(R.id.editTextDOB);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);

        //populate gender spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        //Specify the layout to use when list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter
        spinner.setAdapter(adapter);


        //attach click listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    /*
    *This method is saving a new user to the firebase realtime database
     */
    //define method
    private void addUser(){
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String idNo = editTextIDNo.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String dob = editTextDOB.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();

        String userMobile = getIntent().getExtras().getString("User phone");

        //TODO: check for duplicate accounts

        //check if values are filled or not
        if(!TextUtils.isEmpty(firstName)){
            // TODO: check input in other fields as well
            //generate a unique id
            String id = databaseUsers.push().getKey();

            //create a new user
            User user = new User(id, firstName, lastName, idNo, email, dob, gender, userMobile);

            //store this in Firebase database
            databaseUsers.child(id).setValue(user);
            //inform user of account creation
            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_LONG).show();
            //start the Main Activity
            //TODO: Start the main screen
        }else{
            Toast.makeText(this, "Please enter First name", Toast.LENGTH_LONG).show();
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

