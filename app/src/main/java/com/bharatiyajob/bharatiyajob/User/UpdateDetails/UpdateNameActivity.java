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
import com.bharatiyajob.bharatiyajob.Json.UpdateCandidateProfile.UpdateUserName;
import com.bharatiyajob.bharatiyajob.ProfileSettingActivity;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateNameActivity extends AppCompatActivity {

EditText UUName;
Button UUNameBtn;
String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);
        UUName=findViewById(R.id.UUName);
        UUNameBtn=findViewById(R.id.UUNameBtn);

        getCanDetail();
    }

    public void Onclickuploadname(View view) {
        updateName();
    }

public void updateName(){
    String name=UUName.getText().toString();

    if (name.equals("")){
        UUName.setError("Enter Name");
        UUName.requestFocus();
        return;
    }

        if (!name.isEmpty()){
            Retrofit retrofit= BaseClient.getBaseClient();
            JobApi jobApi=retrofit.create(JobApi.class);

            Call<UpdateCandidateProfileResponse> call=jobApi.upDateUserName(userId,name);

            call.enqueue(new Callback<UpdateCandidateProfileResponse>() {
                @Override
                public void onResponse(Call<UpdateCandidateProfileResponse> call, Response<UpdateCandidateProfileResponse> response) {
                    UpdateCandidateProfileResponse profileResponse = response.body();

                    if (response.isSuccessful() && profileResponse.getError().equals("false")){
                        Toast.makeText(UpdateNameActivity.this, profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intentgotoprofilactivity=new Intent(UpdateNameActivity.this, ProfileSettingActivity.class);
                        startActivity(intentgotoprofilactivity);
                    }else {
                        Toast.makeText(UpdateNameActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<UpdateCandidateProfileResponse> call, Throwable t) {
                    Toast.makeText(UpdateNameActivity.this, "Failed to update name", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "please enter name to update", Toast.LENGTH_SHORT).show();
        }

}

    private void getCanDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        userId = loginOtpResponse.getId();
    }
}