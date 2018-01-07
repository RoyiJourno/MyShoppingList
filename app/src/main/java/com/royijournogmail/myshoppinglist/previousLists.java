package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class previousLists extends AppCompatActivity {
    final User[] new_user = new User[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_lists);
        //final ListView listview = (ListView) findViewById(R.id.lv);

        final FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();

        //save data
        SharedPreferences sp = getSharedPreferences("myshoppinglist", 0);
        final SharedPreferences.Editor sedt = sp.edit();

        final ListView[] list = new ListView[1];
        list[0] = (ListView) findViewById(R.id.lv);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(u_id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.child("listOfLists").getChildren();
                ArrayList<String> items = new ArrayList<String>();
                boolean list_empty=true;
                for (DataSnapshot child : children) {
                    String name = child.child("name").getValue().toString();
                    items.add(name);
                    list_empty=false;
                }
                if(!list_empty)
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(previousLists.this, android.R.layout.simple_list_item_1, items);
                     list[0].setAdapter(adapter);
                }
                else
                {
                    TextView targetText=(TextView) findViewById(R.id.textView) ;
                    targetText.setText("No lists found");
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        list[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                sedt.putString("list_id" , String.valueOf(id));
                sedt.commit();


                Intent intent = new Intent(previousLists.this,ShowShoppingList.class);
                startActivity(intent);


            }
        });

        findViewById(R.id.homeButton).setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth databaseAuth = FirebaseAuth.getInstance();

                Intent intent = new Intent(previousLists.this,HomePage.class);

                startActivity(intent);
            }
        });


    }
}
