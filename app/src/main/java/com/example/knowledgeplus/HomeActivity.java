package com.example.knowledgeplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    ImageButton ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ib = (ImageButton) findViewById(R.id.publishButton);
        ib.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Start publish activity
                Intent intent = new Intent(HomeActivity.this, EmptyPublishActivity.class);
                startActivity(intent);
            }
        });

        MyViewPager viewPager = (MyViewPager) findViewById(R.id.viewPager);
        //ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter((new FragmentAdapter(getSupportFragmentManager(), HomeActivity.this)));

        TabLayout tabLayout  = (TabLayout) findViewById(R.id.tabbar);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.homeicon);
        tabLayout.getTabAt(1).setIcon(R.drawable.accounticon);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}