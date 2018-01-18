package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddItemToList extends AppCompatActivity {
    final User[] new_user = new User[1];

    void update_user() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();
        ref.child(u_id).setValue(new_user[0]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_list);

        SharedPreferences sp = getSharedPreferences("myshoppinglist", 0);
        final SharedPreferences.Editor sedt = sp.edit();


        //      final String[] u_name = new String[1];


        Button proAdd = findViewById(R.id.proAdd);


        proAdd.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                EditText proName = findViewById(R.id.proName);
                EditText proDesc = findViewById(R.id.proDesc);
                final String productName = proName.getText().toString();
                final String productDesc = proDesc.getText().toString();

                if (productName.equals("")) {
                    Toast.makeText(getApplicationContext(), "Must enter a name", Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseAuth userInfo = FirebaseAuth.getInstance();
                    final String u_id = userInfo.getUid();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference ref = database.getReference().child(u_id);
                    // Attach a listener to read the data at our posts reference
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();
                            String u_name = dataSnapshot.child("name").getValue().toString();
                            new_user[0] = new User(u_name);
                            ///bring product
                            for (DataSnapshot child : children) {
                                String name = child.child("p_name").getValue().toString();
                                //                               Toast.makeText(getApplicationContext(), "Value is: " +name, Toast.LENGTH_SHORT).show();
                                String desc = child.child("p_description").getValue().toString();
                                //                              Toast.makeText(getApplicationContext(), "Value is: " +name, Toast.LENGTH_SHORT).show();
                                Product p = new Product(name, null, desc);
                                new_user[0].updateProdToUser(p);

                            }
                            Product p_new = new Product(productName, null, productDesc); // create product
                            new_user[0].updateProdToUser(p_new);
                            //////////bring lists
                            children = dataSnapshot.child("listOfLists").getChildren();
                            for (DataSnapshot child : children) //list of lists
                            {
                                Iterable<DataSnapshot> children2 = child.child("listOfProduct").getChildren();
                                String name_l = child.child("name").getValue().toString();
                                ListForUser list_for_user = new ListForUser(name_l);
                                for (DataSnapshot child2 : children2) //products
                                {
                                    String name_p = child2.child("p_name").getValue().toString();
                                    String desc = child2.child("p_description").getValue().toString();
                                    Product p = new Product(name_p, null, desc);
                                    list_for_user.updateProdToList(p);

                                }
                                new_user[0].updateListToUser(list_for_user);

                            }//for
                            update_user();
                            Toast.makeText(getApplicationContext(), "Product successfully added", Toast.LENGTH_LONG).show();
                            sedt.putString("amount_from", "memory");
                            sedt.commit();

                            startActivity(new Intent(AddItemToList.this, listOfProduct.class));

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    }); //ref
                }//else
            }
        });

        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItemToList.this, HomePage.class);

                startActivity(intent);
            }
        });

    }
}