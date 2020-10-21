package com.bharatiyajob.bharatiyajob.Company.HomePage.CompanyHomeFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;

import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.EnableDisableJobPost.EnableDisableJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.company_job_detail.CompanyJobDetailResponse;
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
        Call<CompanyJobDetailResponse> call = jobApi.getCompanyJobDetails(jobId,"1");
        call.enqueue(new Callback<CompanyJobDetailResponse>() {
            @Override
            public void onResponse(Call<CompanyJobDetailResponse> call, Response<CompanyJobDetailResponse> response) {

                CompanyJobDetailResponse companyJobDetailResponse = response.body();
                if (response.isSuccessful() && companyJobDetailResponse.getStatus().equals("1")){

                    if (companyJobDetailResponse.getData().getDisabled().equals("false")){
                        EnableJob.setVisibility(View.GONE);
                        DisableJob.setVisibility(View.VISIBLE);
                    }else if(companyJobDetailResponse.getData().getDisabled().equals("true")) {
                        EnableJob.setVisibility(View.VISIBLE);
                        DisableJob.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(CompanyJobDetailActivity.this, "Already applied status is something else", Toast.LENGTH_SHORT).show();
                    }

                    companyJobTitle.setText(companyJobDetailResponse.getData().getJob_title());
                    companyName.setText(companyJobDetailResponse.getData().getCompany_name());
                    compayJobLocation.setText(companyJobDetailResponse.getData().getLocation());


                            dateTime = companyJobDetailResponse.getData().getJob_reg_date().split((" "));
                            date = dateTime[0];
                            time = dateTime[1];
                    comJobPostedDate.setText(date);

                    comJobExperience.setText(companyJobDetailResponse.getData().getExperience());
                    comJobSalary.setText(companyJobDetailResponse.getData().getSalary());
                    comJobType.setText(companyJobDetailResponse.getData().getEmp_type());
                    comDescription.setText(companyJobDetailResponse.getData().getDescription());
                    comFunctionalArea.setText(companyJobDetailResponse.getData().getFunctional_area());
                    comIndustry.setText(companyJobDetailResponse.getData().getIndustry_type());
                    companyEmail.setText(companyJobDetailResponse.getData().getEmail());
                    comCompanyNumber.setText(companyJobDetailResponse.getData().getMobile());
                }else {
                    Toast.makeText(CompanyJobDetailActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CompanyJobDetailResponse> call, Throwable t) {
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
//        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();

        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(this);
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();
        companyId = loginOtpResponse.getId();
    }
}