package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class previousLists extends AppCompatActivity {
    ///list view
    private ListView lv;
    public static ArrayList<Model_lists> modelArrayList;
    private CustomAdapter_lists customAdapter;
    ArrayList<List> ListList=new ArrayList<List>();

    final User[] new_user = new User[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_lists);
        //final ListView listview = (ListView) findViewById(R.id.lv);

        final FirebaseAuth userInfo = FirebaseAuth.getInstance();
        final String u_id = userInfo.getUid();

        //save data
        SharedPreferences sp = getSharedPreferences("myshoppinglist", 0);
        final SharedPreferences.Editor sedt = sp.edit();

        //final ListView[] list = new ListView[1];
        //list[0] = (ListView) findViewById(R.id.lv);

        lv = (ListView) findViewById(R.id.lv);

        customAdapter = new CustomAdapter_lists(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(u_id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.child("listOfLists").getChildren();
                //ArrayList<String> items = new ArrayList<String>();
                boolean list_empty=true;
                for (DataSnapshot child : children) {
                    String name = child.child("name").getValue().toString();
                    int buyAll=1;
                    Iterable<DataSnapshot> children2 = child.child("listOfProduct").getChildren();
                    for (DataSnapshot child2 : children2) //products
                    {
                        int purchased = Integer.valueOf(child2.child("p_purchased").getValue().toString());
                        if (purchased == 0)  buyAll=0;
                    }
                    List l=new List(name,buyAll);
                    ListList.add(l);
                    list_empty=false;
                }
                if(!list_empty)
                {
                    modelArrayList = getModel();
                    lv.setAdapter(customAdapter);
                }
                else
                {
                    TextView targetText=(TextView) findViewById(R.id.textView) ;
                    targetText.setText("No lists found");
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                sedt.putString("list_id" , String.valueOf(position));
                sedt.commit();
               // Toast.makeText(getApplicationContext(), "id: "+id+"pos: "+position, Toast.LENGTH_LONG).show();


                Intent intent = new Intent(previousLists.this,ShowShoppingList.class);
                startActivity(intent);


            }
        });

        findViewById(R.id.homeButton).setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth databaseAuth = FirebaseAuth.getInstance();

                Intent intent = new Intent(previousLists.this,HomePage.class);

                startActivity(intent);
            }
        });


    }

    private ArrayList<Model_lists> getModel(){
        ArrayList<Model_lists> list = new ArrayList<>();

        for (List iter:ListList)
        {
            Model_lists model = new Model_lists();
            model.setNameList(iter.nameList);
            model.setBuyAll(iter.buyAll);
           list.add(model);
        }
        return list;
    }
}
