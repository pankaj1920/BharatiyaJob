package com.bharatiyajob.bharatiyajob.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginEntrNumResponse;
import com.bharatiyajob.bharatiyajob.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    Button ForgetOtpBtn;
    EditText forgetPasswordNum;
    String mobNum;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ForgetOtpBtn = findViewById(R.id.ForgetOtpBtn);
        forgetPasswordNum = findViewById(R.id.forgetPasswordNum);

        ForgetOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPassOtp();
            }
        });


    }


    private void forgetPassOtp() {
        mobNum = forgetPasswordNum.getText().toString();

        bundle = new Bundle();
        bundle.putString("mobNum", mobNum);

        if (mobNum.equals("") || mobNum.length() != 10){
            forgetPasswordNum.setText("Enter vaild number");
            forgetPasswordNum.requestFocus();
        }else {

            JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

            Call<LoginEntrNumResponse> call = jobApi.enterLoginNum((mobNum));
            call.enqueue(new Callback<LoginEntrNumResponse>() {
                @Override
                public void onResponse(Call<LoginEntrNumResponse> call, Response<LoginEntrNumResponse> response) {

                    LoginEntrNumResponse entrNumResponse = response.body();

                    if (response.isSuccessful() && entrNumResponse.getStatus().equals("1")) {
                        Toast.makeText(ForgotPassword.this, entrNumResponse.getData(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPassword.this, ChangePasswordActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ForgotPassword.this, entrNumResponse.getStatus() + "failed : ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginEntrNumResponse> call, Throwable t) {
                    Toast.makeText(ForgotPassword.this, t.getMessage() + " Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}