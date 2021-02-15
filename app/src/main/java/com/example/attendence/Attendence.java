package com.example.attendence;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Attendence extends AppCompatActivity {
ArrayList<String> subjectNames = new ArrayList<>();
ArrayAdapter<String> arrayAdapter;
DatabaseReference mref;
String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        Intent intent = getIntent();
        username = intent.getStringExtra("userN");


        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjectNames);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        mref = FirebaseDatabase.getInstance().getReference().child(username).child("courses");

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getKey().toString();
                subjectNames.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(Attendence.this, Integer.toString(i), Toast.LENGTH_SHORT).show();
                String subject = subjectNames.get(i);
                Intent intent = new Intent( Attendence.this, Data.class);
                intent.putExtra("userN", username);
                intent.putExtra("subject", subject);
                startActivity(intent);

            }
        });


    }
}