package com.example.chatapplicationassignment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.chatapplicationassignment.Utilities.AndriodUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class FragmentProfile  extends Fragment {

    Button logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        this.logout=view.findViewById(R.id.btnLogout);
        this.logout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(),"Logout",Toast.LENGTH_SHORT).show();
            startActivity( new Intent(getContext(), LoginScreen.class));
            requireActivity().finish();
        });

        return view;
    }


}