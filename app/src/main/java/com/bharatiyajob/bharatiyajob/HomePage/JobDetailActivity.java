package com.bharatiyajob.bharatiyajob.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobDetails.JobDetailsData;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobDetails.JobDetailsResponse;
import com.bharatiyajob.bharatiyajob.R;

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

        Bundle bundle = getIntent().getExtras();
        jobId = bundle.getString("jobId");

        getJobDetails();
    }

    private void getJobDetails() {

        final JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<JobDetailsResponse> call = jobApi.getJobDetail("1");

        call.enqueue(new Callback<JobDetailsResponse>() {
            @Override
            public void onResponse(Call<JobDetailsResponse> call, Response<JobDetailsResponse> response) {

                JobDetailsResponse jobDetailsResponse = response.body();

                if (jobDetailsResponse != null) {
                    if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code() && jobDetailsResponse.getStatus().equals("1")) {

                        List<JobDetailsData> detailsData = jobDetailsResponse.getData();

                        for (JobDetailsData data : detailsData) {

                            dateTime = data.getJob_reg_date().split((" "));
                            date = dateTime[0];
                            time = dateTime[1];

                            JdJobTitle.setText(data.getJob_title());
                            JdJobLocation.setText(data.getLocation());
                            JdJobType.setText(data.getEmp_type());
                            JdCompanyName.setText(data.getCompany_name());
                            JdSalary.setText(data.getSalary());
                            JbJobPostedDate.setText(date);
                            JdExperience.setText(data.getExperience());
                            JdDescription.setText(data.getDescription());
                            JbFunctionalArea.setText(data.getFunctional_area());
                            JdIndustry.setText(data.getIndustry_type());
                            jdCompanyNumber.setText(data.getIndustry_type());
                            jdCompanyEmail.setText(data.getIndustry_type());

                        }

                        Toast.makeText(JobDetailActivity.this, jobId, Toast.LENGTH_SHORT).show();
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
}