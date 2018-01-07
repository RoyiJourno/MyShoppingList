package com.royijournogmail.myshoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("myshoppinglist", 0);
        final SharedPreferences.Editor sedt = sp.edit();

        Button signIn = (Button)findViewById(R.id.signIn);
        Button signUp = (Button)findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText login = (EditText) findViewById(R.id.loginNameWelcome);
                EditText password = (EditText) findViewById(R.id.loginPasswordWelcome);

                final FirebaseAuth databaseAuth = FirebaseAuth.getInstance();

                String loginPassword = password.getText().toString();
                final String loginName = login.getText().toString();

                final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Processing...", true);
                (databaseAuth.signInWithEmailAndPassword(loginName,loginPassword)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                            final Intent intent = new Intent(MainActivity.this,HomePage.class);
                            //save user name
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            FirebaseAuth userInfo = FirebaseAuth.getInstance();
                            final String u_id = userInfo.getUid();
                            final DatabaseReference ref = database.getReference().child(u_id);
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();
                                    String u_name = dataSnapshot.child("name").getValue().toString();
                                    sedt.putString("User_Name" , u_name);
                                    sedt.commit();
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });




                        }
                        else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
