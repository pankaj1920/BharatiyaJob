package com.bharatiyajob.bharatiyajob.User.Login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bharatiyajob.bharatiyajob.HomePage.HomePageActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginEntrNumResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterOtpActivity extends AppCompatActivity {

    EditText LoginOtpEditText;
    Button LoginVerifyOtp;
    String mobNum, otp;
    TextView resendOTP,DisableResendOtp,ResendCountDown;

    final static String Channel_name="bhartiyaJob";
    final public static String Channel_id="bhartyiaJob";
    final static String Channel_descvription="bhartyiaJob";
    String token;

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
        setContentView(R.layout.activity_enter_otp);

        LoginOtpEditText = findViewById(R.id.LoginOtpEditText);
        resendOTP = findViewById(R.id.resendOTP);
        DisableResendOtp = findViewById(R.id.DisableResendOtp);
        ResendCountDown = findViewById(R.id.ResendCountDown);

        Bundle bundle = getIntent().getExtras();
        mobNum = bundle.getString("logiId");


        // to generate the firebase token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            token=task.getResult().getToken();

                            Log.d("token",token);
                        }else{
                            Toast.makeText(EnterOtpActivity.this, "failed to generate", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        // Start Resend Timer
        startResendTimer();

        LoginVerifyOtp = findViewById(R.id.LoginVerifyOtp);

        LoginVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyLoginOtp();
            }
        });

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResendOtp();
            }
        });


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(Channel_id,Channel_name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(Channel_descvription);

            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }



    private void verifyLoginOtp() {


        otp = LoginOtpEditText.getText().toString();

        if (otp.equals("")){
            LoginOtpEditText.setError("Enter Otp");
            LoginOtpEditText.requestFocus();
            return;
        }
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<LoginOtpResponse> call = jobApi.mobilelLogin(mobNum,otp,token);

        call.enqueue(new Callback<LoginOtpResponse>() {
            @Override
            public void onResponse(Call<LoginOtpResponse> call, Response<LoginOtpResponse> response) {
                LoginOtpResponse loginOtpResponse = response.body();

                if (response.isSuccessful() ){

                    //if the login Responwe is sucessfull we will save the user
                    LoginDetailSharePref.getInstance(EnterOtpActivity.this).saveLoginDetails(loginOtpResponse);

                    Intent intent = new Intent(EnterOtpActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(EnterOtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
            

            @Override
            public void onFailure(Call<LoginOtpResponse> call, Throwable t) {
                Toast.makeText(EnterOtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(EnterOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void ResendOtp() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<LoginEntrNumResponse> call = jobApi.enterLoginNum((mobNum));
        call.enqueue(new Callback<LoginEntrNumResponse>() {
            @Override
            public void onResponse(Call<LoginEntrNumResponse> call, Response<LoginEntrNumResponse> response) {

                LoginEntrNumResponse entrNumResponse = response.body();

                if (response.isSuccessful() && entrNumResponse.getStatus().equals("1")) {
                    Toast.makeText(EnterOtpActivity.this, entrNumResponse.getData(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EnterOtpActivity.this, entrNumResponse.getStatus()+"failed : " + mobNum , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginEntrNumResponse> call, Throwable t) {
                Toast.makeText(EnterOtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(EnterOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                ResendCountDown.setText(timeLEftFormatted);
            }

            @Override
            public void onFinish() {

                // here timmer is finished
                TimmerRunning = false;
                // bcz we cannot start time again if timer is 0 we have to do reset
                DisableResendOtp.setVisibility(View.INVISIBLE);

                ResendCountDown.setVisibility(View.GONE);

                resendOTP.setVisibility(View.VISIBLE);

            }
        }.start();

        TimmerRunning = true;
    }

}