package com.bharatiyajob.bharatiyajob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bharatiyajob.bharatiyajob.Company.CompanyRegistrationActivity;

public class RegisterAsActivity extends AppCompatActivity {

    Button registerCandidate,registerCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

        registerCandidate = findViewById(R.id.registerCandidate);

        registerCompany = findViewById(R.id.registerCompany);

        registerCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterAsActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        registerCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterAsActivity.this,CompanyRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}