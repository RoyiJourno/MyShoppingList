package com.royijournogmail.myshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class listOfProduct extends AppCompatActivity   {
    final User[] new_user = new User[1];
    final Context context = this; ///////////for dialog box
    ///////listview
    private ListView lv;
    public static ArrayList<Model> modelArrayList;
    private CustomAdapter customAdapter;
    ArrayList<Product> prodList=new ArrayList<Product>();
    private static long amountOfChildren = 0;

    ///
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_product);

        final FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(u_id);

        final String[] name_for_list = new String[1]; ///////////for dialog box

        lv = (ListView) findViewById(R.id.lv);

        customAdapter = new CustomAdapter(this);


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String u_name = dataSnapshot.child("name").getValue().toString();
                new_user[0] = new User(u_name);

                amountOfChildren = dataSnapshot.child("listOfLists").getChildrenCount();

                Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();

                //bring user and update list view
                for (DataSnapshot child : children) {
                    String name = child.child("p_name").getValue().toString();
                    String desc = child.child("p_description").getValue().toString();
                    Product p = new Product(name, null, desc);
                    new_user[0].updateProdToUser(p);
                    ////////////////////listview
                    prodList.add(p);


                }
                //listview
                modelArrayList = getModel();
                lv.setAdapter(customAdapter);

                //////////bring lists
                Iterable<DataSnapshot> children_l = dataSnapshot.child("listOfLists").getChildren();
                for (DataSnapshot child : children_l) //list of lists
                {
                    Iterable<DataSnapshot> children2 = child.child("listOfProduct").getChildren();
                    String name_l = child.child("name").getValue().toString();
                    ListForUser list_for_user = new ListForUser(name_l);
                    for (DataSnapshot child2 : children2) //products
                    {
                        String name_p = child2.child("p_name").getValue().toString();
                        String desc = child2.child("p_description").getValue().toString();
                        String amount = child2.child("p_amount").getValue().toString();
                        String purchased=child2.child("p_purchased").getValue().toString();
                        Product p = new Product(name_p, null, desc, amount,purchased);
                        list_for_user.updateProdToList(p);

                    }
                    new_user[0].updateListToUser(list_for_user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        (findViewById(R.id.createList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////dialog box to enter name of list/////////////////////////////////////////////////
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Enter Name For List");
                //               final EditText input = new EditText(context);
                //               input.setInputType(InputType.TYPE_CLASS_TEXT);
                //               builder.setView(input);
                //               builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                //                   @Override
                //                   public void onClick(DialogInterface dialog, int which) {
                //                       name_for_list[0] = input.getText().toString();
                //                       // Toast.makeText(getApplicationContext(), "Hello, " +m_Text,
                //         Toast.LENGTH_SHORT).show();
                //                   }
                //               });
                //              builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                //                   @Override
                //                   public void onClick(DialogInterface dialog, int which) {
                //                       name_for_list[0] = "No Name";
                //                       dialog.cancel();
                //                   }
                //               });
                //              builder.show();

                ////end dialog box
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
                name_for_list[0] = date; ////////////////// delete when dialog box
                ListForUser list_for_user = new ListForUser(name_for_list[0]); //new list
                int count = 0;
                for (Model iter : modelArrayList) {
                    if (iter.getNumber() > 0) {
                        Product p = new Product(iter.getProduct(), null, iter.getproductDesc(), String.valueOf(iter.getNumber()));
                        list_for_user.updateProdToList(p);
                        count++;
                    }

                }
                if (count > 0) {
                    new_user[0].updateListToUser(list_for_user);
                    update_user();
                }
                //go to the last List
                SharedPreferences sp = getSharedPreferences("myshoppinglist", 0);
                final SharedPreferences.Editor sedt = sp.edit();
                sedt.putString("list_id",String.valueOf(amountOfChildren));
                sedt.commit();
                startActivity(new Intent(listOfProduct.this,ShowShoppingList.class));
            }
        });

        (findViewById(R.id.addItem)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listOfProduct.this, AddItemToList.class);
                startActivity(intent);
            }
        });
    }


    public void update_user() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();
        ref.child(u_id).setValue(new_user[0]);

    }

    private ArrayList<Model> getModel(){
        ArrayList<Model> list = new ArrayList<>();

        for (Product iter:prodList)
        {
            Model model = new Model();
            model.setNumber(0);
            model.setProduct(iter.p_name);
            model.setproductDesc(iter.p_description);
            model.setPurchased(iter.p_purchased);
            list.add(model);
        }
        return list;
    }

}
