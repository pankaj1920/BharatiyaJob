package com.bharatiyajob.bharatiyajob.User.UpdateDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class UpdatePasswordActivity extends AppCompatActivity {

    EditText UUPassword, UUPConfirmPassword;
    Button UUPasswordBtn;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        UUPassword = findViewById(R.id.UUPassword);
        UUPConfirmPassword = findViewById(R.id.UUPConfirmPassword);
        UUPasswordBtn = findViewById(R.id.UUPasswordBtn);

        getCanDetail();
    }

    public void updatePassword() {

        if (!UUPConfirmPassword.getText().toString().isEmpty()
                && !UUPassword.getText().toString().isEmpty()) {
            String password = UUPassword.getText().toString();
            String cnpassword = UUPConfirmPassword.getText().toString();

            if (password.equals(cnpassword)) {

                Retrofit retrofit = BaseClient.getBaseClient();
                JobApi jobApi = retrofit.create(JobApi.class);
//                String password=UUPConfirmPassword.getText().toString();
                Call<UpdateCandidateProfileResponse> call = jobApi.upDateUserPassword(userId, password);

                call.enqueue(new Callback<UpdateCandidateProfileResponse>() {
                    @Override
                    public void onResponse(Call<UpdateCandidateProfileResponse> call, Response<UpdateCandidateProfileResponse> response) {

                        UpdateCandidateProfileResponse profileResponse = response.body();

                        if (response.isSuccessful() && profileResponse.getError().equals("false")) {
                            Toast.makeText(UpdatePasswordActivity.this, "successfully updated", Toast.LENGTH_SHORT).show();
                            Intent intentgotoprofilactivity = new Intent(UpdatePasswordActivity.this, ProfileSettingActivity.class);
                            startActivity(intentgotoprofilactivity);
                            finish();

                        } else {
                            Toast.makeText(UpdatePasswordActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<UpdateCandidateProfileResponse> call, Throwable t) {
                        Toast.makeText(UpdatePasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "password not matching", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }


    }

    public void OnclickUpdatePasswowrd(View view) {
        updatePassword();
    }

    private void getCanDetail() {
//        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(this);
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();
        userId = loginOtpResponse.getId();
    }

}