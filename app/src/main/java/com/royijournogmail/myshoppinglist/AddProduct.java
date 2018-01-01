package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ListView listview = (ListView)findViewById(R.id.listView1);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        adapter.add("You Dont Have Product In your List. Push The Button Below To Add");
        listview.setAdapter(adapter);

        (findViewById(R.id.addItem)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProduct.this,AddItemToList.class);
                startActivity(intent);
            }
        });

        /*FirebaseAuth userInfo = FirebaseAuth.getInstance();
        String u_id = userInfo.getUid();
        User userInfoermation = new User(fName,null);
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
        if(dref.child(u_id).child("listOfProduct").toString()!=null){
           dref.child(u_id).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   for(DataSnapshot list:dataSnapshot.getChildren()){

                   }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           })
        }else {
        adapter.add("You Dont Have Product In your List. Push The Button Below To Add");

        }
*/
    }
}
