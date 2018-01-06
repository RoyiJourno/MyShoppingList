package com.royijournogmail.myshoppinglist;

import android.content.Intent;
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
    final User[] new_user= new User [1];
    void update_user()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();
        ref.child(u_id).setValue(new_user[0]);
        Toast.makeText(getApplicationContext(), "good " , Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_list);





  //      final String[] u_name = new String[1];


        Button proAdd = findViewById(R.id.proAdd);



        proAdd.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                try {
                    EditText proName = findViewById(R.id.proName);
                    EditText proDesc = findViewById(R.id.proDesc);
                    final String productName = proName.getText().toString();
                    final String productDesc = proDesc.getText().toString();

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
                            new_user[0]=new User(u_name);
                            ///bring product
                            for (DataSnapshot child : children)
                            {
                                String name = child.child("p_name").getValue().toString();
 //                               Toast.makeText(getApplicationContext(), "Value is: " +name, Toast.LENGTH_SHORT).show();
                                String desc = child.child("p_description").getValue().toString();
  //                              Toast.makeText(getApplicationContext(), "Value is: " +name, Toast.LENGTH_SHORT).show();
                                Product p= new Product(name, null,desc);
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
                                ListForUser list_for_user=new ListForUser(name_l);
                                for (DataSnapshot child2 : children2) //products
                                {
                                    String name_p = child2.child("p_name").getValue().toString();
                                    String desc = child2.child("p_description").getValue().toString();
                                    Product p= new Product(name_p, null,desc);
                                    list_for_user.updateProdToList(p);

                                }
                                new_user[0].updateListToUser(list_for_user);

                            }



                            update_user();
 //                           DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
                            startActivity(new Intent(AddItemToList.this,updateList.class));
                            }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                });

                     /*   value.listOfProduct.add(p);
                        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
                        dref.child(u_id).setValue(value);*/
            }
            catch (Exception ex) {
                Toast.makeText(getApplicationContext(),ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();

            }
            }
        });
    }
}

