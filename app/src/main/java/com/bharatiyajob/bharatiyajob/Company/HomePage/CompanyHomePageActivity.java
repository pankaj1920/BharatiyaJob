package com.bharatiyajob.bharatiyajob.Company.HomePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.bharatiyajob.bharatiyajob.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CompanyHomePageActivity extends AppCompatActivity {

    BottomNavigationView companyBottomNav;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home_page);

        companyBottomNav = findViewById(R.id.companyBottomNav);
        navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        //Bottom navigation
        NavigationUI.setupWithNavController(companyBottomNav,navController);
    }
}