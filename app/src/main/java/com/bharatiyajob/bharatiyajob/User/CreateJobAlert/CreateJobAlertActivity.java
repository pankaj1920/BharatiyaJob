package com.bharatiyajob.bharatiyajob.User.CreateJobAlert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.HomePage.HomePageActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.CreateJobAlert.CreateJobAlertResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateJobAlertActivity extends AppCompatActivity {
    EditText nameOfAlert,AlertSkill,AlertLocation,AlertExperience,
            AlertSalary,AlertFunctionalArea,AlertIndustry;
    Button createAlertBtn;
    String userId;
    String AlertName,Skill,location,experience,salary,functionalArea,Industry;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_alert);

        nameOfAlert = findViewById(R.id.nameOfAlert);
        AlertSkill = findViewById(R.id.AlertSkill);
        AlertLocation = findViewById(R.id.AlertLocation);
        AlertExperience = findViewById(R.id.AlertExperience);
        AlertSalary = findViewById(R.id.AlertSalary);
        AlertFunctionalArea = findViewById(R.id.AlertFunctionalArea);
        AlertIndustry = findViewById(R.id.AlertIndustry);
        createAlertBtn = findViewById(R.id.createAlertBtn);

        getCanDetail();

        createAlertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createJobAlert();
            }
        });
        
    }

    private void createJobAlert() {

        AlertName = nameOfAlert.getText().toString();
        Skill = AlertSkill.getText().toString();
        location = AlertLocation.getText().toString();
        experience = AlertExperience.getText().toString();
        salary = AlertSalary.getText().toString();
        functionalArea = AlertFunctionalArea.getText().toString();
        Industry = AlertIndustry.getText().toString();


        if (AlertName.equals("")){
            nameOfAlert.setError("Enter Name");
            nameOfAlert.requestFocus();
            return;
        }

        if (Skill.equals("")){
            AlertSkill.setError("Enter Skil");
            AlertSkill.requestFocus();
            return;
        }

        if (location.equals("")){
            AlertExperience.setError("Enter Skil");
            AlertExperience.requestFocus();
            return;
        }

        if (salary.equals("")){
            AlertSalary.setError("Enter Skil");
            AlertSalary.requestFocus();
            return;
        }

        if (functionalArea.equals("")){
            AlertFunctionalArea.setError("Enter Skil");
            AlertFunctionalArea.requestFocus();
            return;
        }

        if (Industry.equals("")){
            AlertIndustry.setError("Enter Skil");
            AlertIndustry.requestFocus();
            return;
        }

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<CreateJobAlertResponse> call = jobApi.createJobAlert(userId,AlertName,Skill,location,experience,salary,functionalArea,Industry);
        call.enqueue(new Callback<CreateJobAlertResponse>() {
            @Override
            public void onResponse(Call<CreateJobAlertResponse> call, Response<CreateJobAlertResponse> response) {
                CreateJobAlertResponse jobAlertResponse = response.body();
                if (response.isSuccessful() && jobAlertResponse.getStatus().equals("1")){
                    Toast.makeText(CreateJobAlertActivity.this, jobAlertResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateJobAlertActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(CreateJobAlertActivity.this, "Unscess to create job alert", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateJobAlertResponse> call, Throwable t) {
                Toast.makeText(CreateJobAlertActivity.this, "onFailure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCanDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        userId = loginOtpResponse.getId();
    }
}