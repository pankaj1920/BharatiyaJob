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

public class UpdateCompanyPassword extends AppCompatActivity {

    EditText UCPassword, UCPConfirmPassword;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company_password);

        getCompanyDetail();
        
        UCPassword = findViewById(R.id.UCPassword);
        UCPConfirmPassword = findViewById(R.id.UCPConfirmPassword);
    }

    public void updateComPassword(View view) {

        if (!UCPConfirmPassword.getText().toString().isEmpty()
                && !UCPassword.getText().toString().isEmpty()) {
            String password = UCPassword.getText().toString();
            String cnpassword = UCPConfirmPassword.getText().toString();

            if (password.equals(cnpassword)) {

                Retrofit retrofit = BaseClient.getBaseClient();
                JobApi jobApi = retrofit.create(JobApi.class);
                Call<UpdateCandidateProfileResponse> call = jobApi.upDateUserPassword(userId, password);

                call.enqueue(new Callback<UpdateCandidateProfileResponse>() {
                    @Override
                    public void onResponse(Call<UpdateCandidateProfileResponse> call, Response<UpdateCandidateProfileResponse> response) {

                        UpdateCandidateProfileResponse profileResponse = response.body();

                        if (response.isSuccessful() && profileResponse.getError().equals("false")) {
                            Toast.makeText(UpdateCompanyPassword.this, "successfully updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateCompanyPassword.this, CompanyProfileSetting.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(UpdateCompanyPassword.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<UpdateCandidateProfileResponse> call, Throwable t) {
                        Toast.makeText(UpdateCompanyPassword.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "password not matching", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }

    }

    private void getCompanyDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        userId = loginOtpResponse.getId();
    }
}