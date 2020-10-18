package com.bharatiyajob.bharatiyajob.HomePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.ApplyJob.ApplyJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobDetails.JobDetailsData;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobDetails.JobDetailsResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailActivity extends AppCompatActivity {

    String [] dateTime;
    String date,time;
    TextView JdJobTitle, JdCompanyName, JdJobLocation, JbJobPostedDate, JdExperience,jdCompanyEmail,
            JdSalary, JdJobType, JdDescription, JbFunctionalArea, JdIndustry, jdCompanyNumber;
    String jobId;
    ConstraintLayout jobDetailMainLayout;
    ShimmerFrameLayout jobDetailSimmerLayout;
    String userId,jobAlreadyApplied;
    Button jobDetailApply,jobDetailApplied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        JdJobTitle = findViewById(R.id.JdJobTitle);
        JdCompanyName = findViewById(R.id.JdCompanyName);
        JdJobLocation = findViewById(R.id.JdJobLocation);
        JdJobType = findViewById(R.id.JdJobType);
        JdSalary = findViewById(R.id.JdSalary);
        JbJobPostedDate = findViewById(R.id.JbJobPostedDate);
        JdExperience = findViewById(R.id.JdExperience);
        JdDescription = findViewById(R.id.JdDescription);
        JbFunctionalArea = findViewById(R.id.JbFunctionalArea);
        JdIndustry = findViewById(R.id.JdIndustry);
        jdCompanyNumber = findViewById(R.id.jdCompanyNumber);
        jdCompanyEmail = findViewById(R.id.jdCompanyEmail);
        jobDetailMainLayout = findViewById(R.id.jobDetailMainLayout);
        jobDetailSimmerLayout = findViewById(R.id.jobDetailSimmerLayout);
        jobDetailApply = findViewById(R.id.jobDetailApply);
        jobDetailApplied = findViewById(R.id.jobDetailApplied);

        getCandidateDetail();

        jobDetailSimmerLayout.startShimmer();

        Bundle bundle = getIntent().getExtras();
        jobId = bundle.getString("jobId");

        getJobDetails();

        jobDetailApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JobDetailActivity.this, "can id : "+userId +" JobId : "+jobId, Toast.LENGTH_SHORT).show();

                ApplyJob();
            }
        });
    }

    private void getJobDetails() {

        final JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<JobDetailsResponse> call = jobApi.getJobDetail(jobId,userId);

        call.enqueue(new Callback<JobDetailsResponse>() {
            @Override
            public void onResponse(Call<JobDetailsResponse> call, Response<JobDetailsResponse> response) {

                JobDetailsResponse jobDetailsResponse = response.body();

                if (jobDetailsResponse != null) {
                    if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code() && jobDetailsResponse.getStatus().equals("1")) {

                        jobAlreadyApplied = jobDetailsResponse.getData().getAlready_applied();
                        jobDetailSimmerLayout.stopShimmer();
                        jobDetailSimmerLayout.setVisibility(View.GONE);
                        jobDetailMainLayout.setVisibility(View.VISIBLE);

                        if (jobDetailsResponse.getData().getAlready_applied().equals("false")){
                            jobDetailApply.setVisibility(View.VISIBLE);
                            jobDetailApplied.setVisibility(View.GONE);
                        }else if(jobDetailsResponse.getData().getAlready_applied().equals("true")) {
                            jobDetailApply.setVisibility(View.GONE);
                            jobDetailApplied.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(JobDetailActivity.this, "Already applied status is something else", Toast.LENGTH_SHORT).show();
                        }

                            dateTime = jobDetailsResponse.getData().getJob_reg_date().split((" "));
                            date = dateTime[0];
                            time = dateTime[1];

                            JdJobTitle.setText(jobDetailsResponse.getData().getJob_title());
                            JdJobLocation.setText(jobDetailsResponse.getData().getLocation());
                            JdJobType.setText(jobDetailsResponse.getData().getEmp_type());
                            JdCompanyName.setText(jobDetailsResponse.getData().getCompany_name());
                            JdSalary.setText(jobDetailsResponse.getData().getSalary());
                            JbJobPostedDate.setText(date);
                            JdExperience.setText(jobDetailsResponse.getData().getExperience());
                            JdDescription.setText(jobDetailsResponse.getData().getDescription());
                            JbFunctionalArea.setText(jobDetailsResponse.getData().getFunctional_area());
                            JdIndustry.setText(jobDetailsResponse.getData().getIndustry_type());
                            jdCompanyNumber.setText(jobDetailsResponse.getData().getIndustry_type());
                            jdCompanyEmail.setText(jobDetailsResponse.getData().getIndustry_type());

                        Toast.makeText(JobDetailActivity.this, "Already Applied : "+ jobAlreadyApplied , Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(JobDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JobDetailsResponse> call, Throwable t) {
                Toast.makeText(JobDetailActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ApplyJob() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<ApplyJobResponse> call = jobApi.applyJob(userId,jobId);
        call.enqueue(new Callback<ApplyJobResponse>() {
            @Override
            public void onResponse(Call<ApplyJobResponse> call, Response<ApplyJobResponse> response) {
                ApplyJobResponse applyJobResponse = response.body();
                if (response.isSuccessful() && applyJobResponse.getStatus().equals("1")){
                    Toast.makeText(JobDetailActivity.this, applyJobResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    jobDetailApplied.setVisibility(View.VISIBLE);
                    jobDetailApply.setVisibility(View.GONE);
                }else {
                    Toast.makeText(JobDetailActivity.this, applyJobResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApplyJobResponse> call, Throwable t) {
                Toast.makeText(JobDetailActivity.this, "On Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCandidateDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();

        userId = loginOtpResponse.getId();
    }

}