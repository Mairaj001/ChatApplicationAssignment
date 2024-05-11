package com.example.chatapplicationassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.FrameStats;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    FragmentChat fragmentChat;
    FragmentProfile fragmentProfile;
    ImageButton serach_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentChat= new FragmentChat();
        fragmentProfile= new FragmentProfile();
        bnv=findViewById(R.id.bottomNavigation);
        serach_btn=findViewById(R.id.main_search_btn);

        serach_btn.setOnClickListener(v->{
            startActivity( new Intent(getApplicationContext(), searchActivity.class));
        });

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_chat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,fragmentChat).commit();
                }
                if(item.getItemId()==R.id.menu_profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,fragmentProfile).commit();
                }
                return true;
            }
        });

        bnv.setSelectedItemId(R.id.menu_chat);
    }
}