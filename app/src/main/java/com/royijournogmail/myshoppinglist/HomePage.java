package com.royijournogmail.myshoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        TextView homeEmail = (TextView)findViewById(R.id.homeEmail);
        homeEmail.setText(getIntent().getExtras().getString("Email"));
    }
}
