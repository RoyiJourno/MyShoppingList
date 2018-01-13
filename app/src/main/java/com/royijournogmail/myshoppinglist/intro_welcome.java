package com.royijournogmail.myshoppinglist;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class intro_welcome extends AppCompatActivity {

    private ViewPager mPager;
    private int[] layouts = {R.layout.silde_show_1_1,R.layout.slide_show_2,R.layout.slide_show_3,
            R.layout.slide_show_4,R.layout.slide_show_5};
    private MpagerAdapter mpagerAdapter;

    private LinearLayout Dots_Layout;
    private ImageView[] dots;

    private Button btSkip;

    private String start = "Start";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_welcome);

        mPager=findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);
        Dots_Layout = findViewById(R.id.dotsLayout);
        btSkip = findViewById(R.id.bnSkip);
        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHome();
            }
        });


        createDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);

                if(position==layouts.length-1)btSkip.setText(start);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final CheckedTextView ctv =findViewById(R.id.checkbox1);

        String u_id = FirebaseAuth.getInstance().getUid();
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(u_id).child("showUserGuide");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean showGuide = dataSnapshot.getValue(Boolean.class);
                    if (showGuide)
                        ctv.setChecked(false);
                    else
                        ctv.setChecked(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctv.isChecked()) {
                    ctv.setChecked(true);
                    myRef.setValue(true);
                }
                else {
                    ctv.setChecked(false);
                    myRef.setValue(false);
                }
            }
        });


    }

    private void createDots(int cur_position){
        if(Dots_Layout!=null)
             Dots_Layout.removeAllViews();
        dots = new ImageView[layouts.length];
        for(int i=0;i<layouts.length;i++){
            dots[i] = new ImageView(this);
            if(i == cur_position)
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            else
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            Dots_Layout.addView(dots[i],params);

        }

    }

    private void loadHome(){
        startActivity(new Intent(this,HomePage.class));
        finish();
    }
}
