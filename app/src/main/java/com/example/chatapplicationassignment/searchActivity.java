package com.example.chatapplicationassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.chatapplicationassignment.Model.UserModel;
import com.example.chatapplicationassignment.Utilities.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.firebase.firestore.Query;
import com.example.chatapplicationassignment.Adapters.*;

public class searchActivity extends AppCompatActivity {
    EditText searchInput;
    RecyclerView recyclerViewSearchedUsers;

    ImageButton search,back;
    SearchUserRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchInput=findViewById(R.id.seach_username_input);
        recyclerViewSearchedUsers=findViewById(R.id.search_user_recycler_view);
        search=findViewById(R.id.search_user_btn);
        back=findViewById(R.id.back_btn);

        searchInput.requestFocus();
        back.setOnClickListener(v->{
            onBackPressed();
        });

        search.setOnClickListener(v->{
            String searchTerm = searchInput.getText().toString();
            if(searchTerm.isEmpty() || searchTerm.length()<3){
                searchInput.setError("Invalid Username");
                return;
            }
            setupSearchRecyclerView(searchTerm);
        });



    }



    private void setupSearchRecyclerView(String searchTerm) {

        Query query = FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("username",searchTerm)
                .whereLessThanOrEqualTo("username",searchTerm+'\uf8ff');

        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query,UserModel.class).build();

        adapter = new SearchUserRecyclerAdapter(options,getApplicationContext());
        recyclerViewSearchedUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSearchedUsers.setAdapter(adapter);
        adapter.startListening();
    }


}