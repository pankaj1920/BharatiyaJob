package com.bharatiyajob.bharatiyajob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bharatiyajob.bharatiyajob.User.Login.LoginActivity;

public class RegistrationSucessfulActivity extends AppCompatActivity {

    Button RegContinueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_sucessful);

        RegContinueBtn = findViewById(R.id.RegContinueBtn);

        RegContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationSucessfulActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}