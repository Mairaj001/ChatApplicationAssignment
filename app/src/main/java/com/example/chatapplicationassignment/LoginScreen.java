package com.example.chatapplicationassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    EditText phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        phoneNumber=findViewById(R.id.login_mobile_number);

    }

    public void SendOTP(View view) {
      if (phoneNumber.getText().toString().isEmpty()) {
        Toast.makeText(this, "Invalid PhoneNumber", Toast.LENGTH_SHORT).show();
        return;
    }
    String phoneNumbers = phoneNumber.getText().toString();
    Intent intent = new Intent(getApplicationContext(), Input_OTP.class);
    intent.putExtra("PhoneNumber", phoneNumbers);
    startActivity(intent);
    }
}