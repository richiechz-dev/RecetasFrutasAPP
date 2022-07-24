package com.example.appbeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appbeta.adapter.userAdapter;
import com.example.appbeta.model.user;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class mainAdmin extends AppCompatActivity {

    RecyclerView mRecycler;
    userAdapter mAdapter;
    private FirebaseAuth auth;
    FirebaseFirestore mFirestore;
    Button cierre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        auth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerViewSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));


        Query query = mFirestore.collection("users");

        FirestoreRecyclerOptions<user> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<user>().setQuery(query, user.class).build();

        mAdapter = new userAdapter(firestoreRecyclerOptions,  this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        cierre = findViewById(R.id.cierre);
        cierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(mainAdmin.this, MainActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.startListening();
    }


    }