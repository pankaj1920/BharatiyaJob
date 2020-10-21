package com.bharatiyajob.bharatiyajob.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bharatiyajob.bharatiyajob.Company.HomePage.CompanyHomePageActivity;
import com.bharatiyajob.bharatiyajob.ForgotPassword.ForgotPassword;
import com.bharatiyajob.bharatiyajob.HomePage.HomePageActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    LoginPasswordActivity extends AppCompatActivity {

    String emailId,password,token;
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

        // to generate the firebase token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            token=task.getResult().getToken();

                            Log.d("token",token);
                        }else{
                            Toast.makeText(LoginPasswordActivity.this, "failed to generate", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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

        Toast.makeText(this, "Email : "+ emailId + "Pass : "+ password +" Token "+token, Toast.LENGTH_SHORT).show();

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call <LoginOtpResponse> call = jobApi.emailLogin(emailId,password,token);

        call.enqueue(new Callback<LoginOtpResponse>() {
            @Override
            public void onResponse(Call<LoginOtpResponse> call, Response<LoginOtpResponse> response) {
                LoginOtpResponse loginOtpResponse = response.body();

                if (response.isSuccessful() && loginOtpResponse.getStatus().equals("success")){

                    //if the login Responwe is sucessfull we will save the user
//                    LoginDetailSharePref.getInstance(LoginPasswordActivity.this).saveLoginDetails(loginOtpResponse);

                    LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(LoginPasswordActivity.this);
                    loginDetailSharePref.saveLoginDetails(loginOtpResponse);

                    if(loginOtpResponse.getReg_type().equals("company")){
                        Intent intent = new Intent(LoginPasswordActivity.this, CompanyHomePageActivity.class);
                        startActivity(intent);
                    }else if (loginOtpResponse.getReg_type().equals("candidate")){
                        Intent intent = new Intent(LoginPasswordActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginPasswordActivity.this, "Not company not candidate", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginPasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
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