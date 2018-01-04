package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

public class listOfProduct extends AppCompatActivity {
    final ArrayList<Integer> indexOfSelectedItemd=new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_product);
        final ListView listview = (ListView)findViewById(R.id.listView1);
        FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();
        final ListView[] list=new ListView[1];
        list[0] = (ListView)findViewById(R.id.listView1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(u_id);


        ref.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();
                ArrayList<String> items= new ArrayList<String>();
                int count=1;
                for (DataSnapshot child : children)
                {
                    String name = child.child("p_name").getValue().toString();
                    String desc = child.child("p_description").getValue().toString();
                   String str=new String(count+". "+"Name: "+ name + "  |  Description: " + desc );
                   count++;


                    items.add(str);

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(listOfProduct.this, android.R.layout.simple_list_item_1, items);
                list[0].setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        (findViewById(R.id.createList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child(u_id);
                ref.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();
                        ArrayList<String> items= new ArrayList<String>();
                        int count=1;
                        bringUser(); ///new_user
                        for (DataSnapshot child : children)
                        {
                            if (selectedIndex(count))
                            {
                                String name = child.child("p_name").getValue().toString();
                                String desc = child.child("p_description").getValue().toString();
                                Product p= new Product(name, null,desc);
                            }

                            count++;


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        (findViewById(R.id.addItem)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listOfProduct.this,AddItemToList.class);
                startActivity(intent);
            }
        });



        list[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                indexOfSelectedItemd.add(position);
                Toast.makeText(getApplicationContext(), "Value is: " +view.getBackground().toString(), Toast.LENGTH_SHORT).show();
                /*if (view.getBackground().toString().equals("YELLOW"))
                {
                    view.setBackgroundColor(Color.WHITE);

                }
                else
                {
                    view.setBackgroundColor(Color.YELLOW);

                }*/


             /*   if(position==0)
                    Toast.makeText(getApplicationContext(), "Position :"+position+" ListItem : "
                            +id , Toast.LENGTH_LONG).show(); */


            }
        });
    }
    public boolean selectedIndex(int i)
    {
        for (Integer index :indexOfSelectedItemd)
        {
            if (index==i)
                return true;
        }
        return false;
    }
    final User[] new_user = new User[1];
    public void bringUser()
    {

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
                new_user[0] =new User(u_name);

                for (DataSnapshot child : children)
                {
                    String name = child.child("p_name").getValue().toString();
                     String desc = child.child("p_description").getValue().toString();
                    Product p= new Product(name, null,desc);
                    new_user[0].updateProdToUser(p);

                }
 ///               update_user();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
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
 /*  private void updateList(DataSnapshot dataSnapshot,ListView listview) {
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        HashMap<String, String> productList = new HashMap<>();
        for (DataSnapshot child : children) {
            String name = child.child("p_name").getValue().toString();
            String desc = child.child("p_description").getValue().toString();
            productList.put(name, desc);
        }
        List<HashMap<String, String>> listItems = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.activity_add_product);
        Iterator it = productList.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resMap.put("First", pair.getKey().toString());
            resMap.put("Second", pair.getValue().toString());
            listItems.add(resMap);
        }
        listview.setAdapter(adapter);
    } */
}
