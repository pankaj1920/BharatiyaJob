package com.bharatiyajob.bharatiyajob.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bharatiyajob.bharatiyajob.ForgotPassword.ForgotPassword;
import com.bharatiyajob.bharatiyajob.HomePage.HomePageActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPasswordActivity extends AppCompatActivity {

    String emailId,password;
    Button verifyLoginPassword;
    EditText loginPasswordEditTxt;
    TextView forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        verifyLoginPassword = findViewById(R.id.verifyLoginPassword);
        loginPasswordEditTxt = findViewById(R.id.loginPasswordEditTxt);
        forgetPassword = findViewById(R.id.forgetPassword);

        Bundle bundle = getIntent().getExtras();
        emailId = bundle.getString("logiId");

       verifyLoginPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               verifyPassword();
           }
       });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToForgetPassword();
                
            }
        });

    }

    private void verifyPassword() {
        password = loginPasswordEditTxt.getText().toString();

        if (password.equals("")){
            loginPasswordEditTxt.setError("Enter Password");
            loginPasswordEditTxt.requestFocus();
            return;
        }

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call <LoginOtpResponse> call = jobApi.emailLogin(emailId,password);

        call.enqueue(new Callback<LoginOtpResponse>() {
            @Override
            public void onResponse(Call<LoginOtpResponse> call, Response<LoginOtpResponse> response) {
                LoginOtpResponse loginOtpResponse = response.body();

                if (response.isSuccessful() && loginOtpResponse.getError().equals("false")){

                    //if the login Responwe is sucessfull we will save the user
                    LoginDetailSharePref.getInstance(LoginPasswordActivity.this).saveLoginDetails(loginOtpResponse);

                    Toast.makeText(LoginPasswordActivity.this, loginOtpResponse.getError(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginPasswordActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginPasswordActivity.this, loginOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginOtpResponse> call, Throwable t) {
                Toast.makeText(LoginPasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void goToForgetPassword() {
        Intent intent = new Intent(LoginPasswordActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

}