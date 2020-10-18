package com.bharatiyajob.bharatiyajob.CustomerCare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.CustomerCare.CustomerCareResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerCareActivity extends AppCompatActivity {
    EditText ContactName,ContatUsNumber,ContactUsEmail,ContactUsMessage;
    Button ContactSubmitBtn;
    String name,num,email,language,message,regType,customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_care);
        
        ContactName = findViewById(R.id.ContactName);
        ContatUsNumber = findViewById(R.id.ContatUsNumber);
        ContactUsEmail = findViewById(R.id.ContactUsEmail);
        ContactUsMessage = findViewById(R.id.ContactUsMessage);
        ContactSubmitBtn = findViewById(R.id.ContactSubmitBtn);

        Bundle bundle = getIntent().getExtras();
        customerId = bundle.getString("Id");
        regType = bundle.getString("regType");

        ContactSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerCare();
            }
        });
    }

    private void customerCare() {
        name = ContactName.getText().toString().trim();
        num = ContatUsNumber.getText().toString().trim();
        email = ContactUsEmail.getText().toString().trim();
        message = ContactUsMessage.getText().toString().trim();

        if (name.isEmpty() || name.equals("")){
            ContactName.setError("Enter Name");
            ContactName.requestFocus();
            return;
        }

        if (num.isEmpty() || num.equals("")){
            ContatUsNumber.setError("Enter Number");
            ContatUsNumber.requestFocus();
            return;
        }

        if (message.isEmpty() || message.equals("")){
            ContactUsMessage.setError("Enter Message");
            ContactUsMessage.requestFocus();
            return;
        }

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<CustomerCareResponse> call = jobApi.customerCare(num,name,email,message,language,regType,customerId);
        call.enqueue(new Callback<CustomerCareResponse>() {
            @Override
            public void onResponse(Call<CustomerCareResponse> call, Response<CustomerCareResponse> response) {
                CustomerCareResponse careResponse = response.body();
                if (response.isSuccessful() && careResponse.getStatus().equals("1")){
                    Toast.makeText(CustomerCareActivity.this, careResponse.getData() + " "+regType+"Id : "+customerId +" regType "+regType, Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("regType",regType);
                    Intent intent = new Intent(CustomerCareActivity.this,FeedbackSentActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    finish();
                }else {
                    Toast.makeText(CustomerCareActivity.this, "Toast", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerCareResponse> call, Throwable t) {
                Toast.makeText(CustomerCareActivity.this, "On Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
}