package com.example.chatapplicationassignment;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatapplicationassignment.Utilities.FirebaseUtil;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread t = new Thread(){
            @Override
            public  void run()
            {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //Intent
                    if(FirebaseUtil.isLoggedIn()){
                        startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    }else{
                        startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                    }
                    finish();
                }
            }

        };
        t.start();
    }
}