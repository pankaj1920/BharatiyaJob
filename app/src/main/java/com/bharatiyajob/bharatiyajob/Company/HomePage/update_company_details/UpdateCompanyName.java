package com.bharatiyajob.bharatiyajob.Company.HomePage.update_company_details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyProfile.CompanyProfileSetting;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.UpdateCandidateProfile.UpdateCandidateProfileResponse;
import com.bharatiyajob.bharatiyajob.ProfileSettingActivity;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateCompanyName extends AppCompatActivity {

    EditText UCName;
    String comId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company_name);

        getCompanyDetail();
        
        UCName = findViewById(R.id.UCName);
    }

    public void Onclickuploadname(View view) {

        String name=UCName.getText().toString();

        if (name.equals("")){
            UCName.setError("Enter Name");
            UCName.requestFocus();
            return;
        }

        if (!name.isEmpty()){
            Retrofit retrofit= BaseClient.getBaseClient();
            JobApi jobApi=retrofit.create(JobApi.class);

            Call<UpdateCandidateProfileResponse> call=jobApi.upDateComName(comId,name);

            call.enqueue(new Callback<UpdateCandidateProfileResponse>() {
                @Override
                public void onResponse(Call<UpdateCandidateProfileResponse> call, Response<UpdateCandidateProfileResponse> response) {
                    UpdateCandidateProfileResponse profileResponse = response.body();

                    if (response.isSuccessful() && profileResponse.getError().equals("false")){
                        Toast.makeText(UpdateCompanyName.this, profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intentgotoprofilactivity=new Intent(UpdateCompanyName.this, CompanyProfileSetting.class);
                        startActivity(intentgotoprofilactivity);
                    }else {
                        Toast.makeText(UpdateCompanyName.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<UpdateCandidateProfileResponse> call, Throwable t) {
                    Toast.makeText(UpdateCompanyName.this, "Failed to update name", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "please enter name to update", Toast.LENGTH_SHORT).show();
        }

    }

    private void getCompanyDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        comId = loginOtpResponse.getId();
    }
}