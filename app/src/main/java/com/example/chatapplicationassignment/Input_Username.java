package com.example.chatapplicationassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.chatapplicationassignment.Model.*;
import com.example.chatapplicationassignment.Utilities.FirebaseUtil;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;


import android.content.Intent;

import android.view.View;


public class Input_Username extends AppCompatActivity {

    EditText usernameInput;
    UserModel userModel;
    Button letmein_btn;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_username);
        usernameInput=findViewById(R.id.login_username);
        letmein_btn=findViewById(R.id.letmein);
        phoneNumber=getIntent().getStringExtra("phone");
        getUsername();
        letmein_btn.setOnClickListener(v->{
            setUsername();
        });
    }


    void setUsername(){

        String username = usernameInput.getText().toString();
        if(username.isEmpty() || username.length()<3){
            usernameInput.setError("Username length should be at least 3 chars");
            return;
        }
        setInProgress(true);
        if(userModel!=null){
            userModel.setUsername(username);
        }else{
            userModel = new UserModel(phoneNumber,username, Timestamp.now(), FirebaseUtil.currentUserId());
        }

        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                }
            }
        });

    }

    void getUsername(){
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    userModel =    task.getResult().toObject(UserModel.class);
                    if(userModel!=null){
                        usernameInput.setText(userModel.getUsername());
                    }
                }
            }
        });
    }

    void setInProgress(boolean inProgress){
        if(inProgress){

            letmein_btn.setVisibility(View.GONE);
        }else{

            letmein_btn.setVisibility(View.VISIBLE);
        }
    }
}