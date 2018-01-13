package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        TextView homeEmail = findViewById(R.id.homeEmail);
        SharedPreferences sp = getSharedPreferences("myshoppinglist", 0);
        final SharedPreferences.Editor sedt = sp.edit();
        homeEmail.setText(sp.getString("User_Name" , null));



        Button newList = findViewById(R.id.newList);
        Button updateList = findViewById(R.id.updateList);
        Button previousList =findViewById(R.id.previousList);
        ImageButton help_guide = findViewById(R.id.help_1);

        help_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,intro_welcome.class));
            }
        });

        newList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sedt.putString("amount_from" , "defult");
                sedt.commit();
                Intent intent = new Intent(HomePage.this,listOfProduct.class);
                startActivity(intent);
            }
        });

        updateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,updateList.class);
                startActivity(intent);
            }
        });

        previousList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,previousLists.class);
                startActivity(intent);
            }
        });

    }
}
