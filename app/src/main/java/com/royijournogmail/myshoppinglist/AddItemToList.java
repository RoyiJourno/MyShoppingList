package com.royijournogmail.myshoppinglist;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_list);

        EditText proName = findViewById(R.id.proName);
        EditText proDesc = findViewById(R.id.proDesc);


        final String productName = proName.getText().toString();
        final String productDesc = proDesc.getText().toString();


        Button proAdd = findViewById(R.id.proAdd);

        proAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD

=======
                try {
                    final Product p = new Product(productName, null, productDesc); // create product


//                FirebaseAuth userInfo = FirebaseAuth.getInstance();
//                  final String u_id = userInfo.getUid();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();



                // Attach a listener to read the data at our posts reference

                ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            for (DataSnapshot user :children ){
                                User u = user.getValue(User.class);
                                Toast.makeText(getApplicationContext(), "Value is: " + u.name, Toast.LENGTH_SHORT).show();
                            }

 //

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                });
                     /*   value.listOfProduct.add(p);
                        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
                        dref.child(u_id).setValue(value);*/
>>>>>>> 7f4e98e890e842278374436ca9a7641579e9e74f
            }
            catch (Exception ex) {
                Toast.makeText(getApplicationContext(),ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();

            }










            }
        });
    }
}

