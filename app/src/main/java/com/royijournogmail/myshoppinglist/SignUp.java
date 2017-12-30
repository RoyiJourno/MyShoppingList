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
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUpButton = (Button)findViewById(R.id.signUp1);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText loginName = (EditText)findViewById(R.id.loginName);
                EditText loginPass1 = (EditText)findViewById(R.id.loginPasswordWelcome);
                EditText loginPass2 = (EditText)findViewById(R.id.loginPassword2);

                String name = loginName.getText().toString();
                String loginPassword1 = loginPass1.getText().toString();
                String loginPassword2 = loginPass2.getText().toString();

                if(loginPassword1.equals(loginPassword2)){
                    FirebaseAuth databaseAuth = FirebaseAuth.getInstance();
                    final ProgressDialog progressDialog = ProgressDialog.show(SignUp.this, "Please wait...", "Processing...", true);
                    (databaseAuth.createUserWithEmailAndPassword(name,loginPassword1)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUp.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                    Toast.makeText(getApplicationContext(),"Password don't match, try again!",Toast.LENGTH_LONG).show();
            }
        });


    }
}
