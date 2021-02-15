package com.example.attendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    EditText userNameEditText;
    EditText passwordEditText;
    String userName;
    String password;
    String dataPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText userNameEditText = findViewById(R.id.user_name);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button signUpButton = findViewById(R.id.signUpButton);
        Button loginButton = findViewById(R.id.logginButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference(userName);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(MainActivity.this, "Telivi tetalu vaddu" , Toast.LENGTH_LONG).show();
                        }
                        else{
                            reference.child("password").setValue(password);
                            reference.child("courses").child("Biology").child("present").setValue("0");
                            reference.child("courses").child("Biology").child("absent").setValue("0");
                            reference.child("courses").child("CS101").child("present").setValue("0");
                            reference.child("courses").child("CS101").child("absent").setValue("0");
                            reference.child("courses").child("HSS").child("present").setValue("0");
                            reference.child("courses").child("HSS").child("absent").setValue("0");
                            reference.child("courses").child("Algo").child("present").setValue("0");
                            reference.child("courses").child("Algo").child("absent").setValue("0");
                            Intent intent = new Intent(MainActivity.this, Attendence.class);
                            intent.putExtra("userN", userName);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = userNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference(userName);



                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            dataPass = snapshot.child("password").getValue().toString();
                            if (password == null || dataPass == null) {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                            } else if (password.equals(dataPass)) {
                                Toast.makeText(MainActivity.this, "vachei", Toast.LENGTH_SHORT).show();
                                login();
                            } else {
                                Toast.makeText(MainActivity.this, "dengei!! " + dataPass + " " + password, Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }

            private void login() {
                Intent intent = new Intent(MainActivity.this, Attendence.class);
                intent.putExtra("userN", userName);
                startActivity(intent);
            }
        });

    }
}