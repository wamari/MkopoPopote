package com.harlertechnologies.mkopopopote;

import android.content.Intent;
import android.nfc.Tag;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class RegisterActivity extends AppCompatActivity {

    private EditText phoneNumber;
    private EditText verificationCode;
    private String mVerificationID;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //this requires a real device, it will not work on an AVD

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        verificationCode = (EditText) findViewById(R.id.verificationCode);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if (firebaseAuth.getCurrentUser()!=null){
                    Toast.makeText(RegisterActivity.this, getString(R.string.now_logged_in) + firebaseAuth.getCurrentUser().getProviderId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

    }

    public void requestCode(View view){
        String telNo = phoneNumber.getText().toString();
        if (TextUtils.isEmpty(telNo))
            return;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                telNo,  //phone number to verify
                60,     //timeout
                TimeUnit.SECONDS,
                RegisterActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        //Called if it is not needed to enter verification code
                        signInWithCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        //incorrect phone number, verification code, emulator, etc.
                        Toast.makeText(RegisterActivity.this, "onVerificationFailed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        //now the code has been sent, save the verificationId we may need it
                        super.onCodeSent(verificationId, forceResendingToken);
                        mVerificationID = verificationId;
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String verificationId) {
                        //called after timeout if onVerificationCompleted has not been called
                        super.onCodeAutoRetrievalTimeOut(verificationId);
                        Toast.makeText(RegisterActivity.this, "onCodeAutoRetrievalTimeOut :" + verificationId, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential){
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, R.string.signed_success, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, getString(R.string.sign_credential_fail) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signIn(View view){
        String code = verificationCode.getText().toString();
        if(TextUtils.isEmpty(code))
            return;

        signInWithCredential(PhoneAuthProvider.getCredential(mVerificationID, code));
    }
}
