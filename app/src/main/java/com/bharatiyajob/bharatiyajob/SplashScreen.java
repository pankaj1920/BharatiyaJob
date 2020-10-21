package com.bharatiyajob.bharatiyajob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Company.HomePage.CompanyHomePageActivity;
import com.bharatiyajob.bharatiyajob.HomePage.HomePageActivity;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Login.LoginActivity;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

public class SplashScreen extends AppCompatActivity {

    String regType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set splash screen as full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getCandidateDetail();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(SplashScreen.this);

                if (loginDetailSharePref.getInstance(SplashScreen.this).UserAlreadyLoggedIn()){
                    if (regType.equals("candidate")){
                        Toast.makeText(SplashScreen.this, "Home Page", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashScreen.this, HomePageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else if (regType.equals("company")){
                        Toast.makeText(SplashScreen.this, "Home Page", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashScreen.this, CompanyHomePageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(SplashScreen.this, regType + "RegType", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(SplashScreen.this, "Not a Comapny or Not a Candidate", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SplashScreen.this, "Login Activity", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2500);
    }

    private void getCandidateDetail() {
//        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(this);
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();

        regType = loginOtpResponse.getReg_type();
    }
}