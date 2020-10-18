package com.bharatiyajob.bharatiyajob.Company.HomePage.CompanyJobList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobDetails.JobDetailsResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.EnableDisableJobPost.EnableDisableJobResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyJobDetailActivity extends AppCompatActivity {

    TextView companyJobTitle,companyName,compayJobLocation,comJobPostedDate,
            comJobExperience,comJobSalary,comJobType,comDescription,
            comFunctionalArea,comIndustry,companyEmail,comCompanyNumber;
    
    Button DisableJob,EnableJob;

    String [] dateTime;
    String date,time;
    String jobId,companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_job_detail);

        Bundle bundle = getIntent().getExtras();
        jobId = bundle.getString("comJdJobId");

        companyJobTitle = findViewById(R.id.companyJobTitle);
        companyName = findViewById(R.id.companyName);
        compayJobLocation = findViewById(R.id.compayJobLocation);
        comJobPostedDate = findViewById(R.id.comJobPostedDate);
        comJobExperience = findViewById(R.id.comJobExperience);
        comJobSalary = findViewById(R.id.comJobSalary);
        comJobType = findViewById(R.id.comJobType);
        comDescription = findViewById(R.id.comDescription);
        comFunctionalArea = findViewById(R.id.comFunctionalArea);
        comIndustry = findViewById(R.id.comIndustry);
        companyEmail = findViewById(R.id.companyEmail);
        comCompanyNumber = findViewById(R.id.comCompanyNumber);
        EnableJob = findViewById(R.id.EnableJob);
        DisableJob = findViewById(R.id.DisableJob);

        getJobDetails();

        getCompanyDetail();


        EnableJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableJobPost();
            }
        });
        
        DisableJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableJobPost();
            }
        });

    }


    private void getJobDetails() {

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<JobDetailsResponse> call = jobApi.getJobDetail(jobId,"1");
        call.enqueue(new Callback<JobDetailsResponse>() {
            @Override
            public void onResponse(Call<JobDetailsResponse> call, Response<JobDetailsResponse> response) {

                JobDetailsResponse jobDetailsResponse = response.body();
                if (response.isSuccessful() && jobDetailsResponse.getStatus().equals("1")){

                    companyJobTitle.setText(jobDetailsResponse.getData().getJob_title());
                    companyName.setText(jobDetailsResponse.getData().getCompany_name());
                    compayJobLocation.setText(jobDetailsResponse.getData().getLocation());


                            dateTime = jobDetailsResponse.getData().getJob_reg_date().split((" "));
                            date = dateTime[0];
                            time = dateTime[1];
                    comJobPostedDate.setText(date);

                    comJobExperience.setText(jobDetailsResponse.getData().getExperience());
                    comJobSalary.setText(jobDetailsResponse.getData().getSalary());
                    comJobType.setText(jobDetailsResponse.getData().getEmp_type());
                    comDescription.setText(jobDetailsResponse.getData().getDescription());
                    comFunctionalArea.setText(jobDetailsResponse.getData().getFunctional_area());
                    comIndustry.setText(jobDetailsResponse.getData().getIndustry_type());
                    companyEmail.setText(jobDetailsResponse.getData().getEmail());
                    comCompanyNumber.setText(jobDetailsResponse.getData().getMobile());
                }else {
                    Toast.makeText(CompanyJobDetailActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobDetailsResponse> call, Throwable t) {
                Toast.makeText(CompanyJobDetailActivity.this, "On Faiure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void disableJobPost() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<EnableDisableJobResponse> call = jobApi.disableJobPost(companyId,jobId);

        call.enqueue(new Callback<EnableDisableJobResponse>() {
            @Override
            public void onResponse(Call<EnableDisableJobResponse> call, Response<EnableDisableJobResponse> response) {
                EnableDisableJobResponse jobResponse = response.body();
                if (response.isSuccessful() && jobResponse.getStatus().equals("1")){
                    Toast.makeText(CompanyJobDetailActivity.this, jobResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    EnableJob.setVisibility(View.VISIBLE);
                    DisableJob.setVisibility(View.GONE);
                }else {
                    Toast.makeText(CompanyJobDetailActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EnableDisableJobResponse> call, Throwable t) {
                Toast.makeText(CompanyJobDetailActivity.this, "On Faiure"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enableJobPost() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<EnableDisableJobResponse> call = jobApi.enableJobPost(companyId,jobId);

        call.enqueue(new Callback<EnableDisableJobResponse>() {
            @Override
            public void onResponse(Call<EnableDisableJobResponse> call, Response<EnableDisableJobResponse> response) {
                EnableDisableJobResponse jobResponse = response.body();
                if (response.isSuccessful() && jobResponse.getStatus().equals("1")){
                    Toast.makeText(CompanyJobDetailActivity.this, "Job Disabled Successfully", Toast.LENGTH_SHORT).show();
                    EnableJob.setVisibility(View.GONE);
                    DisableJob.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(CompanyJobDetailActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EnableDisableJobResponse> call, Throwable t) {
                Toast.makeText(CompanyJobDetailActivity.this, "On Faiure"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCompanyDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this
        ).getDetail();

        companyId = loginOtpResponse.getId();
    }
}