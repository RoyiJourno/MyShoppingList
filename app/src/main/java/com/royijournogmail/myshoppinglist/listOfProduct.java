package com.royijournogmail.myshoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import static java.lang.Thread.sleep;

public class listOfProduct extends AppCompatActivity {
    final ArrayList<Integer> indexOfSelectedItemd=new ArrayList<Integer>();
    final User[] new_user = new User[1];
    final Context context = this; ///////////for dialog box
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_product);
        final ListView listview = (ListView)findViewById(R.id.listView1);
        final FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();
        final ListView[] list=new ListView[1];
        list[0] = (ListView)findViewById(R.id.listView1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(u_id);

        final String[] name_for_list=new String[1]; ///////////for dialog box

        ref.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String u_name = dataSnapshot.child("name").getValue().toString();
                new_user[0] =new User(u_name);

                Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();
                ArrayList<String> items= new ArrayList<String>();
                int count=1;
                //bring user and update list view
                for (DataSnapshot child : children)
                {
                    String name = child.child("p_name").getValue().toString();
                    String desc = child.child("p_description").getValue().toString();
                   String str=new String(count+". "+"Name: "+ name + "  |  Description: " + desc );
                    Product p= new Product(name, null,desc);
                    new_user[0].updateProdToUser(p);
                   count++;
                    items.add(str);

                }

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
                        Product p= new Product(name_p, null,desc);
                        list_for_user.updateProdToList(p);

                    }
                    new_user[0].updateListToUser(list_for_user);

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
                //////////dialog box to enter name of list

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enter Name For List");
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name_for_list[0] = input.getText().toString();
                        // Toast.makeText(getApplicationContext(), "Hello, " +m_Text,
                        //         Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name_for_list[0] = "No Name";
                        dialog.cancel();
                    }
                });
                builder.show();

                ////end dialog box

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child(u_id);
                ref.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();
                        ArrayList<String> items= new ArrayList<String>();
                        int count=0;


                        ListForUser list_for_user=new ListForUser(name_for_list[0]);
                      //  list_for_user.name=name_for_list[0];
                        for (DataSnapshot child : children)
                        {
                            if (selectedIndex(count))
                            {
                                String name = child.child("p_name").getValue().toString();
                                String desc = child.child("p_description").getValue().toString();
                                Product p= new Product(name, null,desc);
                                list_for_user.updateProdToList(p);
                            }
                            count++;
                        } //for
                        new_user[0].updateListToUser(list_for_user);
                        update_user();

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
                view.setBackgroundColor(Color.parseColor("#ff6f69"));
            /*    if (((ColorDrawable)view.getBackground()).getColor()==Color.parseColor("#fff68f"))
                {
                    view.setBackgroundColor(Color.parseColor("#ff6f69"));

                }
                else
                {
                    view.setBackgroundColor(Color.parseColor("#fff68f"));

                } */


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
