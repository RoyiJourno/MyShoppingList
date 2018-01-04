package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class AddProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        final ListView listview = (ListView)findViewById(R.id.listView1);
        FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(u_id);


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateList(dataSnapshot,listview);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        (findViewById(R.id.addItem)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProduct.this,AddItemToList.class);
                startActivity(intent);
            }
        });
    }
    private void updateList(DataSnapshot dataSnapshot,ListView listview) {
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        HashMap<String, String> productList = new HashMap<>();
        for (DataSnapshot child : children) {
            String name = child.child("p_name").getValue().toString();
            String desc = child.child("p_description").getValue().toString();
            productList.put(name, desc);
        }
        List<HashMap<String, String>> listItems = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.)
        Iterator it = productList.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resMap.put("First", pair.getKey().toString());
            resMap.put("Second", pair.getValue().toString());
            listItems.add(resMap);
        }
        listview.setAdapter(adapter);
    }
}
