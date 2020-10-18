package com.bharatiyajob.bharatiyajob.CustomerCare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Company.HomePage.CompanyHomePageActivity;
import com.bharatiyajob.bharatiyajob.HomePage.HomePageActivity;
import com.bharatiyajob.bharatiyajob.R;

public class FeedbackSentActivity extends AppCompatActivity {

    Button feedBackOkBtn;
    String regType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_sent);

        Bundle bundle = getIntent().getExtras();
        regType = bundle.getString("regType");

        feedBackOkBtn = findViewById(R.id.feedBackOkBtn);

        feedBackOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomePage();
                Toast.makeText(FeedbackSentActivity.this, "regType"+regType, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToHomePage() {
        if (regType.equals("candidate")){
            Intent intent = new Intent(FeedbackSentActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }else if (regType.equals("company")){
            Intent intent = new Intent(FeedbackSentActivity.this, CompanyHomePageActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, " regType Is wrong", Toast.LENGTH_SHORT).show();
        }
    }
}