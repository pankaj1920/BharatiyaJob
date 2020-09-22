package com.bharatiyajob.bharatiyajob.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.ForgetPassword.ChangePasswordResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginEntrNumResponse;
import com.bharatiyajob.bharatiyajob.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    Button ChangePasswordSubmit;
    EditText ChangePasswordEnterOTp,ChangePasswordPassword,ChangePasswordConfirmPassword;
    String fOtp,FChngPass,fConfirmPass,fNum;
    TextView ChangePasswordResendOtp,ChangePasswordDisableResendOtp,ChangePasswordResendCountDown;

    // starting time 2min
    private static long START_TIME_IN_MILLI = 20000; //120000;

    private CountDownTimer countDownTimer;

    // this will tell is timer is running or not
    private boolean TimmerRunning;

    // here we will start timeLeft intial with start timer
    private long TimeLeftInMillis = START_TIME_IN_MILLI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        Bundle bundle = getIntent().getExtras();
        fNum = bundle.getString("mobNum");

        startResendTimer();

        ChangePasswordSubmit = findViewById(R.id.ChangePasswordSubmit);
        ChangePasswordEnterOTp = findViewById(R.id.ChangePasswordEnterOTp);
        ChangePasswordPassword = findViewById(R.id.ChangePasswordPassword);
        ChangePasswordConfirmPassword = findViewById(R.id.ChangePasswordConfirmPassword);
        ChangePasswordResendOtp = findViewById(R.id.ChangePasswordResendOtp);
        ChangePasswordDisableResendOtp = findViewById(R.id.ChangePasswordDisableResendOtp);
        ChangePasswordResendCountDown = findViewById(R.id.ChangePasswordResendCountDown);


        ChangePasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        ChangePasswordResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResendOtp();
            }
        });
    }

    private void changePassword() {
        fOtp = ChangePasswordEnterOTp.getText().toString();
        FChngPass = ChangePasswordPassword.getText().toString();
        fConfirmPass = ChangePasswordConfirmPassword.getText().toString();

        if (FChngPass.equals(fConfirmPass)){

            JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
            Call<ChangePasswordResponse> call = jobApi.changePassword(fNum,fOtp,FChngPass,fConfirmPass);
            call.enqueue(new Callback<ChangePasswordResponse>() {
                @Override
                public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                    ChangePasswordResponse changePasswordResponse = response.body();

                    if (response.isSuccessful() && changePasswordResponse.getStatus().equals("1")){
                        Toast.makeText(ChangePasswordActivity.this, changePasswordResponse.getData(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordActivity.this,PasswordChangeSucessfullyActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(ChangePasswordActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                    Toast.makeText(ChangePasswordActivity.this, t.getMessage() + " Try Again", Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            ChangePasswordConfirmPassword.setError("Incorrect Password");
            ChangePasswordConfirmPassword.requestFocus();
        }

    }


    private void ResendOtp() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<LoginEntrNumResponse> call = jobApi.enterLoginNum((fNum));
        call.enqueue(new Callback<LoginEntrNumResponse>() {
            @Override
            public void onResponse(Call<LoginEntrNumResponse> call, Response<LoginEntrNumResponse> response) {

                LoginEntrNumResponse entrNumResponse = response.body();

                if (response.isSuccessful() && entrNumResponse.getStatus().equals("1")) {
                    Toast.makeText(ChangePasswordActivity.this, entrNumResponse.getData(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, entrNumResponse.getStatus()+"failed : " + fNum , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginEntrNumResponse> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // here wwe are give timer of two min after that resend Button will be visible
    public void startResendTimer() {

// here we have to give two  parameter 1st one is TimeLett and 2nd is mill second after which the onTick method is called
        countDownTimer = new CountDownTimer(TimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                TimeLeftInMillis = millisUntilFinished;

                // it will convert sec in minute
                int minute = (int) (TimeLeftInMillis / 1000) / 60;

                // here we will get remaining second after getting minute
                int second = (int) (TimeLeftInMillis / 1000) % 60;

                String timeLEftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minute, second);
                ChangePasswordResendCountDown.setText(timeLEftFormatted);
            }

            @Override
            public void onFinish() {

                // here timmer is finished
                TimmerRunning = false;
                // bcz we cannot start time again if timer is 0 we have to do reset
                ChangePasswordDisableResendOtp.setVisibility(View.INVISIBLE);

                ChangePasswordResendCountDown.setVisibility(View.GONE);

                ChangePasswordResendOtp.setVisibility(View.VISIBLE);

            }
        }.start();

        TimmerRunning = true;
    }

}