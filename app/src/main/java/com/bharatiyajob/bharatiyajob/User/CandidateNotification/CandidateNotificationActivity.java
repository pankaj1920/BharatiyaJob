package com.bharatiyajob.bharatiyajob.User.CandidateNotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.bharatiyajob.bharatiyajob.R;
import com.google.android.material.tabs.TabLayout;

public class CandidateNotificationActivity extends AppCompatActivity {

    TabLayout canNotificationTabLayout;
    ViewPager canNotificationViewPager;
    CanNotificationAdapter canNotificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_notification);

        canNotificationTabLayout = findViewById(R.id.canNotificationTabLayout);
        canNotificationViewPager = findViewById(R.id.canNotificationViewPager);

        canNotificationTabLayout.addTab(canNotificationTabLayout.newTab().setText("Notification"));
        canNotificationTabLayout.addTab(canNotificationTabLayout.newTab().setText("Your Job Alert"));

        canNotificationAdapter = new CanNotificationAdapter(getSupportFragmentManager(),canNotificationTabLayout.getTabCount());
        canNotificationViewPager.setAdapter(canNotificationAdapter);

        canNotificationViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(canNotificationTabLayout));

        canNotificationTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                canNotificationViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}