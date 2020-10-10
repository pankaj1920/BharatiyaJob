package com.bharatiyajob.bharatiyajob;

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
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginEntrNumResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Register.RegVerifyOtpResponse;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterOtpActivity extends AppCompatActivity {

    EditText RegOtpEditText;
    Button RegVerifyOtp;
    String name,email,password,mobile,otp,canId;

    TextView RegResendCountDown,RegDisableResendOtp,RegResendOTP;

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
        setContentView(R.layout.activity_register_otp);

        RegOtpEditText = findViewById(R.id.RegOtpEditText);
        RegVerifyOtp = findViewById(R.id.RegVerifyOtp);
        RegResendCountDown = findViewById(R.id.RegResendCountDown);
        RegDisableResendOtp = findViewById(R.id.RegDisableResendOtp);
        RegResendOTP = findViewById(R.id.RegResendOTP);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        email = bundle.getString("email");
        password = bundle.getString("password");
        mobile = bundle.getString("mobile");
        canId = bundle.getString("canId");

        startResendTimer();

        RegVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });

        RegResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResendOtp();
            }
        });
    }

    private void verifyOtp() {
        otp = RegOtpEditText.getText().toString();

        if (otp.equals("")){
            RegOtpEditText.setError("Enter OTP");
            RegOtpEditText.requestFocus();
            return;
        }

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<RegVerifyOtpResponse> call = jobApi.regVerifyOtp(name,email,password,mobile,otp);
        call.enqueue(new Callback<RegVerifyOtpResponse>() {
            @Override
            public void onResponse(Call<RegVerifyOtpResponse> call, Response<RegVerifyOtpResponse> response) {
                RegVerifyOtpResponse verifyOtpResponse = response.body();

                if (response.isSuccessful() && verifyOtpResponse.getError().equals("false")){
                    Toast.makeText(RegisterOtpActivity.this, verifyOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("canId",canId);
                    Intent intent = new Intent(RegisterOtpActivity.this,UserMakeProfileActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(RegisterOtpActivity.this, verifyOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegVerifyOtpResponse> call, Throwable t) {
                Toast.makeText(RegisterOtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(RegisterOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void ResendOtp() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<LoginEntrNumResponse> call = jobApi.enterLoginNum((mobile));
        call.enqueue(new Callback<LoginEntrNumResponse>() {
            @Override
            public void onResponse(Call<LoginEntrNumResponse> call, Response<LoginEntrNumResponse> response) {

                LoginEntrNumResponse entrNumResponse = response.body();

                if (response.isSuccessful() && entrNumResponse.getStatus().equals("1")) {
                    Toast.makeText(RegisterOtpActivity.this, entrNumResponse.getData(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterOtpActivity.this, entrNumResponse.getStatus()+"failed : "  , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginEntrNumResponse> call, Throwable t) {
                Toast.makeText(RegisterOtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(RegisterOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // here wwe are give timer of two min after that resend Button will be visible
    public void startResendTimer() {
        RegDisableResendOtp.setVisibility(View.VISIBLE);

        RegResendCountDown.setVisibility(View.VISIBLE);

        RegResendOTP.setVisibility(View.GONE);

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
                RegResendCountDown.setText(timeLEftFormatted);
            }

            @Override
            public void onFinish() {

                // here timmer is finished
                TimmerRunning = false;
                // bcz we cannot start time again if timer is 0 we have to do reset
                RegDisableResendOtp.setVisibility(View.INVISIBLE);

                RegResendCountDown.setVisibility(View.GONE);

                RegResendOTP.setVisibility(View.VISIBLE);

            }
        }.start();

        TimmerRunning = true;
    }

}