package com.bharatiyajob.bharatiyajob.User.CandidateNotification.recommended_job;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

public class RecommendedJobActivity extends AppCompatActivity {

    String userId;
    RecyclerView recomRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_job);

        recomRecycler = findViewById(R.id.recomRecycler);

        getCanDetail();
    }


    private void getCanDetail() {
        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(this);
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();

        userId = loginOtpResponse.getId();
    }
}