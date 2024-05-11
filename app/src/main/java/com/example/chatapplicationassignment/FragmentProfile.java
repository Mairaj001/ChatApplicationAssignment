package com.example.chatapplicationassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class FragmentProfile
        extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
//        recyclerView = view.findViewById(R.id.recyler_view);
//        setupRecyclerView();

        return view;
    }


}