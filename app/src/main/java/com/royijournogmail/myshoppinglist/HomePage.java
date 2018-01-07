package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
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
        SharedPreferences sp = getSharedPreferences("myshoppinglist", 0);
        final SharedPreferences.Editor sedt = sp.edit();
        homeEmail.setText(sp.getString("User_Name" , null));



        Button newList = (Button)findViewById(R.id.newList);
        Button updateList = (Button)findViewById(R.id.updateList);
        Button previousList = (Button)findViewById(R.id.previousList);
        Button statistic = (Button)findViewById(R.id.statistic);

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
