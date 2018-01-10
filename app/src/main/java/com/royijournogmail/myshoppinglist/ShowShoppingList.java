package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowShoppingList extends AppCompatActivity {
    ///list view
    private ListView lv;
    public static ArrayList<Model> modelArrayList;
    private CustomAdapter_shopping customAdapter;
    ArrayList<Product> prodList=new ArrayList<Product>();
    final User[] new_user = new User[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shopping_list);
        //read data
        SharedPreferences sp = getSharedPreferences("myshoppinglist", 0);
        final SharedPreferences.Editor sedt = sp.edit();
        final String index=sp.getString("list_id" , null);


        final FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(u_id);

        lv = (ListView) findViewById(R.id.listView);

        customAdapter = new CustomAdapter_shopping(this);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String u_name = dataSnapshot.child("name").getValue().toString();
                new_user[0] = new User(u_name);

                int indexInt=Integer.parseInt(index);
                Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();
                //bring user products

                    for (DataSnapshot child2: children) {
                        String name_p = child2.child("p_name").getValue().toString();
                        String desc = child2.child("p_description").getValue().toString();
                        String amount = child2.child("p_amount").getValue().toString();
                        Product p = new Product(name_p, null, desc, amount);
                        new_user[0].updateProdToUser(p);
                    }





            //////////bring lists
                int count=0;
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
                        int purchased=Integer.valueOf(child2.child("p_purchased").getValue().toString());
                        Product p = new Product(name_p, null, desc, amount,purchased);
                        list_for_user.updateProdToList(p);
                        ////////////////////listview
                        if (indexInt==count) {
                            String list_name=child.child("name").getValue().toString();
                            TextView targetText=(TextView) findViewById(R.id.textView) ;
                            targetText.setText("list: "+list_name );
                            prodList.add(p);
                        }
                    }
                    new_user[0].updateListToUser(list_for_user);
                    count++;
                }

                //listview

                modelArrayList = getModel();
                lv.setAdapter(customAdapter);


                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        int count=0;
                        for (Product iter:prodList)
                        {
                            if (count==position)
                            {
                                new_user[0].listOfLists.get(Integer.valueOf(index)).updatePurchasedInList(position);
                                if ( new_user[0].listOfLists.get(Integer.valueOf(index)).getPurchasedInList(position)==1)
                                {
                                    Toast.makeText(getApplicationContext(), "you bought "+iter.p_name, Toast.LENGTH_LONG).show();
                                }

                                //modelArrayList.get(position).updatePurchased();

                                break;
                            }
                            count++;
                        }
                        update_user();
                        recreate();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        findViewById(R.id.homeButton).setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth databaseAuth = FirebaseAuth.getInstance();

                Intent intent = new Intent(ShowShoppingList.this,HomePage.class);

                startActivity(intent);
            }
        });

        findViewById(R.id.sendButton).setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth databaseAuth = FirebaseAuth.getInstance();
                String listToSend="Darling, please buy:\n";

                int size = new_user[0].listOfLists.get(Integer.valueOf(index)).listOfProduct.size();
                int j=0;
                for(int i=0;i<size;i++){

                    if(new_user[0].listOfLists.get(Integer.valueOf(index)).listOfProduct.get(i).p_purchased==0){
                        j++;
                        Product p= new_user[0].listOfLists.get(Integer.valueOf(index)).listOfProduct.get(i);
                        listToSend += j+") "+ p.p_amount+" "+p.p_name;
                        if (!p.p_description.equals(""))
                        {
                            listToSend +="- "+p.p_description;
                        }
                        listToSend +="\n";
                    }
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, listToSend);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
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
            model.setNumber(Integer.parseInt(iter.p_amount));
            model.setProduct(iter.p_name);
            model.setproductDesc(iter.p_description);
            model.setPurchased(iter.p_purchased);
            list.add(model);
        }
        return list;
    }



}
