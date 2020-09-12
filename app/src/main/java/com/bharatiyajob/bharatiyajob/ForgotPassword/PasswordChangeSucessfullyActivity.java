package com.bharatiyajob.bharatiyajob.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bharatiyajob.bharatiyajob.Login.LoginActivity;
import com.bharatiyajob.bharatiyajob.R;

public class PasswordChangeSucessfullyActivity extends AppCompatActivity {
    Button PassCngedLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change_sucessfully);

        PassCngedLoginBtn = findViewById(R.id.PassCngedLoginBtn);
        PassCngedLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordChangeSucessfullyActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}