package com.bharatiyajob.bharatiyajob.Company.HomePage.CandidateDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Company.GetCandidateDetails.GetCandidateDetaiResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateDetailActivity extends AppCompatActivity {

    TextView canDetName,CanDetState,canNam,canDetExperience,canDetAddress,canQualification,
            canDetSkil,candDetEmail,canDetNumber;
    String canDetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_detail);

        canDetName = findViewById(R.id.canDetName);
        CanDetState = findViewById(R.id.CanDetState);
        canNam = findViewById(R.id.canNam);
        canDetExperience = findViewById(R.id.canDetExperience);
        canDetAddress = findViewById(R.id.canDetAddress);
        canQualification = findViewById(R.id.canQualification);
        canDetSkil = findViewById(R.id.canDetSkil);
        candDetEmail = findViewById(R.id.candDetEmail);
        canDetNumber = findViewById(R.id.canDetNumber);

        Bundle bundle = getIntent().getExtras();
        canDetId = bundle.getString("canDetId");

        getCandidateDetails();
    }

    private void getCandidateDetails() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<GetCandidateDetaiResponse> call = jobApi.getCandidateDetail(canDetId);
        call.enqueue(new Callback<GetCandidateDetaiResponse>() {
            @Override
            public void onResponse(Call<GetCandidateDetaiResponse> call, Response<GetCandidateDetaiResponse> response) {

                GetCandidateDetaiResponse candidateDetaiResponse = response.body();

                if (response.isSuccessful() && candidateDetaiResponse.getStatus().equals("1")){

                    canDetName.setText(candidateDetaiResponse.getData().getName());
                    CanDetState.setText(candidateDetaiResponse.getData().getState());
                    canNam.setText(candidateDetaiResponse.getData().getName());
                    canDetExperience.setText(candidateDetaiResponse.getData().getWork_experience());
                    canDetAddress.setText(candidateDetaiResponse.getData().getAddress());
                    canQualification.setText(candidateDetaiResponse.getData().getHeighest_qualification());
                    canDetSkil.setText(candidateDetaiResponse.getData().getSkill());
                    candDetEmail.setText(candidateDetaiResponse.getData().getEmail());
                    canDetNumber.setText(candidateDetaiResponse.getData().getMobile());

                }else {
                    Toast.makeText(CandidateDetailActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCandidateDetaiResponse> call, Throwable t) {
                Toast.makeText(CandidateDetailActivity.this, "On Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}