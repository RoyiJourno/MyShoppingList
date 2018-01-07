package com.royijournogmail.myshoppinglist;

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
        Toast.makeText(ShowShoppingList.this,"index:"+index,Toast.LENGTH_LONG).show() ;

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
                Iterable<DataSnapshot> children = dataSnapshot.child("listOfLists").getChildren();
                int count=0;
                //bring user and update list view
                for (DataSnapshot child : children) {

                    if (indexInt==count)
                    {
                        Iterable<DataSnapshot> children2 = child.child("listOfProduct").getChildren();
                        String list_name=child.child("name").getValue().toString();
                        TextView targetText=(TextView) findViewById(R.id.textView) ;
                        targetText.setText("list: "+list_name );
                        for (DataSnapshot child2 : children2) {
                            String name_p = child2.child("p_name").getValue().toString();
                            String desc = child2.child("p_description").getValue().toString();
                            String amount = child2.child("p_amount").getValue().toString();
                            String purchased=child2.child("p_purchased").getValue().toString();
                            Product p = new Product(name_p, null, desc, amount,purchased);
                            new_user[0].updateProdToUser(p);
                            ////////////////////listview
                            prodList.add(p);

                        }


                        break;
                    }
                    count++;
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
