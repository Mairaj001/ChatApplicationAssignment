package com.example.chatapplicationassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapplicationassignment.Utilities.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Input_OTP extends AppCompatActivity {
     String PhoneNumber;
     EditText Otp_input;
     TextView resendText;

     ProgressBar progressBar;

     Button next_btn;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    PhoneAuthProvider.ForceResendingToken  resendingToken;


    String phoneNumber;
    Long timeoutSeconds = 60L;
    String verificationCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_otp);
        phoneNumber= getIntent().getStringExtra("PhoneNumber");
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            // Handle the case where phoneNumber is empty or null
            // For example, display an error message and finish the activity
            Toast.makeText(this, "Phone number is empty", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity to prevent further execution
            return;
        }

        Otp_input=findViewById(R.id.login_OTP);
        resendText=findViewById(R.id.textResendOTP);
        progressBar=findViewById(R.id.OTP_progressBar);
        next_btn=findViewById(R.id.AuthenticateOTP);


        sendOtp(phoneNumber,false);

        next_btn.setOnClickListener(v->{
            String enteredOtp  = Otp_input.getText().toString();
            PhoneAuthCredential credential =  PhoneAuthProvider.getCredential(verificationCode,enteredOtp);
            signIn(credential);
        });

        resendText.setOnClickListener(v -> {
            sendOtp(phoneNumber,true);
        });

    }



    void sendOtp(String phoneNumber,boolean isResend){
        startResendTimer();
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                setInProgress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                AndriodUtils.showToast(getApplicationContext(),"OTP verification failed");
                                setInProgress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                               AndriodUtils.showToast(getApplicationContext(),"OTP sent successfully");
                                setInProgress(false);
                            }
                        });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }



    void signIn(PhoneAuthCredential phoneAuthCredential){

        setInProgress(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(Input_OTP.this, Input_Username.class);
                    intent.putExtra("phone",phoneNumber);
                    startActivity(intent);
                }else{
                    AndriodUtils.showToast(getApplicationContext(),"OTP verification failed");
                }
            }
        });


    }
    void startResendTimer() {
        resendText.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeoutSeconds--;
                        resendText.setText("Resend OTP in " + timeoutSeconds + " seconds");
                        if (timeoutSeconds <= 0) {
                            timeoutSeconds = 60L;
                            timer.cancel();
                            resendText.setEnabled(true);
                        }
                    }
                });
            }
        }, 0, 1000);
    }
    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            next_btn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            next_btn.setVisibility(View.VISIBLE);
        }
    }
}