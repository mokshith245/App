package com.example.attendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Data extends AppCompatActivity {
String username;
String subject;
DatabaseReference myReff;
TextView presentTextView;
TextView absentTextView;
int presentINT;
int absentINT ;
TextView percenteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Intent intent = getIntent();
        username = intent.getStringExtra("userN");
        subject = intent.getStringExtra("subject");
        presentTextView = findViewById(R.id.presentTextView);
        absentTextView = findViewById(R.id.absentTextView);
        TextView subjectTextView = findViewById(R.id.subjectTextView);
        percenteTextView = findViewById(R.id.percentageTextView);
        Button presentButton = findViewById(R.id.presentButton);
        Button absentButton = findViewById(R.id.absentButton);

        subjectTextView.setText(subject);


        myReff = FirebaseDatabase.getInstance().getReference().child(username).child("courses").child(subject);


        myReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pres = snapshot.child("present").getValue().toString();
                String  abs =  snapshot.child("absent").getValue().toString();
                //Toast.makeText(Data.this, abs, Toast.LENGTH_SHORT).show();
                presentINT = Integer.parseInt(pres);
                absentINT = Integer.parseInt(abs);
                presentTextView.setText(pres);
                absentTextView.setText(abs);
                int totalClass = presentINT + absentINT;
                double per = 100.0 * presentINT / totalClass;
                if(totalClass == 0)
                    percenteTextView.setText("0");
                else
                    percenteTextView.setText(Double.toString(per).substring(0,4));
                Toast.makeText(Data.this, Integer.toString(presentINT)+" "+ Integer.toString(absentINT), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        presentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedPres = Integer.toString (Integer.parseInt(presentTextView.getText().toString()) + 1) ;
                presentTextView.setText(updatedPres);
                myReff.child("present").setValue(updatedPres);
                presentINT = Integer.parseInt(presentTextView.getText().toString());
                absentINT = Integer.parseInt(absentTextView.getText().toString());
                int totalClass = presentINT + absentINT;
                double per = 100.0 * presentINT / totalClass;
                if(totalClass == 0)
                    percenteTextView.setText("0");
                else
                    percenteTextView.setText(Double.toString(per).substring(0,4));
            }
        });
        absentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedAbs = Integer.toString (Integer.parseInt(absentTextView.getText().toString()) + 1) ;
                absentTextView.setText(updatedAbs);
                myReff.child("absent").setValue(updatedAbs);
                presentINT = Integer.parseInt(presentTextView.getText().toString());
                absentINT = Integer.parseInt(absentTextView.getText().toString());
                int totalClass = presentINT + absentINT;
                double per = 100.0 * presentINT / totalClass;
                if(totalClass == 0)
                    percenteTextView.setText("0");
                else
                    percenteTextView.setText(Double.toString(per).substring(0,4));
            }
        });












    }
}