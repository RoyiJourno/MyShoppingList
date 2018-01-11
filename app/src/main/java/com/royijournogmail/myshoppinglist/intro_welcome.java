package com.royijournogmail.myshoppinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class intro_welcome extends AppCompatActivity {

    private ViewPager mPager;
    private int[] layouts = {R.layout.silde_show_1_1,R.layout.slide_show_2,R.layout.slide_show_3,
            R.layout.slide_show_4,R.layout.slide_show_5};
    private MpagerAdapter mpagerAdapter;

    private LinearLayout Dots_Layout;
    private ImageView[] dots;

    private Button btNext,btSkip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_welcome);

        mPager=(ViewPager)findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);
        Dots_Layout = (LinearLayout)findViewById(R.id.dotsLayout);
        btNext = (Button)findViewById(R.id.bnNext);
        btSkip = (Button)findViewById(R.id.bnSkip);

        btNext.setVisibility(View.INVISIBLE);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHome();
            }
        });
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

                if(position==layouts.length-1){
                    btSkip.setVisibility(View.INVISIBLE);
                    btNext.setVisibility(View.VISIBLE);
                }
                else {
                    btNext.setVisibility(View.INVISIBLE);
                    btSkip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
