package com.royijournogmail.myshoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;
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
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this,HomePage.class);
                            intent.putExtra("Email",databaseAuth.getCurrentUser().getEmail());
                            startActivity(intent);
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
