package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        TextView homeEmail = (TextView)findViewById(R.id.homeEmail);
        homeEmail.setText(getIntent().getExtras().getString("Email"));

        Button addProduct = (Button)findViewById(R.id.addProduct);
        Button chooseItem = (Button)findViewById(R.id.chooseItem);
        Button previousList = (Button)findViewById(R.id.previousList);
        Button statistic = (Button)findViewById(R.id.statistic);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,listOfProduct.class);
                startActivity(intent);
            }
        });

    }
}
