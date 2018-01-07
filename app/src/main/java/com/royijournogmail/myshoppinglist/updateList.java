package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class updateList extends AppCompatActivity {
    private ListView lv;
    public static ArrayList<Model> modelArrayList;
    private CustomAdapter_update customAdapter;
    ArrayList<Product> prodList=new ArrayList<Product>();
    final User[] new_user = new User[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_list);
        customAdapter = new CustomAdapter_update(this);
        lv = (ListView) findViewById(R.id.lv);

        //bring products
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();
        DatabaseReference ref = database.getReference().child(u_id);

        ref.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String u_name = dataSnapshot.child("name").getValue().toString();
                new_user[0] =new User(u_name);

                Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();

                //bring user and update list view
                for (DataSnapshot child : children)
                {
                    String name = child.child("p_name").getValue().toString();
                    String desc = child.child("p_description").getValue().toString();
                    //String str=new String(count+". "+"Name: "+ name + "  |  Description: " + desc );
                    Product p= new Product(name, null,desc);
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
                    ListForUser list_for_user=new ListForUser(name_l);
                    for (DataSnapshot child2 : children2) //products
                    {
                        String name_p = child2.child("p_name").getValue().toString();
                        String desc = child2.child("p_description").getValue().toString();
                        String amount= child2.child("p_amount").getValue().toString();
                        Product p= new Product(name_p, null,desc,amount);
                        list_for_user.updateProdToList(p);

                    }
                    new_user[0].updateListToUser(list_for_user);
                }
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*(findViewById(R.id.refrash)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });*/

        (findViewById(R.id.addItem2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(updateList.this,AddItemToUpdateList.class));
            }
        });
    }

    public void updateItemInList(){
        recreate();
    }

    private ArrayList<Model> getModel(){
        ArrayList<Model> list = new ArrayList<>();

        for (Product iter:prodList)
        {
            Model model = new Model();
            model.setNumber(0);
            model.setProduct(iter.p_name);
            model.setproductDesc(iter.p_description);
            list.add(model);
        }
        return list;
    }

    public void update_user() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();
        ref.child(u_id).setValue(new_user[0]);

    }
}
