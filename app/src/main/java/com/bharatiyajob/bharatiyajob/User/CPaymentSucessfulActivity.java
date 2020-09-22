package com.bharatiyajob.bharatiyajob.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bharatiyajob.bharatiyajob.HomePage.HomePageActivity;
import com.bharatiyajob.bharatiyajob.R;

public class CPaymentSucessfulActivity extends AppCompatActivity {

    Button paymentSucessBtn;
    TextView transactionID;
    String transId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_payment_sucessful);

        paymentSucessBtn = findViewById(R.id.paymentSucessBtn);
        transactionID = findViewById(R.id.transactionID);

        Bundle bundle = getIntent().getExtras();
        transId = bundle.getString("TransactionId");

        transactionID.setText(transId);

        paymentSucessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CPaymentSucessfulActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }
}